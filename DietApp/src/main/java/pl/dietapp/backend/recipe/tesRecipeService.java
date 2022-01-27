package pl.dietapp.backend.recipe;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static pl.dietapp.backend.recipe.Creator.createRecipe;

@Service
public class tesRecipeService {
    private final List<RecipeMain> recipes = new ArrayList<>();
        public List<RecipeMain> getList() {
            return recipes;
        }

        public void addIngredient(String name, double amount, double kcalPer100 ){
            new Ingredient(name, amount, kcalPer100);
        }

        public void addRecipeList() {
            Ingredient makaron = new Ingredient("Makaron", 100, UTILSCalorie.MakaronKCAL100);
            Ingredient kurczak = new Ingredient("Kurczak", 50, UTILSCalorie.KurczakKCAL100);
            Ingredient pomidor = new Ingredient("Pomidor", 80, UTILSCalorie.PomidorKCAL100);
            RecipeMain spaghettiBoloneze = createRecipe("Spaghetti Bolognese", makaron, kurczak, pomidor);
            spaghettiBoloneze.setDescription("To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego, To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego");
            spaghettiBoloneze.setShortDescription("Makaron kuchni włoskiej");
            RecipeMain spaghettiBoloneze1 = createRecipe("Spaghetti Bolognese1111111", makaron, kurczak, pomidor);
            spaghettiBoloneze1.setDescription("To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego, To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego");
            spaghettiBoloneze1.setShortDescription("Makaron kuchni włoskiej");
            RecipeMain spaghettiBoloneze2 = createRecipe("Spaghetti Bolognese2222222", makaron, kurczak, pomidor);
            spaghettiBoloneze2.setShortDescription("Makaron kuchni włoskiej");
            spaghettiBoloneze2.setDescription("To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego, To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego");
            RecipeMain spaghettiBoloneze3 = createRecipe("Spaghetti Bolognese3333333", makaron, kurczak, pomidor);
            spaghettiBoloneze3.setShortDescription("Makaron kuchni włoskiej");
            spaghettiBoloneze3.setDescription("To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego, To jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samegoTo jest spaghetti a ja testuje program, miejmy nadzieję, że kod nie będzie przypominał tego samego");
            recipes.add(spaghettiBoloneze);
            recipes.add(spaghettiBoloneze1);
            recipes.add(spaghettiBoloneze2);
            recipes.add(spaghettiBoloneze3);
        }




}
