package pl.dietapp.frontend.pages.twojadieta;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dietapp.backend.model.Recipe;
import pl.dietapp.backend.model.RecipeIngredient;
import pl.dietapp.backend.repositories.IngredientRepository;
import pl.dietapp.backend.repositories.RecipeRepository;
import pl.dietapp.frontend.pages.bazaprzepisów.BazaPrzepisowView;

import java.util.List;
import java.util.stream.Stream;
@CssImport(value = "./styles/gridPulpit.css", themeFor = "vaadin-grid")
public class DailyGrid extends VerticalLayout {
    private RecipeRepository recipeRepository;

    private Grid<Recipe> recipeGrid = new Grid<>();
    private ListDataProvider<Recipe> recipesProvider;

    public DailyGrid(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        createRecipeGrid();
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        recipeGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        recipeGrid.addClassName("my-grid");
        add(recipeGrid);
    }


    private void createRecipeGrid() {

        Grid.Column<Recipe> recipeNameColumn = recipeGrid.addColumn(Recipe::getRecipeName);
        Grid.Column<Recipe> recipeCategoryColumn = recipeGrid.addColumn(Recipe::getCategory);
        Grid.Column<Recipe> recipeShortDescription = recipeGrid.addColumn(Recipe::getRecipeShortDescription);
        Grid.Column<Recipe> recipeKcal = recipeGrid.addColumn(Recipe::getKcal).setSortable(true);

        getRecipeData(recipeGrid);


        recipeGrid.setItemDetailsRenderer(createPersonDetailsRenderer());
    }

    public static ComponentRenderer<RecipeMoreInfo, Recipe> createPersonDetailsRenderer() {
        return new ComponentRenderer<>(
                RecipeMoreInfo::new,
                RecipeMoreInfo::setRecipe);
    }

    private static class RecipeMoreInfo extends FormLayout {
        @Autowired
        private IngredientRepository ingredientRepository;
        private final TextArea nazwaPrzepisu = new TextArea("Nazwa Przepisu");
        private final TextArea kcalRecipe = new TextArea("Ilość Kalorii");
        private final TextArea shortDescription = new TextArea("Krótki opis przepisu");
        private final TextArea descriptionRecipe = new TextArea("Opis Przepisu");
        private final TextArea skladnikiRecipe = new TextArea("Składniki");

        public RecipeMoreInfo() {
            Stream.of(nazwaPrzepisu, shortDescription, kcalRecipe, descriptionRecipe, skladnikiRecipe).forEach(field -> {
                field.setReadOnly(true);
                add(field);
            });

            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(nazwaPrzepisu, 3);
            setColspan(shortDescription, 3);
            setColspan(kcalRecipe, 3);
            setColspan(descriptionRecipe, 3);
        }

        private void setRecipe(Recipe recipe) {

            nazwaPrzepisu.setValue(recipe.getRecipeName());
            kcalRecipe.setValue(String.valueOf(recipe.getKcal()));
            shortDescription.setValue(recipe.getRecipeShortDescription());
            descriptionRecipe.setValue(recipe.getRecipeDescription());
            skladnikiRecipe.setValue(recipe.getIngredList().toString());


        }
    }
    private void getRecipeData(Grid<Recipe> recipeGrid) {
        List<Recipe> recipes = recipeRepository.getRecipes();
        recipesProvider = new ListDataProvider<>(recipes);
        recipeGrid.setDataProvider(recipesProvider);
    }
}
