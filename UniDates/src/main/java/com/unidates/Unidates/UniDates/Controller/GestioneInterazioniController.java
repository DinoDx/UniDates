package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class GestioneInterazioniController {
    @Autowired
    private MatchService matchService;

    public void aggiungiMatch(Studente studente1, Studente studente2){
        matchService.aggiungiMatch(studente1, studente2);
    }

    public void findMatch(Studente studente1, Studente studente2){

    }

}
