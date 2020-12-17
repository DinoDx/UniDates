package com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByEmail(String email);
}
