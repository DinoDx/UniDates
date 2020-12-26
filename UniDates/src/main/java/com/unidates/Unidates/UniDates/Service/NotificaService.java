package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.NotificaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificaService {
    @Autowired
    private NotificaRepository notificaRepository;

    public void addNotifica(Notifica notifica, Utente utente) {
        notifica.setUtente(utente);
        notificaRepository.save(notifica);
    }
}
