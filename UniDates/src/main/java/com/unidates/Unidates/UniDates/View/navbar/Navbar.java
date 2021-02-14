package com.unidates.Unidates.UniDates.View.navbar;

import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.Control.ModifyProfileControl;
import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.NotificaDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
@JsModule("./styles/shared-styles.js")
@CssImport("./styles/views/main/main-view.css")
@PWA(name = "My Project", shortName = "My Project", enableInstallPrompt = false)
public class Navbar extends AppLayout {

    private H1 viewTitle;

    @Autowired
    UserManagementControl userManagementControl;

    @Autowired
    ModifyProfileControl profiloController;

    @Autowired
    InteractionControl interactionControl;

    StudenteDTO studente;


    public Navbar() {
        addAttachListener(event -> create());
    }

    public void create(){
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
    }

    private Component createHeaderContent() {

        studente = userManagementControl.studenteInSessione();


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
        profile.setId("user-button");
        profile.addComponentAsFirst(new Icon(VaadinIcon.USER));

        profile.getSubMenu().addItem(horizontalLayout);
        profile.getSubMenu().addItem(new Anchor("/profilo-personale","Profilo Personale"));
        if(studente.getRuolo() == Ruolo.MODERATORE || studente.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            Anchor pannelloMod = new Anchor("/pannellomoderatore","Sezione Moderazione");
            pannelloMod.setId("pannello-mod");
            profile.getSubMenu().addItem(pannelloMod);
        }
        profile.getSubMenu().addItem(new Anchor("/logout","Logout"));


        //MenuBar Notifiche
        MenuBar notification = new MenuBar();
        notification.getStyle().set("margin-right","1.5em");
        MenuItem notifiche = notification.addItem("");
        notifiche.addComponentAsFirst(new Icon(VaadinIcon.BELL));
        for(NotificaDTO n : studente.getListaNotifica()){
            if(n.getTipo_notifica().equals(Tipo_Notifica.MATCH)){
                if(!interactionControl.ricercaStudente(n.getEmailToMatchWith()).isBanned()) notifiche.getSubMenu().addComponentAtIndex(0,new CardNotifica(n,profiloController));
            }
            else notifiche.getSubMenu().addComponentAtIndex(0,new CardNotifica(n,profiloController));
           // else if(n.getTipo_notifica().equals(Tipo_Notifica.AMMONIMENTO))
              //  notifiche.getSubMenu().addComponentAtIndex(0,new Ammonimento_Notifica(n));
        }

        //Layout Principale
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.setWidthFull();
        layout.setHeight("100px");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

       Anchor home = new Anchor("/");
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
       searchField.setId("ricerca-field");
       searchField.setPlaceholder("Inserisci email dell'utente da cercare");

       Button searchIcon = new Button(new Icon(VaadinIcon.SEARCH));
       searchIcon.setId("ricerca-button");
       searchIcon.addClickListener(buttonClickEvent -> {
           try {
               StudenteDTO daCercare = interactionControl.ricercaStudente(searchField.getValue());
               if (interactionControl.ricercaStudente(searchField.getValue()) != null) {
                   if(!daCercare.isBanned())
                    UI.getCurrent().navigate("ricercaprofilo/"+ searchField.getValue());
                   else  new Notification("L'utente attualmente cercato risulta sospeso!",3000, Notification.Position.MIDDLE).open();
               }
           }
           catch(EntityNotFoundException e){
               Notification erroreRicerca = new Notification("Utente non trovato!",3000, Notification.Position.MIDDLE);
               erroreRicerca.setId("errore-ricerca");
               erroreRicerca.open();
           }
       });
       SearchBox.add(searchField,searchIcon);
       return SearchBox;
    }
}
