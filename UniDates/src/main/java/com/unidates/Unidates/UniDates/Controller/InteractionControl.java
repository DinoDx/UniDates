package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Service.MatchService;
import com.unidates.Unidates.UniDates.Service.NotificaService;
import com.unidates.Unidates.UniDates.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/interactionManager")
public class InteractionControl {
    @Autowired
    private MatchService matchService;

    @Autowired
    private UtenteService utenteService;

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

    @RequestMapping("/bloccoStudente")
    public boolean bloccaStudente(@RequestParam String emailBloccante, @RequestParam String emailBloccato){
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailBloccante)) {
            return utenteService.bloccaStudente(emailBloccante, emailBloccato);
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/sbloccoStudente")
    public boolean sbloccaStudente(@RequestParam String emailSbloccante, @RequestParam String emailSbloccato){
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailSbloccante)) {
            return utenteService.sbloccaStudente(emailSbloccante, emailSbloccato);
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/ricercaStudente")
    public StudenteDTO ricercaStudente(@RequestParam String email){
        if(checkEmail(email)) {
            Studente studente = (Studente) utenteService.trovaUtente(email);

            if(studente != null)
                return EntityToDto.toDTO(studente);
            else throw new UserNotFoundException();
        }
        else throw new UserNotFoundException();
    }

    private boolean checkEmail(String email){
        if (email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
            return true;

        return false;
    }
}
