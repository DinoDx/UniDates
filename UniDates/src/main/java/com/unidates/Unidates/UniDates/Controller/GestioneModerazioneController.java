package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.Ammonimenti;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazioni;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.AmmonimentiService;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.ModeratoreService;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.SegnalazioniService;
import com.unidates.Unidates.UniDates.Service.GestioneModerazione.SospensioniService;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.StudenteService;
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
    StudenteService studenteService;

    @Autowired
    ModeratoreService moderatoreService;

    @RequestMapping("/segnalaFoto")
    public void segnalaFoto(Foto foto){
        segnalazioniService.addSegnalazione(foto);
    }

    @RequestMapping("/inviaAmmonimento")
    public void inviaAmmonimento(Studente studente){
        ammonimentiService.sendAmmonimento(studente);
    }

    @RequestMapping("/sospendiUtente")
    public void sospendiUtente(Studente studente){
        sospensioniService.suspendStudente(studente);
    }

    @RequestMapping("/ammonimentiRicevuti")
    public Collection<Ammonimenti> showAmmonimentiRicevuti(Studente studente){
        return studenteService.findByEmail(studente.getEmail()).getListAmmonimenti();
    }

    @RequestMapping("/ammonimentiInviati")
    public Collection<Ammonimenti> showAmmonimentiInviati(Moderatore moderatore){
        return moderatoreService.findByEmail(moderatore.getEmail()).getAmmonimentiInviati();
    }

    @RequestMapping("/segnalazioniUtente")
    public Collection<Segnalazioni> showSegnalazioni(Studente studente){
        return studenteService.findByEmail(studente.getEmail()).getListSegnalazioni();

    }

}
