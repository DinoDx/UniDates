package com.unidates.Unidates.UniDates.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MatchService {
    @Autowired

    //da aggiustare il match giá presente
    private MatchRepository matchRepository;

    public void aggiungiMatch(Studente studente1, Studente studente2){
        Match tryToFind = matchRepository.findAllByStudente1AndStudente2(studente1,studente2);
        if(tryToFind == null){
            Match match = new Match(studente1, studente2);
            match.setLikedByStudent1(true);
            match.setLikeByStudent2(false);
            matchRepository.save(match);
        }
        else {
            if(tryToFind.isLikedByStudent1())
                tryToFind.setLikeByStudent2(true);
        }
    }

}
