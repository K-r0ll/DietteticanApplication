package pl.dietapp.backend.recipe;

public class Ingredient {
    private String name;
    private double amount;
    private double calorie;

    public double getIngrCalories() {
       return amount * calorie / 100;
    }

    public double caloriesUser(Double userKcal) {
        return (userKcal * amount * UTILSCalorie.KurczakKCAL100) / 100;
    }

    public Ingredient(String name, double amount, double calorie) {
        this.name = name;
        this.amount = amount;
        this.calorie = calorie;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", calorieNa100g=" +
                '}';
    }
}
