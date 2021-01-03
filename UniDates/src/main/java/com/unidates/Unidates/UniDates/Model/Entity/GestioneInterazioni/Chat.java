package com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import jdk.jfr.MemoryAddress;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Chat")
public class Chat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mittente_id")
    private Utente mittente;

    @ManyToOne
    @JoinColumn(name = "destiantario_id")
    private Utente destinatario;

    @OneToMany(mappedBy = "chat", cascade = {CascadeType.REMOVE,CascadeType.MERGE, CascadeType.PERSIST})
    private Collection<Messaggio> messaggi;

    public Chat() {
    }

    public Chat(Utente mittente,Utente destinatario, Collection<Messaggio> messaggi) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.messaggi = messaggi;
    }

    public void aggiungiMessaggio(Messaggio messaggio){
        messaggi.add(messaggio);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Messaggio> getMessaggi() {
        return messaggi;
    }

    public void setMessaggi(Collection<Messaggio> messaggi) {
        this.messaggi = messaggi;
    }

    public Utente getMittente() {
        return mittente;
    }

    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public Utente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Utente destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", mittente=" + mittente.getEmail() +
                ", destinatario=" + destinatario.getEmail() +
                ", messaggi=" + messaggi +
                '}';
    }
}
