package com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo;

import javax.persistence.*;

@Entity
@Table(name="Foto")
public class Foto  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="profilo_id")
    private Profilo profilo;

    private String url;

    public Foto(){
    }

    public Foto(String url){
        this.url = url;
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
}
