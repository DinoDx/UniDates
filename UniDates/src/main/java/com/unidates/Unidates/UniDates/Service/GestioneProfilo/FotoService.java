package com.unidates.Unidates.UniDates.Service.GestioneProfilo;

import com.unidates.Unidates.UniDates.Exception.FotoNotFoundException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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

    public Foto findById(Long id){
        try{
            Optional<Foto> foto = fotoRepository.findById(id);
            if(foto.isPresent()) return foto.get();
            else throw new FotoNotFoundException();
        }catch(FotoNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Foto> findAll(){
        return fotoRepository.findAll();
    }
}
