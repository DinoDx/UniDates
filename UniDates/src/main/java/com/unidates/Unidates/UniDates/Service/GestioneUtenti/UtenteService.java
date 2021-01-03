package com.unidates.Unidates.UniDates.Service.GestioneUtenti;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistUserException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.CommunityManagerRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.ModeratoreRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.StudenteRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private ModeratoreRepository moderatoreRepository;

    @Autowired
    private CommunityManagerRepository communityManagerRepository;


    public boolean isPresent(Utente u){
        return utenteRepository.findByEmail(u.getEmail()) != null;
    }

    public boolean isBanned(Studente s){
        return s.isBanned();
    }

    public boolean isActive(Studente s){
        return s.isActive();
    }

    public void attivaStudente(Studente s){
        s.setActive(true);
        studenteRepository.save(s);
    }

    public boolean registrazioneStudente(Studente studente, Profilo profilo){
        try {
            if (!isPresent(studente)) {
                studente.setProfilo(profilo);
                studente.setPassword(new BCryptPasswordEncoder().encode(studente.getPassword()));
                studente.setListNotifica( new ArrayList<Notifica>());
                studente.setMittente(new ArrayList<Chat>());
                studente.setListaBloccati(new ArrayList<Studente>());
                studenteRepository.save(studente);
                return true;
            }
            else throw new AlreadyExistUserException();
        }catch (AlreadyExistUserException alreadyExistUserException){
            alreadyExistUserException.printStackTrace();
            return false;
        }

    }

    public boolean registrazioneModeratore(Moderatore m,Studente s){
        if(isPresent(m) && m.getEmail().equals(s.getEmail())){
            m.setStudente(s);
            moderatoreRepository.save(m);
            return true;
        }
        return false;
    }

    public boolean registrazioneCommunityManager(CommunityManager cm, Studente s){
        if(isPresent(cm) && cm.getEmail().equals(s.getEmail())){
            cm.setStudente(s);
            communityManagerRepository.save(cm);
            return true;
        }
        return false;
    }

    public Utente findUtenteByEmail(String email) {
        try {
            Utente utente = utenteRepository.findByEmail(email);
            if(utente != null) return utente;
            else throw new UserNotFoundException();
        }catch (UserNotFoundException userNotFoundException){
            userNotFoundException.printStackTrace();
        }
        return null;
    }


    public boolean bloccaStudente(Studente studenteBloccante, Studente studenteBloccato) {
        studenteBloccante.getListaBloccati().add(studenteBloccato);
        studenteRepository.save(studenteBloccante);
        return true;
    }

    public boolean sbloccaStudente(Studente studenteBloccante, Studente studenteBloccato) {
        studenteBloccante.getListaBloccati().remove(studenteBloccato);
        studenteRepository.save(studenteBloccante);
        return true;
    }



// PER TESTING

    public Collection<Studente> findAll(){
        return studenteRepository.findAll();
    }


    public boolean modificaStudente(Studente studente, Profilo profilo) {
        studente.setProfilo(profilo);
        studenteRepository.save(studente);
        return true;
    }

    public void updatestudente(Studente studente) {
        studenteRepository.save(studente);
    }



}

