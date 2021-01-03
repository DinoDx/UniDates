package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.ModerazioneService;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/api/ModManager")
public class GestioneModerazioneController {

    @Autowired
    ModerazioneService moderazioneService;

    @Autowired
    UtenteService utenteService;


    @RequestMapping("/segnalaFoto")
    public void segnalaFoto(Moderatore moderatore, Foto foto, String motivazione, String dettagli){
        checkSegnalazione(motivazione, dettagli);
        moderazioneService.inviaSegnalazione(moderatore, foto, motivazione, dettagli);
    }

    @RequestMapping("/inviaAmmonimento")
    public void inviaAmmonimento(Moderatore moderatore, Studente studente, String motivazione, String dettagli){
        checkAmmonimento(motivazione, dettagli);
        moderazioneService.inviaAmmonimento(moderatore, studente, motivazione, dettagli);
    }

    @RequestMapping("/sospendiUtente")
    public void sospendiUtente(Studente studente, int durata, String dettagli){
        checkSospensione(durata, dettagli);
        moderazioneService.inviaSospensione(studente, durata, dettagli);
    }

    @RequestMapping("/ammonimentiRicevuti")
    public Collection<Ammonimento> showAmmonimentiRicevuti(Studente studente){
        return moderazioneService.visualizzaAmmonimentiRicevuti(studente);
    }

    @RequestMapping("/ammonimentiInviati")
    public Collection<Ammonimento> showAmmonimentiInviati(Moderatore moderatore){
        return moderazioneService.visualizzaAmmonimentiInviati(moderatore);
    }

    @RequestMapping("/segnalazioniUtente")
    public Collection<Segnalazione> showSegnalazioni(Moderatore moderatore){
        return moderazioneService.visualizzaSegnalazioniRicevute(moderatore);

    }

    @RequestMapping("/sospensioniUtente")
    public Collection<Sospensione> visualizzaSospensioni(Studente studente){
        return moderazioneService.visualizzaSospensioni(studente);
    }

    public Boolean checkSegnalazione(String motivazione, String dettagli){
        if(motivazione != null && dettagli != dettagli)
            return true;

        return false;
    }

    public Boolean checkAmmonimento(String motivazione, String dettagli){
        if(motivazione != null && dettagli != dettagli)
            return true;

        return false;
    }

    public boolean checkSospensione(int durata, String dettagli){
        if(durata != 0 && dettagli != null)
            return true;

        return false;
    }

}
