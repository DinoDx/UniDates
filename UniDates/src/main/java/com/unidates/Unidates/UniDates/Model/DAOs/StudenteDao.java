package com.unidates.Unidates.UniDates.Model.DAOs;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudenteDao {
    @Autowired
    private StudenteRepository studenteRepository;

    public boolean isPresent(String email){
        return studenteRepository.findByEmail(email) != null;
    }

    public void saveUtente(Studente studente){
        studenteRepository.save(studente);
    }

    public Collection<Studente> findAll(){
        return studenteRepository.findAll();
    }

    public void removeUtente(Studente studente){
        studenteRepository.deleteById(studente.getId());
    }

    public Studente findByEmail(String email){
        return studenteRepository.findByEmail(email);
    }

}
