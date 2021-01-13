package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Enum.Interessi;
import com.unidates.Unidates.UniDates.Enum.Sesso;
import com.unidates.Unidates.UniDates.Exception.InvalidRegistrationFormatException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/UserManager")
public class GestioneUtentiController {

    @Autowired
    UtenteService utenteService;



    @RequestMapping("/registrazioneStudente")
    public void registrazioneStudente(Studente s, Profilo p) {
        if(checkStudente(s) && checkProfilo(p))
             utenteService.registrazioneStudente(s, p);
        else throw new InvalidRegistrationFormatException();
    }

    @RequestMapping("/registrazioneModeratore")
    public void registrazioneModeratore(Moderatore m, Studente s)  {
        if(checkStudente(s))
            utenteService.registrazioneModeratore(m, s);
        else throw new InvalidRegistrationFormatException();
    }

    @RequestMapping("/registrazioneCommunityManager")
    public void registrazioneModeratore(CommunityManager cm, Studente s){
        if(checkStudente(s)){
            utenteService.registrazioneCommunityManager(cm, s);
        }
        else throw new InvalidRegistrationFormatException();

    }

    @RequestMapping("/bloccoStudente")
    public boolean bloccaStudente(Studente sBloccante, Studente sBloccato){
       return utenteService.bloccaStudente(sBloccante,sBloccato);
    }

    @RequestMapping("/sbloccoStudente")
    public boolean sbloccaStudente(Studente sBloccante, Studente sBloccato){
        return utenteService.sbloccaStudente(sBloccante,sBloccato);
    }

    @RequestMapping("/trovaUtente")
    public Utente trovaUtente(String email) {
        if(checkEmail(email))
            return utenteService.findUtenteByEmail(email);
        else throw new InvalidRegistrationFormatException();
    }


    public boolean checkEmail(String email){
        if (email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
            return true;

        return false;
    }

    public boolean checkProfilo(Profilo p) {
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


    private boolean checkStudente(Studente s) {
        if(s.getEmail() != null && s.getPassword() != null && s.getRuolo() != null){
            if(checkEmail(s.getEmail())){
                return s.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
            }
        }
         return false;
    }
}
