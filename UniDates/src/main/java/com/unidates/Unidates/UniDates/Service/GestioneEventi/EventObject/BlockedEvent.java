package com.unidates.Unidates.UniDates.Service.GestioneEventi.EventObject;

import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import org.springframework.context.ApplicationEvent;

public class BlockedEvent extends ApplicationEvent {
    Studente bloccante;
    Studente bloccato;

    public BlockedEvent(Object source, Studente bloccante, Studente bloccato) {
        super(source);
        this.bloccante = bloccante;
        this.bloccato = bloccato;
    }

    public Studente getBloccante() {
        return bloccante;
    }

    public Studente getBloccato() {
        return bloccato;
    }
}
