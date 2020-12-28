package com.unidates.Unidates.UniDates.Service.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.CommunityManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommunityManagerService {

    @Autowired
    CommunityManagerRepository communityManagerRepository;

    public CommunityManager findByEmail(String email){
        return communityManagerRepository.findByEmail(email);
    }
}
