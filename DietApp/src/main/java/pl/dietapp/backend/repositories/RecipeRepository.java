package pl.dietapp.backend.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import pl.dietapp.backend.model.RecipeIngredient;
import pl.dietapp.backend.model.Recipe;
import pl.dietapp.backend.services.RecipeService;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RecipeRepository {
    //    private final IngredientRepository ingredientRepository;
    //    public RecipeRepository(IngredientRepository ingredientRepository) {
    //       this.ingredientRepository = ingredientRepository;
    //    }
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private JdbcTemplate template;


    public List<Recipe> getRecipes() {
        
        final String sql = "SELECT * FROM recipes\n" +
                "JOIN food_category ON recipes.food_category_idfood_category= food_category.idfood_category";
        final List<Recipe> list = template.query(sql, new RecipeRowMapper(ingredientRepository, recipeService));
        return list;
    }

    public void createRecipe(Recipe recipe) {
        String sql = "INSERT INTO recipes\n" +
                "\t(food_category_idfood_category, recipe_name, recipe_description, recipe_kcal, recipe_shortdescription)\n"+
                "\tVALUES ('"+recipe.getCategoryId()+"', '"+recipe.getRecipeName()+"', '"+recipe.getRecipeDescription()+"' ,'"+recipe.getKcal()+"', '"+recipe.getRecipeShortDescription()+"') RETURNING recipe_id";
        int recipe_idt = template.queryForObject(sql, Integer.class);

        List<RecipeIngredient> recipeIngredientList = recipe.getIngredList();
        recipeIngredientList.stream().forEach(ingred -> createRecipeIngredient(recipe_idt, ingred));

    }
    public void createRecipeIngredient(int recipe_id, RecipeIngredient ingredient) {
        String sql = "INSERT INTO measurement_qty\n"+
                "\t(qty_amount) VALUES('"+ingredient.getQtyAmount()+"') RETURNING measurement_qty_id";
        int measurement_id = template.queryForObject(sql, Integer.class);
        System.out.println("Ingredient id: "+ingredient.getIngredientId());
        String sql1 = "INSERT INTO recipe_ingredients\n" +
                "\t(ingredients_ingredient_id, measurement_qty_id, recipes_recipe_id, measurement_units_id)\n" +
                "\tVALUES ('"+ingredient.getIngredientId()+"','"+measurement_id+"','"+recipe_id+"','"+ingredient.getMeasurementUnitsId()+"')";
        template.update(sql1);
    }
    public void deleteRecipe(Recipe recipe) {

        String sql = "DELETE FROM recipe_ingredients WHERE recipes_recipe_id = ?";
        String sql1 = "DELETE FROM recipes WHERE recipe_id = ?";
        template.update(sql, recipe.getId());
        template.update(sql1, recipe.getId());
    }
}

