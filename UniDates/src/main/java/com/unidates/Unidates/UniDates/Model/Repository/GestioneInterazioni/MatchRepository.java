package com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.Collection;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
     Match findAllByStudente1AndStudente2(Studente studente1, Studente studente2);

}
