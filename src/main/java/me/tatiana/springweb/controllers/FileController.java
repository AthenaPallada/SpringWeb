package me.tatiana.springweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.tatiana.springweb.ipml.FileServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/file")
@Tag(name = "Работа с файлами", description = "Можно загрузить рецепты или ингредиенты, а также получить список всех имеющихся рецептов")
public class FileController {
    private FileServiceImpl fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Выгрузка списка рецептов")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "всё хорошо, запрос выполнился"),
            @ApiResponse(responseCode = "400", description = "есть ошибка в параметрах запроса"),
            @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении"),
            @ApiResponse(responseCode = "500", description = "во время выполнения запроса произошла ошибка на сервере")})
    @GetMapping(value = "/export")
    public ResponseEntity<InputStreamResource> downloadRecipesDataFile() throws FileNotFoundException {
        File dataFile = fileService.getUserRecipesDataFilePath();
        if (dataFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(dataFile));
            return ResponseEntity.ok()
                    .contentLength(dataFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.json\"")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Загрузка рецептов")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "всё хорошо, запрос выполнился"),
            @ApiResponse(responseCode = "400", description = "есть ошибка в параметрах запроса"),
            @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении"),
            @ApiResponse(responseCode = "500", description = "во время выполнения запроса произошла ошибка на сервере")})
    @PostMapping(value = "/import/recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesDataFile(@RequestParam MultipartFile file) {
        fileService.cleanFile(fileService.getRecipesDataFilePath().getPath());
        File dataFile = fileService.getRecipesDataFilePath();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(summary = "Загрузка ингредиентов")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "всё хорошо, запрос выполнился"),
            @ApiResponse(responseCode = "400", description = "есть ошибка в параметрах запроса"),
            @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении"),
            @ApiResponse(responseCode = "500", description = "во время выполнения запроса произошла ошибка на сервере")})
    @PostMapping(value = "/import/ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsDataFile(@RequestParam MultipartFile file) {
        fileService.cleanFile(fileService.getIngredientDataFilePath().getPath());
        File dataFile = fileService.getIngredientDataFilePath();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}