package pl.dietapp.frontend.pages.mainpage;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.templatemodel.TemplateModel;
import pl.dietapp.frontend.pages.MainView;

import javax.annotation.security.PermitAll;

@Route(value = "mainpage", layout = MainView.class)
@RouteAlias(value = "")
@PageTitle("MainPage")
@JsModule("./src/views/MainPagee.js")
public class MainPage extends PolymerTemplate<TemplateModel> {

    @Id("header")
    Header header;

    @Id("main")
    Div main;

    @Id("footer")
    Div footer;

    public MainPage() {
        createHeader();

       // main.add(new H1("Sticky footer with fixed navbar"));
//        Image img = new Image("images/banner.jpg", "placeholder");
//        img.setWidth("900px");
//        img.setHeight("900px");


        VerticalLayout layout1 = new VerticalLayout();
        layout1.getStyle().set("padding-left", "20%");
        layout1.getStyle().set("padding-right", "20%");
       // layout1.getStyle().set("background-color", "#F5F5F5");
        layout1.getStyle().set("flex-shrink", "0");

        layout1.setHeight("400px");
        layout1.setWidth("1200px");
        layout1.setSpacing(false);

        layout1.getStyle().set("background","url(images/banner.gif)");

        H1 header = new H1("Diet Application");
        header.setWidthFull();
        Div contentDiv = new Div();
        contentDiv.setText(
                "Aplikacja oferuje Ci możliwość ułożenia diety z uwzględnieniem Twoich ulubionych przepisów. Twoje ulubione dania zostaną odpowiednio dostosowane pod Twoje wymagania względem spożycia kalorii w odpowiednich proporcjach makro składników.");
        Button button = new Button("Dowiedz się więcej!",
                new Icon(VaadinIcon.ANGLE_DOUBLE_RIGHT));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout1.add(header, contentDiv, button);
        layout1.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        //layout1.setWidthFull();


        HorizontalLayout container = new HorizontalLayout();
        container.setSpacing(false);
        container.getStyle().set("flex-wrap", "wrap");
        container.setSizeFull();
        container.getStyle().set("padding-left", "20%");
        container.getStyle().set("padding-right", "20%");

        String cardHeader = "Heading";
        String cardContent = "Donec id elit non mi porta gravida at eget"
                + " metus. Fusce dapibus, tellus ac cursus commodo, tortor"
                + " mauris condimentum nibh, ut fermentum massa justo sit amet "
                + "risus. Etiam porta sem malesuada magna mollis  euismod. "
                + "Donec sed odio dui.";

        Component card1 = createCard(cardHeader, cardContent);
        Component card2 = createCard(cardHeader, cardContent);
        Component card3 = createCard(cardHeader, cardContent);
        container.add(card1, card2, card3);

        main.add(layout1, container);
        footer.add(new Paragraph("Karol Miller copyright 2021"));
    }
    private Component createCard(String cardHeader, String cardContent) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("30%");
        layout.setMinWidth("250px");

        H2 header = new H2(cardHeader);
        Div content = new Div();
        content.setText(cardContent);

        Button button = new Button("View details",
                new Icon(VaadinIcon.ANGLE_DOUBLE_RIGHT));
        button.addThemeVariants(ButtonVariant.LUMO_SMALL);
        Image image = new Image("https://www.kwestiasmaku.com/sites/v123.kwestiasmaku.com/files/styles/kafelki/public/pieczone-bataty-00.jpg?itok=pH6AkWP5", "test");

        layout.getElement().getStyle().set("flex-grow", "1");
        layout.add(header, image, content, button);
        return layout;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void createHeader() {
        HorizontalLayout layout = new HorizontalLayout();
        RouterLink titleLink = new RouterLink("Strona Główna", getClass());
        titleLink.addClassName("titleLink");
        layout.add(titleLink);
        Button artykuly = new Button("Artykuły");
        Button przepisy = new Button("Przepisy");
        Button mojekonto = new Button("Moje Konto");
        layout.add(przepisy, artykuly, mojekonto);
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, przepisy, artykuly, mojekonto);
        header.add(layout);
        mojekonto.addClickListener( e-> {
            mojekonto.getUI().ifPresent(ui -> ui.navigate("login"));
//            String sql = "DELETE FROM recipes WHERE recipe_name = 'Leszcz'";
//            jdbcTemplate.update(sql);
        });
    }

}