package com.unidates.Unidates.UniDates.Integration;

import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Repository.MatchRepository;
import com.unidates.Unidates.UniDates.Repository.NotificaRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.SecurityTestConfig;
import com.unidates.Unidates.UniDates.UniDatesApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})

public class TestInteractionControl {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    InteractionControl interactionControl;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    NotificaRepository notificaRepository;

    @Autowired
    UserManager userManager;

    @Test
    @WithUserDetails("studenteprova2@gmail.com")
    public void aggiungiMatch_valid(){

        assertTrue(interactionControl.aggiungiMatch("studenteprova2@gmail.com", "studenteprova3@gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova2@gmail.com")
    public void aggiungiMatch_EmailMatchUguale(){
     assertThrows(NotAuthorizedException.class, () -> interactionControl.aggiungiMatch("studenteprova2@gmail.com","studenteprova2@gmail.com"));

    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void aggiungiMatch_EmailDiversoDaLoggato(){
        assertThrows(NotAuthorizedException.class, () -> interactionControl.aggiungiMatch("studenteprova2@gmail.com","studenteprova3@gmail.com"));

    }

    @Test
    @WithUserDetails("studenteprova2@gmail.com")
    public void aggiungiMatch_FormatoEmailNonValido(){
        assertThrows(InvalidFormatException.class, () -> interactionControl.aggiungiMatch("studenteprova2@gmail.com","studenteprova"));

    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void isValidMatch_valid(){
        assertTrue(interactionControl.isValidMatch("studenteprova1@gmail.com", "studenteprova2@gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void isValidMatch_FormatoEmailNonValido(){
        assertThrows(InvalidFormatException.class, () -> interactionControl.aggiungiMatch("studenteprova1@gmail.com","studenteprova"));

    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void bloccaStudente_valid(){

        assertTrue(interactionControl.bloccaStudente("studenteprova1@gmail.com", "studenteprova2@gmail.com"));

    }

    @Test
    @WithUserDetails("studenteprova2@gmail.com")
    public void bloccaStudente_EmailUguale(){
        assertThrows(NotAuthorizedException.class, () -> interactionControl.bloccaStudente("studenteprova2@gmail.com","studenteprova2@gmail.com"));

    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void bloccaStudente_EmailDiversoDaLoggato(){
        assertThrows(NotAuthorizedException.class, () -> interactionControl.bloccaStudente("studenteprova2@gmail.com","studenteprova3@gmail.com"));

    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void bloccaStudente_FormatoEmailNonValido(){
        assertThrows(InvalidFormatException.class, () -> interactionControl.bloccaStudente("studenteprova1@gmail.com","studenteprova"));

    }

    @Test
    @WithUserDetails("studenteprova2@gmail.com")
    public void sbloccaStudente_valid(){
        Studente s1,s2;
        s1=userManager.trovaStudente("studenteprova2@gmail.com");
        s2=userManager.trovaStudente("studenteprova3@gmail.com");
        userManager.bloccaStudente(s1.getEmail(),s2.getEmail());

        assertTrue(interactionControl.sbloccaStudente("studenteprova2@gmail.com","studenteprova3@gmail.com"));
    }

    @Test
    @WithUserDetails("studenteprova2@gmail.com")
    public void sbloccaStudente_EmailUguale(){
        assertThrows(NotAuthorizedException.class, () -> interactionControl.sbloccaStudente("studenteprova2@gmail.com","studenteprova2@gmail.com"));

    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void sbloccaStudente_EmailDiversoDaLoggato(){
        assertThrows(NotAuthorizedException.class, () -> interactionControl.sbloccaStudente("studenteprova2@gmail.com","studenteprova3@gmail.com"));

    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void sbloccaStudente_FormatoEmailNonValido(){
        assertThrows(InvalidFormatException.class, () -> interactionControl.sbloccaStudente("studenteprova1@gmail.com","studenteprova"));

    }

    @Test
    public void ricercaStudente_valid(){
        Studente s=userManager.trovaStudente("studenteprova1@gmail.com");
        StudenteDTO s1=EntityToDto.toDTO(s);

        assertEquals(s1.getEmail(), interactionControl.ricercaStudente("studenteprova1@gmail.com").getEmail());

    }

    @Test
    public void ricercaStudente_EmailNotvalid(){

        assertThrows(InvalidFormatException.class, () -> interactionControl.ricercaStudente("studenteprova"));

    }






}
