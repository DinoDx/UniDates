package com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByEmail(String email);
    List<Utente> findAllByRuolo(Ruolo ruolo);
}
