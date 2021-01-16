package com.unidates.Unidates.UniDates.Model.Service.EventListener;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import org.springframework.context.ApplicationEvent;

import java.util.logging.Logger;

public class MatchEvent extends ApplicationEvent {
    private Studente studente1;
    private Studente studente2;
    Logger logger = Logger.getLogger("global");

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MatchEvent(Object source, Studente studente1, Studente studente2) {
        super(source);
        this.studente1 = studente1;
        this.studente2 = studente2;
        logger.info("Match avvenuto tra " + studente1.getEmail() + " e " + studente2.getEmail());
        logger.info("Evento lanciato!");
    }

    public Studente getStudente1() {
        return studente1;
    }

    public Studente getStudente2() {
        return studente2;
    }
}
