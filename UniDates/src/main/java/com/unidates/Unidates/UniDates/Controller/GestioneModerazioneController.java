package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Exception.InvalidBanFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidReportFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidWarningFormatException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
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
    public void inviaSegnalazione(String motivazione,String dettagli, Foto f){
        Segnalazione s = new Segnalazione(motivazione,dettagli);
        s.setFoto(f);
        if(checkSegnalazione(s))
            moderazioneService.inviaSegnalazione(s);
        else throw new InvalidReportFormatException();
    }

    public void inviaSegnalazioneCommunityManager(Segnalazione s){
        if(checkSegnalazione(s))
            moderazioneService.inviaSegnalazioneCommunityManager(s);
        else throw new InvalidReportFormatException();
    }

    @RequestMapping("/inviaAmmonimento") // non propriamente necessario
    public void inviaAmmonimento(String motivazione,String dettagli,Moderatore m, Studente s, Foto f){
        Ammonimento a = new Ammonimento(motivazione,dettagli);
        a.setFoto(f);
        a.setStudente(s);
        a.setModeratore(m);
        if(checkAmmonimento(a))
            moderazioneService.inviaAmmonimento(a, s);
        else throw new InvalidWarningFormatException();
    }

    @RequestMapping("/inviaSospensione")
    public void inviaSospensione(int durata, String dettagli, Studente s){
        Sospensione sp = new Sospensione(durata, dettagli);
        sp.setStudente(s);
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
