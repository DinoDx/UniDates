package com.unidates.Unidates.UniDates.Service.GestioneProfilo;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    public void addFoto(Profilo profilo, Foto foto){
        foto.setProfilo(profilo);
        fotoRepository.save(foto);
    }

    public void deleteFoto(Foto foto){
        fotoRepository.delete(foto);
    }
}
