package pl.dietapp.backend.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import pl.dietapp.backend.model.Recipe;
import pl.dietapp.backend.services.RecipeService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeRowMapper implements RowMapper<Recipe> {


    private final RecipeService recipeService;

    private final IngredientRepository ingredientRepository;

    public RecipeRowMapper(IngredientRepository ingredientRepository, RecipeService recipeService) {
        this.ingredientRepository = ingredientRepository;
        this.recipeService = recipeService;
    }
    
    @Override
    public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {


        final Recipe recipe = new Recipe();
        recipe.setId(rs.getInt("recipe_id"));
        recipe.setCategoryId(rs.getInt("food_category_idfood_category"));
        recipe.setRecipeName(rs.getString("recipe_name"));
        recipe.setRecipeDescription(rs.getString("recipe_description"));
        recipe.setCategory(rs.getString("food_category_name"));
        recipe.setRecipeShortDescription(rs.getString("recipe_shortdescription"));
        recipe.setIngredList(ingredientRepository.findByRecipeId((long) recipe.getId()));
        recipe.setKcal(recipeService.getKcalRecipe(recipe));

        return recipe;


    }


}


