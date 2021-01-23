package com.unidates.Unidates.UniDates.Service.GestioneEventi.GestioneModerazioni;

import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.NotificaService;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.ModerazioneService;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ModerationListener {


    @Autowired
    private ModerazioneService moderazioneService;

    @Autowired
    private NotificaService notificaService;

    @Autowired
    SessionRegistry sessionRegistry;

    @EventListener
    public void warningHandler(WarningEvent warningEvent){
        Logger logger = Logger.getLogger("WarningLogger");
        logger.info("WarningHandler in azione");
        moderazioneService.nascondiFoto(warningEvent.getAmmonimento().getFoto());
        logger.info("Foto nascosta!");
        notificaService.genereateNotificaWarning(warningEvent.getAmmonimento().getStudente(), warningEvent.getAmmonimento().getFoto(), warningEvent.getAmmonimento());
        logger.info("Notifica di ammonimento inviata!");
        moderazioneService.checkAmmonimentiStudente(warningEvent.getAmmonimento().getStudente());
        logger.info("Check sugli ammonimenti dello studente, avvenuto!");
    }

    @EventListener
    public void banHandler(BannedEvent bannedEvent){
        Logger logger = Logger.getLogger("BannedLogger");
        logger.info("BannedLogger in azione");
        logger.info(bannedEvent.getSospensione().getStudente().getEmail() + " ha ricevuto una sospensione di " + bannedEvent.getSospensione().getDurata() + " giorni!");
        logger.info("Sospensione allo studente inviata");
        SecurityUtils.forceLogout(bannedEvent.getSospensione().getStudente(), sessionRegistry);

    }
}
