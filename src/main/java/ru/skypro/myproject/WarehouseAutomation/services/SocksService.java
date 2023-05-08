package ru.skypro.myproject.WarehouseAutomation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.myproject.WarehouseAutomation.exceptions.SocksNotFoundException;
import ru.skypro.myproject.WarehouseAutomation.models.Socks;
import ru.skypro.myproject.WarehouseAutomation.repositories.SocksRepository;
import ru.skypro.myproject.WarehouseAutomation.repositories.WarehouseRepository;

@Service
public class SocksService {

    private final SocksRepository socksRepository;

    @Autowired
    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    public void addSocks(Socks socks) {
        socksRepository.save(socks);
    }

    public void deleteSocks(Long socksId) {
        socksRepository.deleteById(Math.toIntExact(socksId));
    }

    public void updateSocksQuantity(Long socksId, int quantity) {
        Socks socks = socksRepository.findById(Math.toIntExact(socksId))
                .orElseThrow(() -> new SocksNotFoundException(socksId));
        socks.setQuantity(quantity);
        socksRepository.save(socks);
    }
}
