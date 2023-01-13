package me.tatiana.springweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.tatiana.springweb.ipml.RecipeServiceImpl;
import me.tatiana.springweb.model.Recipe;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты")
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Добавление рецепта в книгу")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    @PostMapping
    public ResponseEntity<Long> addRecipe(@RequestBody Recipe recipe) {
        long id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Получить рецепт по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable(required = true) long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Отредактировать рецепт по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
        Recipe editRecipe = recipeService.editRecipe(recipe, id);
        if (editRecipe != null) {
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Удалить рецепт по id")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeRecipe(@PathVariable long id) {
        if (recipeService.removeRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить все рецепты из книги")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    @GetMapping
    public ResponseEntity<Map<Long, Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @Operation(summary = "Получить список рецептов, содержащие указанные в виде id ингредиенты", description = "Можно передавать один или два ингредиента")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    @GetMapping("/byIngredientId")
    public ResponseEntity<Map<Long, Recipe>> getRecipesByIngredientId(@RequestParam(required = true) Long id1, @RequestParam(required = false) Long id2) {
        Map<Long, Recipe> recipesList = recipeService.getRecipesByIngredientId(id1, id2);
        if (recipesList != null) {
            return ResponseEntity.ok(recipesList);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Получить список из 10ти рецептов по указанной странице книги ", description = "Нужно передать номер интересующей страницы")
    @ApiResponses(value = {@ApiResponse(responseCode = "222", description = "Улет",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))})})
    @GetMapping("/page")
    public ResponseEntity<Map<Long, Recipe>> getListOfRecipes(@RequestParam byte page) {
        Map<Long, Recipe> recipesList = recipeService.getListOfRecipes(page);
        if (ObjectUtils.isNotEmpty(recipesList)) {
            return ResponseEntity.ok(recipesList);
        }
        return ResponseEntity.notFound().build();
    }
}