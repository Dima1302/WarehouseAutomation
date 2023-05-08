package ru.skypro.myproject.WarehouseAutomation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;

import java.util.Optional;

public interface SocksRepository extends JpaRepository<Socks,Integer> {
    Optional<Object> findById(Long id);
}
