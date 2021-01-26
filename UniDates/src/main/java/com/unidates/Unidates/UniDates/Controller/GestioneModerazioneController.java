package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.DTOs.AmmonimentoDTO;
import com.unidates.Unidates.UniDates.DTOs.SegnalazioneDTO;
import com.unidates.Unidates.UniDates.DTOs.SospensioneDTO;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidBanFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidReportFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidWarningFormatException;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Model.Entity.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Service.ModerazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ModManager")
public class GestioneModerazioneController {

    @Autowired
    ModerazioneService moderazioneService;


    @RequestMapping("/inviaSegnalazione")
    public void inviaSegnalazione(SegnalazioneDTO segnalazioneDTO, FotoDTO f){
        Segnalazione s = new Segnalazione(segnalazioneDTO.getMotivazione(),segnalazioneDTO.getDettagli());
        if(checkSegnalazione(s))
            moderazioneService.inviaSegnalazione(s, f.getId());
        else throw new InvalidReportFormatException();
    }
    @RequestMapping("/inviaSegnalazioneManager")
    public void inviaSegnalazioneCommunityManager(SegnalazioneDTO segnalazioneDTO, FotoDTO f){
        if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE)) {
            Segnalazione s = new Segnalazione(segnalazioneDTO.getMotivazione(), segnalazioneDTO.getDettagli());
            if (checkSegnalazione(s))
                moderazioneService.inviaSegnalazioneCommunityManager(s, f.getId());
            else throw new InvalidReportFormatException();
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/inviaAmmonimento")
    public void inviaAmmonimento(AmmonimentoDTO ammonimentoDTO, String emailModeratore, String emailStudenteAmmonito, FotoDTO fotoDTO){
        if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || (SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))){
            Ammonimento a = new Ammonimento(ammonimentoDTO.getMotivazione(), ammonimentoDTO.getDettagli());
            if (checkAmmonimento(a))
                moderazioneService.inviaAmmonimento(a, emailModeratore, emailStudenteAmmonito, fotoDTO.getId());
            else throw new InvalidWarningFormatException();
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/inviaSospensione")
    public void inviaSospensione(SospensioneDTO sospensioneDTO, String emailSospeso){
        if((SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))) {
            Sospensione sp = new Sospensione(sospensioneDTO.getDurata(), sospensioneDTO.getDettagli());
            if (checkSospensione(sp))
                moderazioneService.inviaSospensione(sp, emailSospeso);
            else throw new InvalidBanFormatException();
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
