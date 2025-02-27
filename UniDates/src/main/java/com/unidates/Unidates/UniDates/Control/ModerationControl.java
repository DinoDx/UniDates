package com.unidates.Unidates.UniDates.Control;

import com.unidates.Unidates.UniDates.DTOs.*;
import com.unidates.Unidates.UniDates.Exception.*;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Manager.NotificaManager;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/ModManager")
public class ModerationControl {

    @Autowired
    ModerazioneManager moderazioneManager;

    @Autowired
    NotificaManager notificaManager;

    @Autowired
    UserManager userManager;

    @Autowired
    SessionRegistry sessionRegistry;


    @RequestMapping("/inviaSegnalazione")
    public SegnalazioneDTO inviaSegnalazione(SegnalazioneDTO segnalazioneDTO, Long fotoId) throws InvalidFormatException {
        if(fotoId != null) {
            Segnalazione s = new Segnalazione(segnalazioneDTO.getMotivazione(), segnalazioneDTO.getDettagli());
            if (checkSegnalazione(s)) {
                return EntityToDto.toDTO(moderazioneManager.inviaSegnalazione(s, fotoId));
            }
            else throw new InvalidFormatException("Motivazione e/o dettagli non validi");
        } else throw new InvalidFormatException("Id non valido!");
    }

    @RequestMapping("/inviaSegnalazioneManager")
    public SegnalazioneDTO inviaSegnalazioneCommunityManager(SegnalazioneDTO segnalazioneDTO, Long fotoId)  {
        if(fotoId != null) {
            Segnalazione s = new Segnalazione(segnalazioneDTO.getMotivazione(), segnalazioneDTO.getDettagli());
            if(checkSegnalazione(s)) {
                if (SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE)) {
                    return EntityToDto.toDTO(moderazioneManager.inviaSegnalazioneCommunityManager(s, fotoId));
                } else throw new NotAuthorizedException("Non puoi inviare una segnalazione al CM!");
            }else throw new InvalidFormatException("Motivazione e/o dettagli non validi");
        } else throw new InvalidFormatException("Id foto non valido");
    }

    @RequestMapping("/inviaAmmonimento")
    public AmmonimentoDTO inviaAmmonimento(AmmonimentoDTO ammonimentoDTO, String emailModeratore, String emailStudenteAmmonito, FotoDTO fotoDTO) throws InvalidFormatException, AlreadyExistException {
        if(checkEmail(emailModeratore) && checkEmail(emailStudenteAmmonito)) {
            if (SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || (SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))) {
                Ammonimento a = new Ammonimento(ammonimentoDTO.getMotivazione(), ammonimentoDTO.getDettagli());
                if (checkAmmonimento(a)) {
                        Ammonimento toReturn = moderazioneManager.inviaAmmonimento(a, emailModeratore, emailStudenteAmmonito, fotoDTO.getId());
                        moderazioneManager.nascondiFoto(fotoDTO.getId());
                        notificaManager.genereateNotificaWarning(emailStudenteAmmonito, fotoDTO.getId());
                        moderazioneManager.checkAmmonimentiStudente(emailStudenteAmmonito);
                        return EntityToDto.toDTO(toReturn);
                } else throw new InvalidFormatException("Motivazione e/o dettagli non validi");
            } else throw new NotAuthorizedException("Non puoi inviare un ammonimento!");
        }else throw new InvalidFormatException("Formato email non valido!");
    }

    @RequestMapping("/inviaSospensione")
    public SospensioneDTO inviaSospensione(SospensioneDTO sospensioneDTO, String emailSospeso) throws InvalidFormatException, EntityNotFoundException {
        if (checkEmail(emailSospeso)) {
            if ((SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))) {
                Sospensione sp = new Sospensione(sospensioneDTO.getDurata(), sospensioneDTO.getDettagli());
                if (checkSospensione(sp)) {
                    SecurityUtils.forceLogout(userManager.trovaUtente(emailSospeso), sessionRegistry);
                    return EntityToDto.toDTO(moderazioneManager.inviaSospensione(sp, emailSospeso));
                } else throw new InvalidFormatException("Dettagli e/o durata non validi");
            } else throw new NotAuthorizedException("Non puoi inviare una sospensione!");
        }else throw new InvalidFormatException("Formato email non valido");
    }

    @RequestMapping("/moderatoreInSessione")
    public ModeratoreDTO moderatoreInSessione(){
        Utente mod = SecurityUtils.getLoggedIn();
        if( mod != null && (mod.getRuolo().equals(Ruolo.MODERATORE) || mod.getRuolo().equals(Ruolo.COMMUNITY_MANAGER))){
            return EntityToDto.toDTO((Moderatore) mod);
        } else throw new NotAuthorizedException("Non puoi visualizzare il moderatore in sessione");
    }


    private Boolean checkSegnalazione(Segnalazione s){
        if(s.getMotivazione() != null && s.getDettagli() != null) {
            if(s.getDettagli().length() > 0 && s.getDettagli().length() < 250){
                return true;
            }
        }
        return false;
    }

    private Boolean checkAmmonimento(Ammonimento a){
        if(a.getMotivazione() != null && a.getDettagli() != null) {
            if(a.getDettagli().length() > 0 && a.getDettagli().length() < 250){
                return true;
            }
        }
        return false;
    }

    private boolean checkSospensione(Sospensione sp){
        if(sp.getDurata() != 0 && sp.getDettagli() != null){
            if(sp.getDettagli().length() > 0 && sp.getDettagli().length() < 250){
                return true;
            }
        }
        return false;
    }

    private boolean checkEmail(String email){
        if (email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
            return true;
        return false;
    }

}
