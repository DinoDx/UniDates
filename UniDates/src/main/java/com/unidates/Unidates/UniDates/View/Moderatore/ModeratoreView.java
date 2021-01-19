package com.unidates.Unidates.UniDates.View.Moderatore;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.component_pannello_moderazione.ListaSegnalazioni;
import com.unidates.Unidates.UniDates.View.component_pannello_moderazione.SegnalazioneComponent;
import com.unidates.Unidates.UniDates.View.component_pannello_moderazione.infoModeratore;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "pannellomoderatore", layout = MainView.class)
public class ModeratoreView extends VerticalLayout {

    @Autowired
    GestioneModerazioneController controller;

    Utente utente = SecurityUtils.getLoggedIn();
    Moderatore moderatore = (Moderatore) utente;

    public ModeratoreView(GestioneModerazioneController controller){
        this.controller = controller;
        VerticalLayout verticalLayout =  new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);


       verticalLayout.add(new infoModeratore(moderatore),new ListaSegnalazioni(moderatore,controller));
        add(verticalLayout);
    }


}
