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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;


@Route("homeTest")
public class homeTest extends VerticalLayout {

    @Autowired
    UtenteService utenteService;

    public homeTest() {
        Button button = new Button("Ciao", event -> {
            utenteService.testPython();
        });
            add(button);
    }


}
