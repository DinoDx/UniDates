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
        profilo.setFotoProfilo(studente.getProfilo().getFotoProfilo());
        profilo.setListaFoto(studente.getProfilo().getListaFoto());
        profilo.setStudente(studente);
        profiloRepository.save(profilo);
    }

    public void setFotoProfilo(String email, Foto foto){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        studente.getProfilo().setFotoProfilo(foto);
        profiloRepository.save(studente.getProfilo());
    }

    public void aggiungiFoto(String email, Foto foto){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        foto.setProfilo(studente.getProfilo());
        fotoRepository.save(foto);
    }
    public void eliminaFoto(Long idFototoDelete){
        fotoRepository.deleteById(idFototoDelete);
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
