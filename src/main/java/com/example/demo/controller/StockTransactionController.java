package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.StockTransaction;
import com.example.demo.service.StockTransactionService;

@RestController
@RequestMapping("/transactions")
public class StockTransactionController {

    @Autowired
    private StockTransactionService stockTransactionService;

    @PostMapping
    public StockTransaction addTransaction(@RequestBody StockTransaction transaction) {
        return stockTransactionService.saveTransaction(transaction);
    }

    @GetMapping
    public List<StockTransaction> getAllTransactions() {
        return stockTransactionService.getAllTransactions();
    }

    @GetMapping("/stock/{ingredientId}")
    public Double getCurrentStock(@PathVariable Long ingredientId) {
        return stockTransactionService.calculateStock(ingredientId);
    }
    
    @PutMapping("/{id}")
    public StockTransaction updateTransaction(@PathVariable Long id, @RequestBody StockTransaction transaction) {
        return stockTransactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        stockTransactionService.deleteTransaction(id);
        return "Transaction Deleted";
    }
}