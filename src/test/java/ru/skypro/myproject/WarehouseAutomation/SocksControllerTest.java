package ru.skypro.myproject.WarehouseAutomation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.myproject.WarehouseAutomation.controllers.SocksController;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;
import ru.skypro.myproject.WarehouseAutomation.repositories.SocksRepository;
import ru.skypro.myproject.WarehouseAutomation.services.SocksService;

import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;




@RunWith(SpringRunner.class)
@WebMvcTest(SocksController.class)
public class SocksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SocksService socksService;

    @MockBean
    private SocksRepository socksRepository;
    @InjectMocks
    private SocksController socksController;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(socksController).build();
    }


    @Test
    void testAddSocks() throws Exception {
        // Create a Socks object
        Socks socks = new Socks();
        socks.setColor("blue");
        socks.setCottonPart(0);
        socks.setQuantity(10);


        ObjectMapper objectMapper = new ObjectMapper();
        String socksJson = objectMapper.writeValueAsString(socks);


        mockMvc.perform(post("/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(socksJson))
                .andExpect(status().isOk());


        verify(socksService).addSocks(eq(socks));
    }



    @Test
    public void testDeleteSocks() throws Exception {
        Long socksId = 1L;

        mockMvc.perform(delete("/socks/{id}", socksId))
                .andExpect(status().isOk());

        verify(socksService, times(1)).deleteSocks(socksId);
    }

    @Test
    public void testUpdateSocksQuantity() throws Exception {
        Long socksId = 1L;
        int quantity = 5;

        mockMvc.perform(patch("/socks/{id}", socksId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(quantity)))
                .andExpect(status().isOk());

        verify(socksService, times(1)).updateSocksQuantity(socksId, quantity);
    }


    @Test
    public void testGetSocksById() throws Exception {
        Long socksId = 1L;
        Socks socks = new Socks();
        socks.setColor("blue");
        socks.setQuantity(10);

        when(socksRepository.findById(anyLong())).thenReturn(Optional.of(socks));


        mockMvc.perform(post("/socks/income")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(socks)));





        verify(socksRepository, times(1)).findById(anyLong());
    }


    // Метод для преобразования объекта в JSON-строку
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
