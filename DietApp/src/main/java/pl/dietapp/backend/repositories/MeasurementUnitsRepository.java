package pl.dietapp.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.dietapp.backend.model.MeasurementUnits;
import pl.dietapp.backend.model.SingleIngredient;

import java.util.List;

@Repository
public class MeasurementUnitsRepository {
    @Autowired
    private JdbcTemplate template;

    public List<MeasurementUnits> findAll() {

        String sql = "SELECT * from measurement_units";
        return template.query(sql, new MeasurementUnitsRowMapper());
    }
}
