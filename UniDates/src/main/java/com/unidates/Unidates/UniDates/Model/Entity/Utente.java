package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Utente")
public abstract class Utente implements Serializable {
    @Id
    private String email;
    private String password;
    private Ruolo ruolo;
    private boolean isActive;

    @OneToOne(orphanRemoval = true)
    private VerificationToken verificationToken;


    public Utente() {
        this.setActive(false);
    }

    public Utente(String email, String password, Ruolo ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.setActive(false);
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public String toString() {
        return "Utente{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                ", isActive=" + isActive +
                '}';
    }
}
