package ru.skypro.myproject.WarehouseAutomation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterIncome() throws Exception {
        String color = "white";
        int cottonPart = 50;
        int quantity = 10;
        mockMvc.perform(post("/warehouse/income")
                        .param("color", color)
                        .param("cottonPart", String.valueOf(cottonPart))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterOutcome() throws Exception {
        String color = "white";
        int cottonPart = 50;
        int quantity = 5;
        mockMvc.perform(post("/warehouse/outcome")
                        .param("color", color)
                        .param("cottonPart", String.valueOf(cottonPart))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSocksCountByColor() throws Exception {
        String color = "white";
        mockMvc.perform(get("/warehouse/socks/color/" + color))
                .andExpect(status().isOk())
                .andExpect(content().string("10")); // expected number of socks in warehouse
    }

    @Test
    void testGetSocksCountByCottonPart() throws Exception {
        int cottonPart = 50;
        mockMvc.perform(get("/warehouse/socks/cottonpart/" + cottonPart))
                .andExpect(status().isOk())
                .andExpect(content().string("5")); // expected number of socks in warehouse
    }
}

