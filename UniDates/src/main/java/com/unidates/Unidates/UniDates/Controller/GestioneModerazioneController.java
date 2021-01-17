package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Exception.InvalidBanFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidReportFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidWarningFormatException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione.ModerazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/ModManager")
public class GestioneModerazioneController {

    @Autowired
    ModerazioneService moderazioneService;


    @RequestMapping("/inviaSegnalazione")
    public void inviaSegnalazione(Foto f, Segnalazione s){
        if(checkSegnalazione(s))
            moderazioneService.inviaSegnalazione(s,f);
        else throw new InvalidReportFormatException();
    }

    @RequestMapping("/inviaAmmonimento") // non propriamente necessario
    public void inviaAmmonimento(Ammonimento a,Moderatore m, Studente s, Foto f){
        if(checkAmmonimento(a))
            moderazioneService.inviaAmmonimento(a, s, m, f);
        else throw new InvalidWarningFormatException();
    }

    @RequestMapping("/inviaSospensione")
    public void inviaSospensione(Sospensione sp, Studente s){
        if(checkSospensione(sp))
            moderazioneService.inviaSospensione(sp, s);
        else throw new InvalidBanFormatException();
    }

    @RequestMapping("/visualizzaAmmonimentiInviati")
    public List<Ammonimento> showAmmonimentiInviati(Moderatore m){
        return  moderazioneService.visualizzaAmmonimentiInviati(m);
    }

    @RequestMapping("/visualizzaAmmonimentiRicevuti")
    public List<Ammonimento> showAmmonimentiRicevuti(Studente s){
        return  moderazioneService.visualizzaAmmonimentiRicevuti(s);
    }

    @RequestMapping("/sospensioniUtente")
    public List<Sospensione> visualizzaSospensioni(Studente s){
        return  moderazioneService.visualizzaSospensioni(s);
    }

    @RequestMapping("/visualizzaSegnalazioniRicevute")
    public List<Segnalazione> visualizzaSegnazioniRicevute(Moderatore m){
        return  moderazioneService.visualizzaSegnalazioniRicevute(m);

    }


    public Boolean checkSegnalazione(Segnalazione s){
        if(s.getMotivazione() != null && s.getDettagli() != null) {
            if(s.getDettagli().length() > 0 && s.getDettagli().length() < 250){
                return true;
            }
        }
        return false;
    }

    public Boolean checkAmmonimento(Ammonimento a){
        if(a.getMotivazione() != null && a.getDettagli() != null) {
            if(a.getDettagli().length() > 0 && a.getDettagli().length() < 250){
                return true;
            }
        }
        return false;
    }

    public boolean checkSospensione(Sospensione sp){
        if(sp.getDurata() != 0 && sp.getDettagli() != null){
            if(sp.getDettagli().length() > 0 && sp.getDettagli().length() < 250){
                return true;
            }
        }
        return false;
    }

}
