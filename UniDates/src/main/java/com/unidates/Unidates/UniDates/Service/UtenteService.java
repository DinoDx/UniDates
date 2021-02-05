package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import com.unidates.Unidates.UniDates.Service.Registrazione.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    Publisher publisher;

    public boolean isPresent(String email){
        return utenteRepository.findByEmail(email) != null;
    }

    public boolean registrazioneStudente(Studente s, Profilo p) throws AlreadyExistException {
        if(!isPresent(s.getEmail())) {
            s.setProfilo(p);
            s.setPassword(passwordEncoder.encode(s.getPassword()));
            utenteRepository.save(s);
            return true;
        }
        else throw new AlreadyExistException("Utente giá registrato con questa email!!");
    }

    public void registrazioneModeratore(Moderatore m, Profilo p) throws AlreadyExistException {
        if(!isPresent(m.getEmail())) {
            m.setProfilo(p);
            m.setPassword(passwordEncoder.encode(m.getPassword()));
            utenteRepository.save(m);
        }
        else throw new AlreadyExistException("Utente giá registrato con questa email!!");
    }

    public void registrazioneCommunityManager(CommunityManager cm, Profilo p) throws AlreadyExistException {
        if(!isPresent(cm.getEmail())) {
        cm.setProfilo(p);
        cm.setPassword(passwordEncoder.encode(cm.getPassword()));
        utenteRepository.save(cm);
        }
        else throw new AlreadyExistException("Utente giá registrato con questa email!!");
    }

    public Utente trovaUtente(String email) throws EntityNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        if(utente == null) throw new EntityNotFoundException("Utente non trovato");
        else return utente;
    }

    public Studente trovaStudente(String email) throws EntityNotFoundException {
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) return studente;
         else throw new EntityNotFoundException("Studente non trovato!");
    }


    public boolean bloccaStudente(String emailStudenteBloccante, String emailStudenteBloccato) {
        Studente studenteBloccante = (Studente) utenteRepository.findByEmail(emailStudenteBloccante);
        Studente studenteBloccato = (Studente) utenteRepository.findByEmail(emailStudenteBloccato);
        studenteBloccante.addBloccato(studenteBloccato);
        utenteRepository.save(studenteBloccante);
        return true;
    }

    public boolean sbloccaStudente(String emailStudenteSbloccante, String emailStudenteSbloccato) {
        Studente studenteSbloccante = (Studente) utenteRepository.findByEmail(emailStudenteSbloccante);
        Studente studentesBloccato = (Studente) utenteRepository.findByEmail(emailStudenteSbloccato);
        studenteSbloccante.removeBloccato(studentesBloccato);
        utenteRepository.save(studenteSbloccante);
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


    public void testPython(){
        String result = restTemplate.getForObject("http://localhost:5000/", String.class);

        System.out.println(result);
    }

}

