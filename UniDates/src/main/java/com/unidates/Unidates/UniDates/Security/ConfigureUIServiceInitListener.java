package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.View.home.HomePage;
import com.unidates.Unidates.UniDates.View.loginRegistrazione.LoginPage;
import com.unidates.Unidates.UniDates.View.loginRegistrazione.RegistrazioneAccountPage;
import com.unidates.Unidates.UniDates.View.loginRegistrazione.RegistrazioneProfiloPage;
import com.unidates.Unidates.UniDates.View.moderazione.ModerazionePage;
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
                if (ModerazionePage.class.equals(beforeEnterEvent.getNavigationTarget()) ||
                        RegistrazioneAccountPage.class.equals(beforeEnterEvent.getNavigationTarget()) ||
                        RegistrazioneProfiloPage.class.equals(beforeEnterEvent.getNavigationTarget()) ||
                        LoginPage.class.equals(beforeEnterEvent.getNavigationTarget())){
                    beforeEnterEvent.rerouteTo(HomePage.class);
                }
            }
            else if(loggato.getRuolo().equals(Ruolo.MODERATORE)){
                if (RegistrazioneAccountPage.class.equals(beforeEnterEvent.getNavigationTarget()) ||
                        RegistrazioneProfiloPage.class.equals(beforeEnterEvent.getNavigationTarget()) ||
                        LoginPage.class.equals(beforeEnterEvent.getNavigationTarget())){
                    beforeEnterEvent.rerouteTo(HomePage.class);
                }
            }
        }

        else {
            if (!LoginPage.class.equals(beforeEnterEvent.getNavigationTarget()) &&
                    !RegistrazioneAccountPage.class.equals(beforeEnterEvent.getNavigationTarget()) &&
                    !RegistrazioneProfiloPage.class.equals(beforeEnterEvent.getNavigationTarget())){
                beforeEnterEvent.rerouteTo(LoginPage.class);
            }
        }
    }
}
