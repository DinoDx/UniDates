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
    public void aggiungiFoto(Profilo profilo, Foto foto){
        checkFoto(foto);
        profiloService.aggiungiFoto(profilo, foto);
    }

    @RequestMapping("/eliminaFoto")
    public void eliminaFoto(Foto foto){
        profiloService.eliminaFoto(foto);
    }

    @RequestMapping("/eliminaProfile")
    public void eliminaProfilo(Profilo profilo, String password){
        checkPassword(password, profilo);
        profiloService.eliminaProfilo(profilo, password);
    }

    @RequestMapping("/modificaProfile")
    public void modificaProfilo(Profilo profilo, String password){
        checkPassword(password, profilo);
        checkProfilo(profilo);
        profiloService.modificaProfilo(profilo, password);
    }

    @RequestMapping("/visualizzaProfilo")
    public Profilo visualizzaProfilo(Studente studente){
        return profiloService.visualizzaProfilo(studente);
    }

    public boolean checkFoto(Foto foto){
        if(foto.getUrl() != null)
            return true;

        return false;
    }

    public boolean checkPassword (String password, Profilo profilo){
        if(password==profilo.getStudente().getPassword())
            return true;

        return false;
    }

    public boolean checkProfilo(Profilo profilo){
        if (profilo.getNome() != null && profilo.getCognome() != null)
            return true;

        return false;
    }
}
