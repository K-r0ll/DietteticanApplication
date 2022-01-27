package pl.dietapp.frontend.pages.mojedane;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import pl.dietapp.frontend.pages.MainDashbordView;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.dietapp.frontend.pages.bazaprzepisów.BazaPrzepisowView;

@PageTitle("Moje Dane")
//@Route(value = "dane", layout = MainDashbordView.class)
@Uses(Icon.class)
public class MojeDaneView extends VerticalLayout {


    public MojeDaneView() {
        setSpacing(false);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        add(addHeader("Moje dane"),createPersonDetails());

    }

    private HorizontalLayout addHeader(String header) {
        H3 naglowek = new H3(header);

        naglowek.getStyle().set("margin-right", "auto");
        add(naglowek);
        HorizontalLayout layout1 = createLayout();
        layout1.setPadding(true);
        Icon icon = new Icon("user-card");

        layout1.add(icon, naglowek);
        layout1.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, icon, naglowek);
        return layout1;
    }

    private VerticalLayout createPersonDetails() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();
        HorizontalLayout layout1 = createLayout();
        HorizontalLayout layout2 = createLayout();

        H4 imie = new H4("Karol");
        String odpPlec = "Mężczyzna";
        TextField plec = new TextField("Płeć");
        plec.setPrefixComponent(VaadinIcon.USER.create());
        plec.setValue(odpPlec);
        int odpWiek = 25;
        TextField wiek = new TextField("Wiek");
        wiek.setPrefixComponent(VaadinIcon.TIMER.create());
        wiek.setValue(String.valueOf(odpWiek));
        double odpWzrost = 1.84;
        TextField wzrost = new TextField("Wzrost");
        wzrost.setPrefixComponent(VaadinIcon.CHILD.create());
        wzrost.setValue(String.valueOf(odpWzrost+" m"));
        double odpWaga = 84;
        TextField waga = new TextField("Waga");
        waga.setPrefixComponent(VaadinIcon.SCALE.create());
        waga.setValue(odpWaga+" kg");
        double odpBmi = 24.81;
        TextField bmi = new TextField("Aktualne BMI");
        bmi.setPrefixComponent(VaadinIcon.CALC_BOOK.create());
        bmi.setValue(String.valueOf(odpBmi));
        String odpTyp = "Siedząca";
        TextField typpracy = new TextField("Typ pracy");
        typpracy.setPrefixComponent(VaadinIcon.WORKPLACE.create());
        typpracy.setValue(odpTyp);

        FormLayout formLayout = new FormLayout();
        formLayout.add(plec, wiek, wzrost, waga, bmi, typpracy);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3)
        );

        plec.setReadOnly(true);
        wiek.setReadOnly(true);
        wzrost.setReadOnly(true);
        waga.setReadOnly(true);
        bmi.setReadOnly(true);
        typpracy.setReadOnly(true);
        layout.add(imie,formLayout);
        return layout;
    }


    private HorizontalLayout createLayout() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidthFull();
        add(hl);
        return hl;
    }


}
