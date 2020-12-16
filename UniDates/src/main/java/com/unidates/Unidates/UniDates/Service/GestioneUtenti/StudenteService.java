package com.unidates.Unidates.UniDates.Service.GestioneUtenti;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistUserException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.DAOs.StudenteDao;
import com.unidates.Unidates.UniDates.Model.Entity.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Profilo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class StudenteService {

    @Autowired
    private StudenteDao studenteDao;


    /*public void addUtente(Utente utente, Profilo profilo){
        try {
            if (!studenteDao.isPresent(utente.getEmail())) {
                utente.setListNotifica( new ArrayList<Notifica>());
                utente.setListaChat(new ArrayList<Chat>());
                studenteDao.saveUtente(utente);
            }
            else throw new AlreadyExistUserException();
        }catch (AlreadyExistUserException alreadyExistUserException){
            alreadyExistUserException.printStackTrace();
        }

    }*/

    public void addStudente(Studente studente, Profilo profilo){
        try {
            if (!studenteDao.isPresent(studente.getEmail())) {
                studente.setProfilo(profilo);
                studente.setListNotifica( new ArrayList<Notifica>());
                studente.setListaChat(new ArrayList<Chat>());
                studenteDao.saveUtente(studente);
            }
            else throw new AlreadyExistUserException();
        }catch (AlreadyExistUserException alreadyExistUserException){
            alreadyExistUserException.printStackTrace();
        }

    }


    public Collection<Studente> findAll(){
        return studenteDao.findAll();
    }

    public void removeUtente(Studente studente) {
        try{
            if (studenteDao.isPresent(studente.getEmail()))
                studenteDao.removeUtente(studente);
            else throw new UserNotFoundException();
        }catch (UserNotFoundException userNotFoundException){
            userNotFoundException.printStackTrace();
        }
    }

    public Optional<Studente> findByEmail(String email) {
        return Optional.of(studenteDao.findByEmail(email));
    }
}
