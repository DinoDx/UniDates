package com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommunityManagerRepository extends JpaRepository<CommunityManager, Long> {

    CommunityManager findByEmail(String email);
}
