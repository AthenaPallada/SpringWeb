package me.tatiana.springweb.ipml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.tatiana.springweb.exception.Response500Exception;
import me.tatiana.springweb.model.Ingredient;
import me.tatiana.springweb.model.Recipe;
import me.tatiana.springweb.services.RecipeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static Map<Long, Recipe> recipes = new TreeMap<>();
    //private static long recipeId = 1;
    private FileServiceImpl fileService;

    @Value("${recipes.file.path}")
    private String filePath;

    @Value("${user.recipes.file.path}")
    private String userFilePath;

    public RecipeServiceImpl(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @Override
    public long addRecipe(Recipe recipe) {
        long count = recipes.size();
        recipes.put(count, recipe);
        saveToFile();
        return count;
    }

    @Override
    public Recipe getRecipe(long key) {
        return recipes.get(key);
    }

    @Override
    public Recipe editRecipe(Recipe recipe, long id) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public boolean removeRecipe(long id) {
        Recipe recipe = recipes.remove(id);
        saveToFile();
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

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveToFile(json, filePath);
            fileService.saveToFile(getFormattedRecipes(), userFilePath);
        } catch (JsonProcessingException e) {
            throw new Response500Exception("Ошибка преобразования json файла", e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile(filePath);
            recipes = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>() {
            });
        } catch (IOException e) {
            throw new Response500Exception("Ошибка записи в базу данных", e);
        }
    }

    private String getFormattedRecipes() {
        StringBuilder value = new StringBuilder();
        for (Recipe recipe : recipes.values()) {
            byte i = 0;
            value.append(recipe.getName() + "\n");
            value.append("Время приготовления: " + recipe.getCookingTime() + " минут\nИнгредиенты:\n");
            for (Ingredient ingredient : recipe.getIngredients().values()) {
                value.append("     " + ingredient.getName() + " - " + ingredient.getQuantity() + " " + ingredient.getMeasureUnit() + "\n");
            }
            value.append("Инструкция приготовления:\n");
            for (String step : recipe.getSteps()) {
                value.append("     " + ++i + ". " + step + "\n");
            }
            value.append("\n");
        }
        return value.toString();
    }

    @PostConstruct
    private void primalReader() {
        readFromFile();
        fileService.saveToFile(getFormattedRecipes(), userFilePath);
    }
}