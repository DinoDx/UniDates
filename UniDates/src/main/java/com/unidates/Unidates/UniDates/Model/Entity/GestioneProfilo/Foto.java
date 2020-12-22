package com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo;

import com.unidates.Unidates.UniDates.Model.Entity.Segnalazioni;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "foto", cascade = CascadeType.REMOVE)
    private Collection<Segnalazioni> SegnalazioniRicevute;

    private String url;

    public Foto(){ }

    public Foto(String url){
        this.url = url;
        this.SegnalazioniRicevute = null;
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

    public Collection<Segnalazioni> getSegnalazioniRicevuto() {
        return SegnalazioniRicevute;
    }

    public void setSegnalazioniRicevuto(Collection<Segnalazioni> segnalazioniRicevuto) {
        SegnalazioniRicevute = segnalazioniRicevuto;
    }

    public void addSegnalazione(Segnalazioni segnalazione){
        SegnalazioniRicevute.add(segnalazione);
    }

}
