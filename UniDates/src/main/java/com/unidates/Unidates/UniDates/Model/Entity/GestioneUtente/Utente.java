package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Utente")
public abstract class Utente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String email;
    private String password;
    private Ruolo ruolo;

    private boolean isActive;


    @OneToMany(mappedBy = "utente", cascade = CascadeType.REMOVE)
    private List<Notifica> listNotifica;

    @OneToMany(mappedBy = "mittente",cascade = CascadeType.REMOVE)
    private List<Chat> mittente;

    @OneToMany(mappedBy = "destinatario", cascade = CascadeType.REMOVE)
    private List<Chat> destinatario;


    public Utente() {
    }

    public Utente(String email, String password, Ruolo ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.setActive(false);
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

    public List<Notifica> getListNotifica() {
        return listNotifica;
    }

    public void setListNotifica(List<Notifica> listNotifica) {
        this.listNotifica = listNotifica;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Chat> getMittente() {
        return mittente;
    }

    public void setMittente(List<Chat> mittente) {
        this.mittente = mittente;
    }

    public List<Chat> getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(List<Chat> destinatario) {
        this.destinatario = destinatario;
    }


    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                ", isActive=" + isActive +
                ", listNotifica=" + listNotifica +
                ", mittente=" + mittente +
                ", destinatario=" + destinatario +
                '}';
    }
}
