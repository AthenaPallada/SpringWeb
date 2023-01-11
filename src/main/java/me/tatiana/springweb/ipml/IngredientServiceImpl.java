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
    public void addIngredient(Ingredient ingredient) {
        ingredients.put(ingredientId++, ingredient);
    }
    @Override
    public Ingredient getIngredient(long key) {
        return ingredients.get(key);
    }
}
