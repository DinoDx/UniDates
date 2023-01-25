package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import javax.persistence.Entity;

@Entity
public class CommunityManager extends Moderatore {


    public CommunityManager(){
        super();
        setRuolo(Ruolo.COMMUNITY_MANAGER);
    }

    public CommunityManager(String email, String password) {
        super(email, password);
        setRuolo(Ruolo.COMMUNITY_MANAGER);
    }

}
