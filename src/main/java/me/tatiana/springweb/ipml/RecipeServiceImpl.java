package me.tatiana.springweb.ipml;

import me.tatiana.springweb.model.Recipe;
import me.tatiana.springweb.services.RecipeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static Map<Long, Recipe> recipes = new TreeMap<>();
    private static long recipeId = 1;

    @Override
    public long addRecipe(Recipe recipe) {
        recipes.put(recipeId++, recipe);
        return recipeId++;
    }
    @Override
    public Recipe getRecipe(long key) {
        return recipes.get(key);
    }

    @Override
    public Recipe editRecipe(Recipe recipe, long id) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            return recipe;
        }
        return null;
    }

    @Override
    public boolean removeRecipe(long id) {
        Recipe recipe = recipes.remove(id);
        return recipe != null;
    }

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        return recipes;
    }

    @Override
    public Map<Long, Recipe> getRecipesByIngredientId(Long id1, Long id2) {
        Map<Long, Recipe> recipesContainsIngredient = new TreeMap<>();
        Iterator<Map.Entry<Long, Recipe>> iter = recipes.entrySet().iterator();
        if (ObjectUtils.isEmpty(id2)) {
            for (Map.Entry<Long, Recipe> one : recipes.entrySet()) {
                if (one.getValue().getIngredients().containsKey(id1)) {
                    recipesContainsIngredient.put(one.getKey(), one.getValue());
                }
            }
        } else {
            for (Map.Entry<Long, Recipe> one : recipes.entrySet()) {
                if (one.getValue().getIngredients().containsKey(id1) && one.getValue().getIngredients().containsKey(id2)) {
                    recipesContainsIngredient.put(one.getKey(), one.getValue());
                }
            }
        }
        return recipesContainsIngredient;
    }

    @Override
    public Map<Long, Recipe> getListOfRecipes(byte page) {
        Map<Long, Recipe> recipesByPage = new TreeMap<>();
        byte step = 10;
        int count = 0;
        if (page > 0) {
            for (Map.Entry<Long, Recipe> one : recipes.entrySet()) {
                if (count >= step * (page - 1) && count < (step * page) - 1) {
                    recipesByPage.put(one.getKey(), one.getValue());
                }
                count++;
            }
        }
        return recipesByPage;
    }
}
