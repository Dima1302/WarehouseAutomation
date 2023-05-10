package ru.skypro.myproject.WarehouseAutomation;

import com.example.demo.model.Warehouse;
import com.example.demo.repository.WarehouseRepository;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.skypro.myproject.WarehouseAutomation.controllers.WarehouseController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.skypro.myproject.WarehouseAutomation.SocksControllerTest.asJsonString;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class WarehouseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseController warehouseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(warehouseController).build();
    }

    @Test
    void getAllWarehouses() throws Exception {
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(new Warehouse(1L));
        warehouses.add(new Warehouse(2L));
        when(warehouseRepository.findAll()).thenReturn(warehouses);

        mockMvc.perform(get("/api/warehouses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getWarehouseById() throws Exception {
        Warehouse warehouse = new Warehouse(1L);
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        mockMvc.perform(get("/api/warehouses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse(1L);
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        mockMvc.perform(post("/api/warehouses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(warehouse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse(1L);
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        mockMvc.perform(put("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(warehouse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse(1L);
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        doNothing().when(warehouseRepository).deleteById(anyLong());

        mockMvc.perform(delete("/api/warehouses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void addSocksToWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse(1L);
        Socks socks = new Socks(1, "black", 80, 20);

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
        Warehouse warehouse = new Warehouse(1L);
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