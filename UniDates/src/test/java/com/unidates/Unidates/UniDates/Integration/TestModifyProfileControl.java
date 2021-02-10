package com.unidates.Unidates.UniDates.Integration;


import com.unidates.Unidates.UniDates.Control.ModifyProfileControl;
import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Exception.PasswordMissmatchException;
import com.unidates.Unidates.UniDates.Manager.ProfiloManager;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.ProfiloRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.SecurityTestConfig;
import com.unidates.Unidates.UniDates.UniDatesApplication;
import org.aspectj.weaver.ast.Not;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})
public class TestModifyProfileControl {

    //gli studenti vengono creati dalla classe populator

    @Autowired
    ModifyProfileControl modifyProfileControl;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    FotoRepository fotoRepository;

    @Autowired
    ProfiloRepository profiloRepository;

    @Autowired
    ProfiloManager profiloManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserManager userManager;

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoLista_valid(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);

        FotoDTO fotoDTO;

        fotoDTO = modifyProfileControl.aggiungiFotoLista("studenteprova1@gmail.com",foto);
        assertTrue(fotoRepository.existsById(fotoDTO.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoLista_emailNotValid(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.aggiungiFotoLista("studenteprova1",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoLista_emailNotMatch(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);

        assertThrows(NotAuthorizedException.class, ()->modifyProfileControl.aggiungiFotoLista("studenteprova2@gmail.com",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoLista_fotoNotValid(){
        byte[] img = {};
        FotoDTO foto = new FotoDTO(img);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.aggiungiFotoLista("studenteprova1@gmail.com",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        FotoDTO fotoDTO;

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        fotoDTO = modifyProfileControl.eliminaFotoLista(f.getId(),"studenteprova1@gmail.com");
        assertFalse(fotoRepository.existsById(fotoDTO.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista_emailNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.eliminaFotoLista(f.getId(),"studenteprova1gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista_idFotoNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        f.setId(null);
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.eliminaFotoLista(f.getId(),"studenteprova1@gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista_emailNotMatch(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        assertThrows(NotAuthorizedException.class, ()->modifyProfileControl.eliminaFotoLista(f.getId(),"studenteprova2@gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoProfilo_valid(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);
        foto.setFotoProfilo(true);

        FotoDTO fotoDTO;

        fotoDTO = modifyProfileControl.aggiungiFotoLista("studenteprova1@gmail.com",foto);
        assertTrue(fotoRepository.existsById(fotoDTO.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoProfilo_emailNotValid(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);
        foto.setFotoProfilo(true);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.aggiungiFotoProfilo("studente",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoProfilo_emailNotMacth(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);
        foto.setFotoProfilo(true);

        assertThrows(NotAuthorizedException.class, ()->modifyProfileControl.aggiungiFotoProfilo("studenteprova2@gmail.com",foto));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoProfilo_fotoNotValid(){
        byte[] img = {};
        FotoDTO foto = new FotoDTO(img);
        foto.setFotoProfilo(true);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.aggiungiFotoProfilo("studenteprova1@gmail.com",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void setFotoProfilo_valid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        FotoDTO fotoDTO;

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        fotoDTO = modifyProfileControl.setFotoProfilo("studenteprova1@gmail.com",f.getId());
        assertTrue(fotoRepository.existsById(fotoDTO.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void setFotoProfilo_emailNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.setFotoProfilo("studenteprova1",f.getId()));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void setFotoProfilo_fotoNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        f.setId(null);
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.setFotoProfilo("studenteprova1@gmail.com",f.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void setFotoProfilo_emailNotMatch(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        assertThrows(NotAuthorizedException.class, ()->modifyProfileControl.setFotoProfilo("studenteprova2@gmail.com",f.getId()));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void modificProfilo_valid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo =  studente.getProfilo();
        profilo.setNickInstagram("ciaoIg");
        profilo.setNumeroTelefono("333333333");

        assertTrue(modifyProfileControl.modificaProfilo("studenteprova1@gmail.com",EntityToDto.toDTO(profilo)));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void modificaProfilo_profiloNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo =  studente.getProfilo();
        profilo.setCognome(null);

        assertThrows(InvalidFormatException.class,()-> modifyProfileControl.modificaProfilo("studenteprova1@gmail.com",EntityToDto.toDTO(profilo)));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void modificaProfilo_emailNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo =  studente.getProfilo();
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.modificaProfilo("studente",EntityToDto.toDTO(profilo)));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void modificaProfilo_emailNotMatch(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo =  studente.getProfilo();
        assertThrows(NotAuthorizedException.class,()->modifyProfileControl.modificaProfilo("studenteprova3@gmail.com",EntityToDto.toDTO(profilo)));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cambiaPassword_valid(){
        modifyProfileControl.cambiaPassword("studenteprova1@gmail.com","ciao@123","studenteprova1");
        assertTrue(encoder.matches("ciao@123",utenteRepository.findByEmail("studenteprova1@gmail.com").getPassword()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cambiaPassword_emailNotMatch(){
        assertThrows(NotAuthorizedException.class,()->modifyProfileControl.cambiaPassword("studenteprova2@gmail.com","ciao@232","studenteprova1"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cambiaPassword_passwordNotValid(){
        assertThrows(InvalidFormatException.class,()->modifyProfileControl.cambiaPassword("studenteprova1@gmail.com","ciaooooo","studenteprova1"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cambiaPassword_newPasswordNotMatch(){
        assertThrows(PasswordMissmatchException.class,()->modifyProfileControl.cambiaPassword("studenteprova1@gmail.com","ciaooooo#@1","studenteprova1@"));
    }

    @Test
    @WithUserDetails("studenteprova2@gmail.com")
    public void cancellaAccountPersonale_valid(){
        modifyProfileControl.cancellaAccountPersonale("studenteprova2@gmail.com","studenteprova2");
        assertFalse(userManager.isPresent("studenteprova2@gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cancellaAccountPersonale_passwordNotValid(){
        assertThrows(PasswordMissmatchException.class,()->modifyProfileControl.cancellaAccountPersonale("studenteprova2@gmail.com","studenteprova"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cancellaAccountPersonale_emailNotValid(){
        assertThrows(NotAuthorizedException.class,()-> modifyProfileControl.cancellaAccountPersonale("studente","studenteprova2"));
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void trovaFoto_valid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        assertTrue(fotoRepository.existsById(f.getId()));
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void trovaFoto_fotoNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        f.setId(null);

        assertThrows(InvalidFormatException.class,()->modifyProfileControl.trovaFoto(f.getId()));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void trovaFoto_ruoloNotMatch(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        assertThrows(NotAuthorizedException.class,()->modifyProfileControl.trovaFoto(f.getId()));
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void trovaPorfilo_valid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo = studente.getProfilo();

        assertEquals(profilo.getId(),modifyProfileControl.trovaProfilo(profilo.getId()).getId());
    }


    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void trovaPorfilo_preofiloNotValid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo = studente.getProfilo();
        profilo.setId(null);

        assertThrows(InvalidFormatException.class,()->modifyProfileControl.trovaProfilo(profilo.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void trovaPorfilo_ruoloNotMatch(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo = studente.getProfilo();

        assertThrows(NotAuthorizedException.class,()->modifyProfileControl.trovaProfilo(profilo.getId()));
    }


}
