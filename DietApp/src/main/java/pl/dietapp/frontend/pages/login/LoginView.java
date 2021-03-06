package pl.dietapp.frontend.pages.login;




import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import pl.dietapp.backend.services.AuthService;



@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
public class LoginView extends Div {

    public LoginView(AuthService authService) {
        setId("login-view");
        var username = new TextField("Username");
        var password = new PasswordField("Password");
        add(
        new H1("Welcome"),
        username,
        password,
        new Button("Login", event -> {
            try {
                authService.authenticate(username.getValue(), password.getValue());
                UI.getCurrent().navigate("pulpit");
            } catch (AuthService.AuthException e) {
                Notification.show("Wrong credentials.");
            }
        })
        );
    }
}