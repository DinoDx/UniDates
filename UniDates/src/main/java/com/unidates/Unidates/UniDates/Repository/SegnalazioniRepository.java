package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SegnalazioniRepository extends JpaRepository<Segnalazione, Long> {
    Segnalazione findByModeratoreAndFoto(Moderatore moderatore, Foto foto);
}
