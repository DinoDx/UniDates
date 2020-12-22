package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.Ammonimenti;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmmonimentiService {

    @Autowired
    AmmonimentiRepository ammonimentiRepository;

    @Autowired
    StudenteRepository studenteRepository;

    public void sendAmmonimento(Studente studente){
        studente.addAmmonimento(new Ammonimenti());
    }

}
