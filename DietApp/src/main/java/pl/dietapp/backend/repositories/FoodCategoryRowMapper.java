package pl.dietapp.backend.repositories;

import org.springframework.jdbc.core.RowMapper;
import pl.dietapp.backend.model.FoodCategory;
import pl.dietapp.backend.model.MeasurementUnits;



import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodCategoryRowMapper implements RowMapper<FoodCategory> {

    @Override
    public FoodCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        final FoodCategory foodCategory = new FoodCategory();

        foodCategory.setCategoryId(rs.getInt("idfood_category"));
        foodCategory.setCategoryName(rs.getString("food_category_name"));


        return foodCategory;
    }
}
