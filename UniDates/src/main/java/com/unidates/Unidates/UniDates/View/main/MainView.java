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
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
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

    @Autowired
    GestioneUtentiController gestioneUtentiController;


    public MainView(GestioneUtentiController gestioneUtentiController) {
        this.gestioneUtentiController = gestioneUtentiController;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
    }



    private Component createHeaderContent() {

        Utente utente = SecurityUtils.getLoggedIn();
        Studente studente = (Studente) utente;
        //utente.getListNotifica().add(new Notifica(gestioneUtentiController.trovaUtente("studenteprova1@gmail.com"),"", Tipo_Notifica.MATCH,new Foto(downloadUrl(("https://randomuser.me/api/portraits/women/42.jpg")))));
        //utente.getListNotifica().add(new Notifica(gestioneUtentiController.trovaUtente("studenteprova1@gmail.com"),"", Tipo_Notifica.AMMONIMENTO,new Foto(downloadUrl(("https://randomuser.me/api/portraits/women/42.jpg")))));
        //utente.getListNotifica().add(new Notifica(gestioneUtentiController.trovaUtente("studenteprova1@gmail.com"),"", Tipo_Notifica.MATCH,new Foto(downloadUrl(("https://randomuser.me/api/portraits/women/42.jpg")))));



        //MenuBar Profilo Personale
        MenuBar menuBar = new MenuBar();
        menuBar.setId("profile-img");
        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
        Image image = new Image(resource,"");
        image.setWidth("30px");
        image.setHeight("30px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(image);
        horizontalLayout.add("  Ciao " + studente.getProfilo().getNome());


        MenuItem profile = menuBar.addItem("");
        profile.addComponentAsFirst(new Icon(VaadinIcon.USER));

        profile.getSubMenu().addItem(horizontalLayout);
        profile.getSubMenu().addItem(new Anchor("/logout","Logout"));
        profile.getSubMenu().addItem(new Anchor("/profilo-personale","Profilo Personale"));

        //MenuBar Notifiche
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

        //Button Chat
        Button chats = new Button(new Icon(VaadinIcon.PAPERPLANE_O));
        Anchor anchor = new Anchor("/chat");
        anchor.add(chats);
        anchor.setId("aereo");

        //Layout Principale
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.setWidthFull();
        layout.setHeight("100px");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        viewTitle = new H1("UniDates");
        viewTitle.getStyle().set("margin-left","30px");
        viewTitle.getStyle().set("font-size","30px");

        //Ricerca
        HorizontalLayout filter = SearchFilter();
        filter.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(viewTitle);
        layout.add(SearchFilter(),menuBar,notification);
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
       searchIcon.addClickListener(buttonClickEvent -> {

           if (gestioneUtentiController.trovaUtente(searchField.getValue())!= null) {

                   /*(try)VaadinServletResponse.getCurrent().getHttpServletResponse().sendRedirect("ricercaprofilo?email="+searchField.getValue());
                   UI.getCurrent().navigate("ricercaprofilo/"+searchField.getValue());*/

                   ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                   HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);
                   httpSession.setAttribute("studente",gestioneUtentiController.trovaUtente(searchField.getValue()));
                    UI.getCurrent().navigate("ricercaprofilo");
           }
           else{
               Notification.show("Utente non trovato");
           }
       });
       SearchBox.add(searchField,searchIcon);
       return SearchBox;
    }
}
