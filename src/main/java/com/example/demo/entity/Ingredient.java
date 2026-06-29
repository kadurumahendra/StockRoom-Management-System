package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double quantity;
    private String unit;
    private Double parLevel;

    public Ingredient() {
    }

    public Ingredient(Long id, String name, Double quantity, String unit, Double parLevel) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.parLevel = parLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getParLevel() {
        return parLevel;
    }

    public void setParLevel(Double parLevel) {
        this.parLevel = parLevel;
    }
}