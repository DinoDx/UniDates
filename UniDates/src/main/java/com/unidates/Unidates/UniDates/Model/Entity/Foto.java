package com.unidates.Unidates.UniDates.Model.Entity;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "foto", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Segnalazione> segnalazioneRicevute;

    @Column(length = 10000000)
    private byte[] img;


    private boolean isVisible;

    public Foto(){
        this.segnalazioneRicevute = new ArrayList<Segnalazione>();
        isVisible = true;
    }

    public Foto(byte[] img){
        this.img = img;
        this.segnalazioneRicevute = new ArrayList<Segnalazione>();
        isVisible = true;
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
