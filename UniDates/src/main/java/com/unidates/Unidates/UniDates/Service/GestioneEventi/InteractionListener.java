package com.unidates.Unidates.UniDates.Service.GestioneEventi;

import com.unidates.Unidates.UniDates.Service.MatchService;
import com.unidates.Unidates.UniDates.Service.NotificaService;
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
    MatchService matchService;

    @Autowired
    private SessionRegistry sessionRegistry;

    Logger matchLogger = Logger.getLogger("MatchLogger");

    Logger blockLogger = Logger.getLogger("BlockLogger");

    @EventListener
    public void handleMatchEvent(MatchEvent matchEvent){
        matchLogger.info("Hangling match event");
        notificaService.generateNotificaMatch(matchEvent.getStudente1(), matchEvent.getStudente2());
        matchLogger.info("Notifica gestita!");
        SecurityUtils.refreshNotify(matchEvent.getStudente1(),matchEvent.getStudente2(), sessionRegistry);

    }

    @EventListener
    public void handleBlocckedEvent(BlockedEvent blockedEvent){
        blockLogger.info(blockedEvent.getBloccante().getEmail() + " ha bloccato " + blockedEvent.getBloccato().getEmail() );
        if(matchService.isValidMatch(blockedEvent.getBloccante().getEmail(), blockedEvent.getBloccato().getEmail())){
            blockLogger.info("Match esistente tra i due studenti bloccati : Rimuovo il match");
            matchService.eliminaMatch(blockedEvent.getBloccante().getEmail(), blockedEvent.getBloccato().getEmail());
            blockLogger.info("Match rimosso!");
        }
    }
}
