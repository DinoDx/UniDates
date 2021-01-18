package com.unidates.Unidates.UniDates.Model.Service.GestioneEventi.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni.NotificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class InteractionListener {
    @Autowired
    NotificaService notificaService;

    Logger matchLogger = Logger.getLogger("MatchLogger");

    Logger messageLogger = Logger.getLogger("MessageLogger");

    @EventListener
    public void handleMatchEvent(MatchEvent matchEvent){
        matchLogger.info("Hangling match event");
        notificaService.generateNotificaMatch(matchEvent.getStudente1(), matchEvent.getStudente2());
        matchLogger.info("Notifica gestita!");
    }

    @EventListener
    public void messageHandling(MessageEvent messageEvent){
        messageLogger.info("Messaggio inviato correttamente! " + "Mittente: " +
                messageEvent.getMessaggio().getEmailMittente() + " Destinatario: " +
                messageEvent.getMessaggio().getEmailDestinatario() + "Messaggio: " +
                messageEvent.getMessaggio().getTestoMessaggio());
    }
}
