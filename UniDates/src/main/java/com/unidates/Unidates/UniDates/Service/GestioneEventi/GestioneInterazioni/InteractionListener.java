package com.unidates.Unidates.UniDates.Service.GestioneEventi.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.NotificaService;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class InteractionListener {
    @Autowired
    NotificaService notificaService;

    @Autowired
    private SessionRegistry sessionRegistry;

    Logger matchLogger = Logger.getLogger("MatchLogger");

    Logger messageLogger = Logger.getLogger("MessageLogger");

    @EventListener
    public void handleMatchEvent(MatchEvent matchEvent){
        matchLogger.info("Hangling match event");
        notificaService.generateNotificaMatch(matchEvent.getStudente1(), matchEvent.getStudente2());
        matchLogger.info("Notifica gestita!");
        SecurityUtils.refreshNotify(matchEvent.getStudente1(),matchEvent.getStudente2(), sessionRegistry);

    }
}
