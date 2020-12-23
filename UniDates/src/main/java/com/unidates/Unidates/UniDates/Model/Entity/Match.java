package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;

import javax.persistence.*;

@Entity
@Table(name = "match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studente1_id")
    private Studente studente1;

    @ManyToOne
    @JoinColumn(name = "studente2_id")
    private Studente studente2;


    private boolean likedByStudent1;
    private boolean likeByStudent2;

    public Match() {
    }

    public Match(Studente studente1, Studente studente2) {
        this.studente1 = studente1;
        this.studente2 = studente2;
    }

    public Studente getStudente1() {
        return studente1;
    }

    public void setStudente1(Studente studente1) {
        this.studente1 = studente1;
    }

    public Studente getStudente2() {
        return studente2;
    }

    public void setStudente2(Studente studente2) {
        this.studente2 = studente2;
    }

    public boolean isLikedByStudent1() {
        return likedByStudent1;
    }

    public void setLikedByStudent1(boolean likedByStudent1) {
        this.likedByStudent1 = likedByStudent1;
    }

    public boolean isLikeByStudent2() {
        return likeByStudent2;
    }

    public void setLikeByStudent2(boolean likeByStudent2) {
        this.likeByStudent2 = likeByStudent2;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", studente1=" + studente1.getEmail() +
                ", studente2=" + studente2.getEmail() +
                ", likedByStudent1=" + likedByStudent1 +
                ", likeByStudent2=" + likeByStudent2 +
                '}';
    }
}
