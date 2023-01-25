package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
     Match findAllByStudente1AndStudente2(Studente studente1, Studente studente2);

}
