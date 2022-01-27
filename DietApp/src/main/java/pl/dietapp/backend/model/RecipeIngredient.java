package pl.dietapp.backend.model;

import org.decimal4j.util.DoubleRounder;

public class RecipeIngredient {
    private long recipeIngredientsId;
    private long recipesRecipeId;

    private long ingredientsIngredientId;
    private long ingredientId;
    private String ingredientName;

    private long nutritionProfileId;
    private double kcalPer100;
    private double proteinPer100;
    private double carbohydratesPer100;
    private double fatPer100;

    private long measurementUnitsId;
    private String measurementDescription;

    private long measurementQtyId;
    private double qtyAmount;


    public RecipeIngredient() {

    }

    public RecipeIngredient(long recipeIngredientsId, long recipesRecipeId, long ingredientsIngredientId, long ingredientId, String ingredientName, long nutritionProfileId, float kcalPer100, float proteinPer100, float carbohydratesPer100, float fatPer100, long measurementUnitsId, String measurementDescription, long measurementQtyId, float qtyAmount) {
        this.recipeIngredientsId = recipeIngredientsId;
        this.recipesRecipeId = recipesRecipeId;
        this.ingredientsIngredientId = ingredientsIngredientId;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.nutritionProfileId = nutritionProfileId;
        this.kcalPer100 = kcalPer100;
        this.proteinPer100 = proteinPer100;
        this.carbohydratesPer100 = carbohydratesPer100;
        this.fatPer100 = fatPer100;
        this.measurementUnitsId = measurementUnitsId;
        this.measurementDescription = measurementDescription;
        this.measurementQtyId = measurementQtyId;
        this.qtyAmount = qtyAmount;
    }

    public RecipeIngredient(long ingredientId, String ingredientName, long nutritionProfileId, double kcalPer100, double proteinPer100, double carbohydratesPer100, double fatPer100, long measurementUnitsId, String measurementDescription, double qtyAmount) {


        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.nutritionProfileId = nutritionProfileId;
        this.kcalPer100 = kcalPer100;
        this.proteinPer100 = proteinPer100;
        this.carbohydratesPer100 = carbohydratesPer100;
        this.fatPer100 = fatPer100;
        this.measurementUnitsId = measurementUnitsId;
        this.measurementDescription = measurementDescription;
        this.qtyAmount = qtyAmount;
    }

    public RecipeIngredient(SingleIngredient ingredient, double amount , int idJednostki, String jednostka) {
        this.ingredientId = ingredient.getIngredientId();
        this.ingredientName = ingredient.getIngredientName();
        this.nutritionProfileId = ingredient.getNutritionProfileId();
        this.kcalPer100 = ingredient.getKcalPer100();
        this.proteinPer100 = ingredient.getProteinPer100()*amount/100;
        this.carbohydratesPer100 = ingredient.getCarbohydratesPer100()*amount/100;
        this.fatPer100 = ingredient.getFatPer100()*amount/100;
        this.measurementUnitsId = idJednostki;
        this.measurementDescription = jednostka;
        this.qtyAmount = amount;
    }



    public void changeIngred(long measurementUnitsId, String measurementDescription, double qtyAmount) {

        this.measurementUnitsId = measurementUnitsId;
        this.measurementDescription = measurementDescription;
        this.qtyAmount = qtyAmount;
    }

    public long getRecipeIngredientsId() {
        return recipeIngredientsId;
    }

    public void setRecipeIngredientsId(long recipeIngredientsId) {
        this.recipeIngredientsId = recipeIngredientsId;
    }

    public long getRecipesRecipeId() {
        return recipesRecipeId;
    }

    public void setRecipesRecipeId(long recipesRecipeId) {
        this.recipesRecipeId = recipesRecipeId;
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

        return DoubleRounder.round(kcalPer100*qtyAmount/100, 0);
    }

    public void setKcalPer100(float kcalPer100) {
        this.kcalPer100 = kcalPer100;
    }

    public double getProteinPer100() {
        return DoubleRounder.round(proteinPer100*qtyAmount/100, 0);
    }

    public void setProteinPer100(float proteinPer100) {
        this.proteinPer100 = proteinPer100;
    }

    public double getCarbohydratesPer100() {
        return DoubleRounder.round(carbohydratesPer100*qtyAmount/100, 0);
    }

    public void setCarbohydratesPer100(float carbohydratesPer100) {
        this.carbohydratesPer100 = carbohydratesPer100;
    }

    public double getFatPer100() {
        return DoubleRounder.round(fatPer100*qtyAmount/100, 0);
    }

    public void setFatPer100(float fatPer100) {
        this.fatPer100 = fatPer100;
    }

    public long getMeasurementUnitsId() {
        return measurementUnitsId;
    }

    public void setMeasurementUnitsId(long measurementUnitsId) {
        this.measurementUnitsId = measurementUnitsId;
    }

    public String getMeasurementDescription() {
        return measurementDescription;
    }

    public void setMeasurementDescription(String measurementDescription) {
        this.measurementDescription = measurementDescription;
    }

    public long getMeasurementQtyId() {
        return measurementQtyId;
    }

    public void setMeasurementQtyId(long measurementQtyId) {
        this.measurementQtyId = measurementQtyId;
    }

    public double getQtyAmount() {
        return qtyAmount;
    }

    public void setQtyAmount(float qtyAmount) {
        this.qtyAmount = qtyAmount;
    }

    public String getAmountMeasure() {
        return qtyAmount + measurementDescription;
    }

    @Override
    public String toString() {
        return "Ingred{" +
                "recipeIngredientsId=" + recipeIngredientsId +
                ", recipesRecipeId=" + recipesRecipeId +
                ", ingredientsIngredientId=" + ingredientsIngredientId +
                ", ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", nutritionProfileId=" + nutritionProfileId +
                ", kcalPer100=" + kcalPer100 +
                ", proteinPer100=" + proteinPer100 +
                ", carbohydratesPer100=" + carbohydratesPer100 +
                ", fatPer100=" + fatPer100 +
                ", measurementUnitsId=" + measurementUnitsId +
                ", measurementDescription='" + measurementDescription + '\'' +
                ", measurementQtyId=" + measurementQtyId +
                ", qtyAmount=" + qtyAmount +
                '}';
    }
}
