package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimenti;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazioni;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.AmmonimentiService;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.SegnalazioniService;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.SospensioniService;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/api/ModManager")
public class GestioneModerazioneController {

    @Autowired
    AmmonimentiService ammonimentiService;

    @Autowired
    SegnalazioniService segnalazioniService;

    @Autowired
    SospensioniService sospensioniService;

    @Autowired
    UtenteService utenteService;


    @RequestMapping("/segnalaFoto")
    public void segnalaFoto(Moderatore moderatore, Foto foto, String motivazione, String dettagli){
        checkSegnalazione(motivazione, dettagli);
        segnalazioniService.addSegnalazione(moderatore, foto, motivazione, dettagli);
    }

    @RequestMapping("/inviaAmmonimento")
    public void inviaAmmonimento(Moderatore moderatore, Studente studente, String motivazione, String dettagli){
        checkAmmonimento(motivazione, dettagli);
        ammonimentiService.sendAmmonimento(moderatore, studente, motivazione, dettagli);
    }

    @RequestMapping("/sospendiUtente")
    public void sospendiUtente(Studente studente, int durata, String dettagli){
        checkSospensione(durata, dettagli);
        sospensioniService.suspendStudente(studente, durata, dettagli);
    }

    @RequestMapping("/ammonimentiRicevuti")
    public Collection<Ammonimenti> showAmmonimentiRicevuti(Studente studente){
        Studente s = (Studente) utenteService.findUtenteByEmail(studente.getEmail());
        return s.getListAmmonimenti();
    }

    @RequestMapping("/ammonimentiInviati")
    public Collection<Ammonimenti> showAmmonimentiInviati(Moderatore moderatore){
        Moderatore m = (Moderatore) utenteService.findUtenteByEmail(moderatore.getEmail());
        return m.getAmmonimentiInviati();
    }

    @RequestMapping("/segnalazioniUtente")
    public Collection<Segnalazioni> showSegnalazioni(Moderatore moderatore){
        Moderatore m = (Moderatore) utenteService.findUtenteByEmail(moderatore.getEmail());
        return m.getSegnalazioniRicevute();

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
