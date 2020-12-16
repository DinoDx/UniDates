package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;

import javax.persistence.*;

@Entity
@Table(name = "Ammonimenti")
public class Ammonimenti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studente_id")
    private Studente studente;
}
