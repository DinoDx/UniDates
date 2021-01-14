package com.unidates.Unidates.UniDates.Service.GestioneUtenti;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Exception.AlreadyExistUserException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UtenteService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private StudenteRepository studenteRepository;



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

    public void registrazioneStudente(Studente s, Profilo p) throws AlreadyExistUserException{
        s.setProfilo(p);
        s.setPassword(passwordEncoder.encode(s.getPassword()));
        utenteRepository.save(s);
    }

    public void registrazioneModeratore(Moderatore m, Profilo p){
        m.setProfilo(p);
        m.setPassword(passwordEncoder.encode(m.getPassword()));
        utenteRepository.save(m);
    }

    public void registrazioneCommunityManager(CommunityManager cm, Studente s){
       if(isPresent(s)){
            cm.setStudente(s);
            cm.setPassword(s.getPassword());
            cm.setRuolo(Ruolo.COMMUNITY_MANAGER);
            cm.setEmail(s.getEmail());
            utenteRepository.save(cm);
        }
       else throw new AlreadyExistUserException();
    }

    public Utente trovaUtente(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Studente trovaStudente(String email) {
        Studente studente = studenteRepository.findByEmail(email);
        if(studente != null) return studente;
        else return null;
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


    public Utente getUtenteByVerificationToken(String verificationToken) {
        Utente utente = verificationTokenRepository.findByToken(verificationToken).getUtente();

        return utente;
    }


    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public void createVerificationToken(Utente utente, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUtente(utente);
        verificationTokenRepository.save(myToken);
    }

    public void deleteUtente(Utente utente) {
        utenteRepository.delete(utente);
    }

    public void salvaUtenteRegistrato(Utente utente) {
        utenteRepository.save(utente);
    }
}

