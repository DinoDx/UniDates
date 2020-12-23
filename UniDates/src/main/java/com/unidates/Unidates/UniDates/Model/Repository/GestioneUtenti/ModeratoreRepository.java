package com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import org.dom4j.rule.Mode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeratoreRepository extends JpaRepository<Moderatore, Long> {

    Moderatore findByEmail(String email);
}
