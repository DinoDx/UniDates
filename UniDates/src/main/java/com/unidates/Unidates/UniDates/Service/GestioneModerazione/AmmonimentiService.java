package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.Ammonimenti;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmmonimentiService {

    @Autowired
    AmmonimentiRepository ammonimentiRepository;


    public void sendAmmonimento(Moderatore moderatore, Studente studente, String motivazione, String dettagli){

        Ammonimenti ammonimento = new Ammonimenti(motivazione, dettagli);
        ammonimento.setStudente(studente);
        ammonimento.setModeratore(moderatore);
        ammonimentiRepository.save(ammonimento);
    }

}
