package me.tatiana.springweb.services;

import me.tatiana.springweb.model.Ingredient;

public interface IngredientService {
    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long key);
}