package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Repository.GestioneModerazione.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Repository.GestioneModerazione.SegnalazioniRepository;
import com.unidates.Unidates.UniDates.Repository.GestioneModerazione.SospensioniRepository;
import com.unidates.Unidates.UniDates.Repository.GestioneProfilo.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.GestioneUtenti.UtenteRepository;
import com.unidates.Unidates.UniDates.Service.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ModerazioneService {
    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    SegnalazioniRepository segnalazioniRepository;

    @Autowired
    AmmonimentiRepository ammonimentiRepository;

    @Autowired
    SospensioniRepository sospensioniRepository;

    @Autowired
    FotoRepository fotoRepository;

    @Autowired
    Publisher publisher;


    public void inviaSegnalazione(Segnalazione s){
        List<Utente> moderatores = utenteRepository.findAllByRuolo(Ruolo.MODERATORE);
        s.setModeratore( (Moderatore) moderatores.get(new Random().nextInt(moderatores.size()))); //Moderatore scelto casualmente tra tutti i moderatori
        segnalazioniRepository.save(s);
    }

    public void inviaSegnalazioneCommunityManager(Segnalazione s){
        List<Utente> cms =  utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER);
        s.setModeratore((CommunityManager) cms.get(new Random().nextInt(cms.size())));
        segnalazioniRepository.save(s);
    }

    public void inviaAmmonimento(Ammonimento a, Studente s){
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
        return studente.getListaAmmonimenti();
    }

    public List<Sospensione> visualizzaSospensioni(Studente studente) {
        return studente.getListaSospensioni();
    }

    public void sospendiStudente(Studente studente){
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
