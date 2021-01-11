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
import java.util.List;

@Controller
@RequestMapping("/api/ModManager")
public class GestioneModerazioneController {

    @Autowired
    ModerazioneService moderazioneService;

    @Autowired
    UtenteService utenteService;


    @RequestMapping("/inviaSegnalazione")
    public void inviaSegnalazione(Foto f, Segnalazione s){
        checkSegnalazione(s);
        moderazioneService.inviaSegnalazione(s,f);
    }

    @RequestMapping("/inviaAmmonimento")
    public void inviaAmmonimento(Ammonimento a,Moderatore m, Studente s){
        checkAmmonimento(a);
        moderazioneService.inviaAmmonimento(a, s, m);
    }

    @RequestMapping("/inviaSospensione")
    public void inviaSospensione(Sospensione sp, Studente s){
        checkSospensione(sp);
        moderazioneService.inviaSospensione(sp, s);
    }

    @RequestMapping("/visualizzaAmmonimentiInviati")
    public List<Ammonimento> showAmmonimentiInviati(Moderatore m){
        return (List<Ammonimento>) moderazioneService.visualizzaAmmonimentiInviati(m);
    }

    @RequestMapping("/visualizzaAmmonimentiRicevuti")
    public List<Ammonimento> showAmmonimentiRicevuti(Studente s){
        return (List<Ammonimento>) moderazioneService.visualizzaAmmonimentiRicevuti(s);
    }

    @RequestMapping("/sospensioniUtente")
    public List<Sospensione> visualizzaSospensioni(Studente s){
        return (List<Sospensione>) moderazioneService.visualizzaSospensioni(s);
    }

    @RequestMapping("/visualizzaSegnalazioniRicevute")
    public List<Segnalazione> visualizzaSegnazioniRicevute(Moderatore m){
        return (List<Segnalazione>) moderazioneService.visualizzaSegnalazioniRicevute(m);

    }


    public Boolean checkSegnalazione(Segnalazione s){
        if(s.getMotivazione() != null && s.getDettagli() != null)
            return true;

        return false;
    }

    public Boolean checkAmmonimento(Ammonimento a){
        if(a.getMotivazione() != null && a.getDettagli() != null)
            return true;

        return false;
    }

    public boolean checkSospensione(Sospensione sp){
        if(sp.getDurata() != 0 && sp.getDettagli() != null)
            return true;

        return false;
    }

}
