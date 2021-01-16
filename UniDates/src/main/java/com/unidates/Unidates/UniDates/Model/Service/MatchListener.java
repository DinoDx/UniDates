package com.unidates.Unidates.UniDates.Model.Service;

import com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni.NotificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.logging.Logger;

@Component
public class MatchListener {

    @Autowired
    NotificaService notificaService;
    Logger logger = Logger.getLogger("global");

    @EventListener
    public void handleMatchEvent(MatchEvent matchEvent){
        logger.info("Hangling match event");
        notificaService.generateNotificaMatch(matchEvent.getStudente1(), matchEvent.getStudente2());
        logger.info("Notifica gestita!");
    }
}
