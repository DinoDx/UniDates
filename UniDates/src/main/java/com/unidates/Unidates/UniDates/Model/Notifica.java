package com.unidates.Unidates.UniDates.Model;

import javax.persistence.*;

@Entity
public class Notifica {
    @Id
    private Long id;
    private String testoNotifica;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_utente")
    private Utente utente;

    public Notifica() {
    }

    public Notifica(Long id, String testoNotifica) {
        this.id = id;
        this.testoNotifica = testoNotifica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestoNotifica() {
        return testoNotifica;
    }

    public void setTestoNotifica(String testoNotifica) {
        this.testoNotifica = testoNotifica;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
