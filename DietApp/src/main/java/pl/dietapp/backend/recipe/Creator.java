package pl.dietapp.backend.recipe;

public class Creator {
    public static RecipeMain createRecipe(String name, Ingredient... ingredients) {
        RecipeMain recipeMain = new RecipeMain(name);

        for (Ingredient ingredient: ingredients)        {
            recipeMain.addIngredient(ingredient);
        }

        return recipeMain;
    }
}
