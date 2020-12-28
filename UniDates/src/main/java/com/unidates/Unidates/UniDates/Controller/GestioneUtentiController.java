package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.ChatService;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.NotificaService;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.StudenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/api/UserManager")
public class GestioneUtentiController {

    @Autowired
    StudenteService studenteService;

    @Autowired
    NotificaService notificaService;

    @Autowired
    ChatService chatService;


    @RequestMapping("/registrazioneStudente")
    public boolean registrazioneStudente(Studente studente, Profilo profilo){
        return studenteService.addStudente(studente, profilo);
    }

    @RequestMapping("/registrazioneModeratore")
    public boolean registrazioneModeratore(Moderatore moderatore, Studente studente){
        return studenteService.addModeratore(moderatore, studente);
    }

    @RequestMapping("/bloccoStudente")
    public boolean bloccaStudente(Studente studenteBloccante, Studente studenteBloccato){
       return studenteService.bloccaStudente(studenteBloccante,studenteBloccato);
    }

    @RequestMapping("/removeStudente")
    public void removeUtente(Studente studente){
        studenteService.removeUtente(studente);
    }

    @RequestMapping("/trovaStudente")
    public Studente findByEmail(String email){
        return studenteService.findByEmail(email);
    }




    public Collection<Studente> findAll(){
        return studenteService.findAll();

    }

    /*public void addNotifica(Notifica notifica, Studente studente){ notificaService.addNotifica(notifica, studente); }

    public void addChat(Chat chat ,Studente studente){chatService.createChat(chat, studente);}
     */
}
