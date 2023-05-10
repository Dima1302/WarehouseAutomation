package ru.skypro.myproject.WarehouseAutomation;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.myproject.WarehouseAutomation.controllers.SocksController;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;


import static org.hamcrest.Matchers.any;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import ru.skypro.myproject.WarehouseAutomation.repositories.SocksRepository;
import ru.skypro.myproject.WarehouseAutomation.repositories.WarehouseRepository;
import static org.mockito.ArgumentMatchers.*;

import java.util.*;




@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class SocksControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SocksRepository socksRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private SocksController socksController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(socksController).build();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllSocks() throws Exception {
        List<Socks> socks = new ArrayList<>();
        socks.add(new Socks(1, "black", 80, 20));
        socks.add(new Socks(2, "white", 70, 10));
        when(socksRepository.findAll()).thenReturn(socks);

        mockMvc.perform(get("/api/socks"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }


    @Test
    void getSocksByWarehouseIdAndColorAndCottonPart() throws Exception {
        Warehouse warehouse = new Warehouse();
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        List<Socks> socks = new ArrayList<>();
        socks.add(new Socks(1, "black", 80, 20));
        when(socksRepository.findAllByWarehouseAndColorAndCottonPart(warehouse, "black", 80)).thenReturn(socks);

        mockMvc.perform(get("/api/socks")
                        .param("warehouseId", "1")
                        .param("color", "black")
                        .param("cottonPart", "80"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    void increaseQuantityOfSocks() throws Exception {
        Warehouse warehouse = new Warehouse();
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        Socks socks = new Socks(1, "black", 80, 20);
        when(socksRepository.findAllByWarehouseAndColorAndCottonPart(eq(warehouse), eq("black"), eq(80))).thenReturn(Collections.singletonList(socks));
        when(socksRepository.save((Socks) any(Socks.class))).thenReturn(socks);

        mockMvc.perform(post("/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(Collections.singletonMap("color", "black")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity").value(40));
    }

    @Test
    void decreaseQuantityOfSocks() throws Exception {
        Warehouse warehouse = new Warehouse();
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        Socks socks = new Socks(1, "black", 80, 20);
        when(socksRepository.findAllByWarehouseAndColorAndCottonPart((Warehouse) any(Warehouse.class), anyString(), anyInt()))
                .thenReturn(Collections.singletonList(socks));


        mockMvc.perform(put("/api/warehouses/1/socks/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(socks)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(19));
    }
}





