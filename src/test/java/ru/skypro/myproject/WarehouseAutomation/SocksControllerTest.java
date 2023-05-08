package ru.skypro.myproject.WarehouseAutomation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SocksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllSocks() throws Exception {
        mockMvc.perform(get("/socks"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSocksByColorAndCottonPart() throws Exception {
        String color = "white";
        int cottonPart = 50;

        mockMvc.perform(get("/socks")
                        .param("color", color)
                        .param("cottonPart", String.valueOf(cottonPart)))
                .andExpect(status().isOk());
    }

    @Test
    void testAddSocks() throws Exception {
        Socks socks = new Socks(1,"Red",50,50);

        mockMvc.perform(post("/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(socks)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSocks() throws Exception {
        Socks socks = new Socks(1,"Red",50,50);

        mockMvc.perform(put("/socks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(socks)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSocks() throws Exception {
        mockMvc.perform(delete("/socks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSocksInWarehouse() throws Exception {
        mockMvc.perform(get("/warehouses/1/socks"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddSocksToWarehouse() throws Exception {
        Socks socks = new Socks(1,"white",50,65);

        mockMvc.perform(post("/warehouses/1/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(socks)))
                .andExpect(status().isOk());
    }
}


