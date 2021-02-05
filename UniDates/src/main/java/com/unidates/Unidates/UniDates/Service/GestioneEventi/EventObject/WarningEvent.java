package com.unidates.Unidates.UniDates.Service.GestioneEventi.EventObject;

import com.unidates.Unidates.UniDates.Model.Entity.Ammonimento;
import org.springframework.context.ApplicationEvent;

import java.util.logging.Logger;

public class WarningEvent extends ApplicationEvent {
    Logger logger = Logger.getLogger("WarningLogger");
    private Ammonimento ammonimento;

    public WarningEvent(Object source, Ammonimento ammonimento) {
        super(source);
        this.ammonimento = ammonimento;
        logger.info("WarningEvent creato");
    }

    public Ammonimento getAmmonimento() {
        return ammonimento;
    }
}
