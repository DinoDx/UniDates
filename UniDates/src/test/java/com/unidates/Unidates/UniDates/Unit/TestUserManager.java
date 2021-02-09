package com.unidates.Unidates.UniDates.Unit;


import com.helger.commons.system.ENewLineMode;
import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class TestUserManager {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserManager userManager;

    @MockBean
    UtenteRepository utenteRepository;

    @MockBean
    VerificationTokenRepository verificationTokenRepository;

    @Test
    public void registrazioneStudente_valid(){
        Studente s = new Studente("marcoprova1@gmail.com","ciao");
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);

        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        Studente registrato = userManager.registrazioneStudente(s, p);
        assertFalse(registrato.isActive());
        assertTrue(passwordEncoder.matches("ciao", registrato.getPassword()));
    }

    @Test
    public void registrazioneStudente_esiste(){
        Studente s = new Studente("marcoprova1@gmail.com","ciao");
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);

        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente("marcoprova1@gmail.com", "password"));
        assertThrows(AlreadyExistException.class, () -> userManager.registrazioneStudente(s, p));
    }

    @Test
    public void registrazioneModeratore_valid(){

        Moderatore m = new Moderatore("moderatore@gmail.com","ciao");
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);

        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        Moderatore registrato = userManager.registrazioneModeratore(m, p);

        assertTrue(registrato.isActive());
        assertTrue(passwordEncoder.matches("ciao", registrato.getPassword()));
    }

    @Test
    public void registrazioneModeratore_esiste(){
        Moderatore m = new Moderatore("moderatore@gmail.com","ciao");
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);

        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Moderatore("moderatore@gmail.com", "password"));

        assertThrows(AlreadyExistException.class, () -> userManager.registrazioneModeratore(m,p));
    }

    @Test
    public void registrazioneCommunityManager_valid(){
            CommunityManager m = new CommunityManager("cmmanager@gmail.com","ciao");
            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);

            Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

            CommunityManager registrato = userManager.registrazioneCommunityManager(m, p);

            assertTrue(registrato.isActive());
            assertTrue(passwordEncoder.matches("ciao", registrato.getPassword()));
    }

    @Test
    public void registrazioneCommunityManager_esiste(){
        CommunityManager m = new CommunityManager("cmmanager@gmail.com","ciao");
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);

        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new CommunityManager("cmmanger@gmail.com", "password"));

        assertThrows(AlreadyExistException.class, () -> userManager.registrazioneModeratore(m,p));
    }
    @Test
    public void trovaUtente_valid(){

        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
            String email = invocation.getArgument(0, String.class);
            Utente u = new Studente();
            u.setEmail(email);
            return u;
        });

        Utente s = new Studente("marcoprova1@gmail.com","ciao");

        assertEquals(s, userManager.trovaUtente("marcoprova1@gmail.com"));
    }

    @Test
    public void trovaUtente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.trovaUtente("pippo@gmail.com"));
    }

    @Test
    public void trovaStudente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
            String email = invocation.getArgument(0, String.class);
            Studente s = new Studente();
            s.setEmail(email);
            return s;
        });

        Studente s  = new Studente("paolobelli@gmail.com", "paoloprovapasword00@@");;
        assertEquals(s, userManager.trovaStudente("paolobelli@gmail.com"));
    }

    @Test
    public void trovaStudente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> userManager.trovaStudente("paolobelli@gmail.com"));
    }

    @Test
    public void bloccaStudente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class), "password123456!!"));
        assertTrue(userManager.bloccaStudente("paoloprova1@gmail.com", "marcoprova2@gmail.com"));
    }

    @Test
    public void bloccaStudente_nontrovati(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> userManager.bloccaStudente("paoloprova1@gmail.com", "marcoprova2@gmail.com"));
    }

    @Test
    public void bloccaStudente_giabloccato(){


        Mockito.when(utenteRepository.findByEmail("marcoprova1@gmail.com")).thenAnswer(invocation -> {
            Studente s = new Studente(invocation.getArgument(0, String.class), "password123456@");
            ArrayList<Studente> listaBloccati = new ArrayList<>();
            listaBloccati.add(new Studente("paoloprova2@gmail.com", "plutoplutopluto"));
            s.setListaBloccati(listaBloccati);
            return s;
        });
        Mockito.when(utenteRepository.findByEmail("paoloprova2@gmail.com")).thenReturn(new Studente("paoloprova2@gmail.com", "plutoplutopluto"));
        assertThrows(AlreadyExistException.class, () -> userManager.bloccaStudente("marcoprova1@gmail.com", "paoloprova2@gmail.com"));

    }

    @Test
    public void sbloccaStudente_valid(){
        Mockito.when(utenteRepository.findByEmail("marcoprova1@gmail.com")).thenAnswer(invocation -> {
            Studente s = new Studente(invocation.getArgument(0, String.class), "password123456@");
            ArrayList<Studente> listaBloccati = new ArrayList<>();
            listaBloccati.add(new Studente("paoloprova2@gmail.com", "plutoplutopluto"));
            s.setListaBloccati(listaBloccati);
            return s;
        });
        Mockito.when(utenteRepository.findByEmail("paoloprova2@gmail.com")).thenReturn(new Studente("paoloprova2@gmail.com", "plutoplutopluto"));

        assertTrue(userManager.sbloccaStudente("marcoprova1@gmail.com", "paoloprova2@gmail.com"));
    }

    @Test
    public void sbloccaStudente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.sbloccaStudente("marcoprova1@gmail.com", "paoloprova2@gmail.com"));
    }

    @Test
    public void isPresent_true(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class), "password123456!"));
        assertTrue(userManager.isPresent("marcoprova1@gmail.com"));
    }

    @Test
    public void isPresent_false(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertFalse(userManager.isPresent("marcoprova1@gmail.com"));
    }

    @Test
    public void getUtenteByVerificationToken_valid() {
        Mockito.when(verificationTokenRepository.findByToken(anyString())).thenAnswer(invocation -> {

            VerificationToken token = new VerificationToken();
            token.setToken(invocation.getArgument(0, String.class));
            Utente u = new Studente();
            u.setEmail("marcoprova1@gmail.com");
            token.setUtente(u);
            return token;

        });

        VerificationToken oracolo = new VerificationToken();
        oracolo.setToken("token");
        Utente u = new Studente();
        u.setEmail("marcoprova1@gmail.com");
        oracolo.setUtente(u);


        assertEquals(oracolo.getUtente().getEmail(), userManager.getUtenteByVerificationToken("token").getEmail());
    }

    @Test
    public void getUtenteByVerificationToken_nontrovatoToken() {
        Mockito.when(verificationTokenRepository.findByToken(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.getUtenteByVerificationToken("token"));
    }

    @Test
    public void getUtenteByVerificationToken_nontrovatoUtente() {
        Mockito.when(verificationTokenRepository.findByToken(anyString())).thenAnswer(invocation -> {

            VerificationToken token = new VerificationToken();
            token.setToken(invocation.getArgument(0, String.class));
            token.setUtente(null);
            return token;

        });

        assertThrows(EntityNotFoundException.class, () -> userManager.getUtenteByVerificationToken("token"));
    }

    @Test
    public void createVerificationToken_valid(){
        Mockito.when(verificationTokenRepository.save(any(VerificationToken.class))).thenAnswer( invocation -> {
            VerificationToken verificationToken = invocation.getArgument(0, VerificationToken.class);
            verificationToken.setId(1L);
            return verificationToken;
        });

        VerificationToken oracle = new VerificationToken();
        oracle.setId(1L);

        assertEquals(oracle, userManager.createVerificationToken(new Studente("marcoprova1@gmail.com","ciao"), "pluto"));
    }

    @Test
    public void deleteUtente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class),"iltestingdiunitaébello"));
        Utente oracolo = new Studente("paoloprova1@gmail.com","iltestingdiunitaébello");
        assertEquals(oracolo, userManager.deleteUtente("paoloprova1@gmail.com"));
    }

    @Test
    public void deleteUtente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.deleteUtente("paoloprova1@gmail.com"));
    }


    @Test
    public void attivaUtenteRegistrato_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(
                invocation -> new Studente(invocation.getArgument(0, String.class), "passwordbrutta!!")
        );

        assertTrue(userManager.attivaUtenteRegistrato("paolodaRegistrare@gmail.com").isActive());
    }

    @Test
    public void attivaUtenteRegistrato_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class , () -> userManager.attivaUtenteRegistrato("paolodaRegistrare@gmail.com"));
    }

    @Test
    public void findAllStudenti_valid(){
        Mockito.when(utenteRepository.findAll()).thenAnswer(invocation -> {
            List<Studente> lista = new ArrayList<>();
            lista.add(new Studente("sonolunicostudente@gmail.com", "passworddiunpoveraccio"));
            return lista;
        });

        List<Studente> oracolo = new ArrayList<>();
        oracolo.add(new Studente("sonolunicostudente@gmail.com", "passworddiunpoveraccio"));

        assertEquals(oracolo, userManager.findAllStudenti());
    }

    @Test
    public void cambiaPassword_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class), "passwordDaCambiare"));
        Utente passwordCambiata = userManager.cambiaPassword("marcopasswordDaCambiare", "plutoseipropriobello");
        assertTrue(passwordEncoder.matches("plutoseipropriobello", passwordCambiata.getPassword()));
    }

    @Test
    public void cambiaPassword_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.cambiaPassword("marcopasswordDaCambiare", "plutoseipropriobello"));
    }
    //testare find all studenti

    // testare cambia password
}
