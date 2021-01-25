package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.Interessi;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Enum.Sesso;
import com.unidates.Unidates.UniDates.Exception.InvalidModifyFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidPhotoException;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Service.ProfiloService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/ProfileManager")
public class GestioneProfiloController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProfiloService profiloService;


    @RequestMapping("/aggiungiFoto")
    public void aggiungiFoto(@RequestParam String emailFotoToAdd,@RequestBody FotoDTO fotoDTO){
        Studente s = (Studente) SecurityUtils.getLoggedIn();
        if(s.getEmail().equals(emailFotoToAdd)) {
            Foto f = new Foto(fotoDTO.getImg());
            if (checkFoto(f))
                profiloService.aggiungiFoto(emailFotoToAdd, f);
            else throw new InvalidPhotoException();
        }
        else throw new NotAuthorizedException();
    }

    @RequestMapping("/eliminaFoto")
    public void eliminaFoto(@RequestBody FotoDTO f){
        if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.STUDENTE)){
            Studente studente = (Studente) SecurityUtils.getLoggedIn();
            boolean founded = false;
            for (Foto f1 : studente.getProfilo().getListaFoto()) {
                if (f1.getId().equals(f.getId()))
                    founded = true;
            }
            if (founded)
                profiloService.eliminaFoto(f.getId());
            else throw new NotAuthorizedException();
        }else{
            profiloService.eliminaFoto(f.getId());
        }
    }

    @RequestMapping("/aggiungifotoProfilo")
    public void aggiungiFotoProfilo(@RequestParam String emailFotoToAdd, @RequestBody FotoDTO fotoDTO){
        if(SecurityUtils.getLoggedIn().getEmail().equals(emailFotoToAdd)) {
            Foto f = new Foto(fotoDTO.getImg());
            if (checkFoto(f))
                profiloService.setFotoProfilo(emailFotoToAdd, f);
            else throw new InvalidPhotoException();
        }
        else throw new NotAuthorizedException();
    }

   /* public void eliminaProfilo(ProfiloDTO p,String emailToDelete, String password){
        Studente s = utenteService.trovaStudente(emailToDelete);
        if (passwordEncoder.matches(password,s.getPassword()){
            profiloService.eliminaProfilo(p);
        }
        else throw new PasswordMissmatchException();
    }
    */

    @RequestMapping("/modificaProfile")
    public void modificaProfilo(String emailStudenteToModify, ProfiloDTO profiloDTO){
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
            else throw new InvalidModifyFormatException();
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
        //if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))
            return EntityToDto.toDTO(profiloService.findFotoById(fotoId));
        //else throw new NotAuthorizedException();
    }

    @RequestMapping("/trovaProfilo")
    public ProfiloDTO trovaProfilo(@RequestParam Long profiloId) {
        //if(SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.MODERATORE) || SecurityUtils.getLoggedIn().getRuolo().equals(Ruolo.COMMUNITY_MANAGER))
            return EntityToDto.toDTO(profiloService.findProfiloById(profiloId));
        //else throw new NotAuthorizedException();
    }
}
