package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Chat")
public class Chat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "chat", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Collection<Messaggio> messaggi;

    public Chat() {
    }

    public Chat(Utente utente, Collection<Messaggio> messaggi) {
        this.utente = utente;
        this.messaggi = messaggi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Collection<Messaggio> getMessaggi() {
        return messaggi;
    }

    public void setMessaggi(Collection<Messaggio> messaggi) {
        this.messaggi = messaggi;
    }
}
