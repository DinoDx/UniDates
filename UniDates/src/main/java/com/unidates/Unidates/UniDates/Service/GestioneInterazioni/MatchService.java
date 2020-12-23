package com.unidates.Unidates.UniDates.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class  MatchService {
    @Autowired

    //da aggiustare il match giá presente
    private MatchRepository matchRepository;

    public void aggiungiMatch(Studente studente1, Studente studente2){
        Match attuale = matchRepository.findAllByStudente1AndStudente2(studente1,studente2);
        if(attuale == null){
            Match reverse = matchRepository.findAllByStudente1AndStudente2(studente2,studente1);
            if(reverse == null){
                System.out.println("match non trovato");
                Match match = new Match(studente1, studente2);
                match.setLikedByStudent1(true);
                match.setLikeByStudent2(false);
                matchRepository.save(match);
            }
            else{
                System.out.println("Match trovato!");
                reverse.setLikeByStudent2(true);
                matchRepository.save(reverse);
            }
        }
    }
}

