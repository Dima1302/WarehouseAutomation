package ru.skypro.myproject.WarehouseAutomation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;


import ru.skypro.myproject.WarehouseAutomation.services.WarehouseService;

import java.util.UUID;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }
   @PostMapping("/income")
    public ResponseEntity<?> registerIncome(@RequestParam String color,
                                            @RequestParam int cottonPart,
                                            @RequestParam int quantity) {
        warehouseService.registerIncome(color, cottonPart, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/outcome")
    public ResponseEntity<?> registerOutcome(@RequestParam String color,
                                             @RequestParam int cottonPart,
                                             @RequestParam int quantity) {
        warehouseService.registerOutcome(color, cottonPart, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/socks/color/{color}")
    public ResponseEntity<Integer> getSocksCountByColor(@PathVariable String color) {
        int count = warehouseService.getSocksCountByColor(color);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/socks/cottonpart/{cottonPart}")
    public ResponseEntity<Integer> getSocksCountByCottonPart(@PathVariable int cottonPart) {
        int count = warehouseService.getSocksCountByCottonPart(cottonPart);
        return ResponseEntity.ok(count);
    }


    @PostMapping("/warehouses")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);
        UUID id = UUID.randomUUID(); // Генерация уникального идентификатора
        createdWarehouse.setId(id.getMostSignificantBits()); // Присваивание числового значения UUID
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWarehouse);
    }





}
