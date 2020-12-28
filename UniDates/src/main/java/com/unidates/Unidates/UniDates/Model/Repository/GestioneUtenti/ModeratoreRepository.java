package com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeratoreRepository extends JpaRepository<Moderatore, Long> {

    Moderatore findByEmail(String email);
}
