package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Service.MatchService;
import com.unidates.Unidates.UniDates.Service.NotificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/interactionManager")
public class GestioneInterazioniController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private NotificaService notificaService;

    @RequestMapping("/aggiungiMatch")
    public void aggiungiMatch(@RequestParam String emailStudente1,@RequestParam String emailStudente2){
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailStudente1)) {
            matchService.aggiungiMatch(emailStudente1, emailStudente2);
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/isValidMatch")
    public boolean isValidMatch(@RequestParam String emailStudente1, @RequestParam String emailStudente2){

       return matchService.isValidMatch(emailStudente1, emailStudente2);
    }
}
