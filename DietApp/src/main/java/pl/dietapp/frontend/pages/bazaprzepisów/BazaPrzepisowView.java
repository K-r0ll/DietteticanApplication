package pl.dietapp.frontend.pages.bazaprzepisów;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;
import pl.dietapp.backend.model.SingleIngredient;
import pl.dietapp.backend.repositories.FoodCategoryRepository;
import pl.dietapp.backend.repositories.IngredientRepository;
import pl.dietapp.backend.model.Recipe;
import pl.dietapp.backend.repositories.MeasurementUnitsRepository;
import pl.dietapp.backend.repositories.RecipeRepository;
import pl.dietapp.frontend.pages.MainDashbordView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Stream;

@PageTitle("Baza Przepisów")
//@Route(value = "bazaprzepis", layout = MainDashbordView.class)
public class BazaPrzepisowView extends VerticalLayout {
    private List<SingleIngredient> ingreds;
    private Grid<Recipe> recipeGrid = new Grid<>(Recipe.class, false);
    private Grid<SingleIngredient> ingredientMainGrid = new Grid<>(SingleIngredient.class, false);
    private Grid<SingleIngredient> ingredientDialogGrid = new Grid<>(SingleIngredient.class, false);
    private Tab bazaPrzepisow;
    private Tab bazaSkladnikow;
    private Tabs tabs;
    private TextField searchRecipe, searchField;
    public static ListDataProvider<Recipe> recipesProvider;
    public static ListDataProvider<SingleIngredient> ingredsProvider, ingredsDialogProvider;
    private final VerticalLayout content = new VerticalLayout();;
    private IngredientFieldView ingredientFieldView;
    private RecipeForm recipeField;
    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;
    private MeasurementUnitsRepository measurementUnitsRepository;
    private FoodCategoryRepository foodCategoryRepository;


    public BazaPrzepisowView(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, MeasurementUnitsRepository measurementUnitsRepository, FoodCategoryRepository foodCategoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.measurementUnitsRepository = measurementUnitsRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        createRecipeGrid();
        createIngredientGrid();
        createIngredientDialogGrid();
        setSpacing(false);
        add(createTabs());
        setContent(tabs.getSelectedTab());
        content.setSpacing(false);
        add(tabs, content);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("text-align", "center");
    }

