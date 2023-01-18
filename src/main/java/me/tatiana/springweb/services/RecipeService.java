package me.tatiana.springweb.services;

import me.tatiana.springweb.model.Recipe;

import java.util.Map;

public interface  RecipeService {
    long addRecipe(Recipe recipe);
    Recipe getRecipe(long key);
    Recipe editRecipe(Recipe recipe, long id);
    boolean removeRecipe(long id);
    Map<Long, Recipe> getAllRecipes();
    Map<Long, Recipe> getRecipesByIngredientId(Long id1, Long id2);
    Map<Long, Recipe> getListOfRecipes(byte page);
}
