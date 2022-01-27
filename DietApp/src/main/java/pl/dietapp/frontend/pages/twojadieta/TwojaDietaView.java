package pl.dietapp.frontend.pages.twojadieta;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import pl.dietapp.backend.repositories.RecipeRepository;
import pl.dietapp.frontend.pages.MainDashbordView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@PageTitle("Twoja Dieta")
//@Route(value = "dieta", layout = MainDashbordView.class)
public class TwojaDietaView extends VerticalLayout {
    private RecipeRepository recipeRepository;
    private LocalDate date = LocalDate.now();
    private H3 naglowek;
    public TwojaDietaView(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;


//        Image img = new Image("images/empty-plant.png", "placeholder plant");
//        img.setWidth("200px");
//        add(img);
//
//        add(new H2("This place intentionally left empty"));
//        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        DailyGrid dailyGrid = new DailyGrid(recipeRepository);

        add(addHeader(dietaString()),dailyGrid);
        setSpacing(false);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
    private String dietaString() {
        String dieta = "Twoja dieta na dzień ";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        return dieta+" "+date.format(formatter);
    }

    private HorizontalLayout addHeader(String header) {
        naglowek = new H3(header);
        Icon icon = new Icon("calendar");
        naglowek.getStyle().set("margin-right", "auto");
        add(naglowek);
        HorizontalLayout layout1 = createLayout();

        Button nextDay = new Button("", new Icon(VaadinIcon.ARROW_RIGHT));
        nextDay.addClickListener(e -> {
            date = date.plusDays(1);
            System.out.println(date);
            naglowek.setText(dietaString());
            System.out.println(naglowek);
        });
        Button beforeDay = new Button("", new Icon(VaadinIcon.ARROW_LEFT));
        beforeDay.addClickListener(e -> {
            date = date.minusDays(1);
            System.out.println(date);
            naglowek.setText(dietaString());
            System.out.println(naglowek);
        });
        layout1.setPadding(true);
        HorizontalLayout buttons = createLayout();
        buttons.add(beforeDay,nextDay);
        buttons.setVerticalComponentAlignment(Alignment.CENTER, beforeDay,nextDay);
        nextDay.getStyle().set("margin-right", "auto");

        layout1.add(icon, naglowek,buttons);
        layout1.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, icon,naglowek, buttons);
        return layout1;
    }

    private HorizontalLayout createLayout() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidthFull();
        add(hl);
        return hl;
    }

}
