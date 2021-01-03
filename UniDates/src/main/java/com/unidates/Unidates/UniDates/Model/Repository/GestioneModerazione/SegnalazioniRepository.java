package com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegnalazioniRepository extends JpaRepository<Segnalazione, Long> {
}
