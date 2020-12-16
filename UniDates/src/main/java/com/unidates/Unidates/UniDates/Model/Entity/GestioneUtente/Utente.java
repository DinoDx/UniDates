package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.Notifica;
import com.unidates.Unidates.UniDates.Model.Profilo;
import org.aspectj.weaver.ast.Not;

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



    @OneToMany(mappedBy = "utente", cascade = CascadeType.REMOVE)//DA RISOLVERE LA QUESTIONE FETCH
    private Collection<Notifica> listNotifica;

    @OneToMany(mappedBy = "utente",cascade = CascadeType.REMOVE) //DA RISOLVERE LA QUESTIONE FETCH
    private Collection<Chat> listaChat;


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

    public void setListaChat(ArrayList<Chat> chats) {
        this.listaChat = chats;
    }

    public Collection<Chat> getListaChat() {
        return listaChat;
    }
}
