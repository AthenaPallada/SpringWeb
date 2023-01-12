package me.tatiana.springweb.services;

import me.tatiana.springweb.model.Recipe;

public interface  RecipeService {
    void addRecipe(Recipe recipe);

    Recipe getRecipe(long key);

}
