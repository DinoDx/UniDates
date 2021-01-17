package com.unidates.Unidates.UniDates.Model.Service;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni.InteractionEvent.MatchEvent;
import com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni.InteractionEvent.MessageEvent;
import com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione.ModerationEvent.BannedEvent;
import com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione.ModerationEvent.WarningEvent;
import com.unidates.Unidates.UniDates.Security.ForcedLogoutEvent;
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

    public void publishMessage(final Messaggio messaggio){
        publisher.publishEvent(new MessageEvent(this, messaggio));
    }

    public void publishWarning(final Ammonimento ammonimento) {publisher.publishEvent(new WarningEvent(this, ammonimento));}

    public void publishBannedEvent(final Sospensione sospensione) {
        publisher.publishEvent(new BannedEvent(this, sospensione));
    }

    public void publishForcedLogout(Studente studente){
        publisher.publishEvent(new ForcedLogoutEvent(this, studente));
    }
}

