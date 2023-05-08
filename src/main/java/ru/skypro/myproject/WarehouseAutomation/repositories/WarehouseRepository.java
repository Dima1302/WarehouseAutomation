package ru.skypro.myproject.WarehouseAutomation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}
