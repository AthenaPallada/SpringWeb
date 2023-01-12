package me.tatiana.springweb.controllers;

import me.tatiana.springweb.model.Ingredient;
import me.tatiana.springweb.model.Recipe;
import me.tatiana.springweb.ipml.IngredientServiceImpl;
import me.tatiana.springweb.ipml.RecipeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/cooking")
public class CookController {
    private final RecipeServiceImpl recipeService;
    private final IngredientServiceImpl ingredientService;

    public CookController(RecipeServiceImpl recipeService, IngredientServiceImpl ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @PostMapping("/recipes/add")
    public void recipe(@RequestParam String name, @RequestParam int cookingTime, @RequestParam LinkedList<String> steps, @RequestParam LinkedList<Ingredient> ingredients) {
        recipeService.addRecipe(new Recipe(name, cookingTime, steps, ingredients));
    }

    @GetMapping("/recipes/{id}")
    public Recipe showRecipe(@PathVariable long id) {
        return recipeService.getRecipe(id);
    }

    @PostMapping
    public void ingredient(@RequestParam String name, @RequestParam int quantity, @RequestParam String measureUnit) {
        ingredientService.addIngredient(new Ingredient(name, quantity, measureUnit));
    }

    @GetMapping("/ingredients/{id}")
    public Ingredient showIngredient(@PathVariable long id) {
        return ingredientService.getIngredient(id);
    }
}
