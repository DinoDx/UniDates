package com.unidates.Unidates.UniDates.Model.Service;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    private final ApplicationEventPublisher publisher;

    public Publisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishMatch(final Studente studente, final Studente studente2){
        publisher.publishEvent(new MatchEvent(this, studente, studente2));
    }
}

