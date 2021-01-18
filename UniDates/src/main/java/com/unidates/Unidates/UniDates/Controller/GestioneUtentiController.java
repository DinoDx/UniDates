package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Service.GestioneEventi.GestioneUtenti.OnRegistrationCompleteEvent;
import com.unidates.Unidates.UniDates.Enum.Interessi;
import com.unidates.Unidates.UniDates.Enum.Sesso;
import com.unidates.Unidates.UniDates.Exception.AlreadyExistUserException;
import com.unidates.Unidates.UniDates.Exception.InvalidRegistrationFormatException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Service.GestioneUtenti.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/api/UserManager")
public class GestioneUtentiController {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    UtenteService utenteService;

    @RequestMapping("/registrazioneStudente")
    public void registrazioneStudente(Studente s, Profilo p, HttpServletRequest request) {
        if(checkStudente(s) && checkProfilo(p)) {
            if(!utenteService.isPresent(s)){
                utenteService.registrazioneStudente(s, p);
                String appUrl = request.getContextPath();
                applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(s, request.getLocale(), appUrl));
            }
            else throw new AlreadyExistUserException();
        }
        else throw new InvalidRegistrationFormatException();
    }

    @RequestMapping("/registrazioneModeratore")
    public void registrazioneModeratore(Moderatore m, Profilo p )  {
        if(checkStudente(m) && checkProfilo(p)) { // per il moderatore e il cm non viene inviata alcuna mail di registrazione
            if(!utenteService.isPresent(m))
                utenteService.registrazioneModeratore(m, p);
            else throw new AlreadyExistUserException();
        }
        else throw new InvalidRegistrationFormatException();
    }

    @RequestMapping("/registrazioneCommunityManager")
    public void registrazioneCommunityManager(CommunityManager cm, Profilo p){
        if(checkStudente(cm) && checkProfilo(p)){
            if(!utenteService.isPresent(cm))
                utenteService.registrazioneCommunityManager(cm, p);
            else throw new AlreadyExistUserException();
        }
        else throw new InvalidRegistrationFormatException();
    }

    @RequestMapping("/bloccoStudente")
    public boolean bloccaStudente(Studente sBloccante, Studente sBloccato){
       return utenteService.bloccaStudente(sBloccante,sBloccato);
    }

    @RequestMapping("/sbloccoStudente")
    public boolean sbloccaStudente(Studente sBloccante, Studente sBloccato){
        return utenteService.sbloccaStudente(sBloccante,sBloccato);
    }

    @RequestMapping("/trovaUtente")
    public Utente trovaUtente(String email) {
        if(checkEmail(email))
            return utenteService.trovaUtente(email);
        else throw new InvalidRegistrationFormatException();
    }


    @GetMapping("/registrationConfirm")
    public String confermaRegistrazione(@RequestParam("token") String token) {

        VerificationToken verificationToken = utenteService.getVerificationToken(token);
        System.out.println(token);
        if (verificationToken == null) {
            return "Token non valido";
        }

        Utente utente = utenteService.getUtenteByVerificationToken(token);
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            utenteService.deleteUtente(utente);
            return "Token scaduto";
        }
        System.out.println("Utente attivato: " + utente);
        utente.setActive(true);
        utenteService.salvaUtenteRegistrato(utente);
        return "Utente confermato";
    }

    @RequestMapping("/findAll")
    public List<Utente> findAll(){
        return utenteService.findAll();
    }

    public boolean checkEmail(String email){
        if (email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
            return true;

        return false;
    }

    public boolean checkProfilo(Profilo p) {
        if (p.getNome() != null && p.getCognome() != null && p.getLuogoNascita() != null && p.getResidenza() != null && p.getDataDiNascita() != null && p.getAltezza() != 0 && p.getSesso() != null && p.getInteressi() != null && p.getColori_capelli() != null && p.getColore_occhi() != null && p.getHobbyList().size() > 0){
            if (p.getNome().length() > 0 && p.getCognome().length() > 0 && p.getLuogoNascita().length() > 0 && p.getResidenza().length() > 0){
                if(p.getSesso() == Sesso.UOMO || p.getSesso() == Sesso.DONNA || p.getSesso() == Sesso.ALTRO){
                    if(p.getInteressi() == Interessi.UOMINI || p.getInteressi() == Interessi.DONNE || p.getInteressi() == Interessi.ENTRAMBI || p.getInteressi() == Interessi.ALTRO){
                        // Controlli su colore occhi e capelli
                        //if(!p.getHobbyList().contains(null))
                        return true;
                    }
                }
            }
        }

        return false;
    }


    private boolean checkStudente(Studente s) {
        if(s.getEmail() != null && s.getPassword() != null){
            if(checkEmail(s.getEmail())){
                return s.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
            }
        }
         return false;
    }

    public void cambiaPassword(Utente utente, String value) {
    }
}
