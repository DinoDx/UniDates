package com.unidates.Unidates.UniDates.Manager;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Repository.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.ProfiloRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfiloManager {
    @Autowired
    private ProfiloRepository profiloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private FotoRepository fotoRepository;

    //
    public Profilo modificaProfilo(String email ,Profilo profilo){ // Con modifica profilo modifichi solo le info e non le foto
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null){
            profilo.setId(studente.getProfilo().getId());
            profilo.setListaFoto(studente.getProfilo().getListaFoto());
            profilo.setStudente(studente);
            profiloRepository.save(profilo);
            return profilo;
        } else throw new EntityNotFoundException("Studente non trovato!");
    }

    //
    public Foto setFotoProfilo(String email, Long idNuovaFoto){
        eliminaFotoProfilo(email);
        Foto f = fotoRepository.findFotoById(idNuovaFoto);
        if(f != null) {
            f.setFotoProfilo(true);
            fotoRepository.save(f);
            return f;
        }
        else throw new EntityNotFoundException("Foto non trovata!");
    }

    //
    public Foto aggiungiFotoLista(String email, Foto foto){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) {
            Foto f = foto;
                studente.getProfilo().addFoto(f, false);
                Profilo p = profiloRepository.save(studente.getProfilo());
                return p.getListaFoto().get(p.getListaFoto().size()-1);
        }else throw new EntityNotFoundException("Studente non trovato!");
    }


    public Foto aggiungiFotoProfilo(String email, Foto foto){
        eliminaFotoProfilo(email);
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null ){
                Foto f = new Foto(foto.getImg());
                studente.getProfilo().addFoto(f, true);
                profiloRepository.save(studente.getProfilo());
                return f;
        }else throw new EntityNotFoundException("Studente non trovato!");
    }

    //
    public Foto eliminaFotoLista(String email, Long idFototoDelete){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) {
            Foto f = fotoRepository.findFotoById(idFototoDelete);
            if(f != null) {
                studente.getProfilo().removeFoto(f);
                profiloRepository.save(studente.getProfilo());
                return f;
            }else throw new EntityNotFoundException("Foto non trovata!");
        }else throw new EntityNotFoundException("Studente non trovato!");
    }


    //
    public Studente eliminaFotoProfilo(String email){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) {
            Foto foto = studente.getProfilo().getFotoProfilo();
            if(foto != null) {
                foto.setFotoProfilo(false);
                fotoRepository.save(foto);
                return studente;
            }else throw new EntityNotFoundException("Foto profilo non trovata!");
        } else throw new EntityNotFoundException("Studente non trovato!");
    }

    //
    public Foto findFotoById(Long fotoId) {
        Foto f = fotoRepository.findFotoById(fotoId);
        if(f != null) return f;
        else throw new EntityNotFoundException("Foto non trovata!");
    }

    //
    public Profilo findProfiloById(Long profiloId) {
        Profilo p = profiloRepository.findProfiloById(profiloId);
        if(p != null) return p;
        else throw new EntityNotFoundException("Profilo non trovato!");
    }

}
