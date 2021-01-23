package com.unidates.Unidates.UniDates.Repository.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
     Match findAllByStudente1AndStudente2(Studente studente1, Studente studente2);

}
