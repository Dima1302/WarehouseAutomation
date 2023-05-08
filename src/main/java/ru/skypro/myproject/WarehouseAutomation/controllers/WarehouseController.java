package ru.skypro.myproject.WarehouseAutomation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.skypro.myproject.WarehouseAutomation.services.WarehouseService;

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
}
