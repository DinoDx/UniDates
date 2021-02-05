package com.unidates.Unidates.UniDates.Control;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
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
        if (checkEmail(emailStudente1) && checkEmail(emailStudente2)) {
            if (SecurityUtils.getLoggedIn().getEmail().equals(emailStudente1)) {
                if (!emailStudente1.equals(emailStudente2)) {
                    matchService.aggiungiMatch(emailStudente1, emailStudente2);
                    if (matchService.isValidMatch(emailStudente1, emailStudente2)) // se il match si è verificato da entrambe le parti
                        notificaService.generateNotificaMatch(emailStudente1, emailStudente2);
                } else throw new NotAuthorizedException("Non puoi inserire un match per te stesso!");
            } else throw new NotAuthorizedException("Non puoi inserire un match per un altro utente!");
        }else throw new InvalidFormatException("Formato email non valido");
    }

    @RequestMapping("/isValidMatch")
    public boolean isValidMatch(@RequestParam String emailStudente1, @RequestParam String emailStudente2) {
        if (checkEmail(emailStudente1) && checkEmail(emailStudente2)) {
           // if (SecurityUtils.getLoggedIn().equals(emailStudente1)) {
               // if (!emailStudente1.equals(emailStudente2))
                    return matchService.isValidMatch(emailStudente1, emailStudente2);
              //  else throw new NotAuthorizedException("Non puoi verificare un match per te stesso!");
           // } else throw new NotAuthorizedException("Non puoi verificare un match per un altro studente!");
        }else throw new InvalidFormatException("Formato email non valido");
    }


    @RequestMapping("/bloccoStudente")
    public boolean bloccaStudente(@RequestParam String emailBloccante, @RequestParam String emailBloccato){
        if(checkEmail(emailBloccante) && checkEmail(emailBloccato)) {
            if (SecurityUtils.getLoggedIn().getEmail().equals(emailBloccante)) {
                if (emailBloccante.equals(emailBloccato)) {
                    utenteService.bloccaStudente(emailBloccante, emailBloccato);
                    if (matchService.isValidMatch(emailBloccante, emailBloccato)) {
                        matchService.eliminaMatch(emailBloccante, emailBloccato);
                        notificaService.eliminaNoificaMatch(emailBloccante, emailBloccato);
                        return true;
                    }
                    return true;
                } else throw new NotAuthorizedException("Non puoi bloccare te stesso");
            } else throw new NotAuthorizedException("Non puoi bloccare per un altro studente");
        }else throw new InvalidFormatException("Formato email non valido");
    }

    @RequestMapping("/sbloccoStudente")
    public boolean sbloccaStudente(@RequestParam String emailSbloccante, @RequestParam String emailSbloccato) {
        if (checkEmail(emailSbloccante) && checkEmail(emailSbloccato)) {
            if (SecurityUtils.getLoggedIn().getEmail().equals(emailSbloccante)) {
                if (emailSbloccante.equals(emailSbloccato)) {
                    return utenteService.sbloccaStudente(emailSbloccante, emailSbloccato);
                } else throw new NotAuthorizedException("Non puoi sbloccare te stesso");
            } else throw new NotAuthorizedException("Non puoi sbloccare per un altro studente");
        } else throw new InvalidFormatException("Formato email non valido");
    }

    @RequestMapping("/ricercaStudente")
    public StudenteDTO ricercaStudente(@RequestParam String email) throws EntityNotFoundException, InvalidFormatException {
            if (checkEmail(email)) {
                Studente studente = (Studente) utenteService.trovaUtente(email);
                return EntityToDto.toDTO(studente);
            }
            else throw new InvalidFormatException("Formato email non valido!");
    }

    private boolean checkEmail(String email){
        if (email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
            return true;
        return false;
    }
}