    private Tabs createTabs() {
        bazaPrzepisow = new Tab("Baza Przepisów");
        bazaSkladnikow = new Tab("Baza Składników");

        tabs = new Tabs(bazaPrzepisow, bazaSkladnikow);
        tabs.getStyle().set("margin-right", "auto");
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );
        return tabs;

    }

    private void setContent(Tab tab) {
        content.removeAll();
        content.getStyle().set("text-align", "center");
        content.setJustifyContentMode(JustifyContentMode.START);
        content.setDefaultHorizontalComponentAlignment(Alignment.START);
        content.setSizeFull();
        if (tab.equals(bazaPrzepisow)) {
            content.add(addHeader("Dostępne posiłki"), addTopBanner(true, true), recipeGrid);
        } else if (tab.equals(bazaSkladnikow)) {
            content.add(addHeader("Dostępne składniki"), addTopBanner(false, true), ingredientMainGrid);
        }
    }

    private HorizontalLayout addHeader(String header) {
        H3 naglowek = new H3(header);
        naglowek.getStyle().set("margin-right", "auto");
        add(naglowek);
        HorizontalLayout layout1 = createLayout();
        layout1.setPadding(true);
        layout1.add(naglowek);
        return layout1;
    }
    private HorizontalLayout addTopBanner(Boolean recipeButton, Boolean ingredientButton) {




            configSearchField(recipeButton, ingredientButton);


        HorizontalLayout layout = createLayout();
        layout.setPadding(true);
        Button addRecipeButton = button("Dodaj przepis");
        Button addIngredientButton = button("Dodaj składnik");
        addRecipeButton.addClickListener(event -> {
            Dialog dialogRecipe = new Dialog();
            VerticalLayout dialogRecipeLayout = createRecipeDialogLayout(dialogRecipe);
            dialogRecipe.add(dialogRecipeLayout);
            dialogRecipe.open();
        });
        addIngredientButton.addClickListener(event -> {
            Dialog dialogIngredient = new Dialog();
            VerticalLayout dialogIngredientLayout = createIngredientDialogLayout(dialogIngredient);
            dialogIngredient.add(dialogIngredientLayout);
            dialogIngredient.open();
            searchField.clear();
        });
        addIngredientButton.getStyle().set("margin-left", "auto"); // expands the empty space left of button two
        if (recipeButton && ingredientButton) {

            layout.add(searchRecipe, addIngredientButton, addRecipeButton);
        }else layout.add(searchRecipe, addIngredientButton);
        return layout;

    }



    private void configSearchField(Boolean recipeButton, Boolean ingredientButton) {
        searchRecipe = new TextField();
        searchRecipe.setPlaceholder("Search");
        searchRecipe.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchRecipe.setValueChangeMode(ValueChangeMode.EAGER);
        if (recipeButton && ingredientButton) {

            searchRecipe.addValueChangeListener(e -> {
                recipesProvider.refreshAll();
                System.out.println(searchRecipe.getValue());
            });
        } else searchRecipe.addValueChangeListener(e ->{
                        System.out.println(searchRecipe.getValue());
                ingredsProvider.refreshAll();
        });
    }

    private void createRecipeGrid() {

        Grid.Column<Recipe> recipeNameColumn = recipeGrid.addColumn(Recipe::getRecipeName).setSortable(true).setHeader("Nazwa przepisu");
        Grid.Column<Recipe> recipeCategoryColumn = recipeGrid.addColumn(Recipe::getCategory).setSortable(true).setHeader("Rodzaj dania");
        Grid.Column<Recipe> recipeShortDescription = recipeGrid.addColumn(Recipe::getRecipeShortDescription).setSortable(true).setHeader("Krótki opis");
        Grid.Column<Recipe> recipeKcal = recipeGrid.addColumn(Recipe::getKcal).setSortable(true).setHeader("Kalorie");
        recipeGrid.addColumn(
                new ComponentRenderer<>(Button::new, (button, recipe) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e ->  {
                        recipeRepository.deleteRecipe(recipe);

                        getRecipeData(recipeGrid);
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                }));
        recipeGrid.addComponentColumn(recipe -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
            Dialog dialog = new Dialog();
            VerticalLayout dialogLayout = createEditRecipeDialogLayout(dialog, recipe);
            dialog.add(dialogLayout);
            dialog.open();
            });
            return editButton;
        });
        getRecipeData(recipeGrid);

        recipeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        recipeGrid.setItemDetailsRenderer(createPersonDetailsRenderer());
    }
    private void getRecipeData(Grid<Recipe> grid) {
        List<Recipe> recipes = recipeRepository.getRecipes();
        recipesProvider = new ListDataProvider<>(recipes);
        grid.setDataProvider(recipesProvider);
        dynamicSearchRecipe();
    }
    private void dynamicSearchRecipe() {
        recipesProvider.addFilter(recipe -> {
            String searchTerm = searchRecipe.getValue().trim();


            if (searchTerm.isEmpty())
                return true;

            boolean matchesRecipeName = matchesTerm(recipe.getRecipeName(),
                    searchTerm);
            boolean matchesShortDescription = matchesTerm(recipe.getRecipeShortDescription(), searchTerm);
            boolean matchesKcal = matchesTerm(String.valueOf(recipe.getKcal()),
                    searchTerm);

            return matchesRecipeName || matchesShortDescription || matchesKcal;
        });
    }

    private void createIngredientGrid() {

        setIngredientGridColumnName(ingredientMainGrid);
        getIngredientData(ingredientMainGrid);
        dynamicSearchMainGridIngr();
        ingredientMainGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void createIngredientDialogGrid() {

        setIngredientGridColumnName(ingredientDialogGrid);
        getDialogIngredientData(ingredientDialogGrid);
        dynamicSearchInDialogIngr();
        ingredientDialogGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void getIngredientData(Grid<SingleIngredient> grid) {
        ingreds = ingredientRepository.findAllSingleIngr();
        ingredsProvider = new ListDataProvider<>(ingreds);
        grid.setDataProvider(ingredsProvider);
    }
    private void getDialogIngredientData(Grid<SingleIngredient> grid) {
        ingreds = ingredientRepository.findAllSingleIngr();
        ingredsDialogProvider = new ListDataProvider<>(ingreds);
        grid.setDataProvider(ingredsDialogProvider);
    }
    private void setIngredientGridColumnName(Grid<SingleIngredient> grid) {

        Grid.Column<SingleIngredient> recipeNameColumn = grid.addColumn(SingleIngredient::getIngredientName).setSortable(true).setHeader("Składnik");
        Grid.Column<SingleIngredient> recipeKcal = grid.addColumn(SingleIngredient::getKcalPer100).setSortable(true).setHeader("Kalorie");
        Grid.Column<SingleIngredient> recipeCarbo = grid.addColumn(SingleIngredient::getCarbohydratesPer100).setSortable(true).setHeader("Węglowodany");
        Grid.Column<SingleIngredient> recipeProtein = grid.addColumn(SingleIngredient::getProteinPer100).setSortable(true).setHeader("Białko");
        Grid.Column<SingleIngredient> recipeFat = grid.addColumn(SingleIngredient::getFatPer100).setSortable(true).setHeader("Tłuszcz");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, SingleIngredient) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                button.addClickListener(e ->  {
                    ingredientRepository.deleteSinlgeIngr(SingleIngredient.getNutritionProfileId(), SingleIngredient.getIngredientId());
                    getDialogIngredientData(ingredientMainGrid);
                    getIngredientData(ingredientMainGrid);});

                    button.setIcon(new Icon(VaadinIcon.TRASH));
                }));
        grid.addComponentColumn(singleIngredient -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                Dialog dialog = new Dialog();
                VerticalLayout dialogRecipeLayout = createEditIngredientLayout(dialog, singleIngredient);
                dialog.add(dialogRecipeLayout);
                dialog.open();
            });
            return editButton;
        });

    }

    private void dynamicSearchInDialogIngr() {
        ingredsDialogProvider.addFilter(ingred -> {
            String addTerm = searchField.getValue().trim();
            if (addTerm.isEmpty())
                return true;
            boolean matchesDialogIngrName = matchesTerm(ingred.getIngredientName(),
                    addTerm);
            boolean matchesDialogCarbo = matchesTerm(String.valueOf(ingred.getCarbohydratesPer100()), addTerm);
            boolean matchesDialogKcal = matchesTerm(String.valueOf(ingred.getKcalPer100()), addTerm);
            boolean matchesDialogProtein = matchesTerm(String.valueOf(ingred.getProteinPer100()), addTerm);
            boolean matchesDialogFat = matchesTerm(String.valueOf(ingred.getFatPer100()), addTerm);
            return matchesDialogIngrName || matchesDialogCarbo || matchesDialogKcal || matchesDialogProtein || matchesDialogFat  ;
        });
    }

    private void dynamicSearchMainGridIngr() {
        ingredsProvider.addFilter(ingred -> {
            String addTerm = searchRecipe.getValue().trim();
            if (addTerm.isEmpty())
                return true;
            boolean matchesDialogIngrName = matchesTerm(ingred.getIngredientName(),
                    addTerm);
            boolean matchesDialogCarbo = matchesTerm(String.valueOf(ingred.getCarbohydratesPer100()), addTerm);
            boolean matchesDialogKcal = matchesTerm(String.valueOf(ingred.getKcalPer100()), addTerm);
            boolean matchesDialogProtein = matchesTerm(String.valueOf(ingred.getProteinPer100()), addTerm);
            boolean matchesDialogFat = matchesTerm(String.valueOf(ingred.getFatPer100()), addTerm);
            return matchesDialogIngrName || matchesDialogCarbo || matchesDialogKcal || matchesDialogProtein || matchesDialogFat  ;
        });
    }


    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
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
            skladnikiRecipe.setValue(String.valueOf(recipe.getIngredientListDescription()));


        }
    }
        public void endEditing(Dialog dialog) {
            getDialogIngredientData(ingredientMainGrid);
            getIngredientData(ingredientMainGrid);
            ingredientFieldView.clearIngredientFields();
            dialog.close();
        }

        private Button exitButton(Dialog dialog) {
            Button exitButton = new Button("Wyjdź", e -> {
                endEditing(dialog);
            });
            exitButton.getStyle().set("margin-left", "auto");
            return exitButton;
        }

        private void setEditFormValue(SingleIngredient singleIngredient) {
            ingredientFieldView.getIngredient().setValue(singleIngredient.getIngredientName());
            ingredientFieldView.getCarbo().setValue((double) singleIngredient.getCarbohydratesPer100());
            ingredientFieldView.getFat().setValue((double) singleIngredient.getFatPer100());
            ingredientFieldView.getKcal().setValue((double) singleIngredient.getKcalPer100());
            ingredientFieldView.getProtein().setValue((double) singleIngredient.getProteinPer100());
        }

    private VerticalLayout createEditIngredientLayout(Dialog dialog, SingleIngredient singleIngredient) {
        H2 headline = new H2("Edytuj składnik");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        ingredientFieldView = new IngredientFieldView();
        setEditFormValue(singleIngredient);
        HorizontalLayout buttonLayout = buttonLayoutEditing(dialog, singleIngredient);
        dialog.getElement().setProperty("noCloseOnEsc", true).setProperty("noCloseOnOutsideClick", true);
        VerticalLayout dialogLayout = new VerticalLayout(headline, ingredientFieldView, buttonLayout, exitButton(dialog));
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "1000").set("max-width", "100%").set("height", "400");
        return dialogLayout;
    }

    private HorizontalLayout buttonLayoutEditing(Dialog dialog, SingleIngredient singleIngredient) {
        Button deleteButton = new Button("Usuń");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener( e -> {
            ingredientRepository.deleteSinlgeIngr(singleIngredient.getNutritionProfileId(),singleIngredient.getIngredientId());
            endEditing(dialog);
        });
        Button addButton = new Button("Zmień", e-> {
            ingredientRepository.updateSingleIngr(ingredientFieldView.getIngredient().getValue(), singleIngredient.getIngredientId(), singleIngredient.getNutritionProfileId(), ingredientFieldView.getKcal().getValue(), ingredientFieldView.getProtein().getValue(), ingredientFieldView.getCarbo().getValue(), ingredientFieldView.getFat().getValue());
            endEditing(dialog);
            getRecipeData(recipeGrid);

        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(deleteButton, addButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return buttonLayout;
    }


    private VerticalLayout createIngredientDialogLayout(Dialog dialog) {
        H2 headline = new H2("Dodaj nowy składnik");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        ingredientFieldView = new IngredientFieldView();
        dialog.getElement().setProperty("noCloseOnEsc", true).setProperty("noCloseOnOutsideClick", true);

        searchField = new TextField();
        searchField.setPlaceholder("Search");
        searchField.getStyle().set("margin-right", "auto");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> ingredsDialogProvider.refreshAll());


        HorizontalLayout buttonLayout = buttonLayoutCreateIngr(dialog);
        VerticalLayout dialogLayout = new VerticalLayout(headline, ingredientFieldView, buttonLayout, ingredientDialogGrid, exitButtonAddIngredient(dialog));
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "1000").set("max-width", "100%").set("height", "700");

        return dialogLayout;
    }
    private Button exitButtonAddIngredient(Dialog dialog) {
        Button exitButton = new Button("Wyjdź", e -> {

            getIngredientData(ingredientMainGrid);
            dynamicSearchInDialogIngr();
            ingredientFieldView.clearIngredientFields();
            dialog.close();

        });
        exitButton.getStyle().set("margin-left", "auto");
        return exitButton;
    }
    private HorizontalLayout buttonLayoutCreateIngr(Dialog dialog) {
        Button cancelButton = new Button("Wyczyść", e -> {
            ingredientFieldView.clearIngredientFields();
        });
        Button addButton = new Button("Dodaj", e-> {
            ingredientRepository.addSinglIngr(ingredientFieldView.getIngredient().getValue(), ingredientFieldView.getKcal().getValue(), ingredientFieldView.getProtein().getValue(), ingredientFieldView.getCarbo().getValue(), ingredientFieldView.getFat().getValue());
            getDialogIngredientData(ingredientDialogGrid);
            getDialogIngredientData(ingredientMainGrid);
            dynamicSearchInDialogIngr();
            ingredientFieldView.clearIngredientFields();
            dialog.close();
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(searchField, cancelButton, addButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return buttonLayout;
    }

    private VerticalLayout createRecipeDialogLayout(Dialog dialog) {
        H2 headline = new H2("Dodaj nowy przepis");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        dialog.getElement().setProperty("noCloseOnEsc", true).setProperty("noCloseOnOutsideClick", true);



        recipeField =RecipeForm.createRecipeForm(ingredientRepository, recipeRepository, measurementUnitsRepository, foodCategoryRepository);
        Button cancelButton = new Button("Wyjdź", e -> dialog.close());
        Button saveButton = new Button("Dodaj", e -> {
                recipeField.addNewRecipe();
                getRecipeData(recipeGrid);
                getIngredientData(ingredientMainGrid);
                getDialogIngredientData(ingredientDialogGrid);
                dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(JustifyContentMode.END);


        VerticalLayout dialogLayout = new VerticalLayout(headline,recipeField,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.setHeight("1000px");
        dialogLayout.setWidth("1200px");


        return dialogLayout;
    }
    private VerticalLayout createEditRecipeDialogLayout(Dialog dialog, Recipe recipe) {
        H2 headline = new H2("Edytuj istniejący przepis");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        dialog.getElement().setProperty("noCloseOnEsc", true).setProperty("noCloseOnOutsideClick", true);



        recipeField =RecipeForm.createEditRecipeForm(ingredientRepository, recipeRepository, measurementUnitsRepository, foodCategoryRepository, recipe);
        Button cancelButton = new Button("Wyjdź", e -> dialog.close());
        Button saveButton = new Button("Zapisz", e -> {
            recipeField.editRecipe(recipe);
            getRecipeData(recipeGrid);
            getIngredientData(ingredientMainGrid);
            getDialogIngredientData(ingredientDialogGrid);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(JustifyContentMode.END);


        VerticalLayout dialogLayout = new VerticalLayout(headline,recipeField,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.setHeight("1000px");
        dialogLayout.setWidth("1200px");


        return dialogLayout;
    }



    private HorizontalLayout createLayout() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidthFull();
        add(hl);
        return hl;
    }

    private Button button(String caption) {
        Button button = new Button(caption);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }




}
