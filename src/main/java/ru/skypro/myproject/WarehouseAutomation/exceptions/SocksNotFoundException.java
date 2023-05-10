package ru.skypro.myproject.WarehouseAutomation.exceptions;

public class SocksNotFoundException extends RuntimeException {
    public SocksNotFoundException(Long socksId) {
        super("Socks with ID " + socksId + " not found.");
    }
}
