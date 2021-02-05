package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.Exception.*;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
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
        if(s.getEmail().equals(emailFotoToAdd)) {
            Foto f = new Foto(fotoDTO.getImg());
            if (checkFoto(f))
                profiloService.aggiungiFotoLista(emailFotoToAdd, f);
            else throw new InvalidFormatException("La foto non rispetta le dimensioni consentite");
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/eliminaFoto")
    public void eliminaFotoLista(@RequestBody FotoDTO f, @RequestParam String email){
        if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.STUDENTE)){
            Studente studente = (Studente) SecurityUtils.getLoggedIn();
            Foto toDelete = new Foto();
            toDelete.setId(f.getId());
            if (studente.getProfilo().getListaFoto().contains(toDelete))
                profiloService.eliminaFotoLista(email,f.getId());
            else throw new NotAuthorizedException();
        }else{
            profiloService.eliminaFotoLista(email,f.getId());
        }
    }

    @RequestMapping("/aggiungifotoProfilo")
    public void aggiungiFotoProfilo(@RequestParam String emailFotoToAdd, @RequestBody FotoDTO fotoDTO) throws InvalidFormatException {
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailFotoToAdd)) {
            Foto f = new Foto(fotoDTO.getImg());
            if (checkFoto(f))
                profiloService.aggiungiFotoProfilo(emailFotoToAdd, f);
            else throw new InvalidFormatException("La foto non rispetta le dimensioni consentite");
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/setFotoProfilo")
    public void setFotoProfilo(@RequestParam String emailStudenteToModify, @RequestBody FotoDTO fotoDTO){
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailStudenteToModify)) {
            profiloService.setFotoProfilo(emailStudenteToModify, fotoDTO.getId());
        }
        else throw new NotAuthorizedException();
    }


    @RequestMapping("/modificaProfilo")
    public void modificaProfilo(String emailStudenteToModify, ProfiloDTO profiloDTO) throws InvalidFormatException {
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailStudenteToModify)) {
            Profilo p = new Profilo(profiloDTO.getNome(), profiloDTO.getCognome(), profiloDTO.getLuogoNascita(), profiloDTO.getResidenza(),
                    profiloDTO.getDataDiNascita(), profiloDTO.getAltezza(), profiloDTO.getSesso(), profiloDTO.getInteressi(), profiloDTO.getColori_capelli(), profiloDTO.getColore_occhi(),
                    new Foto(), profiloDTO.getHobbyList());

            if(profiloDTO.getNumeroTelefono()!= null)
                p.setNickInstagram(profiloDTO.getNickInstagram());
            if(profiloDTO.getNickInstagram() != null)
                p.setNumeroTelefono(profiloDTO.getNumeroTelefono());

            if (checkProfilo(p))
                profiloService.modificaProfilo(emailStudenteToModify, p);
            else throw new InvalidFormatException("Uno o più campi inseriti non hanno un formato valido");
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/cambiaPassword")
    public void cambiaPassword(@RequestParam String emailUtente,@RequestParam  String nuovaPassword,@RequestParam  String vecchiaPassword) throws InvalidFormatException {
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailUtente)) {
            Studente toChange = utenteService.trovaStudente(emailUtente);
            if (checkStudente(new Studente(emailUtente, nuovaPassword))) {
                if (passwordEncoder.matches(vecchiaPassword, toChange.getPassword())) {
                    utenteService.cambiaPassword(emailUtente, nuovaPassword);
                } else throw new PasswordMissmatchException();
            } else throw new InvalidFormatException("La password inserita non rispetta il formato");
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/cancellaAccountPersonale")
    public void cancellaAccountPersonale(@RequestParam String email,@RequestParam  String password){
        if(SecurityUtils.getLoggedIn().getEmail().equals(email)){
            Studente toDelete = utenteService.trovaStudente(email);
            if(passwordEncoder.matches(password, toDelete.getPassword())){
                utenteService.deleteUtente(toDelete);
                SecurityUtils.forceLogout(toDelete, sessionRegistry);
            }else throw new PasswordMissmatchException();
        }
        else throw new NotAuthorizedException();
    }


  /*  @RequestMapping("/visualizzaProfilo")
    public Profilo visualizzaProfilo(Studente s){
        return profiloService.visualizzaProfilo(s);
    } */

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
                        if(p.getHobbyList().size()>0) return true;
                    }
                }
            }
        }

        return false;
    }

    @RequestMapping("/trovaFoto")
    public FotoDTO trovaFoto(@RequestParam Long fotoId) {
        if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))
            return EntityToDto.toDTO(profiloService.findFotoById(fotoId));
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/trovaProfilo")
    public ProfiloDTO trovaProfilo(@RequestParam Long profiloId) {
        //if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))
            return EntityToDto.toDTO(profiloService.findProfiloById(profiloId));
        //else throw new NotAuthorizedException();
    }

    private boolean checkStudente(Studente s) {
        if(s.getEmail() != null && s.getPassword() != null){
            if(checkEmail(s.getEmail())){
                return s.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
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
