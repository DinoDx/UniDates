package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
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
import com.unidates.Unidates.UniDates.Service.Registrazione.Publisher;
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
        Moderatore moderatore = (Moderatore) moderatores.get(new Random().nextInt(moderatores.size()));
        Foto segnalata = fotoRepository.findById(idFoto).get();
        s.setModeratore(moderatore); //Moderatore scelto casualmente tra tutti i moderatori
        s.setFoto(segnalata);

        if(segnalazioniRepository.findByModeratoreAndFoto(moderatore, segnalata) == null){ // viene mandata una segnalazione se giá non ne esiste una associata a quella foto
            segnalazioniRepository.save(s);
            System.out.println("Segnalazione aggiunta!");
        }

    }

    public void inviaSegnalazioneCommunityManager(Segnalazione s, Long idFoto){
        List<Utente> cms =  utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER);
        CommunityManager cm = (CommunityManager) cms.get(new Random().nextInt(cms.size()));
        s.setModeratore(cm);

        Foto f = fotoRepository.findById(idFoto).get();
        s.setFoto(f);
        if(segnalazioniRepository.findByModeratoreAndFoto((Moderatore) cm, f) == null){ // viene mandata una segnalazione se giá non ne esiste una associata a quella foto
            segnalazioniRepository.save(s);
            System.out.println("Segnalazione aggiunta!");
        }
        segnalazioniRepository.save(s);
    }

    public void inviaAmmonimento(Ammonimento a, String emailModeratore, String emailStudenteAmmonito, Long idFoto) throws AlreadyExistException {
        Studente ammonito = (Studente) utenteRepository.findByEmail(emailStudenteAmmonito);

        a.setModeratore((Moderatore) utenteRepository.findByEmail(emailModeratore));
        a.setStudente(ammonito);
        a.setFoto(fotoRepository.findById(idFoto).get());

        if(!ammonito.getListaAmmonimenti().contains(a)) { //Utente ammonito solo nel caso non abbia già un ammonimento per la stessa foto
            ammonimentiRepository.save(a);
            ammonito.addAmmonimentoattivo();
            utenteRepository.save(ammonito);
        }
        else throw new AlreadyExistException("Ammonimento per la stessa foto giá presente!");
    }

    public void inviaSospensione(Sospensione sp,String emailSospeso){
        Studente daSospendere = (Studente) utenteRepository.findByEmail(emailSospeso);
        sp.setStudente(daSospendere);
        sospensioniRepository.save(sp);

        daSospendere.setBanned(true);
        utenteRepository.save(daSospendere);
    }


    public void nascondiFoto(Long idFoto){
        Foto foto = fotoRepository.getOne(idFoto);
        foto.setVisible(false);
        fotoRepository.save(foto);
    }

    public void checkAmmonimentiStudente(String emailStudente) {

        Studente studente = (Studente) utenteRepository.findByEmail(emailStudente);
        if(studente.getAmmonimentiAttivi() == 3){
            int durataWarningSuspension = 3;
            studente.resetAmmonimentiattivi();
            Sospensione toSend = new Sospensione(durataWarningSuspension, "Sei stato ammonito per 3 volte di seguito. Hai ricevuto una sospensione di " + durataWarningSuspension + " giorni");
            inviaSospensione(toSend, studente.getEmail());
        }
    }

}
