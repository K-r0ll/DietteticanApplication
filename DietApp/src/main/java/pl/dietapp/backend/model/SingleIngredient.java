package pl.dietapp.backend.model;

public class SingleIngredient {

    private long ingredientsIngredientId;
    private long ingredientId;
    private String ingredientName;
    private long nutritionProfileId;
    private double kcalPer100;
    private double proteinPer100;
    private double carbohydratesPer100;
    private double fatPer100;

    public SingleIngredient(long ingredientsIngredientId, long ingredientId, String ingredientName, long nutritionProfileId, float kcalPer100, float proteinPer100, float carbohydratesPer100, float fatPer100) {
        this.ingredientsIngredientId = ingredientsIngredientId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.nutritionProfileId = nutritionProfileId;
        this.kcalPer100 = kcalPer100;
        this.proteinPer100 = proteinPer100;
        this.carbohydratesPer100 = carbohydratesPer100;
        this.fatPer100 = fatPer100;
    }
    public SingleIngredient(String ingredientName, Double kcalPer100, Double proteinPer100, Double carbohydratesPer100, Double fatPer100) {
        this.ingredientName = ingredientName;
        this.kcalPer100 = kcalPer100;
        this.proteinPer100 = proteinPer100;
        this.carbohydratesPer100 = carbohydratesPer100;
        this.fatPer100 = fatPer100;
    }

    public SingleIngredient() {

    }

    public long getIngredientsIngredientId() {
        return ingredientsIngredientId;
    }

    public void setIngredientsIngredientId(long ingredientsIngredientId) {
        this.ingredientsIngredientId = ingredientsIngredientId;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public long getNutritionProfileId() {
        return nutritionProfileId;
    }

    public void setNutritionProfileId(long nutritionProfileId) {
        this.nutritionProfileId = nutritionProfileId;
    }

    public double getKcalPer100() {
        return kcalPer100;
    }

    public void setKcalPer100(float kcalPer100) {
        this.kcalPer100 = kcalPer100;
    }

    public double getProteinPer100() {
        return proteinPer100;
    }

    public void setProteinPer100(float proteinPer100) {
        this.proteinPer100 = proteinPer100;
    }

    public double getCarbohydratesPer100() {
        return carbohydratesPer100;
    }

    public void setCarbohydratesPer100(float carbohydratesPer100) {
        this.carbohydratesPer100 = carbohydratesPer100;
    }

    public double getFatPer100() {
        return fatPer100;
    }

    public void setFatPer100(float fatPer100) {
        this.fatPer100 = fatPer100;
    }
}
