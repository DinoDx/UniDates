package com.unidates.Unidates.UniDates.Service.GestioneUtenti;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistUserException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.DAOs.StudenteDao;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.ModeratoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StudenteService {

    @Autowired
    private StudenteDao studenteDao;

    @Autowired
    private ModeratoreRepository moderatoreRepository;


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

    public boolean addStudente(Studente studente, Profilo profilo){
        try {
            if (!studenteDao.isPresent(studente.getEmail())) {
                studente.setProfilo(profilo);
                studente.setPassword(new BCryptPasswordEncoder().encode(studente.getPassword()));
                studente.setListNotifica( new ArrayList<Notifica>());
                studente.setMittente(new ArrayList<Chat>());
                studente.setListaBloccati(new ArrayList<Studente>());
                studenteDao.saveUtente(studente);
                return true;
            }
            else throw new AlreadyExistUserException();
        }catch (AlreadyExistUserException alreadyExistUserException){
            alreadyExistUserException.printStackTrace();
            return false;
        }

    }



    public boolean bloccaStudente(Studente studenteBloccante, Studente studenteBloccato) {
        studenteBloccante.getListaBloccati().add(studenteBloccato);
        studenteDao.saveUtente(studenteBloccante);
        return true;
    }

    public Studente findByEmail(String email) {
        try {
            Studente studente = studenteDao.findByEmail(email);
            if(studente != null) return studente;
            else throw new UserNotFoundException();
        }catch (UserNotFoundException userNotFoundException){
            userNotFoundException.printStackTrace();
        }
       return null;
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

    public boolean addModeratore(Moderatore moderatore, Studente studente) {
        moderatore.setStudente(studente);
        moderatoreRepository.save(moderatore);
        return true;
    }

    public boolean modificaStudente(Studente studente, Profilo profilo) {
        studente.setProfilo(profilo);
        studenteDao.saveUtente(studente);
        return true;
    }
}

