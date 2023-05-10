package ru.skypro.myproject.WarehouseAutomation;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.myproject.WarehouseAutomation.controllers.WarehouseController;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;
import ru.skypro.myproject.WarehouseAutomation.repositories.WarehouseRepository;
import ru.skypro.myproject.WarehouseAutomation.repositories.SocksRepository;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



class WarehouseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseController warehouseController;
    @Mock
    private SocksRepository socksRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(warehouseController).build();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllWarehouses() throws Exception {
        // Arrange
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(new Warehouse());
        warehouses.add(new Warehouse());
        when(warehouseRepository.findAll()).thenReturn(warehouses);

        // Act & Assert
        mockMvc.perform(get("/api/warehouses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getWarehouseById() throws Exception {
        // Arrange
        Warehouse warehouse = new Warehouse();
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        // Act & Assert
        mockMvc.perform(get("/api/warehouses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void createWarehouse() throws Exception {
        // Arrange
        Warehouse warehouse = new Warehouse();
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        // Act & Assert
        mockMvc.perform(post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(warehouse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void updateWarehouse() throws Exception {
        // Arrange
        Warehouse warehouse = new Warehouse();
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        // Act & Assert
        mockMvc.perform(put("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(warehouse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void deleteWarehouse() throws Exception {
        // Arrange
        Warehouse warehouse = new Warehouse();
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        doNothing().when(warehouseRepository).deleteById(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addSocksToWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse();
        Socks socks = new Socks(Math.toIntExact(1L), "black", 80, 20);

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(socksRepository.save(any(Socks.class))).thenReturn(socks);

        mockMvc.perform(post("/api/warehouses/1/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(socks)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void removeSocksFromWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse();
        Socks socks = new Socks(1, "black", 80, 20);

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(socksRepository.findAllByWarehouseAndColorAndCottonPart(eq(warehouse), eq("black"), eq(80)))
                .thenReturn(Collections.singletonList(socks));
        mockMvc.perform(delete("/api/warehouses/1/socks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(socks)))
                .andExpect(status().isOk());

    }
}
