package com.unidates.Unidates.UniDates.Service.GestioneEventi.GestioneModerazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import org.springframework.context.ApplicationEvent;

import java.util.logging.Logger;

public class BannedEvent extends ApplicationEvent {
    Logger logger = Logger.getLogger("BannedLogger");
    Sospensione sospensione;
    public BannedEvent(Object source, Sospensione sospensione) {
        super(source);
        this.sospensione = sospensione;
        logger.info("Nuova sospensione crata!");
    }

    public Sospensione getSospensione() {
        return sospensione;
    }
}
