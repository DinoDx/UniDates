package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.View.LoginRegistrazione.Login;
import com.unidates.Unidates.UniDates.View.LoginRegistrazione.RegistrazioneAccount;
import com.unidates.Unidates.UniDates.View.LoginRegistrazione.RegistrazioneProfilo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component

public class ConfigureUIServiceInitListener implements VaadinServiceInitListener{
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter); //
        });
    }

    private void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        if(SecurityUtils.isUserLoggedIn()) {
            Utente loggato = SecurityUtils.getLoggedIn();
            if(loggato.getRuolo().equals(Ruolo.STUDENTE)){
                // Specificare le pagine in cui l'utente non puó entratre
            }
            else if(loggato.getRuolo().equals(Ruolo.MODERATORE)){
                //Specificare le pagine in cui il moderatore non puó entrare
            }
        }

        else {
            //Utente non loggato - Pagine permesse: login, registrazione,
            if (!Login.class.equals(beforeEnterEvent.getNavigationTarget()) && !RegistrazioneAccount.class.equals(beforeEnterEvent.getNavigationTarget()) && !RegistrazioneProfilo.class.equals(beforeEnterEvent.getNavigationTarget())){
                beforeEnterEvent.rerouteTo(Login.class);
            }
        }
    }
}
