package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.Enum.Ruolo;

import java.util.List;

public class Manager extends Utente{
    public Manager(String email, String password){
        super(email, password, Ruolo.COMMUNITY_MANAGER);
    }

    private List<Segnalzioni> segnalzioniList;
}
