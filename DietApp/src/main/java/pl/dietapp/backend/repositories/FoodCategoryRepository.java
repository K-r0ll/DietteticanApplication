package pl.dietapp.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.dietapp.backend.model.FoodCategory;
import pl.dietapp.backend.model.MeasurementUnits;

import java.util.List;

@Repository
public class FoodCategoryRepository {
    @Autowired
    private JdbcTemplate template;

    public List<FoodCategory> findAll() {

        String sql = "SELECT * from food_category";
        return template.query(sql, new FoodCategoryRowMapper());
    }
}

