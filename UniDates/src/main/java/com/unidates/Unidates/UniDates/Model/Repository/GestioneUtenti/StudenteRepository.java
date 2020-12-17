package com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface StudenteRepository extends JpaRepository<Studente, Long> {
    Studente findByEmail(String email);
}
