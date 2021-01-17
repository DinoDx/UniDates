package com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione.ModerationEvent;

import com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni.NotificaService;
import com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione.ModerazioneService;
import com.unidates.Unidates.UniDates.Model.Service.Publisher;
import com.unidates.Unidates.UniDates.Security.ForcedLogoutEvent;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ModerationListener {


    @Autowired
    private ModerazioneService moderazioneService;

    @Autowired
    private NotificaService notificaService;

    @Autowired
    private Publisher publisher;

    @EventListener
    public void warningHandler(WarningEvent warningEvent){
        Logger logger = Logger.getLogger("WarningLogger");
        logger.info("WarningHandler in azione");
        moderazioneService.nascondiFoto(warningEvent.getAmmonimento().getFoto());
        logger.info("Foto nascosta!");
        notificaService.genereateNotificaWarning(warningEvent.getAmmonimento().getStudente(), warningEvent.getAmmonimento().getFoto());
        logger.info("Notifica di ammonimento inviata!");
        moderazioneService.checkAmmonimentiStudente(warningEvent.getAmmonimento().getStudente());
        logger.info("Check sugli ammonimenti dello studente, avvenuto!");
    }

    @EventListener
    public void banHandler(BannedEvent bannedEvent){
        Logger logger = Logger.getLogger("BannedLogger");
        logger.info(bannedEvent.getSospensione().getStudente().getEmail() + " ha ricevuto una sospensione di " + bannedEvent.getSospensione().getDurata() + " giorni!");
        publisher.publishForcedLogout(bannedEvent.getSospensione().getStudente());
        //new PersistentTokenBasedRememberMeServices().logout(request, response, auth);

    }
}
