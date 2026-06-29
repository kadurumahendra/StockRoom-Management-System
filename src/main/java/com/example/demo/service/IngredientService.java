package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public String checkLowStock(Long ingredientId) {

        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);

        if (ingredient == null) {
            return "Ingredient not found";
        }

        Double currentStock = ingredient.getQuantity();

        if (currentStock < ingredient.getParLevel()) {
            return "LOW STOCK ALERT";
        }

        return "Stock is sufficient";
    }
    
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        Ingredient existing = ingredientRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(ingredient.getName());
            existing.setQuantity(ingredient.getQuantity());
            existing.setUnit(ingredient.getUnit());
            existing.setParLevel(ingredient.getParLevel());

            return ingredientRepository.save(existing);
        }

        return null;
    }

    public String deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
        return "Ingredient deleted successfully";
    }
    
    
  
}