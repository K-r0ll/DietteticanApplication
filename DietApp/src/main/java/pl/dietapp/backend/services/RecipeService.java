package pl.dietapp.backend.services;

import org.springframework.stereotype.Service;
import pl.dietapp.backend.model.RecipeIngredient;
import pl.dietapp.backend.model.Recipe;

import java.util.List;

@Service
public class RecipeService {



public float getKcalRecipe(Recipe recipe) {
    return (float) recipe.getIngredList().stream().mapToDouble( ingred-> ingred.getKcalIngredient()).sum();
}
public Recipe createRecipe(int categoryId, String recipeName, String recipeDescription, String recipeShortDescription, List<RecipeIngredient> recipeIngredientList) {
    Recipe recipe = new Recipe( categoryId, recipeName, recipeDescription, recipeShortDescription);
    recipe.setIngredList(recipeIngredientList);
    recipe.setKcal(getKcalRecipe(recipe));
    return recipe;
}
}
