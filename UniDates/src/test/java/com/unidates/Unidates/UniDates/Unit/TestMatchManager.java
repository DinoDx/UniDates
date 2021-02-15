package com.unidates.Unidates.UniDates.Unit;


import com.helger.commons.system.ENewLineMode;
import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.MatchManager;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Repository.MatchRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@ActiveProfiles("test")
@SpringBootTest
public class TestMatchManager {

    @Autowired
    MatchManager matchManager;

    @MockBean
    UtenteRepository utenteRepository;

    @MockBean
    MatchRepository matchRepository;


    @Test
    public void aggiungiMatch_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
           return new Studente(invocation.getArgument(0, String.class), "questoétestingblackbox?");
        });
        assertTrue(matchManager.aggiungiMatch("studenteprova1@gmail.com","studenteprova2@gmail.com"));
    }

    @Test
    public void aggiungiMatch_utenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> matchManager.aggiungiMatch("email1bella@gmail.com","email2bella@gmail.com"));
    }

    @Test
    public void trovaMatch_valid(){

        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class), "password"));
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(any(Studente.class), any(Studente.class))).thenAnswer(invocation -> {
            Match match = new Match();
            match.setStudente1(invocation.getArgument(0, Studente.class));
            match.setStudente2(invocation.getArgument(1, Studente.class));
            return match;
        });

        Match trovato = matchManager.trovaMatch("paoloprova1@gmail.com", "marcobello1@gmail.com");
        assertEquals(trovato.getStudente1().getEmail(),"paoloprova1@gmail.com");
        assertEquals(trovato.getStudente2().getEmail(),"marcobello1@gmail.com");
    }

    @Test
    public void trovaMatch_matchNonTrovato(){

        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
            Studente s = new Studente(invocation.getArgument(0, String.class), "paassword");
            return s;
        });
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(any(Studente.class),any(Studente.class))).thenReturn(null);

        assertThrows(EntityNotFoundException.class,()-> matchManager.trovaMatch("prova1@gmail.com","prova2@gmail.com"));
    }

    @Test
    public void trovaMatch_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class,()-> matchManager.trovaMatch("prova1@gmail.com","prova2@gmail.com"));
    }

    @Test
    public void isValidMatch_true(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class), "password"));

        Mockito.when(matchRepository.findAllByStudente1AndStudente2(any(Studente.class), any(Studente.class))).thenAnswer(invocation -> {
            Match match = new Match();
            match.setStudente1(invocation.getArgument(0, Studente.class));
            match.setStudente1(invocation.getArgument(1, Studente.class));
            match.setLikedByStudent1(true);
            match.setLikeByStudent2(true);
            return match;
        });

        assertTrue(matchManager.isValidMatch("antoniobestfrontendeveloper@gmail.com","dinonondorme@gmail.com"));

    }

    @Test
    public void isValidMatch_false(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class), "password"));

        Mockito.when(matchRepository.findAllByStudente1AndStudente2(any(Studente.class), any(Studente.class))).thenAnswer(invocation -> {
            Match match = new Match();
            match.setStudente1(invocation.getArgument(0, Studente.class));
            match.setStudente1(invocation.getArgument(1, Studente.class));
            match.setLikedByStudent1(true);
            match.setLikeByStudent2(false);
            return match;
        });

        assertFalse(matchManager.isValidMatch("antoniobestfrontendeveloper@gmail.com","dinonondorme@gmail.com"));

    }

    @Test
    public void isValidMatch_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class,()-> matchManager.isValidMatch("épropriounabellaserata@live.it","macistailcovid@gmail.com"));
    }


    //da controlalre
    @Test
    public void eliminaMacth_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> new Studente(invocation.getArgument(0, String.class), "password"));
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(any(Studente.class), any(Studente.class))).thenAnswer(invocation -> {
            Match match = new Match();
            match.setStudente1(invocation.getArgument(0, Studente.class));
            match.setStudente1(invocation.getArgument(1, Studente.class));
            return match;
        });

        assertTrue(matchManager.eliminaMatch("copio@gmail.com","eincollo@live.it"));
    }

    @Test
    public void eliminaMatch_nonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class,()-> matchManager.eliminaMatch("épropriounabellaserata@live.it","macistailcovid@gmail.com"));
    }


}
