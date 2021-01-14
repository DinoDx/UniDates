package com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.NotificaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificaService {
    @Autowired
    private NotificaRepository notificaRepository;

    public void agiunginNotifica(Notifica notifica, Utente utente) {
        notifica.setUtente(utente);
        notificaRepository.save(notifica);
    }

    public List<Notifica> visualizzaNotifiche(Utente u){
        return (List<Notifica>) u.getListNotifica();
    }
}
