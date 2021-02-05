package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.DTOs.*;
import com.unidates.Unidates.UniDates.Exception.*;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Service.ModerazioneService;
import com.unidates.Unidates.UniDates.Service.NotificaService;
import com.unidates.Unidates.UniDates.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ModManager")
public class ModerationControl {

    @Autowired
    ModerazioneService moderazioneService;

    @Autowired
    NotificaService notificaService;

    @Autowired
    UtenteService utenteService;

    @Autowired
    SessionRegistry sessionRegistry;


    @RequestMapping("/inviaSegnalazione")
    public void inviaSegnalazione(SegnalazioneDTO segnalazioneDTO, FotoDTO f) throws InvalidFormatException {
        Segnalazione s = new Segnalazione(segnalazioneDTO.getMotivazione(),segnalazioneDTO.getDettagli());
        if(checkSegnalazione(s))
            moderazioneService.inviaSegnalazione(s, f.getId());
        else throw new InvalidFormatException("Motivazione e/o dettagli non validi");
    }
    @RequestMapping("/inviaSegnalazioneManager")
    public void inviaSegnalazioneCommunityManager(SegnalazioneDTO segnalazioneDTO, FotoDTO f)  {
        if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE)) {
            Segnalazione s = new Segnalazione(segnalazioneDTO.getMotivazione(), segnalazioneDTO.getDettagli());
                moderazioneService.inviaSegnalazioneCommunityManager(s, f.getId());
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/inviaAmmonimento")
    public void inviaAmmonimento(AmmonimentoDTO ammonimentoDTO, String emailModeratore, String emailStudenteAmmonito, FotoDTO fotoDTO) throws InvalidFormatException {
       // if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || (SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))){
            Ammonimento a = new Ammonimento(ammonimentoDTO.getMotivazione(), ammonimentoDTO.getDettagli());
            if (checkAmmonimento(a)) {
                if(moderazioneService.inviaAmmonimento(a, emailModeratore, emailStudenteAmmonito, fotoDTO.getId())){
                    moderazioneService.nascondiFoto(fotoDTO.getId());
                    notificaService.genereateNotificaWarning(emailStudenteAmmonito, fotoDTO.getId());
                    moderazioneService.checkAmmonimentiStudente(emailStudenteAmmonito);
                }
            }
            else throw new InvalidFormatException("Motivazione e/o dettagli non validi");
        }
      //  else throw new NotAuthorizedException();
    //}

    @RequestMapping("/inviaSospensione")
    public void inviaSospensione(SospensioneDTO sospensioneDTO, String emailSospeso) throws InvalidFormatException {
        if((SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))) {
            Sospensione sp = new Sospensione(sospensioneDTO.getDurata(), sospensioneDTO.getDettagli());
            if (checkSospensione(sp)) {
                moderazioneService.inviaSospensione(sp, emailSospeso);
                SecurityUtils.forceLogout(utenteService.trovaUtente(emailSospeso), sessionRegistry);
            }
            else throw new InvalidFormatException("Dettagli e/o durata non validi");
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/moderatoreInSessione")
    public ModeratoreDTO moderatoreInSessione(){
        Utente mod = SecurityUtils.getLoggedIn();
        if( mod != null && (mod.getRuolo().equals(Ruolo.MODERATORE) || mod.getRuolo().equals(Ruolo.COMMUNITY_MANAGER))){
            return EntityToDto.toDTO((Moderatore) mod);
        }
        else throw new NotAuthorizedException();
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
