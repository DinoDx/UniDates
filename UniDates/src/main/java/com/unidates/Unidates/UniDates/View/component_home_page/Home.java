package com.unidates.Unidates.UniDates.View.component_home_page;


import com.example.application.views.Person;
import com.unidates.Unidates.UniDates.Controller.GestioneInterazioniController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.UtenteRepository;
import com.unidates.Unidates.UniDates.Model.Service.GestioneUtenti.UtenteService;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.component.Card_Utente_Home_Component;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.List;


@Route(value = "home", layout = MainView.class)
@CssImport("./styles/views/home/home.css")
@PageTitle("Home")
public class Home extends VerticalLayout{

    @Autowired
    GestioneInterazioniController gestioneInterazioniController;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    public Home(GestioneUtentiController gestioneUtentiController,GestioneInterazioniController gestioneInterazioniController){
        this.gestioneInterazioniController = gestioneInterazioniController;
        this.gestioneUtentiController = gestioneUtentiController;
        setAlignItems(Alignment.CENTER);
        VerticalLayout utenti = new VerticalLayout();
        utenti.setAlignItems(Alignment.CENTER);

        Utente utente = SecurityUtils.getLoggedIn();
        for(Utente u : gestioneUtentiController.findAll()){
                if(!(u.getEmail().equals(utente.getEmail()))) {
                    utenti.add(new Card_Utente_Home_Component(gestioneInterazioniController,u));
                }
        }
        add(utenti);
    }
}


