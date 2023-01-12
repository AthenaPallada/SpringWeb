package me.tatiana.springweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private LinkedList<String> steps;
    private LinkedList<Ingredient> ingredients;
}
