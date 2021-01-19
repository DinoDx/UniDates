package com.unidates.Unidates.UniDates.Model.Service.GestioneUtenti;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistUserException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtenteRepository utenteRepository;



    public boolean isPresent(Utente u){
        return utenteRepository.findByEmail(u.getEmail()) != null;
    }

    public boolean isBanned(Studente s){
        return s.isBanned();
    }

    public boolean isActive(Studente s){
        return s.isActive();
    }

    public void registrazioneStudente(Studente s, Profilo p){
        s.setProfilo(p);
        s.setPassword(passwordEncoder.encode(s.getPassword()));
        utenteRepository.save(s);
    }

    public void registrazioneModeratore(Moderatore m, Profilo p){
        m.setProfilo(p);
        m.setPassword(passwordEncoder.encode(m.getPassword()));
        utenteRepository.save(m);
    }

    public void registrazioneCommunityManager(CommunityManager cm, Profilo p){
        cm.setProfilo(p);
        cm.setPassword(passwordEncoder.encode(cm.getPassword()));
        utenteRepository.save(cm);
    }

    public Utente trovaUtente(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Studente trovaStudente(String email) {
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) return studente;
        else return null;
    }


    public boolean bloccaStudente(Studente studenteBloccante, Studente studenteBloccato) {
        studenteBloccante.getListaBloccati().add(studenteBloccato);
        utenteRepository.save(studenteBloccante);
        return true;
    }

    public boolean sbloccaStudente(Studente studenteBloccante, Studente studenteBloccato) {
        studenteBloccante.getListaBloccati().remove(studenteBloccato);
        utenteRepository.save(studenteBloccante);
        return true;
    }

    //Da inserire nell'SDD

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

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public void cambiaPassword(Utente utente, String nuovaPassword) {
        utente.setPassword(passwordEncoder.encode(nuovaPassword));
        utenteRepository.save(utente);
    }
}

