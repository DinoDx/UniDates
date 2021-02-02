package com.unidates.Unidates.UniDates.Model.Entity;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="Foto")
public class Foto implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="profilo_id")
    private Profilo profilo;

    @OneToMany(mappedBy = "foto", orphanRemoval = true)
    private List<Segnalazione> segnalazioneRicevute;

    @OneToMany(mappedBy = "foto",orphanRemoval = true)
    private List<Notifica> notifiche;

    private boolean isFotoProfilo;

    @Column(length = 100000000)
    private byte[] img;

    private LocalDateTime creazione;


    private boolean isVisible;

    public Foto(){
        this.segnalazioneRicevute = new ArrayList<Segnalazione>();
        isVisible = true;
        creazione = LocalDateTime.now();
        isFotoProfilo = false;
    }

    public Foto(byte[] img){
        this.img = img;
        this.segnalazioneRicevute = new ArrayList<Segnalazione>();
        isVisible = true;
        creazione = LocalDateTime.now();
        isFotoProfilo = false;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Profilo getProfilo() {
        return profilo;
         }

    public void setProfilo(Profilo profilo) {
       this.profilo = profilo;
        }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }


    public void setSegnalazioniRicevuto(List<Segnalazione> segnalazioneRicevuto) {
        segnalazioneRicevute = segnalazioneRicevuto;
    }

    public void addSegnalazione(Segnalazione segnalazione){
        segnalazioneRicevute.add(segnalazione);
    }

    public List<Segnalazione> getSegnalazioneRicevute() {
        return segnalazioneRicevute;
    }

    public void setSegnalazioneRicevute(List<Segnalazione> segnalazioneRicevute) {
        this.segnalazioneRicevute = segnalazioneRicevute;
    }


    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }


    public List<Notifica> getNotifiche() {
        return notifiche;
    }

    public void setNotifiche(List<Notifica> notifiche) {
        this.notifiche = notifiche;
    }

    public LocalDateTime getCreazione() {
        return creazione;
    }

    public void setCreazione(LocalDateTime creazione) {
        this.creazione = creazione;
    }

    public boolean isFotoProfilo() {
        return isFotoProfilo;
    }

    public void setFotoProfilo(boolean fotoProfilo) {
        isFotoProfilo = fotoProfilo;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Foto)) {
            return false;
        }
        // typecast o to Complex so that we can compare data members
        Foto f = (Foto) o;
        // Compare the data members and return accordingly
        return this.getId().equals(f.getId()); // in questo caso controlliamo solo l'array di byte
    }


}
