package ru.skypro.myproject.WarehouseAutomation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ru.skypro.myproject.WarehouseAutomation.models.Socks;

import static org.hamcrest.Matchers.is;



@SpringBootTest
@AutoConfigureMockMvc
public class SocksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllSocks() throws Exception {
        mockMvc.perform(get("/api/socks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$[1].color", is(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].cottonPart", is(80)))
                .andExpect((ResultMatcher) jsonPath("$[0].quantity", is(100)))
                .andExpect((ResultMatcher) jsonPath("$[1].id", is(2)))
                .andExpect((ResultMatcher) jsonPath("$[1].color", is(0)))
                .andExpect((ResultMatcher) jsonPath("$[1].cottonPart", is(70)))
                .andExpect((ResultMatcher) jsonPath("$[1].quantity", is(200)));
    }

    @Test
    void testGetSocksByColorAndCottonPart() throws Exception {
        String color = "white";
        int cottonPart = 80;

        mockMvc.perform(get("/api/socks")
                        .param("color", color)
                        .param("cottonPart", String.valueOf(cottonPart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].color", is(0)))
                .andExpect((ResultMatcher) jsonPath("$[0].cottonPart", is(80)))
                .andExpect((ResultMatcher) jsonPath("$[0].quantity", is(100)));
    }

    @Test
    void testAddSocks() throws Exception {
        Socks socks = new Socks(1, "red", 50, 50);

        mockMvc.perform(post("/api/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(socks)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(3)))
                .andExpect((ResultMatcher) jsonPath("$.color", is(Integer.parseInt("red"))))
                .andExpect((ResultMatcher) jsonPath("$.cottonPart", is(50)))
                .andExpect((ResultMatcher) jsonPath("$.quantity", is(50)));
    }

    @Test
    void testUpdateSocks() throws Exception {
        Socks socks = new Socks(1, "red", 50, 50);

        mockMvc.perform(put("/api/socks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(socks)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$.color", is(Integer.parseInt("red"))))
                .andExpect((ResultMatcher) jsonPath("$.cottonPart", is(50)))
                .andExpect((ResultMatcher) jsonPath("$.quantity", is(50)));
    }

    @Test
    void testDeleteSocks() throws Exception {
        mockMvc.perform(delete("/api/socks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void testAddSocksToWarehouse() throws Exception {
        Socks socks = new Socks(1, "red", 50, 50);
        Warehouse warehouse = new Warehouse();

        mockMvc.perform(post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouse)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)));

        mockMvc.perform(post("/api/warehouses/1/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(socks)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$.color", is(Integer.parseInt("red"))))
                .andExpect((ResultMatcher) jsonPath("$.cottonPart", is(50)))
                .andExpect((ResultMatcher) jsonPath("$.quantity", is(50)));
    }

    @Test
    void testGetSocksInWarehouse() throws Exception {
        Socks socks1 = new Socks(1, "white", 80, 50);
        Socks socks2 = new Socks(2, "black", 70, 100);

        Warehouse warehouse = new Warehouse();
        warehouse.addSocks(socks1);
        warehouse.addSocks(socks2);

        mockMvc.perform(post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouse)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(1)));

        mockMvc.perform(get("/api/warehouses/1/socks")
                        .param("color", "white")
                        .param("operation", "lessThan")
                        .param("cottonPart", "90"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].color", is(0)))

                .andExpect((ResultMatcher) jsonPath("$[0].cottonPart", is(80)))
                .andExpect((ResultMatcher) jsonPath("$[0].quantity", is(50)));
    }


}



