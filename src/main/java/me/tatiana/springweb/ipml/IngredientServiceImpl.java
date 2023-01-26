package me.tatiana.springweb.ipml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import me.tatiana.springweb.exception.Response500Exception;
import me.tatiana.springweb.model.Ingredient;
import me.tatiana.springweb.services.IngredientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static Map<Long, Ingredient> ingredients = new TreeMap<>();
   // private static long ingredientId = 1;

    private FileServiceImpl fileService;
    @Value("${ingredients.file.name}")
    private String filePath;
    public IngredientServiceImpl(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @Override
    public long addIngredient(Ingredient ingredient) {
        long count = ingredients.size();
        ingredients.put(count, ingredient);
        saveToFile();
        return count;
    }

    @Override
    public Ingredient getIngredient(long key) {
        return ingredients.get(key);
    }

    @Override
    public Ingredient editIngredient(Ingredient ingredient, long id) {
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            saveToFile();
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

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileService.saveToFile(json, filePath);
        } catch (JsonProcessingException e) {
            throw new Response500Exception("Ошибка преобразования json файла", e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile(filePath);
            ingredients = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>() {
            });
        } catch (IOException e) {
            throw new Response500Exception("Ошибка записи в базу данных", e);
        }
    }

    @PostConstruct
    private void primalReader() {
        fileService.readFromFile(filePath);
    }
}