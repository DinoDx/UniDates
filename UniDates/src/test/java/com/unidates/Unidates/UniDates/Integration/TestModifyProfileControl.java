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
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestModifyProfileControl {

    //gli studenti vengono creati dalla classe populator

    @Autowired
    PasswordEncoder passwordEncoder;

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
    public void aggiungiFotoLista_formanoEmailNonValido(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.aggiungiFotoLista("studenteprova1",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoLista_emailNonCorrispondente(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);

        assertThrows(NotAuthorizedException.class, ()->modifyProfileControl.aggiungiFotoLista("studenteprova2@gmail.com",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoLista_fotoNonValida(){
        byte[] img = {};
        FotoDTO foto = new FotoDTO(img);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.aggiungiFotoLista("studenteprova1@gmail.com",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista_valid(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        FotoDTO fotoDTO;

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        fotoDTO = modifyProfileControl.eliminaFotoLista(f.getId(),"studenteprova1@gmail.com");
        assertFalse(fotoRepository.existsById(fotoDTO.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista_formatoEmailNonValido(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.eliminaFotoLista(f.getId(),"studenteprova1gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista_idFotoNonValido(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        f.setId(null);
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.eliminaFotoLista(f.getId(),"studenteprova1@gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void eliminaFotoLista_emailNonCorrispondente(){
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
    public void aggiungiFotoProfilo_formatoEmailNonVAlido(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);
        foto.setFotoProfilo(true);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.aggiungiFotoProfilo("studente",foto));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoProfilo_emailNonCorrispondente(){
        byte[] img = {1,2,3};
        FotoDTO foto = new FotoDTO(img);
        foto.setFotoProfilo(true);

        assertThrows(NotAuthorizedException.class, ()->modifyProfileControl.aggiungiFotoProfilo("studenteprova2@gmail.com",foto));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiFotoProfilo_fotoNonValida(){
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
    public void setFotoProfilo_formatoEmailNonValido(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);

        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.setFotoProfilo("studenteprova1",f.getId()));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void setFotoProfilo_fotoNonValida(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();

        Foto f = studente.getProfilo().getListaFoto().get(studente.getProfilo().getListaFoto().size()-1);
        f.setId(null);
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.setFotoProfilo("studenteprova1@gmail.com",f.getId()));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void setFotoProfilo_emailNonCorrispondente(){
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
    public void modificaProfilo_formatoProfiloNonValido(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo =  studente.getProfilo();
        profilo.setCognome(null);

        assertThrows(InvalidFormatException.class,()-> modifyProfileControl.modificaProfilo("studenteprova1@gmail.com",EntityToDto.toDTO(profilo)));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void modificaProfilo_formatoEmailNonValido(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo =  studente.getProfilo();
        assertThrows(InvalidFormatException.class, ()->modifyProfileControl.modificaProfilo("studente",EntityToDto.toDTO(profilo)));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void modificaProfilo_emailNonCorrispondente(){
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
    public void cambiaPassword_emailNonCorrispondente(){
        assertThrows(NotAuthorizedException.class,()->modifyProfileControl.cambiaPassword("studenteprova2@gmail.com","ciao@232","studenteprova1"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cambiaPassword_formatoPasswordNonValido(){
        assertThrows(InvalidFormatException.class,()->modifyProfileControl.cambiaPassword("studenteprova1@gmail.com","ciaooooo","studenteprova1"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cambiaPassword_passwordNonCorrispondente(){
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
    public void cancellaAccountPersonale_passwordNonCorrispondente(){
        assertThrows(PasswordMissmatchException.class,()->modifyProfileControl.cancellaAccountPersonale("studenteprova1@gmail.com","studenteprova"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void cancellaAccountPersonale_emailNonCorrispondente(){
        assertThrows(NotAuthorizedException.class,()-> modifyProfileControl.cancellaAccountPersonale("studenteprova2@gmail.com","studenteprova2"));
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
    public void trovaFoto_idFotoNonValido(){
        assertThrows(InvalidFormatException.class,()->modifyProfileControl.trovaFoto(null));
    }


    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void trovaFoto_ruoloNonCorrispondente(){
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
        assertThrows(InvalidFormatException.class,()->modifyProfileControl.trovaProfilo(null));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void trovaPorfilo_ruoloNotMatch(){
        Studente studente = (Studente) SecurityUtils.getLoggedIn();
        Profilo profilo = studente.getProfilo();

        assertThrows(NotAuthorizedException.class,()->modifyProfileControl.trovaProfilo(profilo.getId()));
    }


    @BeforeEach
    public void pupulateDB(){
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        byte[] img = {1,2,3};

        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(img) ,hobbyArrayList);
        p1.setNumeroTelefono("3333339900");
        p1.setNickInstagram("marco.prova1");

        Studente s1 = new Studente("studenteprova1@gmail.com",passwordEncoder.encode("studenteprova1"));
        s1.setActive(true);
        s1.setProfilo(p1);
        Studente s2 = new Studente("studenteprova2@gmail.com",passwordEncoder.encode("studenteprova2"));
        s2.setActive(true);
        s2.setProfilo(p1);
        Studente s3 = new Studente("studenteprova3@gmail.com", passwordEncoder.encode("studenteprova3"));
        s3.setActive(false);
        s3.setProfilo(p1);


        Moderatore m1 = new Moderatore("moderatore@gmail.com", "moderatore");
        Profilo p4 = new Profilo("Marcello", "Moderatore", "Napoli", "Napoli", LocalDate.of(1999,6,12), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.GRIGI, Colore_Occhi.AZZURRI,new Foto(img),hobbyArrayList );
        m1.setProfilo(p4);

        utenteRepository.saveAll(Arrays.asList(s1,s2,s3,m1));
    }

}
