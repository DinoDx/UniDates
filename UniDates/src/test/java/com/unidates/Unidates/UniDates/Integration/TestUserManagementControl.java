package com.unidates.Unidates.UniDates.Integration;

import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.SecurityTestConfig;
import com.unidates.Unidates.UniDates.UniDatesApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.boot.test.context.SpringBootTest;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.*;

@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})
public class TestUserManagementControl {

    //gli studenti vengono creati dalla classe populator

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserManager userManager;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    UserManagementControl userManagementControl;

    @MockBean
    HttpServletRequest httpServletRequest;

    @Test
    public void registrazioneStudente_valid(){

        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);
        byte[] img = {1,2,3,4};
        ProfiloDTO p1 = new ProfiloDTO("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,hobbyArrayList, new FotoDTO(img));
        StudenteDTO s1 = new StudenteDTO("studenteprova4@gmail.com","studenteprova4@@", p1);

        Mockito.when(httpServletRequest.getContextPath()).thenReturn("");

        userManagementControl.registrazioneStudente(s1, httpServletRequest);

        assertTrue(utenteRepository.existsById("studenteprova4@gmail.com"));

    }

    @Test
    public void registrazioneStudente_emailnonvalida(){
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);
        byte[] img = {1,2,3,4};
        ProfiloDTO p1 = new ProfiloDTO("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,hobbyArrayList, new FotoDTO(img));
        StudenteDTO s1 = new StudenteDTO("studenteprova","studenteprova4@@", p1);

        Mockito.when(httpServletRequest.getContextPath()).thenReturn("");

        assertThrows(InvalidFormatException.class, () -> userManagementControl.registrazioneStudente(s1, httpServletRequest));

    }

    @Test
    public void registrazioneStudente_passwordnonvalida(){
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);
        byte[] img = {1,2,3,4};
        ProfiloDTO p1 = new ProfiloDTO("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,hobbyArrayList, new FotoDTO(img));
        StudenteDTO s1 = new StudenteDTO("studenteprova@gmail.com","", p1);

        Mockito.when(httpServletRequest.getContextPath()).thenReturn("");

        assertThrows(InvalidFormatException.class, () -> userManagementControl.registrazioneStudente(s1, httpServletRequest));

    }

    @Test
    public void registrazioneStudente_profilononvalida(){
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);
        byte[] img = {1,2,3,4};
        ProfiloDTO p1 = new ProfiloDTO("", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,hobbyArrayList, new FotoDTO(img));
        StudenteDTO s1 = new StudenteDTO("studenteprova@gmail.com","studenteprova1@@", p1);

        Mockito.when(httpServletRequest.getContextPath()).thenReturn("");

        assertThrows(InvalidFormatException.class, () -> userManagementControl.registrazioneStudente(s1, httpServletRequest));

    }

    @Test
    public void isAlreadyRegistered_true(){
        assertTrue(userManagementControl.isAlreadyRegistered("studenteprova1@gmail.com"));
    }

    @Test
    public void isAlreadyRegistered_false(){
        assertFalse(userManagementControl.isAlreadyRegistered("studenteprova4@gmail.com"));
    }

    @Test
    public void isAlreadyRegistered_emailnonvalida(){
        assertThrows(InvalidFormatException.class, () -> userManagementControl.isAlreadyRegistered("studenteprova1"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void studenteInSessione(){
        Studente studente = (Studente) utenteRepository.findByEmail("studenteprova1@gmail.com");
        assertEquals(studente.getEmail(), userManagementControl.studenteInSessione().getEmail());

    }

    @Test
    public void confermaRegistrazione_valid(){
        userManager.createVerificationToken(userManager.trovaUtente("studenteprova3@gmail.com"), "token");
        assertTrue(userManagementControl.confermaRegistrazione("token"));
    }

    @Test
    public void confermaRegistrazione_tokenNonTrovato(){
        assertThrows(EntityNotFoundException.class, () -> userManagementControl.confermaRegistrazione("token"));
    }

    @Test
    public void confermaRegistrazione_tokenNonValido(){
        Utente utente = userManager.trovaUtente("studenteprova3@gmail.com");
        userManager.createVerificationToken(utente, "token");
        VerificationToken verificationToken = verificationTokenRepository.findByUtente(utente);
        //verificationToken.setExpiryDate();
        assertThrows(EntityNotFoundException.class, () -> userManagementControl.confermaRegistrazione("token"));
    }




}
