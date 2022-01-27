package pl.dietapp.backend.recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Scanner;

import static pl.dietapp.backend.recipe.Creator.*;

public class Test {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double kcal;

        Ingredient makaron = new Ingredient("Makaron", 100, UTILSCalorie.MakaronKCAL100);
        Ingredient kurczak = new Ingredient("Kurczak", 50, UTILSCalorie.KurczakKCAL100);
        Ingredient pomidor = new Ingredient("Pomidor", 80, UTILSCalorie.PomidorKCAL100);
        RecipeMain spaghettiBoloneze = createRecipe("Spaghetti Bolognese", makaron, kurczak, pomidor);

        System.out.println(spaghettiBoloneze);

        System.out.println("Podaj ile kcal ma byc w posilku: ");
        kcal = scanner.nextDouble();

        double newKcal = kcal/spaghettiBoloneze.getCalories();

        System.out.println(newKcal);

        System.out.println(spaghettiBoloneze.caloriesForUser(newKcal));





//
//        Ingredient salata = new Ingredient("salata", 200);
//        Ingredient ser = new Ingredient("ser", 200);
//        Ingredient fasola = new Ingredient("fasola", 500);
//        Ingredient mieso = new Ingredient("mieso", 500);
//        Ingredient chljeb = new Ingredient("chljeb", 500);

//        RecipeMain sniadanie = createRecipe("Sniadanie", salata, ser, fasola, mieso, chljeb);
//        System.out.println(sniadanie);


    }




}
