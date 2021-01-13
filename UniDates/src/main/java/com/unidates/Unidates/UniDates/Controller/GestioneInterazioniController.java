package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Exception.InvalidMessageFormatException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Service.GestioneInterazioni.ChatService;
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
    private ChatService chatService;

    @Autowired
    private NotificaService notificaService;


    @RequestMapping("/aggiungiMatch")
    public void aggiungiMatch(Studente s1, Studente s2){
        matchService.aggiungiMatch(s1, s2);
    }

    @RequestMapping("/inviaMessaggio")
    public void inviaMessaggio(Utente mittente, Utente destinatario, Messaggio m){
        if(checkMessaggio(m))
            chatService.inviaMessaggio(mittente, destinatario, m);
        else throw new InvalidMessageFormatException();
    }

    @RequestMapping("/visualizzaArchivioChat")
    public List<Chat> visualizzaArchivioChat(Utente u){
        return chatService.visualizzaArchivioChat(u);
    }

    @RequestMapping("/visualizzaNotifica")
    public List<Notifica> visualizzaNotifica(Utente u){
        return notificaService.visualizzaNotifiche(u);
    }

    private boolean checkMessaggio(Messaggio m){
        if(m != null && m.getTestoMessaggio().length() > 0 && m.getTestoMessaggio().length() < 250)
            return true;
        return false;
    }

}
