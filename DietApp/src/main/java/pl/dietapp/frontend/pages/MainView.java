package pl.dietapp.frontend.pages;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;




@JsModule("./styles/shared-styles.js")
@JsModule("./src/utils.js")
//@Theme(value = Lumo.class, variant = Lumo.LIGHT)
//@JsModule("MainPage.js")
public class MainView extends Div implements RouterLayout {
    public MainView() {

        System.out.println(getElement().getStyle());
        getElement().getStyle().set("height", "100%");
    }
}
