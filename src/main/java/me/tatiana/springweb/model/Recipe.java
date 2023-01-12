package me.tatiana.springweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.Map;

@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private LinkedList<String> steps;
    private Map<Long, Ingredient> ingredients;
}
