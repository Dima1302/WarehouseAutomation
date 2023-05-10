package ru.skypro.myproject.WarehouseAutomation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;
import ru.skypro.myproject.WarehouseAutomation.models.Warehouse;

import java.util.List;
import java.util.Optional;

public interface SocksRepository extends JpaRepository<Socks,Integer> {
    Optional<Object> findById(Long id);
    List<Socks> findAllByWarehouseAndColorAndCottonPart(Warehouse warehouse, String color, int cottonPart);
    Socks save(Socks socks);

}
