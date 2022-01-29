package pl.dietapp.backend.services;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;
import pl.dietapp.backend.model.AppUser;
import pl.dietapp.backend.model.Role;
import pl.dietapp.backend.repositories.AppUserRepository;
import pl.dietapp.frontend.pages.MainDashbordView;
import pl.dietapp.frontend.pages.MainView;
import pl.dietapp.frontend.pages.bazaprzepisów.BazaPrzepisowView;
import pl.dietapp.frontend.pages.listazakupowa.ListaZakupowaView;
import pl.dietapp.frontend.pages.mainpage.MainPage;
import pl.dietapp.frontend.pages.mojedane.MojeDaneView;
import pl.dietapp.frontend.pages.pulpitgłówny.PulpitGłównyView;
import pl.dietapp.frontend.pages.twojadieta.TwojaDietaView;
import pl.dietapp.frontend.pages.ulubioneprzepisy.UlubionePrzepisyView;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {


    public record AuthorizedRoute(String route, String name,String iconClass, Class<? extends Component> view) {

    }


    public class AuthException extends Exception {

    }



    private final AppUserRepository userRepository;


    public AuthService(AppUserRepository userRepository) {

        this.userRepository = userRepository;

    }


    public void authenticate(String username, String password) throws AuthException {

        AppUser user = userRepository.findByUserName(username);

        if (user != null && user.checkPassword(password)) {

            VaadinSession.getCurrent().setAttribute(AppUser.class, user);

            createRoutes(user.getRole());

        } else {

            throw new AuthException();

        }

    }


    private void createRoutes(Role role) {

        getAuthorizedRoutes(role).stream()

                .forEach(route ->

                        RouteConfiguration.forSessionScope().setRoute(

                                route.route(), route.view, MainDashbordView.class));

    }


    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {

        var routes = new ArrayList<AuthorizedRoute>();


        if (role.equals(Role.USER)) {

            routes.add(new AuthorizedRoute("pulpit", "Pulpit","la la-marker", PulpitGłównyView.class));
            routes.add(new AuthorizedRoute("twojadieta", "Twoja Dieta","la la-pizza-slice", TwojaDietaView.class));
            routes.add(new AuthorizedRoute("listazakupowa", "Lista Zakupowa","la la-shopping-cart", ListaZakupowaView.class));
            routes.add(new AuthorizedRoute("bazaprzepisow", "Baza Przepisów","la la-database", BazaPrzepisowView.class));
            routes.add(new AuthorizedRoute("ulubioneprzepisy", "Ulubione Przepisy","la la-th-list", UlubionePrzepisyView.class));
            routes.add(new AuthorizedRoute("mojedane", "Moje Dane","la la-user", MojeDaneView.class));


        } else if (role.equals(Role.ADMIN)) {

            routes.add(new AuthorizedRoute("pulpit", "Pulpit","la la-marker", PulpitGłównyView.class));
            routes.add(new AuthorizedRoute("twojadieta", "Twoja Dieta","la la-pizza-slice", TwojaDietaView.class));
            routes.add(new AuthorizedRoute("listazakupowa", "Lista Zakupowa","la la-shopping-cart", ListaZakupowaView.class));
            routes.add(new AuthorizedRoute("bazaprzepisow", "Baza Przepisów","la la-database", BazaPrzepisowView.class));
            routes.add(new AuthorizedRoute("ulubioneprzepisy", "Ulubione Przepisy","la la-th-list", UlubionePrzepisyView.class));
            routes.add(new AuthorizedRoute("mojedane", "Moje Dane","la la-user", MojeDaneView.class));

        }


        return routes;

    }


}