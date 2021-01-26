package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Model.Entity.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Service.GestioneEventi.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Publisher {
    private final ApplicationEventPublisher publisher;

    public Publisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishMatch(final Studente studente, final Studente studente2){
        publisher.publishEvent(new MatchEvent(this, studente, studente2));
    }

    public void publishWarning(final Ammonimento ammonimento) {publisher.publishEvent(new WarningEvent(this, ammonimento));}

    public void publishBannedEvent(final Sospensione sospensione) {
        publisher.publishEvent(new BannedEvent(this, sospensione));
    }

    public void publishOnRegistrationEvent(final Studente s, final Locale locale, final String url){
        publisher.publishEvent(new OnRegistrationCompleteEvent(s,locale, url));
    }

    public void publishBlockedEvent (final Studente bloccate, final Studente bloccato){
        publisher.publishEvent(new BlockedEvent(this, bloccate, bloccato));
    }
}

