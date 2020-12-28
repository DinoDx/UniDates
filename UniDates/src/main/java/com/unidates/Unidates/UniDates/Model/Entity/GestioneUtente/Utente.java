package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Utente")
public abstract class Utente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String email;
    private String password;
    private Ruolo ruolo;



    @OneToMany(mappedBy = "utente", cascade = CascadeType.REMOVE)
    private Collection<Notifica> listNotifica;

    @OneToMany(mappedBy = "mittente",cascade = CascadeType.REMOVE)
    private Collection<Chat> mittente;

    @OneToMany(mappedBy = "destinatario", cascade = CascadeType.REMOVE)
    private Collection<Chat> destinatario;


    public Utente() {
    }

    public Utente(String email, String password, Ruolo ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }


    public Collection<Notifica> getListNotifica() {
        return listNotifica;
    }

    public void setListNotifica(Collection<Notifica> listNotifica) {
        this.listNotifica = listNotifica;
    }

    public void setMittente(ArrayList<Chat> chats) {
        this.mittente = chats;
    }

    public Collection<Chat> getMittente() {
        return mittente;
    }

    public void setMittente(Collection<Chat> mittente) {
        this.mittente = mittente;
    }

    public Collection<Chat> getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Collection<Chat> destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                ", listNotifica=" + listNotifica +
                ", mittente=" + mittente +
                ", destinatario=" + destinatario +
                '}';
    }
}
