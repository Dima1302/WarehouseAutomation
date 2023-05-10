package ru.skypro.myproject.WarehouseAutomation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.skypro.myproject.WarehouseAutomation.controllers.WarehouseController;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;
import ru.skypro.myproject.WarehouseAutomation.services.WarehouseService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@WebMvcTest(WarehouseController.class)
public class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseService warehouseService;

    @Test
    public void testRegisterIncome() throws Exception {
        mockMvc.perform(post("/warehouse/income")
                        .param("color", "red")
                        .param("cottonPart", "80")
                        .param("quantity", "100"))
                .andExpect(status().isOk());

        // Дополнительная проверка вызова соответствующего метода сервиса
        verify(warehouseService, times(1)).registerIncome("red", 80, 100);
    }

    @Test
    public void testRegisterOutcome() throws Exception {
        mockMvc.perform(post("/warehouse/outcome")
                        .param("color", "blue")
                        .param("cottonPart", "70")
                        .param("quantity", "50"))
                .andExpect(status().isOk());

        // Дополнительная проверка вызова соответствующего метода сервиса
        verify(warehouseService, times(1)).registerOutcome("blue", 70, 50);
    }

    @Test
    public void testGetSocksCountByColor() throws Exception {
        int socksCount = 50;

        when(warehouseService.getSocksCountByColor("red")).thenReturn(socksCount);

        mockMvc.perform(get("/warehouse/socks/color/{color}", "red"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(socksCount)));
    }

    @Test
    public void testCreateWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse();

        when(warehouseService.createWarehouse(any(Warehouse.class))).thenReturn(warehouse);

        mockMvc.perform(post("/warehouse/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(warehouse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty()); // Обновленное ожидание

        // Дополнительная проверка вызова соответствующего метода сервиса
        verify(warehouseService, times(1)).createWarehouse(any(Warehouse.class));
    }


    // Вспомогательный метод для преобразования объекта в JSON-строку
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
