package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.ChatService;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.NotificaService;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/api/UserManager")
public class GestioneUtentiController {

    @Autowired
    UtenteService utenteService;

    @Autowired
    NotificaService notificaService;

    @Autowired
    ChatService chatService;


    @RequestMapping("/registrazioneStudente")
    public boolean registrazioneStudente(Studente s, Profilo p){
        checkStudente(s);
        checkProfilo(p);
        return utenteService.registrazioneStudente(s, p);
    }

    @RequestMapping("/registrazioneModeratore")
    public boolean registrazioneModeratore(Moderatore m, Studente s){
        return utenteService.registrazioneModeratore(m, s);
    }

    @RequestMapping("/registrazioneCommunityManager")
    public boolean registrazioneModeratore(CommunityManager cm, Studente s){
        return utenteService.registrazioneCommunityManager(cm, s);
    }

    @RequestMapping("/bloccoStudente")
    public boolean bloccaStudente(Studente sBloccante, Studente sBloccato){
       return utenteService.bloccaStudente(sBloccante,sBloccato);
    }

    @RequestMapping("/sbloccoStudente")
    public boolean sbloccaStudente(Studente sBloccante, Studente sBloccato){
        return utenteService.sbloccaStudente(sBloccante,sBloccato);
    }

    @RequestMapping("/trovaStudente")
    public Studente trovaUtente(String email){
        checkEmail(email);
        return (Studente) utenteService.findUtenteByEmail(email);
    }


    public boolean checkEmail(String email){
        if (email != null)
            return true;

        return false;
    }

    public boolean checkProfilo(Profilo p){
        if (p.getNome() != null && p.getCognome() != null)
            return true;

        return false;
    }


    private boolean checkStudente(Studente s) {
        if(s.getEmail() != null && s.getPassword() != null)
            return true;

        return false;
    }
}
