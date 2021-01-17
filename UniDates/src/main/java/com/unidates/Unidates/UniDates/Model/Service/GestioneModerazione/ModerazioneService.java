package com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SegnalazioniRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SospensioniRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.FotoRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.UtenteRepository;
import com.unidates.Unidates.UniDates.Model.Service.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    FotoRepository fotoRepository;


    @Autowired
    Publisher publisher;


    public void inviaSegnalazione(Segnalazione s ,Foto f){
        s.setFoto(f);
        // s.setModeratore(moderatore); moderatore scelto in automatico dal Service
        segnalazioniRepository.save(s);
    }

    public void inviaAmmonimento(Ammonimento a, Studente s, Moderatore m, Foto foto){
        a.setStudente(s);
        a.setModeratore(m);
        a.setFoto(foto);
        ammonimentiRepository.save(a);

        s.addAmmonimentoattivo();
        utenteRepository.save(s);

        publisher.publishWarning(a);
    }

    public void inviaSospensione(Sospensione sp,Studente s){
        sp.setStudente(s);
        sospendiStudente(s);
        sospensioniRepository.save(sp);
        publisher.publishBannedEvent(sp);
    }

    public List<Segnalazione> visualizzaSegnalazioniRicevute(Moderatore moderatore) {
        return moderatore.getSegnalazioneRicevute();
    }

    public List<Ammonimento> visualizzaAmmonimentiInviati(Moderatore moderatore) {
        return moderatore.getAmmonimentoInviati();
    }


    public List<Ammonimento> visualizzaAmmonimentiRicevuti(Studente studente) {
        return studente.getListAmmonimenti();
    }

    public List<Sospensione> visualizzaSospensioni(Studente studente) {
        return studente.getListSospensioni();
    }

    private void sospendiStudente(Studente studente){
        studente.setBanned(true);
        utenteRepository.save(studente);
    }

    public void nascondiFoto(Foto foto){
        foto.setVisible(false);
        fotoRepository.save(foto);
    }

    public void checkAmmonimentiStudente(Studente studente) {
        if(studente.getAmmonimentiAttivi() == 3){
            int durataWarningSuspension = 3;
            studente.resetAmmonimentiattivi();
            Sospensione toSend = new Sospensione(durataWarningSuspension, "Sei stato ammonito per 3 volte di seguito. Hai ricevuto una sospensione di " + durataWarningSuspension + " giorni");
            inviaSospensione(toSend, studente);
        }
    }
}
