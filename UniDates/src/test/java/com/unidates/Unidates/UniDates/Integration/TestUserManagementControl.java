package com.unidates.Unidates.UniDates.Integration;

import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.VerificationToken;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import com.unidates.Unidates.UniDates.SecurityTestConfig;
import com.unidates.Unidates.UniDates.UniDatesApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestUserManagementControl {

    //gli studenti vengono creati dalla classe populator

    @Autowired
    PasswordEncoder passwordEncoder;
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
        assertEquals("home", userManagementControl.confermaRegistrazione("token"));
    }

    @Test
    public void confermaRegistrazione_tokenNonTrovato(){
        assertThrows(EntityNotFoundException.class, () -> userManagementControl.confermaRegistrazione("token"));
    }

    @Test
    public void confermaRegistrazione_tokenNonValido(){
        assertThrows(EntityNotFoundException.class, () -> userManagementControl.confermaRegistrazione("tokenscaduto"));
    }

    @Test
    public void trovaTuttiStudenti_valid(){
        assertDoesNotThrow(() -> userManagementControl.trovaTuttiStudenti());
    }


    @BeforeEach
    public void populateDB(){
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
        Studente s = new Studente("studenteprovatokenscaduto@gmail.com", passwordEncoder.encode("studenteprovascaduto"));
        s.setActive(false);
        s.setProfilo(p1);
        utenteRepository.saveAll(Arrays.asList(s1,s2,s3,s));

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken("tokenscaduto");
        verificationToken.setUtente(s);
        verificationToken.setExpiryDate(Calendar.getInstance().getTime());
        verificationTokenRepository.save(verificationToken);
    }


}
