package com.unidates.Unidates.UniDates.Service.GestioneProfilo;

import com.unidates.Unidates.UniDates.Exception.ProfileNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.ProfiloRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfiloService {

    private ProfiloRepository profiloRepository;

    public void deleteProfile(Profilo profilo){
        profiloRepository.deleteById(profilo.getId());
    }

    public void updateProfile(Profilo profilo){
        profiloRepository.save(profilo);
    }
}
