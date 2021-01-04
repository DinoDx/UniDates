package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SegnalazioniRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SospensioniRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.StudenteRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ModerazioneService {

    @Autowired
    SegnalazioniRepository segnalazioniRepository;

    @Autowired
    AmmonimentiRepository ammonimentiRepository;

    @Autowired
    SospensioniRepository sospensioniRepository;

    @Autowired
    UtenteRepository utenteRepository;



    public void inviaSegnalazione(Segnalazione s ,Foto f){
        s.setFoto(f);
        // s.setModeratore(moderatore); moderatore scelto in automatico dal Service
        segnalazioniRepository.save(s);
    }

    public void inviaAmmonimento(Ammonimento a, Studente s, Moderatore m){
        a.setStudente(s);
        a.setModeratore(m);
        ammonimentiRepository.save(a);
    }

    public void inviaSospensione(Sospensione sospensione,Studente studente){
        sospensione.setStudente(studente);
        sospendiStudente(studente);
        sospensioniRepository.save(sospensione);
    }

    public Collection<Segnalazione> visualizzaSegnalazioniRicevute(Moderatore moderatore) {
        return moderatore.getSegnalazioniRicevute();
    }

    public Collection<Ammonimento> visualizzaAmmonimentiInviati(Moderatore moderatore) {
        return moderatore.getAmmonimentiInviati();
    }


    public Collection<Ammonimento> visualizzaAmmonimentiRicevuti(Studente studente) {
        return studente.getListAmmonimenti();
    }

    public Collection<Sospensione> visualizzaSospensioni(Studente studente) {
        return studente.getListSospensioni();
    }

    private void sospendiStudente(Studente studente){
        studente.setBanned(true);
        utenteRepository.save(studente);
    }

}
