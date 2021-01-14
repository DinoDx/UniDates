package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import org.dom4j.rule.Mode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;

@Entity
public class CommunityManager extends Moderatore {


    public CommunityManager(){
    }

    public CommunityManager(String email, String password) {
        super(email, password);
        setRuolo(Ruolo.COMMUNITY_MANAGER);
    }

}
