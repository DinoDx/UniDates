package com.unidates.Unidates.UniDates.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.MarshalException;
import java.util.Collection;

@Service
public class  MatchService {
    @Autowired

    //da aggiustare il match giá presente
    private MatchRepository matchRepository;

    public void aggiungiMatch(Studente invia, Studente riceve){
        Match attuale = matchRepository.findAllByStudente1AndStudente2(invia,riceve);
        if(attuale == null){
            Match reverse = matchRepository.findAllByStudente1AndStudente2(riceve,invia);
            if(reverse == null){
                Match match = new Match(invia, riceve);
                match.setLikedByStudent1(true);
                match.setLikeByStudent2(false);
                matchRepository.save(match);
            }
            else{
                reverse.setLikeByStudent2(true);
                matchRepository.save(reverse);
            }
        }
    }

    public Match findMatch(Studente studente1, Studente studente2){
        Match invia = matchRepository.findAllByStudente1AndStudente2(studente1, studente2);

        Match reverse = matchRepository.findAllByStudente1AndStudente2(studente2, studente1);

        if(invia == null)
            return reverse;
        else return invia;
    }
}

