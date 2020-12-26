package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazioni;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SegnalazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegnalazioniService {

    @Autowired
    SegnalazioniRepository segnalazioniRepository;

    public void addSegnalazione(Moderatore moderatore, Foto foto, String motivazione, String dettagli){
        Segnalazioni segnalazione = new Segnalazioni(motivazione, dettagli);
        segnalazione.setFoto(foto);
        segnalazione.setModeratore(moderatore);
        segnalazioniRepository.save(segnalazione);
    }
}
