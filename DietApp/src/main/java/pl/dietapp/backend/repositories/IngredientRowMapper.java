package pl.dietapp.backend.repositories;

import org.decimal4j.util.DoubleRounder;
import org.springframework.jdbc.core.RowMapper;
import pl.dietapp.backend.model.RecipeIngredient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientRowMapper implements RowMapper<RecipeIngredient> {

    @Override
    public RecipeIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
        final RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipeIngredientsId(rs.getLong("recipe_ingredients_id"));
        recipeIngredient.setRecipesRecipeId(rs.getLong("recipes_recipe_id"));
        recipeIngredient.setIngredientsIngredientId(rs.getLong("ingredients_ingredient_id"));
        recipeIngredient.setIngredientId(rs.getLong("ingredient_id"));
        recipeIngredient.setIngredientName(rs.getString("ingredient_name"));
        recipeIngredient.setNutritionProfileId(rs.getLong("nutrition_profile_id"));
        recipeIngredient.setKcalPer100((float) DoubleRounder.round(rs.getFloat("kcal_per100"),0));
        recipeIngredient.setProteinPer100((float) DoubleRounder.round(rs.getFloat("protein_per100"),0));
        recipeIngredient.setCarbohydratesPer100((float) DoubleRounder.round(rs.getFloat("carbohydrates_per100"),0));
        recipeIngredient.setFatPer100((float) DoubleRounder.round(rs.getFloat("fat_per100"),0));
        recipeIngredient.setMeasurementUnitsId(rs.getLong("measurement_units_id"));
        recipeIngredient.setMeasurementDescription(rs.getString("measurement_description"));
        recipeIngredient.setMeasurementQtyId(rs.getLong("measurement_qty_id"));
        recipeIngredient.setQtyAmount(rs.getFloat("qty_amount"));
        return recipeIngredient;
    }
}
