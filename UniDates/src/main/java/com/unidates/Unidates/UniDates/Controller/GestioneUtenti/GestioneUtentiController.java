package com.unidates.Unidates.UniDates.Controller.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Profilo;
import com.unidates.Unidates.UniDates.Service.ChatService;
import com.unidates.Unidates.UniDates.Service.NotificaService;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.StudenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/api/UserManager")
public class GestioneUtentiController {

    @Autowired
    StudenteService studenteService;

    @Autowired
    NotificaService notificaService;

    @Autowired
    ChatService chatService;


    @RequestMapping("/add")
    public void addStudente(Studente studente, Profilo profilo){
        studenteService.addStudente(studente, profilo);
        }

    @RequestMapping("/all")
    public Collection<Studente> findAll(){
        return studenteService.findAll();

    }

    @RequestMapping("/remove")
    public void removeUtente(Studente studente){
        studenteService.removeUtente(studente);
    }

    @RequestMapping("/findByEmail")
    public Optional<Studente> findByEmail(String email){
        return studenteService.findByEmail(email);
    }

    public void addNotifica(Notifica notifica, Studente studente){ notificaService.addNotifica(notifica, studente); }

    public void addChat(Chat chat ,Studente studente){chatService.createChat(chat, studente);}
}
