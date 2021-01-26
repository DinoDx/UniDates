package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Exception.BannedUserException;
import com.unidates.Unidates.UniDates.Exception.NotConfirmedAccountException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    Publisher publisher;



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


    public boolean bloccaStudente(String emailStudenteBloccante, String emailStudenteBloccato) {
        Studente studenteBloccante = (Studente) utenteRepository.findByEmail(emailStudenteBloccante);
        Studente studenteBloccato = (Studente) utenteRepository.findByEmail(emailStudenteBloccato);
        studenteBloccante.getListaBloccati().add(studenteBloccato);
        utenteRepository.save(studenteBloccante);
        publisher.publishBlockedEvent(studenteBloccante, studenteBloccato);
        return true;
    }

    public boolean sbloccaStudente(String emailStudenteSbloccante, String emailStudenteSbloccato) {
        Studente studenteSbloccante = (Studente) utenteRepository.findByEmail(emailStudenteSbloccante);
        Studente studentesBloccato = (Studente) utenteRepository.findByEmail(emailStudenteSbloccato);
        studenteSbloccante.getListaBloccati().remove(studentesBloccato);
        utenteRepository.save(studentesBloccato);
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

    public List<Studente> findAllStudenti() {
        List<Studente>  listaStudenti = new ArrayList<Studente>();
        utenteRepository.findAll().forEach(s -> listaStudenti.add((Studente) s));
        return listaStudenti;
    }
    public void cambiaPassword(String emailUtente, String nuovaPassword) {
        Utente toChange = utenteRepository.findByEmail(emailUtente);
        toChange.setPassword(passwordEncoder.encode(nuovaPassword));
        utenteRepository.save(toChange);
    }

}

