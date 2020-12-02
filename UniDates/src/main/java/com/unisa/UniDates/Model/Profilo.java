package com.unisa.UniDates.Model;

import javax.persistence.*;

@Entity
@Table(name = "profilo")
public class Profilo {
    @Id
    private Long aLong;


    @OneToOne
    @MapsId
    @JoinColumn(name = "utente_email")
    private Utente utente;
}
