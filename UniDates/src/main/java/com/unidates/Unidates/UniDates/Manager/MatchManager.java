package com.unidates.Unidates.UniDates.Manager;

import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import com.unidates.Unidates.UniDates.Repository.MatchRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchManager {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private MatchRepository matchRepository;

    public void aggiungiMatch(String emailStudente1, String emailStudente2){

        Studente invia = (Studente) utenteRepository.findByEmail(emailStudente1);
        Studente riceve = (Studente) utenteRepository.findByEmail(emailStudente2);
        if(invia != null && riceve != null) {
            Match attuale = matchRepository.findAllByStudente1AndStudente2(invia, riceve);
            if (attuale == null) {
                Match reverse = matchRepository.findAllByStudente1AndStudente2(riceve, invia);
                if (reverse == null) {
                    Match match = new Match(invia, riceve);
                    match.setLikedByStudent1(true);
                    match.setLikeByStudent2(false);
                    matchRepository.save(match);
                } else {
                    reverse.setLikeByStudent2(true);
                    matchRepository.save(reverse);
                }
            }
        } else throw new EntityNotFoundException("Studente non trovato!");
    }

    public Match trovaMatch(String emailStudente1, String  emailStudente2){
        Studente studente1 = (Studente) utenteRepository.findByEmail(emailStudente1);
        Studente studente2 = (Studente) utenteRepository.findByEmail(emailStudente2);
        if(studente1 != null && studente2 != null) {
            Match invia = matchRepository.findAllByStudente1AndStudente2(studente1, studente2);
            Match reverse = matchRepository.findAllByStudente1AndStudente2(studente2, studente1);
            if(invia != null || reverse!= null) {
                return invia == null ? reverse : invia;
            } else throw new EntityNotFoundException("Match non trovato");
        }else throw new EntityNotFoundException("Studente non trovato!");
    }

    public boolean isValidMatch(String emailStudente1, String emailStudente2){
        Studente studente1 = (Studente) utenteRepository.findByEmail(emailStudente1);
        Studente studente2 = (Studente) utenteRepository.findByEmail(emailStudente2);

        if(studente1 != null && studente2 != null) {
            Match invia = matchRepository.findAllByStudente1AndStudente2(studente1, studente2);
            Match reverse = matchRepository.findAllByStudente1AndStudente2(studente2, studente1);

            if (invia != null) {
                return invia.isLikeByStudent2() && invia.isLikedByStudent1();
            } else if (reverse != null) {
                return reverse.isLikedByStudent1() && reverse.isLikeByStudent2();
            } else {
                throw new EntityNotFoundException("Match non trovato!");
            }
        }else throw new EntityNotFoundException("Studente non trovato!");
    }


    public void eliminaMatch(String  emailStudente1, String emailStudente2){
        Studente invia = (Studente) utenteRepository.findByEmail(emailStudente1);
        Studente riceve = (Studente) utenteRepository.findByEmail(emailStudente2);
        if(invia != null && riceve != null) {
            matchRepository.deleteById(trovaMatch(emailStudente1, emailStudente2).getId());
        }
    }
}

