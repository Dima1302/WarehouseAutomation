package ru.skypro.myproject.WarehouseAutomation.models;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public List<Socks> getSocksList() {
        return socksList;
    }



    public void setSocksList(List<Socks> socksList) {
        this.socksList = socksList;
    }

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Socks> socksList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Integer> getSocksByColor() {
        return socksByColor;
    }

    public void setSocksByColor(Map<String, Integer> socksByColor) {
        this.socksByColor = socksByColor;
    }

    public Map<String, Integer> getSocksByCottonPart() {
        return socksByCottonPart;
    }

    public void setSocksByCottonPart(Map<String, Integer> socksByCottonPart) {
        this.socksByCottonPart = socksByCottonPart;
    }

    @ElementCollection
    private Map<String, Integer> socksByColor = new HashMap<>();

    @ElementCollection
    private Map<String, Integer> socksByCottonPart = new HashMap<>();

}
