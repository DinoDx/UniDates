package com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="Foto")
public class Foto  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="profilo_id")
    private Profilo profilo;

    @OneToMany(mappedBy = "foto", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Collection<Segnalazione> segnalazioneRicevute;

    private String url;

    public Foto(){ }

    public Foto(String url){
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

}
