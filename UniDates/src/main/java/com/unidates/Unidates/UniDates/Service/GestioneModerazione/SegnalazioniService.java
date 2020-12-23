package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazioni;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SegnalazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegnalazioniService {

    @Autowired
    SegnalazioniRepository segnalazioniRepository;

    public void addSegnalazione(Foto foto, String motivazione, String dettagli){

        foto.addSegnalazione(new Segnalazioni(motivazione, dettagli));
    }
}
