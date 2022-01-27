package pl.dietapp.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.dietapp.backend.model.RecipeIngredient;
import pl.dietapp.backend.model.SingleIngredient;

import java.sql.DriverManager;
import java.util.List;

@Repository
public class IngredientRepository {
    @Autowired
    private JdbcTemplate template;

    public List<RecipeIngredient> findByRecipeId(Long recipeId) {

        String sql = "SELECT * from recipe_ingredients\n" +
                "JOIN measurement_qty ON recipe_ingredients.measurement_qty_id= measurement_qty.measurement_qty_id\n" +
                "JOIN measurement_units ON recipe_ingredients.measurement_units_id = measurement_units.measurement_units_id\n" +
                "JOIN ingredients ON recipe_ingredients.ingredients_ingredient_id = ingredients.ingredient_id\n" +
                "JOIN nutrition_profile ON ingredients.nutrition_profile_id= nutrition_profile.nutrition_profile_id\n" +
                "WHERE recipes_recipe_id = ?";
        return template.query(sql, new Object[]{recipeId}, new IngredientRowMapper());
    }

    public List<SingleIngredient> findAllSingleIngr() {

        String sql = "SELECT * from ingredients\n" +
                "JOIN nutrition_profile ON ingredients.nutrition_profile_id= nutrition_profile.nutrition_profile_id";
        return template.query(sql, new SingleIngrRowMapper());
    }

    public void addSinglIngr(String ingredientName,Double kcal_per100, Double protein_per100, Double carbohydrates_per100, Double fat_per100) {
        String sql = "INSERT INTO nutrition_profile (kcal_per100, protein_per100, carbohydrates_per100, fat_per100) VALUES('"+kcal_per100+"','"+protein_per100+"','"+carbohydrates_per100+"','"+fat_per100+"')";
        String sql1 = "INSERT INTO ingredients (ingredient_name, nutrition_profile_id) VALUES('"+ingredientName+"',(SELECT MAX(nutrition_profile_id) from nutrition_profile))";
                template.execute(sql);
                template.execute(sql1);
    }
    public int addSinglIngr(SingleIngredient ingredient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO nutrition_profile (kcal_per100, protein_per100, carbohydrates_per100, fat_per100) VALUES('"+ingredient.getKcalPer100()+"','"+ingredient.getProteinPer100()+"','"+ingredient.getCarbohydratesPer100()+"','"+ingredient.getFatPer100()+"')";
        String sql1 = "INSERT INTO ingredients (ingredient_name, nutrition_profile_id) VALUES('"+ingredient.getIngredientName()+"',(SELECT MAX(nutrition_profile_id) from nutrition_profile)) RETURNING ingredient_id";
        template.execute(sql);
        int id = template.queryForObject(sql1, Integer.class);
        System.out.println(id);
        return id;
    }
//    public void getLastAdd() {
//        String sql = "SELECT MAX(ingredient_id) from ingredients";
//        template.query(sql, new RowMapper<Integer>() {
//        });
//    }



    public void deleteSinlgeIngr(long nutrition_profile_id, long ingredient_id) {
        try {
            String sql = "DELETE FROM ingredients WHERE ingredient_id='" + ingredient_id + "'";

        String sql1 = "DELETE FROM nutrition_profile WHERE nutrition_profile_id='" + nutrition_profile_id + "'";
        template.execute(sql);
        template.execute(sql1);
    } catch (DataIntegrityViolationException e) {
            System.out.println("TRZEBA OBSŁUŻYĆ TAKIE USUWANIE");
        }

    }

    public void updateSingleIngr(String ingredientName, long ingredient_id, long nutrition_profile_id, Double kcal_per100, Double protein_per100, Double carbohydrates_per100, Double fat_per100) {
        String sql = "UPDATE ingredients SET ingredient_name='" + ingredientName + "' WHERE ingredient_id='" + ingredient_id + "'";
        String sql1 = "UPDATE nutrition_profile SET kcal_per100='" + kcal_per100 + "',protein_per100='" + protein_per100 + "',carbohydrates_per100='" +carbohydrates_per100+ "',fat_per100='" +fat_per100+ "' WHERE nutrition_profile_id='" + nutrition_profile_id + "'";
        template.execute(sql);
        template.execute(sql1);
    }

}
