package pl.dietapp.frontend.pages.bazaprzepisów;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;

public class IngredientFieldView extends Div {
    private FormLayout formLayout;
    private TextField ingredient;
    private NumberField kcal;

    public NumberField getKcal() {
        return kcal;
    }

    public void setKcal(NumberField kcal) {
        this.kcal = kcal;
    }

    public NumberField getCarbo() {
        return carbo;
    }

    public void setCarbo(NumberField carbo) {
        this.carbo = carbo;
    }

    public NumberField getFat() {
        return fat;
    }

    public void setFat(NumberField fat) {
        this.fat = fat;
    }

    public NumberField getProtein() {
        return protein;
    }

    public void setProtein(NumberField protein) {
        this.protein = protein;
    }

    private NumberField carbo;
    private NumberField fat;
    private NumberField protein;

    public TextField getIngredient() {
        return ingredient;
    }

    public void setIngredient(TextField ingredient) {
        this.ingredient = ingredient;
    }

    public IngredientFieldView() {
        ingredient = new TextField("Nazwa składnika");
        kcal = new NumberField("Ilość kalorii");
        carbo = new NumberField("Ilość węglowodanów");
        fat = new NumberField("Ilość tłuszczu");
        protein = new NumberField("Ilość białka");
        ingredient.setValueChangeMode(ValueChangeMode.EAGER);
        ingredient.addValueChangeListener(e -> BazaPrzepisowView.ingredsProvider.refreshAll());

        FormLayout formLayout = new FormLayout();
        formLayout.add(ingredient, kcal, carbo, fat, protein);
        formLayout.setColspan(ingredient, 3);
        formLayout.setColspan(kcal, 3);
        formLayout.setColspan(carbo, 2);
        formLayout.setColspan(fat, 2);
        formLayout.setColspan(protein, 2);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 6)
        );
        add(formLayout);
    }

    public void clearIngredientFields() {
        ingredient.clear();
        kcal.clear();
        carbo.clear();
        fat.clear();
        protein.clear();
    }

    public void onlyReadableFields(Boolean set) {
        ingredient.setReadOnly(set);
        kcal.setReadOnly(set);
        carbo.setReadOnly(set);
        fat.setReadOnly(set);
        protein.setReadOnly(set);
    }


}

