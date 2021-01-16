package com.unidates.Unidates.UniDates.Model.Service.EventListener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageListener {
    Logger logger = Logger.getLogger("MessageLogger");

    @EventListener
    public void messageHandling(MessageEvent messageEvent){
        logger.info("Messaggio inviato correttamente! " + "Mittente: " +
                messageEvent.getMessaggio().getEmailMittente() + " Destinatario: " +
                messageEvent.getMessaggio().getEmailDestinatario() + "Messaggio: " +
                messageEvent.getMessaggio().getTestoMessaggio());
    }
}
