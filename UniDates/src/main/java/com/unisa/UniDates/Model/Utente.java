package com.unisa.UniDates.Model;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table( name = "utente")
public class Utente {
    @Id @Email
    private String email;
    private String password;

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profilo profilo;


}
