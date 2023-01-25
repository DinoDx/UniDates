package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, String> {
    Utente findByEmail(String email);
    List<Utente> findAllByRuolo(Ruolo ruolo);
}
