package com.unidates.Unidates.UniDates.Service.GestioneEventi;

import com.unidates.Unidates.UniDates.Service.GestioneEventi.EventObject.BlockedEvent;
import com.unidates.Unidates.UniDates.Service.GestioneEventi.EventObject.MatchEvent;
import com.unidates.Unidates.UniDates.Service.MatchService;
import com.unidates.Unidates.UniDates.Service.NotificaService;
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
    MatchService matchService;

    @Autowired
    private SessionRegistry sessionRegistry;

    Logger matchLogger = Logger.getLogger("MatchLogger");

    Logger blockLogger = Logger.getLogger("BlockLogger");

    @EventListener
    public void handleMatchEvent(MatchEvent matchEvent){
        matchLogger.info("Hangling match event");
        notificaService.generateNotificaMatch(matchEvent.getStudente1(), matchEvent.getStudente2());
        matchLogger.info("Notifica gestita!");;

    }

    @EventListener
    public void handleBlocckedEvent(BlockedEvent blockedEvent){
        blockLogger.info(blockedEvent.getBloccante().getEmail() + " ha bloccato " + blockedEvent.getBloccato().getEmail() );
        if(matchService.isValidMatch(blockedEvent.getBloccante().getEmail(), blockedEvent.getBloccato().getEmail())){
            blockLogger.info("Match esistente tra i due studenti bloccati : Rimuovo il match");
            matchService.eliminaMatch(blockedEvent.getBloccante().getEmail(), blockedEvent.getBloccato().getEmail());
            blockLogger.info("Match rimosso! Ora rimuovo le notifiche!");
            notificaService.eliminaNoificaMatch(blockedEvent.getBloccante(), blockedEvent.getBloccato());
            blockLogger.info("Notifiche rimosse!");
        }
    }
}
