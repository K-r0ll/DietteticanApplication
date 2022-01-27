package pl.dietapp.frontend.pages.pulpitgłówny;


import pl.dietapp.backend.repositories.RecipeRepository;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import pl.dietapp.frontend.pages.twojadieta.DailyGrid;


@PageTitle("Pulpit Główny")
//@Route(value = "pulpit", layout = MainDashbordView.class)
//@RouteAlias(value = "", layout = MainLayoutTest.class)
public class PulpitGłównyView extends VerticalLayout {
    private RecipeRepository recipeRepository;
    public PulpitGłównyView(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        setSpacing(false);

//        Image img = new Image("images/empty-plant.png", "placeholder plant");
//        img.setWidth("200px");
//        add(img);
//
//       // add(new H2("This place intentionally left empty"));
//       // add(new Paragraph("It’s a place where you can grow your own UI 🤗"));


        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }



}
