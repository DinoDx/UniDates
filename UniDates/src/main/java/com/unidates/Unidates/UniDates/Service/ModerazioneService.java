package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Repository.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Repository.SegnalazioniRepository;
import com.unidates.Unidates.UniDates.Repository.SospensioniRepository;
import com.unidates.Unidates.UniDates.Repository.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
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


    public void inviaSegnalazione(Segnalazione s, Long idFoto){
        List<Utente> moderatores = utenteRepository.findAllByRuolo(Ruolo.MODERATORE);
        s.setModeratore( (Moderatore) moderatores.get(new Random().nextInt(moderatores.size()))); //Moderatore scelto casualmente tra tutti i moderatori
        s.setFoto(fotoRepository.findById(idFoto).get());
        segnalazioniRepository.save(s);
    }

    public void inviaSegnalazioneCommunityManager(Segnalazione s, Long idFoto){
        List<Utente> cms =  utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER);
        s.setModeratore((CommunityManager) cms.get(new Random().nextInt(cms.size())));
        s.setFoto(fotoRepository.findById(idFoto).get());
        segnalazioniRepository.save(s);
    }

    public void inviaAmmonimento(Ammonimento a, String emailModeratore, String emailStudenteAmmonito, Long idFoto){
        Studente ammonito = (Studente) utenteRepository.findByEmail(emailStudenteAmmonito);
        a.setModeratore((Moderatore) utenteRepository.findByEmail(emailModeratore));
        a.setStudente(ammonito);
        a.setFoto(fotoRepository.findById(idFoto).get());
        ammonimentiRepository.save(a);
        ammonito.addAmmonimentoattivo();
        utenteRepository.save(ammonito);
        publisher.publishWarning(a);
    }

    public void inviaSospensione(Sospensione sp,String emailSospeso){
        Studente daSospendere = (Studente) utenteRepository.findByEmail(emailSospeso);
        sp.setStudente(daSospendere);
        sospensioniRepository.save(sp);

        daSospendere.setBanned(true);
        utenteRepository.save(daSospendere);

        publisher.publishBannedEvent(sp);
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
            inviaSospensione(toSend, studente.getEmail());
        }
    }

}
