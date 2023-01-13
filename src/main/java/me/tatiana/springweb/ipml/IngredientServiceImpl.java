package me.tatiana.springweb.ipml;

import me.tatiana.springweb.model.Ingredient;
import me.tatiana.springweb.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static Map<Long, Ingredient> ingredients = new TreeMap<>();
    private static long ingredientId = 1;

    @Override
    public long addIngredient(Ingredient ingredient) {
        ingredients.put(ingredientId++, ingredient);
        return ingredientId++;
    }
    @Override
    public Ingredient getIngredient(long key) {
        return ingredients.get(key);
    }

    @Override
    public Ingredient editIngredient(Ingredient ingredient, long id) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            return ingredient;
        }
        return null;
    }
    @Override
    public boolean removeIngredient(long id) {
        Ingredient ingredient = ingredients.remove(id);
        return ingredient != null;
    }
    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        return ingredients;
    }
}