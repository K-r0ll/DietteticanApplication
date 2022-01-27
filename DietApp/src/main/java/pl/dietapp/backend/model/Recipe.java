package pl.dietapp.backend.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int id;
    private int categoryId;
    private String category;
    private String recipeName;
    private String recipeDescription;
    private Float kcal;
    private String recipeShortDescription;
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

    public Recipe() {

    }

    public Recipe(int id, int categoryId, String recipeName, String recipeDescription, Float kcal, String recipeShortDescription) {
        this.id = id;
        this.categoryId = categoryId;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.kcal = kcal;
        this.recipeShortDescription = recipeShortDescription;
    }
    public Recipe(int categoryId, String recipeName, String recipeDescription, String recipeShortDescription) {
        this.categoryId = categoryId;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.kcal = kcal;
        this.recipeShortDescription = recipeShortDescription;
    }

    public List<RecipeIngredient> getIngredList() {
        return recipeIngredientList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIngredList(List<RecipeIngredient> recipeIngredientList) {
        this.recipeIngredientList = recipeIngredientList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Float getKcal() {
        return kcal;
    }

    public void setKcal(Float kcal) {
        this.kcal = kcal;
    }

    public String getRecipeShortDescription() {
        return recipeShortDescription;
    }

    public void setRecipeShortDescription(String recipeShortDescription) {
        this.recipeShortDescription = recipeShortDescription;
    }
}
