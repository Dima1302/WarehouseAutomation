package ru.skypro.myproject.WarehouseAutomation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.skypro.myproject.WarehouseAutomation.exceptions.SocksNotFoundException;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;
import ru.skypro.myproject.WarehouseAutomation.repositories.SocksRepository;
import ru.skypro.myproject.WarehouseAutomation.services.SocksService;



@Controller
@RequestMapping("/socks")
public class SocksController {
    private final SocksService socksService;
    private final SocksRepository socksRepository;

    public SocksController(SocksService socksService, SocksRepository socksRepository) {
        this.socksService = socksService;
        this.socksRepository = socksRepository;
    }

    @PostMapping
    public ResponseEntity<?> addSocks(@RequestBody Socks socks) {
        socksService.addSocks(socks);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSocks(@PathVariable("id") Long socksId) {
        socksService.deleteSocks(socksId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSocksQuantity(@PathVariable("id") Long socksId, @RequestBody int quantity) {
        socksService.updateSocksQuantity(socksId, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Socks> getSocksById(@PathVariable Long id) {
        Socks socks = (Socks) socksRepository.findById(id)
                .orElseThrow(() -> new SocksNotFoundException(id));
        return ResponseEntity.ok(socks);
    }
}
