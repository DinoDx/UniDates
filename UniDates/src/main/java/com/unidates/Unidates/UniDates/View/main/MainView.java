package com.unidates.Unidates.UniDates.View.main;

import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.NotificaDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.DTOs.UtenteDTO;
import com.unidates.Unidates.UniDates.Model.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Exception.InvalidRegistrationFormatException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.component.Ammonimento_Notifica;
import com.unidates.Unidates.UniDates.View.component.Notifica_Component;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
@JsModule("./styles/shared-styles.js")
@CssImport("./styles/views/main/main-view.css")
@PWA(name = "My Project", shortName = "My Project", enableInstallPrompt = false)
public class MainView extends AppLayout {

    private H1 viewTitle;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    GestioneProfiloController profiloController;

    NotificaDTO notifica;
    ProfiloDTO profilo;
    StudenteDTO studente;
    UtenteDTO utente;


    public MainView() {
        addAttachListener(event -> create());
    }

    public void create(){
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
    }


    private Component createHeaderContent() {

        utente = gestioneUtentiController.utenteInSessione();
        studente = (StudenteDTO) utente;


        //MenuBar Profilo Personale
        MenuBar menuBar = new MenuBar();
        menuBar.setId("profile-img");
        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(studente.getProfilo().getFotoProfilo().getImg()));
        Image image = new Image(resource,"");
        image.setWidth("30px");
        image.setHeight("30px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(false);
        Span ciao = new Span("Ciao");
        ciao.getStyle().set("margin-left","10px");
        ciao.getStyle().set("margin-right","5px");
        Span nome = new Span(studente.getProfilo().getNome());
        horizontalLayout.add(image,ciao,nome);


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
        for(NotificaDTO n : utente.getListaNotifica()){
            if(n.getTipo_notifica().equals(Tipo_Notifica.MATCH))
                notifiche.getSubMenu().addComponentAtIndex(0,new Notifica_Component(n,profiloController));
            else
                notifiche.getSubMenu().addComponentAtIndex(0,new Ammonimento_Notifica(n));
        }

        //Layout Principale
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.setWidthFull();
        layout.setHeight("100px");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        Anchor home = new Anchor("/home");
        viewTitle = new H1("UniDates");
        viewTitle.getStyle().set("margin-left","30px");
        viewTitle.getStyle().set("font-size","30px");
        home.add(viewTitle);

        //Ricerca
        HorizontalLayout filter = SearchFilter();
        filter.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(home);
        layout.add(SearchFilter(),menuBar,notification);
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
           try {
               StudenteDTO daCercare = gestioneUtentiController.trovaStudente(searchField.getValue());
               if (gestioneUtentiController.trovaUtente(searchField.getValue()) != null && !(daCercare.isBanned())) {
                   UI.getCurrent().navigate("ricercaprofilo/"+ searchField.getValue());
               }
           }
           catch(UserNotFoundException e){
               Notification erroreRicerca = new Notification("Utente non trovato!",5000, Notification.Position.MIDDLE);
               erroreRicerca.open();
           }
           catch(InvalidRegistrationFormatException e){
               Notification erroreRicerca = new Notification("Email non valida",5000, Notification.Position.MIDDLE);
               erroreRicerca.open();
           }
       });
       SearchBox.add(searchField,searchIcon);
       return SearchBox;
    }
}
