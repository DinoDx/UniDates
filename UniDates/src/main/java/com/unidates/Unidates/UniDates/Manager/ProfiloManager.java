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

    public void modificaProfilo(String email ,Profilo profilo){ // Con modifica profilo modifichi solo le info e non le foto
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null){
            profilo.setId(studente.getProfilo().getId());
            profilo.setListaFoto(studente.getProfilo().getListaFoto());
            profilo.setStudente(studente);
            profiloRepository.save(profilo);
        } else throw new EntityNotFoundException("Studente non trovato!");
    }

    public void setFotoProfilo(String email, Long idNuovaFoto){
        eliminaFotoProfilo(email);
        Foto f = fotoRepository.findById(idNuovaFoto).orElse(null);
        if(f != null) {
            f.setFotoProfilo(true);
            fotoRepository.save(f);
        }
        else throw new EntityNotFoundException("Foto non trovata!");
    }

    public void aggiungiFotoLista(String email, Foto foto){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) {
            Foto f = new Foto(foto.getImg());
            if(!studente.getProfilo().getListaFoto().contains(f)) {
                studente.getProfilo().addFoto(f, false);
                profiloRepository.save(studente.getProfilo());
            }else throw new AlreadyExistException("Foto già inserita!");
        }else throw new EntityNotFoundException("Studente non trovato!");
    }

    public void aggiungiFotoProfilo(String email, Foto foto){
        eliminaFotoProfilo(email);
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null ){
            Foto f = new Foto(foto.getImg());
            if(!studente.getProfilo().getListaFoto().contains(foto)) {
                studente.getProfilo().addFoto(f, true);
                profiloRepository.save(studente.getProfilo());
            }else throw new AlreadyExistException("Foto già inserita!");
        }else throw new EntityNotFoundException("Studente non trovato!");
    }
    public void eliminaFotoLista(String email, Long idFototoDelete){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) {
            Foto f = fotoRepository.findById(idFototoDelete).orElse(null);
            if(f != null) {
                studente.getProfilo().removeFoto(f);
                profiloRepository.save(studente.getProfilo());
            }else throw new EntityNotFoundException("Foto non trovata!");
        }else throw new EntityNotFoundException("Studente non trovato!");
    }

    private void eliminaFotoProfilo(String email){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        if(studente != null) {
            Foto foto = studente.getProfilo().getFotoProfilo();
            if(foto != null) {
                foto.setFotoProfilo(false);
                fotoRepository.save(foto);
            }else throw new EntityNotFoundException("Foto profilo non trovata!");
        } else throw new EntityNotFoundException("Studente non trovato!");
    }

    public Foto findFotoById(Long fotoId) {
        Foto f = fotoRepository.findById(fotoId).orElse(null);
        if(f != null) return f;
        else throw new EntityNotFoundException("Foto non trovata!");
    }

    public Profilo findProfiloById(Long profiloId) {
        Profilo p = profiloRepository.findById(profiloId).orElse(null);
        if(p != null) return p;
        else throw new EntityNotFoundException("Profilo non trovato!");
    }

}
