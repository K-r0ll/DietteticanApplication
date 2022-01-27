package pl.dietapp.frontend.pages.bazaprzepisów;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.decimal4j.util.DoubleRounder;
import pl.dietapp.backend.model.*;
import pl.dietapp.backend.repositories.FoodCategoryRepository;
import pl.dietapp.backend.repositories.IngredientRepository;
import pl.dietapp.backend.repositories.MeasurementUnitsRepository;
import pl.dietapp.backend.repositories.RecipeRepository;
import pl.dietapp.backend.services.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeForm extends Div {
    private List<SingleIngredient> ingredsAddList;
    private List<RecipeIngredient> recipeIngredientList;
    private List<FoodCategory> foodCategories;
    private ListDataProvider<SingleIngredient> ingredsAddProvider;
    private ListDataProvider<RecipeIngredient> ingredsProvider;
    private TextField recipe_name, recipe_shortdescritption,searchField;
    private NumberField recipe_kcal,recipe_carbo,recipe_protein,recipe_fat, amount;
    private Select<FoodCategory> selectFoodCategory;
    private Select<MeasurementUnits> selectJednostka;
    private TextArea recipe_description;
    private Grid<RecipeIngredient> ingredGrid;
    private Grid<SingleIngredient> ingredAddingGrid;
    private VerticalLayout gridComponent;
    private Button addIngredientButton,addSIngredientInAddToRecipe;
    private Grid.Column<RecipeIngredient> recipeNameColumn, recipeKcal, recipeCarbo, recipeProtein, recipeFat,recipeIlosc;
    private Grid.Column<SingleIngredient> recipeNameColumnS, recipeKcalS, recipeCarboS, recipeProteinS, recipeFatS;
    private IngredientFieldView ingredientFieldView;
    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;
    private MeasurementUnitsRepository measurementUnitsRepository;
    private FoodCategoryRepository foodCategoryRepository;
    private RadioButtonGroup<String> radioGroup;
    private SingleIngredient selectedIngredient, newIngred;
    private RecipeService recipeService;
    private Recipe recipe;

    public static RecipeForm createRecipeForm(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, MeasurementUnitsRepository measurementUnitsRepository, FoodCategoryRepository foodCategoryRepository) {
        RecipeForm recipeForm = new RecipeForm(ingredientRepository, recipeRepository, measurementUnitsRepository, foodCategoryRepository);
        recipeForm.createGridAndButton();
        recipeForm.configFormLayout();
        recipeForm.add(recipeForm.recipeFormLayout());
        return recipeForm;
    }

    public static RecipeForm createEditRecipeForm(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, MeasurementUnitsRepository measurementUnitsRepository, FoodCategoryRepository foodCategoryRepository, Recipe recipe) {
        RecipeForm recipeForm = new RecipeForm(ingredientRepository, recipeRepository, measurementUnitsRepository, foodCategoryRepository);
        recipeForm.createGridAndButton();
        recipeForm.configFormLayout();
        recipeForm.add(recipeForm.recipeFormLayout());
        recipeForm.recipeToForm(recipe);
        recipeForm.getIngredData();
        return recipeForm;
    }
    public RecipeForm(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, MeasurementUnitsRepository measurementUnitsRepository, FoodCategoryRepository foodCategoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.measurementUnitsRepository = measurementUnitsRepository;
        this.recipeService = new RecipeService();
        this.foodCategoryRepository = foodCategoryRepository;
    }

    private VerticalLayout createGridAndButton() {
        recipeIngredientList = new ArrayList<>();
        ingredGrid = new Grid<>();
        ingredGrid.setMinHeight("200px");
        ingredGrid.setMaxHeight("200px");
        gridComponent = new VerticalLayout();
        addIngredientButton = new Button("Dodaj składnik do przepisu");
        addIngredientButton.getStyle().set("margin-left", "auto");
        addIngredientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        addIngredientButton.addClickListener(e -> {
            Dialog dialogAdd = new Dialog();
            VerticalLayout dialogAddlay = createAddIngredientDialogLayout(dialogAdd);
            dialogAdd.add(dialogAddlay);
            dialogAdd.open();
            addSIngredientInAddToRecipe.setEnabled(false);
        });
        gridComponent.add(addIngredientButton,ingredGrid);
        addColumnMainGrid(ingredGrid);
        return gridComponent;
    }
    private void configFormLayout() {
        recipe_name = new TextField("Nazwa przepisu");

        recipe_description = new TextArea("Sposób przygotowania");
        recipe_description.setMinHeight("200px");
        recipe_description.setMaxHeight("200px");

        recipe_shortdescritption = new TextField("Opis");
        recipe_kcal = new NumberField("Ilość Kalorii");
        recipe_carbo = new NumberField("Ilość węglowodanów");
        recipe_protein = new NumberField("Ilość białka");
        recipe_fat = new NumberField("Ilość tłuszczy");
        recipe_kcal.setReadOnly(true);
        recipe_carbo.setReadOnly(true);
        recipe_protein.setReadOnly(true);
        recipe_fat.setReadOnly(true);
        selectFoodCategory = new Select<>();
        selectFoodCategory.setLabel("Wybierz rodzaj dania");
        foodCategories = foodCategoryRepository.findAll();
        selectFoodCategory.setItems(foodCategories);

    }
    private void recipeToForm(Recipe recipe) {
        recipe_name.setValue(recipe.getRecipeName());
        recipe_description.setValue(recipe.getRecipeDescription());
        recipe_shortdescritption.setValue(recipe.getRecipeShortDescription());
        selectFoodCategory.setValue(setCategory(recipe));
//        recipe_kcal.setValue((double) recipeService.getKcalRecipe(recipe));
//        recipe_fat.setValue(recipe.getIngredList().stream().mapToDouble(RecipeIngredient::getFatPer100).sum());
//        recipe_carbo.setValue(recipe.getIngredList().stream().mapToDouble(RecipeIngredient::getCarbohydratesPer100).sum());
//        recipe_protein.setValue(recipe.getIngredList().stream().mapToDouble(RecipeIngredient::getProteinPer100).sum());
        recipeIngredientList = recipe.getIngredList();
    }
    private FoodCategory setCategory(Recipe recipe) {
        Optional<FoodCategory> foodCategory = foodCategories.stream().filter(foodCategory1 -> foodCategory1.getCategoryId() == recipe.getCategoryId()).findFirst();
        FoodCategory foodCategory1 = foodCategory.get();
        return foodCategory1;
    }

    private FormLayout recipeFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(recipe_name, selectFoodCategory);
        formLayout.add(recipe_shortdescritption, recipe_kcal, recipe_carbo, recipe_protein, recipe_fat, gridComponent, recipe_description);
        formLayout.add(recipe_description);
        formLayout.setColspan(recipe_name, 2);
        formLayout.setColspan(selectFoodCategory, 2);
        formLayout.setColspan(recipe_shortdescritption, 4);
        formLayout.setColspan(recipe_kcal, 1);
        formLayout.setColspan(recipe_carbo, 1);
        formLayout.setColspan(recipe_protein, 1);
        formLayout.setColspan(recipe_fat, 1);
        formLayout.setColspan(gridComponent, 4);
        formLayout.setColspan(recipe_description, 4);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 4)
        );
        return formLayout;
    }

    private void addColumnMainGrid(Grid<RecipeIngredient> grid) {
        recipeNameColumn = grid.addColumn(RecipeIngredient::getIngredientName).setSortable(true).setHeader("Składnik").setAutoWidth(true);
        recipeIlosc = grid.addColumn(RecipeIngredient::getAmountMeasure).setSortable(true).setHeader("Ilość");
        recipeKcal = grid.addColumn(RecipeIngredient::getKcalPer100).setSortable(true).setHeader("Kalorie");
        recipeCarbo = grid.addColumn(RecipeIngredient::getCarbohydratesPer100).setSortable(true).setHeader("Węglowodany");
        recipeProtein = grid.addColumn(RecipeIngredient::getProteinPer100).setSortable(true).setHeader("Białko");
        recipeFat = grid.addColumn(RecipeIngredient::getFatPer100).setSortable(true).setHeader("Tłuszcz");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, Ingred) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e ->  {
                        recipeIngredientList.removeIf(ingred -> ingred.getIngredientId() == Ingred.getIngredientId());
                        getIngredData();
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                }));
        grid.addComponentColumn(ingred -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                Dialog dialog = new Dialog();
                VerticalLayout dialogRecipeLayout = editIngredientDialogLayout(dialog, ingred);
                dialog.add(dialogRecipeLayout);
                dialog.open();
            });
            return editButton;
        });
        grid.addThemeVariants(GridVariant.LUMO_COMPACT);
    }
    private void addColumnAddingGrid(Grid<SingleIngredient> grid) {
        recipeNameColumnS = grid.addColumn(SingleIngredient::getIngredientName).setSortable(true).setHeader("Składnik");
        recipeKcalS = grid.addColumn(SingleIngredient::getKcalPer100).setSortable(true).setHeader("Kalorie");
        recipeCarboS = grid.addColumn(SingleIngredient::getCarbohydratesPer100).setSortable(true).setHeader("Węglowodany");
        recipeProteinS = grid.addColumn(SingleIngredient::getProteinPer100).setSortable(true).setHeader("Białko");
        recipeFatS = grid.addColumn(SingleIngredient::getFatPer100).setSortable(true).setHeader("Tłuszcz");
    }

    private VerticalLayout createAddIngredientDialogLayout(Dialog dialog) {
        H2 headline = new H2("Dodaj nowy składnik");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        ingredientFieldView = new IngredientFieldView();
        ingredientFieldView.onlyReadableFields(true);
        dialog.getElement().setProperty("noCloseOnEsc", true).setProperty("noCloseOnOutsideClick", true);
        searchField = new TextField();
        configSearchField();
        ingredAddingGrid = new Grid<>();
        addColumnAddingGrid(ingredAddingGrid);
        FormLayout measurementLayout = measurementLayoutCreateIngr();
        gridListener();
        createRadioButton();
        getIngredAddingData();
        dynamicAddSearchInDialogIngr();
        HorizontalLayout buttonLayout = buttonLayoutCreateIngr(dialog);

        VerticalLayout dialogLayout = new VerticalLayout(headline, radioGroup, ingredientFieldView,measurementLayout, buttonLayout, ingredAddingGrid, exitButton(dialog));
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "1000").set("max-width", "100%").set("height", "700");

        return dialogLayout;
    }

    private VerticalLayout editIngredientDialogLayout(Dialog dialog, RecipeIngredient recipeIngredient) {
        H2 headline = new H2("Edytuj składnik");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        ingredientFieldView = new IngredientFieldView();
        ingredientFieldView.onlyReadableFields(true);
        ingredientFieldView.getIngredient().setValue(recipeIngredient.getIngredientName());
        ingredientFieldView.getCarbo().setValue(recipeIngredient.getCarbohydratesPer100());
        ingredientFieldView.getFat().setValue(recipeIngredient.getFatPer100());
        ingredientFieldView.getKcal().setValue(recipeIngredient.getKcalPer100());
        ingredientFieldView.getProtein().setValue(recipeIngredient.getProteinPer100());
        dialog.getElement().setProperty("noCloseOnEsc", true).setProperty("noCloseOnOutsideClick", true);
        searchField = new TextField();
        ingredAddingGrid = new Grid<>();
        addColumnAddingGrid(ingredAddingGrid);
        FormLayout measurementLayout = measurementLayoutCreateIngr(recipeIngredient);
        gridListener();
        HorizontalLayout buttonLayout = editButtonLayoutCreateIngr(dialog, recipeIngredient);

        VerticalLayout dialogLayout = new VerticalLayout(headline, ingredientFieldView,measurementLayout, buttonLayout, exitButton(dialog));
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "1000").set("max-width", "100%").set("height", "700");

        return dialogLayout;
    }

    private void gridListener() {
        ingredAddingGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        ingredAddingGrid.addSelectionListener(e -> {

            selectedIngredient = e.getFirstSelectedItem().get();
            amount.setValue(0D);
            ingredientFieldView.getIngredient().setValue(selectedIngredient.getIngredientName());
            ingredientFieldView.getCarbo().setValue(Double.valueOf(selectedIngredient.getCarbohydratesPer100()));
            ingredientFieldView.getFat().setValue(Double.valueOf(selectedIngredient.getFatPer100()));
            ingredientFieldView.getKcal().setValue(Double.valueOf(selectedIngredient.getKcalPer100()));
            ingredientFieldView.getProtein().setValue(Double.valueOf(selectedIngredient.getProteinPer100()));
        });
    }
    private void dynamicAddSearchInDialogIngr() {
        ingredsAddProvider.addFilter(ingred -> {
            String addTerm = searchField.getValue().trim();
            System.out.println(addTerm);
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


    private void getIngredAddingData() {
        ingredsAddList = ingredientRepository.findAllSingleIngr();
        ingredsAddProvider = new ListDataProvider<>(ingredsAddList);
        ingredAddingGrid.setDataProvider(ingredsAddProvider);
    }
    private void getIngredData() {
        ingredsProvider = new ListDataProvider<>(recipeIngredientList);
        ingredGrid.setDataProvider(ingredsProvider);
        setKFCPvariable();

    }
    private void setKFCPvariable() {
        recipe_kcal.setValue(recipeIngredientList.stream().mapToDouble(RecipeIngredient::getKcalPer100).sum());
        recipe_fat.setValue(recipeIngredientList.stream().mapToDouble(RecipeIngredient::getFatPer100).sum());
        recipe_carbo.setValue(recipeIngredientList.stream().mapToDouble(RecipeIngredient::getCarbohydratesPer100).sum());
        recipe_protein.setValue(recipeIngredientList.stream().mapToDouble(RecipeIngredient::getProteinPer100).sum());
    }
    private Button exitButton(Dialog dialog) {
        Button exitButton = new Button("Wyjdź", e -> {
            dialog.close();
            searchField.clear();
        });
        exitButton.getStyle().set("margin-left", "auto");
        exitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return exitButton;
    }
    private void createRadioButton() {
        radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Czy chcesz użyć istniejącego składnika?");
        radioGroup.setItems("Użyj istniejącego", "Dodaj nowy");
        radioGroup.setValue("Użyj istniejącego");

        radioGroup.addValueChangeListener(e -> {
           if (radioGroup.getValue().equals("Użyj istniejącego")) {
               ingredientFieldView.onlyReadableFields(true);
               ingredAddingGrid.setEnabled(true);
               addSIngredientInAddToRecipe.setEnabled(false);
               amount.setEnabled(true);
               selectJednostka.setEnabled(true);
           } else {
               ingredientFieldView.onlyReadableFields(false);
               ingredientFieldView.clearIngredientFields();
               ingredAddingGrid.setEnabled(false);
               addSIngredientInAddToRecipe.setEnabled(true);
               amount.setEnabled(false);
               selectJednostka.setEnabled(false);
           }
        });
    }
    private void configSearchField() {
        searchField.setPlaceholder("Search");
        searchField.getStyle().set("margin-right", "auto");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> ingredsAddProvider.refreshAll());
    }

    private HorizontalLayout buttonLayoutCreateIngr(Dialog dialog) {

        addSIngredientInAddToRecipe = new Button("Dodaj do bazy danych");
        addSIngredientInAddToRecipe.addClickListener(event -> {
            selectedIngredient = new SingleIngredient(ingredientFieldView.getIngredient().getValue(), ingredientFieldView.getKcal().getValue(), ingredientFieldView.getProtein().getValue(), ingredientFieldView.getCarbo().getValue(), ingredientFieldView.getFat().getValue());
            int id = ingredientRepository.addSinglIngr(selectedIngredient);
            selectedIngredient.setIngredientId(id);
            ingredientFieldView.onlyReadableFields(true);
            addSIngredientInAddToRecipe.setEnabled(false);
            getIngredAddingData();
            amount.setEnabled(true);
            selectJednostka.setEnabled(true);

        });
        Button addButton = new Button("Dodaj", e-> {
            if (radioGroup.getValue().equals("Użyj istniejącego")) {

                recipeIngredientList.add(new RecipeIngredient(selectedIngredient.getIngredientId(), selectedIngredient.getIngredientName(), selectedIngredient.getNutritionProfileId(), selectedIngredient.getKcalPer100(), selectedIngredient.getProteinPer100(), selectedIngredient.getCarbohydratesPer100(), selectedIngredient.getFatPer100(), selectJednostka.getValue().getId(), selectJednostka.getValue().getMeasurement_description(), amount.getValue()));
            } else  {
                recipeIngredientList.add(new RecipeIngredient(selectedIngredient,amount.getValue(), selectJednostka.getValue().getId(), selectJednostka.getValue().getMeasurement_description()));
                getIngredAddingData();
                dynamicAddSearchInDialogIngr();
            }
            dialog.close();
            getIngredData();


        });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(searchField, addSIngredientInAddToRecipe, addButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return buttonLayout;
    }
    private HorizontalLayout editButtonLayoutCreateIngr(Dialog dialog, RecipeIngredient recipeIngredient) {

        Button edit = new Button("Zmień", e-> {
            RecipeIngredient recipeIngredientToChange = recipeIngredientList.stream().filter(ingred1 -> ingred1.getIngredientId()== recipeIngredient.getIngredientId()).findFirst().get();
            recipeIngredientToChange.changeIngred(selectJednostka.getValue().getId(), selectJednostka.getValue().getMeasurement_description(), amount.getValue());
            dialog.close();
            getIngredData();
        });
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(edit);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return buttonLayout;
    }

    private FormLayout measurementLayoutCreateIngr() {

        FormLayout formLayout = new FormLayout();
        amount = new NumberField("Podaj ilość");
        amount.setValueChangeMode(ValueChangeMode.EAGER);
        amount.addValueChangeListener(e -> refreshAll());
        createJednostkaSelect();

        formLayout.add(amount, selectJednostka);
        formLayout.setColspan(amount, 2);
        formLayout.setColspan(selectJednostka, 2);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 6)
        );

        return formLayout;
    }

    private FormLayout measurementLayoutCreateIngr(RecipeIngredient recipeIngredient) {

        FormLayout formLayout = new FormLayout();
        amount = new NumberField("Podaj ilość");
        amount.setValueChangeMode(ValueChangeMode.EAGER);
        amount.addValueChangeListener(e -> refreshAll(recipeIngredient));
        createJednostkaSelect();
        formLayout.add(amount, selectJednostka);
        formLayout.setColspan(amount, 2);
        formLayout.setColspan(selectJednostka, 2);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 6)
        );

        return formLayout;
    }
    private Select createJednostkaSelect() {
        selectJednostka = new Select<>();
        selectJednostka.setLabel("Wybierz jednostki");
        List<MeasurementUnits> measurementUnits = measurementUnitsRepository.findAll();
        selectJednostka.setItems(measurementUnits);
        selectJednostka.setValue(measurementUnits.get(0));


        return selectJednostka;
    }
    private void refreshAll() {

        ingredientFieldView.getProtein().setValue(DoubleRounder.round(selectedIngredient.getProteinPer100()*(amount.getValue()/100),0));
        ingredientFieldView.getKcal().setValue(DoubleRounder.round(selectedIngredient.getKcalPer100()*(amount.getValue()/100),0));
        ingredientFieldView.getFat().setValue(DoubleRounder.round(selectedIngredient.getFatPer100()*(amount.getValue()/100),0));
        ingredientFieldView.getCarbo().setValue(DoubleRounder.round(selectedIngredient.getCarbohydratesPer100()*(amount.getValue()/100),0));
    }
    private void refreshAll(RecipeIngredient recipeIngredient) {

        ingredientFieldView.getProtein().setValue(DoubleRounder.round(recipeIngredient.getProteinPer100()*(amount.getValue()/ recipeIngredient.getQtyAmount()),0));
        ingredientFieldView.getKcal().setValue(DoubleRounder.round(recipeIngredient.getKcalPer100()*(amount.getValue()/ recipeIngredient.getQtyAmount()),0));
        ingredientFieldView.getFat().setValue(DoubleRounder.round(recipeIngredient.getFatPer100()*(amount.getValue()/ recipeIngredient.getQtyAmount()),0));
        ingredientFieldView.getCarbo().setValue(DoubleRounder.round(recipeIngredient.getCarbohydratesPer100()*(amount.getValue()/ recipeIngredient.getQtyAmount()),0));
    }

    public void addNewRecipe() {
    recipe = recipeService.createRecipe(selectFoodCategory.getValue().getCategoryId(), recipe_name.getValue(), recipe_description.getValue(), recipe_shortdescritption.getValue(), recipeIngredientList);
    recipeRepository.createRecipe(recipe);

    }

    public void editRecipe(Recipe recipeToChange) {
        recipeRepository.deleteRecipe(recipeToChange);
        recipe = recipeService.createRecipe(selectFoodCategory.getValue().getCategoryId(), recipe_name.getValue(), recipe_description.getValue(), recipe_shortdescritption.getValue(), recipeIngredientList);
        recipeRepository.createRecipe(recipe);

    }
}
