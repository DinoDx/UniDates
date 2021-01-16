package com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

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
    private Collection<Segnalazione> segnalazioneRicevute;

    @Column(length = 10000000)
    private byte[] img;

    public Foto(){ }

    public Foto(byte[] img){
        this.img = img;
        this.segnalazioneRicevute = new ArrayList<Segnalazione>();
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

    public Collection<Segnalazione> getSegnalazioniRicevuto() {
        return segnalazioneRicevute;
    }

    public void setSegnalazioniRicevuto(Collection<Segnalazione> segnalazioneRicevuto) {
        segnalazioneRicevute = segnalazioneRicevuto;
    }

    public void addSegnalazione(Segnalazione segnalazione){
        segnalazioneRicevute.add(segnalazione);
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
