package pl.dietapp.backend.repositories;

import org.springframework.jdbc.core.RowMapper;
import pl.dietapp.backend.model.MeasurementUnits;
import pl.dietapp.backend.model.SingleIngredient;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeasurementUnitsRowMapper implements RowMapper<MeasurementUnits> {

    @Override
    public MeasurementUnits mapRow(ResultSet rs, int rowNum) throws SQLException {
        final MeasurementUnits measurementUnits = new MeasurementUnits();

        measurementUnits.setId(rs.getInt("measurement_units_id"));
        measurementUnits.setMeasurement_description(rs.getString("measurement_description"));


        return measurementUnits;
    }
}
