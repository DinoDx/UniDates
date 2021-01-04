package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Service.GestioneProfilo.ProfiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ProfileManager")
public class GestioneProfiloController {

    @Autowired
    ProfiloService profiloService;


    @RequestMapping("/aggiungiFoto")
    public void aggiungiFoto(Profilo p, Foto f){
        checkFoto(f);
        profiloService.aggiungiFoto(p, f);
    }

    @RequestMapping("/eliminaFoto")
    public void eliminaFoto(Foto f){
        profiloService.eliminaFoto(f);
    }

    @RequestMapping("/eliminaProfile")
    public void eliminaProfilo(Profilo p, String password){
        checkPassword(password, p);
        profiloService.eliminaProfilo(p, password);
    }

    @RequestMapping("/modificaProfile")
    public void modificaProfilo(Profilo p, String password){
        checkPassword(password, p);
        checkProfilo(p);
        profiloService.modificaProfilo(p, password);
    }

    @RequestMapping("/visualizzaProfilo")
    public Profilo visualizzaProfilo(Studente s){
        return profiloService.visualizzaProfilo(s);
    }

    private boolean checkFoto(Foto foto){
        if(foto.getUrl() != null)
            return true;

        return false;
    }

    private boolean checkPassword (String password, Profilo profilo){
        if(password==profilo.getStudente().getPassword())
            return true;

        return false;
    }

    private boolean checkProfilo(Profilo profilo){
        if (profilo.getNome() != null && profilo.getCognome() != null)
            return true;

        return false;
    }
}
