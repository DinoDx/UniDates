package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Repository.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.ProfiloRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfiloService {
    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    private ProfiloRepository profiloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private FotoRepository fotoRepository;

   /* public void eliminaProfilo(Profilo profilo){
        profiloRepository.deleteById(profilo.getId());
        SecurityUtils.forceLogout(profilo.getStudente(), sessionRegistry);
    }
    */
    public void modificaProfilo(String email ,Profilo profilo){ // Con modifica profilo modifichi solo le info e non le foto
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        profilo.setId(studente.getProfilo().getId());
        profilo.setListaFoto(studente.getProfilo().getListaFoto());
        profilo.setStudente(studente);
        profiloRepository.save(profilo);
    }

    public void setFotoProfilo(String email, Long idNuovaFoto){
        eliminaFotoProfilo(email);
        Foto f = fotoRepository.getOne(idNuovaFoto);
        f.setFotoProfilo(true);
        fotoRepository.save(f);
    }

    public void aggiungiFotoLista(String email, Foto foto){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        Foto f = new Foto(foto.getImg());
        studente.getProfilo().addFoto(f, false);
        profiloRepository.save(studente.getProfilo());
    }
    public void aggiungiFotoProfilo(String email, Foto foto){
        eliminaFotoProfilo(email);
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        Foto f = new Foto(foto.getImg());
        studente.getProfilo().addFoto(f, true);
        profiloRepository.save(studente.getProfilo());
    }
    public void eliminaFotoLista(String email, Long idFototoDelete){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        Foto f = fotoRepository.getOne(idFototoDelete);
        studente.getProfilo().removeFoto(f);
        profiloRepository.save(studente.getProfilo());
    }

    private void eliminaFotoProfilo(String email){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        Foto foto = studente.getProfilo().getFotoProfilo();
        foto.setFotoProfilo(false);
        fotoRepository.save(foto);
    }

    public Foto findFotoById(Long fotoId) {
        return fotoRepository.findById(fotoId).get();
    }

    public Profilo findProfiloById(Long profiloId) {
        return profiloRepository.findById(profiloId).get();
    }
    /*
    public Profilo visualizzaProfilo(Studente studente){
        return studente.getProfilo();
    }
     */

}
