package me.tatiana.springweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.tatiana.springweb.ipml.IngredientServiceImpl;
import me.tatiana.springweb.model.Ingredient;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "Пока ингредиенты нужно добавлять самостоятельно")
public class IngredientController {
    private final IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "Добавление ингредиента")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))})})
    @PostMapping
    public ResponseEntity<Long> ingredient(@RequestBody Ingredient ingredient) {
        long id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Отображение ингредиента по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))})})
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> showIngredient(@PathVariable(required = true) long id) {
        Ingredient ingredient =  ingredientService.getIngredient(id);
        if (ObjectUtils.isNotEmpty(ingredient)) {
            return ResponseEntity.ok(ingredient);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Редактирование ингредиента по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))})})
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable long id, @RequestBody Ingredient ingredient) {
        Ingredient editIngredient =  ingredientService.editIngredient(ingredient, id);
        if (ObjectUtils.isNotEmpty(editIngredient)) {
            return ResponseEntity.ok(ingredient);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Удаление ингредиента по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeIngredient(@PathVariable long id) {
        if (ingredientService.removeIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получение списка всех ингредиентов")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class)))})})
    @GetMapping
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }
}