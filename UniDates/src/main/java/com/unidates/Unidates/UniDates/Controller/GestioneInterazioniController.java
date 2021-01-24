package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Service.MatchService;
import com.unidates.Unidates.UniDates.Service.NotificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/interactionManager")
public class GestioneInterazioniController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private NotificaService notificaService;

    @RequestMapping("/aggiungiMatch")
    public void aggiungiMatch(String emailStudente1, String emailStudente2){
        matchService.aggiungiMatch(emailStudente1, emailStudente2);
    }

    @RequestMapping("/isValidMatch")
    public boolean isValidMatch(String emailStudente1, String emailStudente2){
       return matchService.isValidMatch(emailStudente1, emailStudente2);
    }
}
