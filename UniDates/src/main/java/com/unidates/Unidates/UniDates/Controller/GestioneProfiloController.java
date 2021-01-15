package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Enum.Interessi;
import com.unidates.Unidates.UniDates.Enum.Sesso;
import com.unidates.Unidates.UniDates.Exception.InvalidModifyFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidPhotoException;
import com.unidates.Unidates.UniDates.Exception.PasswordMissmatchException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Service.GestioneProfilo.ProfiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ProfileManager")
public class GestioneProfiloController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProfiloService profiloService;


    @RequestMapping("/aggiungiFoto")
    public void aggiungiFoto(Profilo p, Foto f){
        if (checkFoto(f))
                profiloService.aggiungiFoto(p, f);
            else throw new InvalidPhotoException();
    }

    @RequestMapping("/eliminaFoto")
    public void eliminaFoto(Foto f){
        profiloService.eliminaFoto(f);
    }

    @RequestMapping("/aggiungifotoProfilo")
    public void aggiungiFotoProfilo(Profilo p, Foto f){
        if(checkFoto(f))
            profiloService.aggiungiFotoProfilo(p,f);
        else throw new InvalidPhotoException();
    }

    @RequestMapping("/eliminaProfile")
    public void eliminaProfilo(Profilo p, String password){
        if (passwordEncoder.matches(password, p.getStudente().getPassword()))
            profiloService.eliminaProfilo(p, password);
        else throw new PasswordMissmatchException();
    }

    @RequestMapping("/modificaProfile")
    public void modificaProfilo(Profilo p, String password){
        if(checkProfilo(p))
            profiloService.modificaProfilo(p, password);
        throw new InvalidModifyFormatException();
    }

    @RequestMapping("/visualizzaProfilo")
    public Profilo visualizzaProfilo(Studente s){
        return profiloService.visualizzaProfilo(s);
    }

    private boolean checkFoto(Foto f){
        if(f != null){
            if(f.getImg().length > 0 && f.getImg().length < 10000000){
                return true;
            }
        }
        return false;
    }

    private boolean checkProfilo(Profilo p){
        if (p.getNome() != null && p.getCognome() != null && p.getLuogoNascita() != null && p.getResidenza() != null && p.getDataDiNascita() != null && p.getAltezza() != 0 && p.getSesso() != null && p.getInteressi() != null && p.getColori_capelli() != null && p.getColore_occhi() != null && p.getHobbyList().size() > 0){
            if (p.getNome().length() > 0 && p.getCognome().length() > 0 && p.getLuogoNascita().length() > 0 && p.getResidenza().length() > 0){
                if(p.getSesso() == Sesso.UOMO || p.getSesso() == Sesso.DONNA || p.getSesso() == Sesso.ALTRO){
                    if(p.getInteressi() == Interessi.UOMINI || p.getInteressi() == Interessi.DONNE || p.getInteressi() == Interessi.ENTRAMBI || p.getInteressi() == Interessi.ALTRO){
                        // Controlli su colore occhi e capelli
                        if(!p.getHobbyList().contains(null)) return true;
                    }
                }
            }
        }

        return false;
    }
}
