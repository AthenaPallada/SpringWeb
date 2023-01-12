package me.tatiana.springweb.ipml;

import me.tatiana.springweb.model.Recipe;
import me.tatiana.springweb.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static Map<Long, Recipe> recipes = new TreeMap<>();
    private static long recipeId = 1;

    @Override
    public void addRecipe(Recipe recipe) {
        recipes.put(recipeId++, recipe);
    }

    @Override
    public Recipe getRecipe(long key) {
        return recipes.get(key);
    }
}
