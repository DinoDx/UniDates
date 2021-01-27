package com.unidates.Unidates.UniDates.View.Moderatore;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.ModeratoreDTO;
import com.unidates.Unidates.UniDates.View.component_pannello_moderazione.ListaSegnalazioni;
import com.unidates.Unidates.UniDates.View.component_pannello_moderazione.infoModeratore;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "pannellomoderatore", layout = MainView.class)
public class ModeratoreView extends VerticalLayout {

    @Autowired
    GestioneModerazioneController gestioneModerazioneController;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    GestioneProfiloController gestioneProfiloController;

    public ModeratoreView(){
        addAttachListener(event -> create());
    }

    public void create(){

        ModeratoreDTO moderatore =  gestioneUtentiController.moderatoreInSessione();

        VerticalLayout verticalLayout =  new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new ListaSegnalazioni(moderatore,gestioneUtentiController,gestioneModerazioneController, gestioneProfiloController));

        verticalLayout.add(new infoModeratore(moderatore),horizontalLayout);
        add(verticalLayout);
    }

}
