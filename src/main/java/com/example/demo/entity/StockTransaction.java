package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_transactions")
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ingredientId;
    private Long storeId;

    private String transactionType; // RECEIVE, ISSUE, WASTE, ADJUST
    private Double quantity;

    public StockTransaction() {
    }

    public StockTransaction(Long id, Long ingredientId, Long storeId, String transactionType, Double quantity) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.storeId = storeId;
        this.transactionType = transactionType;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}