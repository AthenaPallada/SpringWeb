package me.tatiana.springweb.ipml;

import me.tatiana.springweb.exception.Response500Exception;
import me.tatiana.springweb.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FilesService {
    @Value("${recipes.file.path}")
    private String recipesFilePath;
    @Value("${ingredients.file.name}")
    private String ingredientsFilePath;

    @Value("${user.recipes.file.path}")
    private String userRecipesFilePath;

    @Override
    public boolean saveToFile(String json, String filePath) {
        try {
            cleanFile(filePath);
            Files.writeString(Path.of(filePath), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new Response500Exception("Ошибка записи в базу данных", e);
        }
    }

    @Override
    public boolean cleanFile(String filePath) {
        Path path = Path.of(filePath);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getRecipesDataFilePath() {
        return new File(recipesFilePath);
    }

    public File getIngredientDataFilePath() {
        return new File(ingredientsFilePath);
    }

    public File getUserRecipesDataFilePath() {
        return new File(userRecipesFilePath);
    }
}