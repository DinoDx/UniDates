package com.unidates.Unidates.UniDates.Model.Service.GestioneEventi.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import org.springframework.context.ApplicationEvent;

import java.util.logging.Logger;


public class MessageEvent extends ApplicationEvent {

    Logger logger = Logger.getLogger("MessageLogger");
    Messaggio messaggio;

    public MessageEvent(Object source, Messaggio messaggio) {
        super(source);
        this.messaggio = messaggio;
        logger.info("MessageEvent creato");
    }

    public Messaggio getMessaggio() {
        return messaggio;
    }
}
