package com.unidates.Unidates.UniDates.Unit;


import com.helger.commons.system.ENewLineMode;
import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class TestUserManager {

    @Autowired
    UserManager userManager;

    @MockBean
    UtenteRepository utenteRepository;

    @MockBean
    VerificationTokenRepository verificationTokenRepository;

    @Test
    public void registrazioneStudente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertEquals(new Studente("email", "password"), userManager.registrazioneStudente(new Studente("email", "password"), new Profilo()));
    }

    @Test
    public void registrazioneStudente_esiste(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente());
        assertThrows(AlreadyExistException.class, () -> userManager.registrazioneStudente(new Studente("ciao", "ciao"), new Profilo()));
    }

    @Test
    public void registrazioneModeratore_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertEquals( new Moderatore("ciao", "ciao"),userManager.registrazioneModeratore(new Moderatore("ciao", "ciao"), new Profilo()));
    }

    @Test
    public void registrazioneModeratore_esiste(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Moderatore());
        assertThrows(AlreadyExistException.class, () -> userManager.registrazioneModeratore(new Moderatore("ciao", "ciao"), new Profilo()));
    }

    @Test
    public void trovaUtente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
            String email = invocation.getArgument(0, String.class);
            Utente u = new Studente();
            u.setEmail(email);
            return u;
        });

        Utente u = new Studente();
        u.setEmail("prova");
        assertEquals(u, userManager.trovaUtente("prova"));
    }

    @Test
    public void trovaUtente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.trovaUtente("pippo"));
    }

    @Test
    public void trovaStudente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
            String email = invocation.getArgument(0, String.class);
            Studente s = new Studente();
            s.setEmail(email);
            return s;
        });

        Studente s  = new Studente();
        s.setEmail("pippo");
        assertEquals(s, userManager.trovaStudente("pippo"));
    }

    @Test
    public void trovaStudente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.trovaStudente("pippo"));
    }

    @Test
    public void bloccaStudente_valid(){
        Mockito.when(utenteRepository.findByEmail("pippo")).thenReturn(new Studente("pippo", "pippo"));
        Mockito.when(utenteRepository.findByEmail("pluto")).thenReturn(new Studente("pluto", "pluto"));
        assertTrue(userManager.bloccaStudente("pippo", "pluto"));
    }

    @Test
    public void bloccaStudente_nontrovati(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> userManager.bloccaStudente("pippo", "pluto"));
    }

    @Test
    public void bloccaStudente_giabloccato(){
        Studente s = new Studente("pippo", "pippo");
        ArrayList<Studente> listaBloccati = new ArrayList<>();
        listaBloccati.add(new Studente("pluto", "pluto"));
        s.setListaBloccati(listaBloccati);

        Mockito.when(utenteRepository.findByEmail("pippo")).thenReturn(s);
        Mockito.when(utenteRepository.findByEmail("pluto")).thenReturn(new Studente("pluto", "pluto"));
        assertThrows(AlreadyExistException.class, () -> userManager.bloccaStudente("pippo", "pluto"));
    }

    @Test
    public void sbloccaStudente_valid(){
        Studente s = new Studente("pippo", "pippo");
        ArrayList<Studente> listaBloccati = new ArrayList<>();
        listaBloccati.add(new Studente("pluto", "pluto"));
        s.setListaBloccati(listaBloccati);

        Mockito.when(utenteRepository.findByEmail("pippo")).thenReturn(s);
        Mockito.when(utenteRepository.findByEmail("pluto")).thenReturn(new Studente("pluto", "pluto"));
        assertTrue(userManager.sbloccaStudente("pippo", "pluto"));
    }

    @Test
    public void sbloccaStudente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.sbloccaStudente("pippo", "pluto"));
    }

    @Test
    public void isPresent_true(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente());
        assertTrue(userManager.isPresent("pluto"));
    }

    @Test
    public void isPresent_false(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertFalse(userManager.isPresent("pluto"));
    }

    @Test
    public void getUtenteByVerificationToken_valid() {
        Mockito.when(verificationTokenRepository.findByToken(anyString())).thenAnswer(invocation -> {

            VerificationToken token = new VerificationToken();
            token.setToken(invocation.getArgument(0, String.class));
            Utente u = new Studente();
            u.setEmail("pippo");
            token.setUtente(u);
            return token;

        });

        VerificationToken token = new VerificationToken();
        token.setToken("token");
        Utente u = new Studente();
        u.setEmail("pippo");
        token.setUtente(u);


        assertEquals(token.getUtente().getEmail(), userManager.getUtenteByVerificationToken("pippo").getEmail());
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

        assertEquals(oracle, userManager.createVerificationToken(new Studente(), "pluto"));
    }

    @Test
    public void deleteUtente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
            Utente u = new Studente();
            u.setEmail(invocation.getArgument(0, String.class));
            return u;
        });

        Utente u = new Studente();
        u.setEmail("pippo");

        assertEquals(u, userManager.deleteUtente("pippo"));
    }

    @Test
    public void deleteUtente_nontrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> userManager.deleteUtente("pippo"));
    }

    // testare attiva utente registrato

    //testare find all studenti

    // testare cambia password
}
