package ru.skypro.myproject.WarehouseAutomation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;
import ru.skypro.myproject.WarehouseAutomation.repositories.WarehouseRepository;


import java.util.Map;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    public void registerIncome(String color, int cottonPart, int quantity) {
        Warehouse warehouse = warehouseRepository.findAll().get(0);
        Map<String, Integer> socksByColor = warehouse.getSocksByColor();
        Map<String, Integer> socksByCottonPart = warehouse.getSocksByCottonPart();
        updateSocksByColor(color, quantity, socksByColor);
        updateSocksByCottonPart(cottonPart, quantity, socksByCottonPart);
        warehouse.setSocksByColor(socksByColor);
        warehouse.setSocksByCottonPart(socksByCottonPart);
        warehouseRepository.save(warehouse);
    }

    public void registerOutcome(String color, int cottonPart, int quantity) {
        Warehouse warehouse = warehouseRepository.findAll().get(0);
        Map<String, Integer> socksByColor = warehouse.getSocksByColor();
        Map<String, Integer> socksByCottonPart = warehouse.getSocksByCottonPart();
        updateSocksByColor(color, -quantity, socksByColor);
        updateSocksByCottonPart(cottonPart, -quantity, socksByCottonPart);
        warehouse.setSocksByColor(socksByColor);
        warehouse.setSocksByCottonPart(socksByCottonPart);
        warehouseRepository.save(warehouse);
    }

    public int getSocksCountByColor(String color) {
        Warehouse warehouse = warehouseRepository.findAll().get(0);
        return warehouse.getSocksByColor().getOrDefault(color, 0);
    }

    public int getSocksCountByCottonPart(int cottonPart) {
        Warehouse warehouse = warehouseRepository.findAll().get(0);
        return warehouse.getSocksByCottonPart().getOrDefault(String.valueOf(cottonPart), 0);
    }

    public void updateSocksByColor(String color, int quantity, Map<String, Integer> socksByColor) {
        socksByColor.merge(color, quantity, Integer::sum);
    }

    public void updateSocksByCottonPart(int cottonPart, int quantity, Map<String, Integer> socksByCottonPart) {
        socksByCottonPart.merge(String.valueOf(cottonPart), quantity, Integer::sum);
    }

    public Warehouse createWarehouse(Warehouse warehouse){
        warehouseRepository.save(warehouse);
        return warehouse;
    }
}
