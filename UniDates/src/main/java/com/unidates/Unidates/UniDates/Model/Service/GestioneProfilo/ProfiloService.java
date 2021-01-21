package com.unidates.Unidates.UniDates.Model.Service.GestioneProfilo;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.FotoRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.ProfiloRepository;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfiloService {
    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    private ProfiloRepository profiloRepository;

    @Autowired
    private FotoRepository fotoRepository;

    public void eliminaProfilo(Profilo profilo){
        profiloRepository.deleteById(profilo.getId());
        SecurityUtils.forceLogout(profilo.getStudente(), sessionRegistry);
    }
    
    public void modificaProfilo(Profilo profilo){
        profiloRepository.save(profilo);
    }

    public void setFotoProfilo(Profilo profilo, Foto foto){
        profilo.setFotoProfilo(foto);
        profiloRepository.save(profilo);
    }

    public void aggiungiFoto(Profilo profilo, Foto foto){
       foto.setProfilo(profilo);
       fotoRepository.save(foto);
    }
    public void eliminaFoto(Foto foto){
        fotoRepository.delete(foto);
    }

    public Profilo visualizzaProfilo(Studente studente){
        return studente.getProfilo();
    }
}
