package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Exception.MatchNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Match;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.ChatService;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/interactionManager")
public class GestioneInterazioniController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private ChatService chatService;


    @RequestMapping("/aggiungiMatch")
    public void aggiungiMatch(Studente studente1, Studente studente2){
        matchService.aggiungiMatch(studente1, studente2);
    }

    @RequestMapping("/findMatch")
    public Match findMatch (Studente studente1, Studente studente2){
        return matchService.trovaMatch(studente1, studente2);
    }

    public void sendMessage(Utente mittente, Utente destinatario, Messaggio messaggio){
        try {
            checkMessaggio(messaggio);
            chatService.inviaMessaggio(mittente, destinatario, messaggio);
        }
        catch (MatchNotFoundException matchNotFoundException){
            matchNotFoundException.printStackTrace();
        }
    }

    public boolean checkMessaggio(Messaggio messaggio){
        if(messaggio != null)
            return true;

        return false;
    }

}
