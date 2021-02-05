package com.unidates.Unidates.UniDates.Control;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.Exception.*;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Model.Enum.Interessi;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Enum.Sesso;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Service.ProfiloService;
import com.unidates.Unidates.UniDates.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/ProfileManager")
public class ModifyProfileControl {

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProfiloService profiloService;

    @Autowired
    UtenteService utenteService;


    @RequestMapping("/aggiungiFoto")
    public void aggiungiFotoLista(@RequestParam String emailFotoToAdd, @RequestBody FotoDTO fotoDTO) throws InvalidFormatException {
        Studente s = (Studente) SecurityUtils.getLoggedIn();
        if (checkEmail(emailFotoToAdd)) {
            if (s.getEmail().equals(emailFotoToAdd)) {
                if (checkFoto(fotoDTO))
                    profiloService.aggiungiFotoLista(emailFotoToAdd, new Foto(fotoDTO.getImg()));
                else throw new InvalidFormatException("La foto non rispetta le dimensioni consentite");
            } else throw new NotAuthorizedException("Non puoi inserire una foto per un altro utente");
        } else throw new InvalidFormatException("Formato email non valido!");
    }

    @RequestMapping("/eliminaFoto")
    public void eliminaFotoLista(@RequestBody Long idFoto, @RequestParam String email) {
        Utente utente = SecurityUtils.getLoggedIn();
        if (checkEmail(email) && idFoto != null) {
            if (utente.getRuolo().equals(Ruolo.STUDENTE)) {
                if(utente.getEmail().equals(email)) {
                    profiloService.eliminaFotoLista(email, idFoto);
                } else throw new NotAuthorizedException("Non puoi rimuovere la foto di un altro utente");
            } else {
                profiloService.eliminaFotoLista(email, idFoto);
            }
        }else throw new  InvalidFormatException("Formato email e/o id foto non valido! ");
    }


    @RequestMapping("/aggiungifotoProfilo")
    public void aggiungiFotoProfilo(@RequestParam String emailFotoToAdd, @RequestBody FotoDTO fotoDTO) throws InvalidFormatException {
        if (checkEmail(emailFotoToAdd)) {
            if (SecurityUtils.getLoggedIn().getEmail().equals(emailFotoToAdd)) {
                Foto f = new Foto(fotoDTO.getImg());
                if (checkFoto(fotoDTO))
                    profiloService.aggiungiFotoProfilo(emailFotoToAdd, f);
                else throw new InvalidFormatException("La foto non rispetta le dimensioni consentite");
            } else throw new NotAuthorizedException("Non puoi aggiungere foto ad un altro profilo");
        }else throw new InvalidFormatException("Formato email non valido!");
    }

    @RequestMapping("/setFotoProfilo")
    public void setFotoProfilo(@RequestParam String emailStudenteToModify, @RequestBody Long fotoId){
        if(checkEmail(emailStudenteToModify) || fotoId == null) {
            if (SecurityUtils.getLoggedIn().getEmail().equals(emailStudenteToModify)) {
                profiloService.setFotoProfilo(emailStudenteToModify, fotoId);
            } else throw new NotAuthorizedException("Non puoi settare la foto di un altro profilo");
        }else throw new InvalidFormatException("Formato email e/o id foto non validi!");
    }


    @RequestMapping("/modificaProfilo")
    public void modificaProfilo(String emailStudenteToModify, ProfiloDTO profiloDTO) throws InvalidFormatException {
        if(checkEmail(emailStudenteToModify)) {
            if (SecurityUtils.getLoggedIn().getEmail().equals(emailStudenteToModify)) {
                Profilo p = new Profilo(profiloDTO.getNome(), profiloDTO.getCognome(), profiloDTO.getLuogoNascita(), profiloDTO.getResidenza(),
                        profiloDTO.getDataDiNascita(), profiloDTO.getAltezza(), profiloDTO.getSesso(), profiloDTO.getInteressi(), profiloDTO.getColori_capelli(), profiloDTO.getColore_occhi(),
                        new Foto(), profiloDTO.getHobbyList());

                if (profiloDTO.getNumeroTelefono() != null)
                    p.setNickInstagram(profiloDTO.getNickInstagram());
                if (profiloDTO.getNickInstagram() != null)
                    p.setNumeroTelefono(profiloDTO.getNumeroTelefono());

                if (checkProfilo(p))
                    profiloService.modificaProfilo(emailStudenteToModify, p);
                else throw new InvalidFormatException("Uno o più campi inseriti non hanno un formato valido");
            } else throw new NotAuthorizedException("Non puoi modificare il profilo di un altro studente!");
        }else throw new InvalidFormatException("Formato email non valido!");
    }

    @RequestMapping("/cambiaPassword")
    public void cambiaPassword(@RequestParam String emailUtente,@RequestParam  String nuovaPassword,@RequestParam  String vecchiaPassword) throws InvalidFormatException, EntityNotFoundException {
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailUtente)) {
            Studente toChange = utenteService.trovaStudente(emailUtente);
            if (checkPassword(nuovaPassword)){
                if (passwordEncoder.matches(vecchiaPassword, toChange.getPassword())) {
                    utenteService.cambiaPassword(emailUtente, nuovaPassword);
                } else throw new PasswordMissmatchException();
            } else throw new InvalidFormatException("La password inserita non rispetta il formato");
        } else throw new NotAuthorizedException("Non puoi cambiare la password di un altro utente!");
    }

    @RequestMapping("/cancellaAccountPersonale")
    public void cancellaAccountPersonale(@RequestParam String email,@RequestParam  String password) throws EntityNotFoundException {
        if(SecurityUtils.getLoggedIn().getEmail().equals(email)){
            Studente toDelete = utenteService.trovaStudente(email);
            if(passwordEncoder.matches(password, toDelete.getPassword())){
                utenteService.deleteUtente(toDelete);
                SecurityUtils.forceLogout(toDelete, sessionRegistry);
            }else throw new PasswordMissmatchException();
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/trovaFoto")
    public FotoDTO trovaFoto(@RequestParam Long fotoId) {
        if(fotoId != null) {
            if (SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))
                return EntityToDto.toDTO(profiloService.findFotoById(fotoId));
            else throw new NotAuthorizedException("Non puoi cercare le foto di altri utenti!");
        }else throw new InvalidFormatException("Id foto non valido");
    }

    @RequestMapping("/trovaProfilo")
    public ProfiloDTO trovaProfilo(@RequestParam Long profiloId) {
        if(profiloId != null) {
            if (SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))
                return EntityToDto.toDTO(profiloService.findProfiloById(profiloId));
            else throw new NotAuthorizedException("Non puoi cercare i profili di altri utenti!");
        }else throw new InvalidFormatException("Id profilo non valido!");
    }


    private boolean checkFoto(FotoDTO f){
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
                        if(p.getHobbyList().size()>0) return true;
                    }
                }
            }
        }

        return false;
    }


    private boolean checkPassword(String password){
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    }

    private boolean checkEmail(String email){
        if (email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
            return true;

        return false;
    }
}
