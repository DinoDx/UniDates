package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import org.springframework.context.ApplicationEvent;

public class ForcedLogoutEvent extends ApplicationEvent {

    Studente toLogout;
    public ForcedLogoutEvent(Object source, Studente toLogout) {
        super(source);
        this.toLogout = toLogout;
    }

    public Studente getToLogout() {
        return toLogout;
    }
}
