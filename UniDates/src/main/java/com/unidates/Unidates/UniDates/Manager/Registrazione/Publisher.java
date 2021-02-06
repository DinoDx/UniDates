package com.unidates.Unidates.UniDates.Manager.Registrazione;

import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Publisher {
    private final ApplicationEventPublisher publisher;

    public Publisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishOnRegistrationEvent(final Studente s, final Locale locale, final String url){
        publisher.publishEvent(new OnRegistrationCompleteEvent(s,locale, url));
    }
}

