package pl.dietapp.backend.model;

public class MeasurementUnits {
    private Integer id;
    private String measurement_description;

    public MeasurementUnits() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeasurement_description() {
        return measurement_description;
    }

    public void setMeasurement_description(String measurement_description) {
        this.measurement_description = measurement_description;
    }

    @Override
    public String toString() {
        return measurement_description;
    }
}
