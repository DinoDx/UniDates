package com.unidates.Unidates.UniDates.View.main;

import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Service.GestioneUtenti.UtenteService;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.component.Ammonimento_Notifica;
import com.unidates.Unidates.UniDates.View.component.Notifica_Component;
import com.unidates.Unidates.UniDates.View.component_home_page.Home;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
@JsModule("./styles/shared-styles.js")
@CssImport("./styles/views/main/main-view.css")
@PWA(name = "My Project", shortName = "My Project", enableInstallPrompt = false)
public class MainView extends AppLayout {

    private H1 viewTitle;

    public MainView(GestioneUtentiController gestioneUtentiController) {
        this.gestioneUtentiController = gestioneUtentiController;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
    }

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    UtenteService utenteService;

    private Component createHeaderContent() {

        Utente utente = SecurityUtils.getLoggedIn();
        Studente studente = (Studente) utente;
        utente.getListNotifica().add(new Notifica(gestioneUtentiController.trovaUtente("studenteprova1@gmail.com"),"", Tipo_Notifica.MATCH,new Foto(downloadUrl(("https://randomuser.me/api/portraits/women/42.jpg")))));
        utente.getListNotifica().add(new Notifica(gestioneUtentiController.trovaUtente("studenteprova1@gmail.com"),"", Tipo_Notifica.AMMONIMENTO,new Foto(downloadUrl(("https://randomuser.me/api/portraits/women/42.jpg")))));
        utente.getListNotifica().add(new Notifica(gestioneUtentiController.trovaUtente("studenteprova1@gmail.com"),"", Tipo_Notifica.MATCH,new Foto(downloadUrl(("https://randomuser.me/api/portraits/women/42.jpg")))));



        MenuBar menuBar = new MenuBar();
        menuBar.setId("profile-img");
        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
        Image image = new Image(resource,"");
        image.setWidth("30px");
        image.setHeight("30px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(image);
        horizontalLayout.add("  ciao " + studente.getProfilo().getNome());


        MenuItem profile = menuBar.addItem("");
        profile.addComponentAsFirst(new Icon(VaadinIcon.USER));

        profile.getSubMenu().addItem(horizontalLayout);
        profile.getSubMenu().addItem(new Anchor("/logout","Logout"));
        profile.getSubMenu().addItem(new Anchor("/profilo-personale","Profilo Personale"));

        MenuBar notification = new MenuBar();
        notification.getStyle().set("margin-right","1em");
        MenuItem notifiche = notification.addItem("");
        notifiche.addComponentAsFirst(new Icon(VaadinIcon.BELL));

        for(Notifica n : utente.getListNotifica()){
            if(n.getTipo_notifica().equals(Tipo_Notifica.MATCH))
                notifiche.getSubMenu().addItem(new Notifica_Component(n));
            else
                notifiche.getSubMenu().addItem(new Ammonimento_Notifica(n));
        }

        Button chats = new Button(new Icon(VaadinIcon.PAPERPLANE_O));
        Anchor anchor = new Anchor("/chat");
        anchor.add(chats);
        anchor.setId("aereo");


        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(false);
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setHeight("100px");
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        viewTitle = new H1("UniDates");
        layout.add(viewTitle);
        layout.add(SearchFilter());
        layout.add(menuBar,notification);
        layout.add(anchor);
        return layout;
    }

    private HorizontalLayout SearchFilter() {
       HorizontalLayout SearchBox = new HorizontalLayout();
       SearchBox.setAlignItems(FlexComponent.Alignment.CENTER);
       SearchBox.setSpacing(false);

       //componenti ricerca
       TextField searchField = new TextField();
       searchField.setPlaceholder("Inserisci email dell'utente da cercare");
       Button searchIcon = new Button(new Icon(VaadinIcon.SEARCH));
       //searchIcon.addClickListener();
       SearchBox.add(searchField,searchIcon);
       return SearchBox;
        /*filter.addKeyPressListener(Key.ENTER, e -> {
           if(utenteService.isPresent(gestioneUtentiController.trovaUtente(filter.getValue()))){
                UI.getCurrent().navigate("/ricercaprofilo?email=filter.getValue()");
            }

            //VaadinServletRequest.getCurrent().getHttpServletRequest().getParameter("");
        });*/

    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "My Project logo"));
        logoLayout.add(new H1("UniDates"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{/*createTab("Find Your Match", FindYourMatch.class),*/createTab("Home", Home.class)};
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

/*    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
  } */

    private byte[] downloadUrl(String stringDownload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            URL toDownload = new URL(stringDownload);
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();
            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toByteArray();
    }
}
