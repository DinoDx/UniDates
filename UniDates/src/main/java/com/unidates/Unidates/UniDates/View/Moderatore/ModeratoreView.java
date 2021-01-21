package com.unidates.Unidates.UniDates.View.Moderatore;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.component_pannello_moderazione.ListaSegnalazioni;
import com.unidates.Unidates.UniDates.View.component_pannello_moderazione.infoModeratore;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "pannellomoderatore", layout = MainView.class)
public class ModeratoreView extends VerticalLayout /*implements BeforeEnterListener */{

    @Autowired
    GestioneModerazioneController controller;

    Utente utente = SecurityUtils.getLoggedIn();


    public ModeratoreView(GestioneModerazioneController controller){
        this.controller = controller;
        Moderatore moderatore = (Moderatore) utente;

        VerticalLayout verticalLayout =  new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new ListaSegnalazioni(utente,controller));

        verticalLayout.add(new infoModeratore(moderatore),horizontalLayout);
        add(verticalLayout);
    }

/*
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(utente.getRuolo() == Ruolo.STUDENTE){
            UI.getCurrent().navigate("home");
        }
    }
       */

}
