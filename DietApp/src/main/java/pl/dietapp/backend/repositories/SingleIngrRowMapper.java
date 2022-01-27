package pl.dietapp.backend.repositories;

import org.decimal4j.util.DoubleRounder;
import org.springframework.jdbc.core.RowMapper;
import pl.dietapp.backend.model.SingleIngredient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleIngrRowMapper implements RowMapper<SingleIngredient> {

    @Override
    public SingleIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
        final SingleIngredient ingred = new SingleIngredient();

        ingred.setIngredientId(rs.getLong("ingredient_id"));
        ingred.setIngredientName(rs.getString("ingredient_name"));
        ingred.setNutritionProfileId(rs.getLong("nutrition_profile_id"));
        ingred.setKcalPer100((float) DoubleRounder.round(rs.getFloat("kcal_per100"),0));
        ingred.setProteinPer100((float) DoubleRounder.round(rs.getFloat("protein_per100"),0));
        ingred.setCarbohydratesPer100((float) DoubleRounder.round(rs.getFloat("carbohydrates_per100"),0));
        ingred.setFatPer100((float) DoubleRounder.round(rs.getFloat("fat_per100"),0));

        return ingred;
    }
}
