package com.unidates.Unidates.UniDates.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Match;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  MatchService {
    @Autowired

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

        return invia == null ? reverse: invia;
    }

    public boolean isValidMatch(Studente studente1, Studente studente2){
        Match invia = matchRepository.findAllByStudente1AndStudente2(studente1, studente2);

        Match reverse = matchRepository.findAllByStudente1AndStudente2(studente2, studente1);

        if(invia != null){
            return invia.isLikeByStudent2() && invia.isLikedByStudent1();
        }
        else if(reverse != null){
            return reverse.isLikedByStudent1() && reverse.isLikeByStudent2();
        }
        else {
            return false;
        }
    }
}

