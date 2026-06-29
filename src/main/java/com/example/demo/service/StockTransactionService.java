package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.StockTransaction;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.StockTransactionRepository;

@Service
public class StockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public StockTransaction saveTransaction(StockTransaction transaction) {

        Ingredient ingredient = ingredientRepository
                .findById(transaction.getIngredientId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        if (transaction.getTransactionType().equalsIgnoreCase("RECEIVE")) {
            ingredient.setQuantity(
                ingredient.getQuantity() + transaction.getQuantity()
            );
        }
        else if (transaction.getTransactionType().equalsIgnoreCase("ISSUE")) {
            ingredient.setQuantity(
                ingredient.getQuantity() - transaction.getQuantity()
            );
        }
        else if (transaction.getTransactionType().equalsIgnoreCase("WASTE")) {
            ingredient.setQuantity(
                ingredient.getQuantity() - transaction.getQuantity()
            );
        }

        ingredientRepository.save(ingredient);

        return stockTransactionRepository.save(transaction);
    }

    public List<StockTransaction> getAllTransactions() {
        return stockTransactionRepository.findAll();
    }

    public Double calculateStock(Long ingredientId) {

        List<StockTransaction> transactions =
                stockTransactionRepository.findByIngredientId(ingredientId);

        double stock = 0;

        for (StockTransaction tx : transactions) {

            if (tx.getTransactionType().equalsIgnoreCase("RECEIVE")) {
                stock += tx.getQuantity();
            }
            else if (tx.getTransactionType().equalsIgnoreCase("ISSUE")) {
                stock -= tx.getQuantity();
            }
            else if (tx.getTransactionType().equalsIgnoreCase("WASTE")) {
                stock -= tx.getQuantity();
            }
            else if (tx.getTransactionType().equalsIgnoreCase("ADJUST")) {
                stock += tx.getQuantity();
            }
        }

        return stock;
    }

    public StockTransaction updateTransaction(Long id, StockTransaction transaction) {
        transaction.setId(id);
        return stockTransactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        stockTransactionRepository.deleteById(id);
    }
}