package com.unidates.Unidates.UniDates.Controller;

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
    public boolean registrazioneStudente(Studente studente, Profilo profilo){
        checkStudente(studente);
        checkProfilo(profilo);
        return utenteService.registrazioneStudente(studente, profilo);
    }

    @RequestMapping("/registrazioneModeratore")
    public boolean registrazioneModeratore(Moderatore moderatore, Studente studente){
        return utenteService.registrazioneModeratore(moderatore, studente);
    }

    @RequestMapping("/bloccoStudente")
    public boolean bloccaStudente(Studente studenteBloccante, Studente studenteBloccato){
       return utenteService.bloccaStudente(studenteBloccante,studenteBloccato);
    }

    @RequestMapping("/removeStudente")
    public void removeUtente(Studente studente){
      //  utenteService.removeUtente(studente);
    }

    @RequestMapping("/trovaStudente")
    public Studente findByEmail(String email){
        checkEmail(email);
        return (Studente) utenteService.findUtenteByEmail(email);
    }

    public void updatestudente(Studente studente){
        utenteService.updatestudente(studente);
    }


    public Collection<Studente> findAll(){
        return utenteService.findAll();

    }

    public void updateStudente(Studente registrato, Profilo profilo) {
    }

    /*public void addNotifica(Notifica notifica, Studente studente){ notificaService.addNotifica(notifica, studente); }

    public void addChat(Chat chat ,Studente studente){chatService.createChat(chat, studente);}
     */

    public boolean checkEmail(String email){
        if (email != null)
            return true;

        return false;
    }

    public boolean checkProfilo(Profilo profilo){
        if (profilo.getNome() != null && profilo.getCognome() != null)
            return true;

        return false;
    }


    private boolean checkStudente(Studente studente) {
        if(studente.getEmail() != null && studente.getPassword() != null)
            return true;

        return false;
    }
}
