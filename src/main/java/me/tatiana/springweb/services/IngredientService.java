package me.tatiana.springweb.services;

import me.tatiana.springweb.model.Ingredient;

import java.util.Map;

public interface IngredientService {
    long addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long key);

    Ingredient editIngredient(Ingredient ingredient, long id);

    boolean removeIngredient(long id);

    Map<Long, Ingredient> getAllIngredients();
}