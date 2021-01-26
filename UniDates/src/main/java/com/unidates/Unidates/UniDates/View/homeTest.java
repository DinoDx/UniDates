package com.unidates.Unidates.UniDates.View;

import com.unidates.Unidates.UniDates.Controller.GestioneInterazioniController;
import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Service.UtenteService;
import com.unidates.Unidates.UniDates.Service.Publisher;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;


@Route("/homeTest")
public class homeTest extends VerticalLayout {

    @Autowired
    Publisher publisher;

    @Autowired
    UtenteService utenteService;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    GestioneProfiloController gestioneProfiloController;

    @Autowired
    GestioneInterazioniController gestioneInterazioniController;

    @Autowired
    GestioneModerazioneController gestioneModerazioneController;

    @Autowired
    SessionRegistry sessionRegistry;
    public homeTest() {
        Utente utente = SecurityUtils.getLoggedIn();  // Come prendere l'utente attualmente loggato


    }


}
