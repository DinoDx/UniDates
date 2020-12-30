package com.unidates.Unidates.UniDates.Controller;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Service.GestioneProfilo.FotoService;
import com.unidates.Unidates.UniDates.Service.GestioneProfilo.ProfiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/api/ProfileManager")
public class GestioneProfiloController {

    @Autowired
    ProfiloService profiloService;

    @Autowired
    FotoService fotoService;

    @RequestMapping("/addFoto")
    public void addFoto(Profilo profilo, Foto foto){
        checkFoto(foto);
        fotoService.addFoto(profilo, foto);
    }

    @RequestMapping("/deleteFoto")
    public void deleteFoto(Foto foto){
        fotoService.deleteFoto(foto);
    }

    @RequestMapping("/deleteProfile")
    public void deleteProfile(Profilo profilo, String password){
        checkPassword(password, profilo);
        profiloService.deleteProfile(profilo, password);
    }

    @RequestMapping("/editProfile")
    public void updateProfile(Profilo profilo, String password){
        checkPassword(password, profilo);
        checkProfilo(profilo);
        profiloService.updateProfile(profilo, password);
    }

    @RequestMapping("/findFoto")
    public Foto findFotoById(Long id){
        return fotoService.findById(id);
    }

    @RequestMapping("/findAllFoto")
    public Collection<Foto> findAllFoto(){
        return fotoService.findAll();
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
