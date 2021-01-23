package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.MatchService;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.NotificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/interactionManager")
public class GestioneInterazioniController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private NotificaService notificaService;

    @RequestMapping("/aggiungiMatch")
    public void aggiungiMatch(Studente s1, Studente s2){
        matchService.aggiungiMatch(s1, s2);
    }

    @RequestMapping("/isValidMatch")
    public boolean isValidMatch(Studente s1, Studente s2){
       return matchService.isValidMatch(s1, s2);
    }

    @RequestMapping("/visualizzaNotifica")
    public List<Notifica> visualizzaNotifica(Utente u){
        return notificaService.visualizzaNotifiche(u);
    }


}
