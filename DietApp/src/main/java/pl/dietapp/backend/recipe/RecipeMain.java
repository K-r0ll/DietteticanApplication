package pl.dietapp.backend.recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeMain {
    private String recipeName;
    private String description;
    private String shortDescription;
    private double kcal;
    private List<Ingredient> ingredientList = new ArrayList<>();


    public RecipeMain(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public double getCalories() {
        return ingredientList.stream().mapToDouble(Ingredient::getIngrCalories).sum();
    }

    public double getKcal() {
        return ingredientList.stream().mapToDouble(Ingredient::getIngrCalories).sum();
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }

    public StringBuilder getIngredientList() {
        StringBuilder ingredientList1 = new StringBuilder();
        for (Ingredient ingredient : ingredientList) {
            ingredientList1.append(ingredient.getName()).append(" Ilość: ").append(ingredient.getAmount()).append(" gram").append("\n");
        }
        return ingredientList1;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String caloriesForUser(Double userKcal) {
        StringBuilder ingrList = new StringBuilder();

        for (Ingredient ingredient : ingredientList) {
            ingrList.append(ingredient.getName()).append(" ilość: ").append(ingredient.getAmount() * userKcal).append(" ").append("KCAL: ").append(ingredient.caloriesUser(userKcal)).append("\n");
        }

        return
                "Nazwa przepisu: " + recipeName + "\n" +
                        "Lista składników: \n" + ingrList
                ;
    }

    @Override
    public String toString() {
        StringBuilder ingrList = new StringBuilder();
        double recipeKcal = 0;
        for (Ingredient ingredient : ingredientList) {
            ingrList.append(ingredient.getName()).append(" ilość: ").append(ingredient.getAmount()).append(" KCAL: ").append(ingredient.getIngrCalories()).append("\n");
            recipeKcal += ingredient.getIngrCalories();
        }

        return
                "Nazwa przepisu: " + recipeName + "\n" +
                        "Ilość kcal łącznie: " + recipeKcal + "\n" +
                        "Lista składników: \n" + ingrList
                ;
    }


}
