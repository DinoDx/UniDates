package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.Model.Enum.Interessi;
import com.unidates.Unidates.UniDates.Model.Enum.Sesso;
import com.unidates.Unidates.UniDates.Exception.InvalidModifyFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidPhotoException;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Service.ProfiloService;
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
    public void aggiungiFoto(String emailFotoToAdd, FotoDTO fotoDTO){
        Foto f = new Foto(fotoDTO.getImg());
        if (checkFoto(f))
            profiloService.aggiungiFoto(emailFotoToAdd, f);
        else throw new InvalidPhotoException();
    }

    @RequestMapping("/eliminaFoto")
    public void eliminaFoto(FotoDTO f){
        profiloService.eliminaFoto(f.getId());
    }

    @RequestMapping("/aggiungifotoProfilo")
    public void aggiungiFotoProfilo(String emailFotoToAdd, FotoDTO fotoDTO){
        Foto f = new Foto(fotoDTO.getImg());
        if(checkFoto(f))
            profiloService.setFotoProfilo(emailFotoToAdd, f);
        else throw new InvalidPhotoException();
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
        Profilo p = new Profilo(profiloDTO.getNome(), profiloDTO.getCognome(), profiloDTO.getLuogoNascita(), profiloDTO.getResidenza(),
                profiloDTO.getDataDiNascita(), profiloDTO.getAltezza(), profiloDTO.getSesso(), profiloDTO.getInteressi(), profiloDTO.getColori_capelli(), profiloDTO.getColore_occhi(),
                null,profiloDTO.getHobbyList());

        if(checkProfilo(p))
            profiloService.modificaProfilo(emailStudenteToModify,p);
        else throw new InvalidModifyFormatException();
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

    public FotoDTO trovaFoto(Long fotoId) {
        return EntityToDto.toDTO(profiloService.findFotoById(fotoId));
    }

    public ProfiloDTO trovaProfilo(Long profiloId) {
        return EntityToDto.toDTO(profiloService.findProfiloById(profiloId));
    }
}
