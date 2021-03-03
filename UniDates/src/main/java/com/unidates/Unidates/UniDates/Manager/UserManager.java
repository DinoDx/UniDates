package com.unidates.Unidates.UniDates.Manager;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Repository.ProfiloRepository;
import com.unidates.Unidates.UniDates.Repository.StudenteRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManager {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private StudenteRepository studenteRepository;

    @Autowired
    private ProfiloRepository profiloRepository;

   /* @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SessionRegistry sessionRegistry;
    */



    public Studente registrazioneStudente(Studente s, Profilo p) throws AlreadyExistException {
        if(!isPresent(s.getEmail())) {
            s.setProfilo(p);
            s.setPassword(passwordEncoder.encode(s.getPassword()));
           return utenteRepository.saveAndFlush(s); //
        }
        else throw new AlreadyExistException("Utente giá registrato con questa email!!");
    }

    public Moderatore registrazioneModeratore(Moderatore m, Profilo p) throws AlreadyExistException {
        if(!isPresent(m.getEmail())) {
            m.setProfilo(p);
            m.setPassword(passwordEncoder.encode(m.getPassword()));
            utenteRepository.saveAndFlush(m);
            return m;
        }
        else throw new AlreadyExistException("Utente giá registrato con questa email!!");
    }

    public CommunityManager registrazioneCommunityManager(CommunityManager cm, Profilo p) throws AlreadyExistException {
        if(!isPresent(cm.getEmail())) {
            cm.setProfilo(p);
            cm.setPassword(passwordEncoder.encode(cm.getPassword()));
            utenteRepository.saveAndFlush(cm);
            return cm;
        }
        else throw new AlreadyExistException("Utente giá registrato con questa email!!");
    }

    public Utente trovaUtente(String email) throws EntityNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        if(utente != null) return utente;
        else throw new EntityNotFoundException("Utente non trovato");
    }

    public Studente trovaStudente(String email) throws EntityNotFoundException {
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) return studente;
        else throw new EntityNotFoundException("Studente non trovato!");
    }


    public boolean bloccaStudente(String emailStudenteBloccante, String emailStudenteBloccato) {
        Studente studenteBloccante = (Studente) utenteRepository.findByEmail(emailStudenteBloccante);
        Studente studenteBloccato = (Studente) utenteRepository.findByEmail(emailStudenteBloccato);
        if(studenteBloccante != null && studenteBloccato != null) {
            if(!studenteBloccante.getListaBloccati().contains(studenteBloccato)) {
                studenteBloccante.addBloccato(studenteBloccato);
                utenteRepository.save(studenteBloccante);
                return true;
            }else throw new AlreadyExistException("L'utente è già stato bloccato!");
        }else throw new EntityNotFoundException("Studente non trovato");
    }

    public boolean sbloccaStudente(String emailStudenteSbloccante, String emailStudenteSbloccato) {
        Studente studenteSbloccante = (Studente) utenteRepository.findByEmail(emailStudenteSbloccante);
        Studente studentesBloccato = (Studente) utenteRepository.findByEmail(emailStudenteSbloccato);
        if(studenteSbloccante != null && studentesBloccato != null) {
            studenteSbloccante.removeBloccato(studentesBloccato);
            utenteRepository.save(studenteSbloccante);
            return true;
        }else throw new EntityNotFoundException("Studente non trovato");

    }

    public boolean isPresent(String email){
        return utenteRepository.findByEmail(email) != null;
    }

    public Utente getUtenteByVerificationToken(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        if(token != null) {
            Utente utente = token.getUtente();
            if (utente != null) return utente;
            else throw new EntityNotFoundException("Utente non trovato!");
        }else throw new EntityNotFoundException("Token non trovato!");
    }


    public VerificationToken getVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken != null) return verificationToken;
        else throw new EntityNotFoundException("VerificationTokern non trovato!");
    }

    public VerificationToken createVerificationToken(Utente utente, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUtente(utente);
        verificationTokenRepository.save(myToken);
        return myToken;
    }

    public Utente deleteUtente(String email) {
        Utente u = utenteRepository.findByEmail(email);
        if(  u != null) {
            utenteRepository.delete(u);
            return u;
        }
        else throw new EntityNotFoundException("Utente non trovato!");
    }

    public Utente attivaUtenteRegistrato(String email) {
        Utente u = utenteRepository.findByEmail(email);
        if(u!= null) {
            u.setActive(true);
            utenteRepository.save(u);
            return u;
        }
        else throw new EntityNotFoundException("Utente non trovato!");
    }

    public List<Studente> findAllStudenti() {
        List<Studente>  listaStudenti = new ArrayList<Studente>();
        utenteRepository.findAll().forEach(s -> listaStudenti.add((Studente) s));
        return listaStudenti;
    }
    public Utente cambiaPassword(String emailUtente, String nuovaPassword) {
        Utente toChange = utenteRepository.findByEmail(emailUtente);
        if(toChange != null) {
            toChange.setPassword(passwordEncoder.encode(nuovaPassword));
            utenteRepository.save(toChange);
            return toChange;
        } else throw new EntityNotFoundException("Utente non trovato!");
    }

    public List<Studente> trovaStudentiAffini(ArrayList<Long> idProfili){
        List<Studente> toReturn = new ArrayList<>();
        idProfili.forEach( idProfilo -> toReturn.add(studenteRepository.findByProfilo(profiloRepository.findProfiloById(idProfilo))));
        return toReturn;
    }


    /* public void testPython(){
        String result = restTemplate.getForObject("http://localhost:5000/", String.class);

        System.out.println(result);
    }*/

}

