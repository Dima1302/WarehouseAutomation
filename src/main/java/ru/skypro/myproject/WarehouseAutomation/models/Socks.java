package ru.skypro.myproject.WarehouseAutomation.models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "Socks")
public class Socks {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "color")
    private String color;
    @Column(name = "cottonPart")
    private int cottonPart;
    @Column(name = "quantity")
    private int quantity;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    public Socks(int id, String color, int cottonPart, int quantity) {
        this.id = id;
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public Socks() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(int cottonPart) {
        this.cottonPart = cottonPart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Socks{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", cottonPart=" + cottonPart +
                ", quantity=" + quantity +
                '}';
    }
}
