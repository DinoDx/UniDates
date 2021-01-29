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
        profilo.setFotoProfilo(studente.getProfilo().getFotoProfilo());
        profilo.setListaFoto(studente.getProfilo().getListaFoto());
        profilo.setStudente(studente);
        profiloRepository.save(profilo);
    }

    public void setFotoProfilo(String email, Foto foto){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        Profilo profilo = studente.getProfilo();

        profilo.addFoto(profilo.getFotoProfilo());

        Foto toAdd = new Foto(foto.getImg());
        toAdd.setProfilo(profilo);

        profilo.setFotoProfilo(toAdd);
        profiloRepository.save(profilo);
    }

    public void aggiungiFoto(String email, Foto foto){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        Foto f = new Foto(foto.getImg());
        studente.getProfilo().addFoto(f);
        profiloRepository.save(studente.getProfilo());
    }
    public void eliminaFotoLista(String email, Long idFototoDelete){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        Foto f = fotoRepository.getOne(idFototoDelete);
        studente.getProfilo().getListaFoto().remove(f);
        profiloRepository.save(studente.getProfilo());
    }
    public void eliminaFotoProfilo(String email){
        Studente studente = (Studente) utenteRepository.findByEmail(email);
        studente.getProfilo().setFotoProfilo(studente.getProfilo().getListaFoto().remove(0));
        profiloRepository.save(studente.getProfilo());
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
