package pl.dietapp.frontend.pages;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import pl.dietapp.backend.model.AppUser;
import pl.dietapp.backend.services.AuthService;
import pl.dietapp.frontend.pages.bazaprzepisów.BazaPrzepisowView;
import pl.dietapp.frontend.pages.listazakupowa.ListaZakupowaView;
import pl.dietapp.frontend.pages.mojedane.MojeDaneView;
import pl.dietapp.frontend.pages.pulpitgłówny.PulpitGłównyView;
import pl.dietapp.frontend.pages.twojadieta.TwojaDietaView;
import pl.dietapp.frontend.pages.ulubioneprzepisy.UlubionePrzepisyView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Theme("myapp")
public class MainDashbordView extends AppLayout implements AppShellConfigurator {
    private AuthService authService;
    public static class MenuItemInfo {

        private String text;
        private String iconClass;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public String getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

    private H2 viewTitle;

    public MainDashbordView(AuthService authService) {
        this.authService = authService;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames("m-0", "text-l");
        Button logout = new Button("Wyloguj");
        logout.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logout.getStyle().set("margin-left", "auto");

        logout.addClickListener(event -> {
            UI.getCurrent().getPage().setLocation("logout");
        });
        HorizontalLayout layout = new HorizontalLayout(toggle, viewTitle, logout);
        layout.setMargin(true);
        layout.setWidthFull();
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, toggle, viewTitle, logout);
        Header header = new Header(layout);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                "w-full");

        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Panel Dietetyczny");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");
        Button strGl = new Button("Strona główna");
        strGl.addClickListener( e-> {
           strGl.getUI().ifPresent(ui -> ui.navigate("MainPage"));
        });
        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName, strGl,
                createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    private List<RouterLink> createLinks() {
        var user = VaadinSession.getCurrent().getAttribute(AppUser.class);

        MenuItemInfo[] menuItems = new MenuItemInfo[]{

        };


        List<RouterLink> links = new ArrayList<>();

        if (user != null) {
            MenuItemInfo[] menuitems1 = authService.getAuthorizedRoutes(user.getRole()).stream()
                    .map(r -> new MenuItemInfo(r.name(), r.iconClass(), r.view()))
                    .toArray(MenuItemInfo[]::new);
            for (MenuItemInfo menuItemInfo : menuitems1) {
                links.add(createLink(menuItemInfo));

            }
        }
        return links;
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = new Span();
        icon.addClassNames("me-s", "text-l");
        if (!menuItemInfo.getIconClass().isEmpty()) {
            icon.addClassNames(menuItemInfo.getIconClass());
        }

        Span text = new Span(menuItemInfo.getText());
        text.addClassNames("font-medium", "text-s");

        link.add(icon, text);
        return link;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
