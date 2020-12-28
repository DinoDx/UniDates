package com.unidates.Unidates.UniDates.Service.GestioneProfilo;

import com.unidates.Unidates.UniDates.Exception.ProfileNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.ProfiloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfiloService {

    @Autowired
    private ProfiloRepository profiloRepository;

    public void deleteProfile(Profilo profilo, String password){

        if(password==profilo.getStudente().getPassword())
        profiloRepository.deleteById(profilo.getId());
    }

    public void updateProfile(Profilo profilo, String password){

        if(password==profilo.getStudente().getPassword())
        profiloRepository.save(profilo);
    }
}
