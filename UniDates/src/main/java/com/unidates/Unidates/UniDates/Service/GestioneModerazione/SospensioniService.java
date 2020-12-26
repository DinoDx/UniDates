package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensioni;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SospensioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SospensioniService {

    @Autowired
    SospensioniRepository sospensioniRepository;

    public void suspendStudente(Studente studente, int durata, String dettagli){

        Sospensioni sospensione = new Sospensioni(durata, dettagli);
        sospensione.setStudente(studente);
        sospensioniRepository.save(sospensione);

    }
}
