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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


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
        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(new Studente());
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(new Studente());
        assertTrue(matchManager.aggiungiMatch("prova1","prova2"));
    }

    @Test
    public void aggiungiMatch_utenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> matchManager.aggiungiMatch("email1","email2"));
    }

    @Test
    public void trovaMatch_valid(){
        Studente prova1 = new Studente();
        Studente prova2 = new Studente();
        prova1.setEmail("prova1");
        prova2.setEmail("prova2");

        Match match = new Match(prova1,prova2);

        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(prova1);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(prova2);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova1,prova2)).thenReturn(match);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova2,prova1)).thenReturn(match);

        assertEquals(match,matchManager.trovaMatch("prova1","prova2"));
    }

    @Test
    public void trovaMatch_matchNonTrovato(){
        Studente prova1 = new Studente();
        Studente prova2 = new Studente();
        prova1.setEmail("prova1");
        prova2.setEmail("prova2");

        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(prova1);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(prova2);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova1,prova2)).thenReturn(null);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova2,prova1)).thenReturn(null);

        assertThrows(EntityNotFoundException.class,()-> matchManager.trovaMatch("prova1","prova2"));
    }

    @Test
    public void trovaMatch_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(null);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(null);

        assertThrows(EntityNotFoundException.class,()-> matchManager.trovaMatch("prova1","prova2"));
    }

    @Test
    public void isValidMatch_valid(){
        Studente prova1 = new Studente();
        Studente prova2 = new Studente();
        prova1.setEmail("prova1");
        prova2.setEmail("prova2");

        Match match = new Match(prova1,prova2);
        match.setLikedByStudent1(true);
        match.setLikeByStudent2(true);

        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(prova1);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(prova2);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova1,prova2)).thenReturn(match);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova2,prova1)).thenReturn(match);

        assertTrue(matchManager.isValidMatch("prova1","prova2"));

    }

    @Test
    public void isValidMatch_nonTrovatoMatch(){
        Studente prova1 = new Studente();
        Studente prova2 = new Studente();
        prova1.setEmail("prova1");
        prova2.setEmail("prova2");

        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(prova1);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(prova2);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova1,prova2)).thenReturn(null);
        Mockito.when(matchRepository.findAllByStudente1AndStudente2(prova2,prova1)).thenReturn(null);

        assertThrows(EntityNotFoundException.class,()-> matchManager.isValidMatch("prova1","prova2"));
    }

    @Test
    public void isValidMatch_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(null);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(null);

        assertThrows(EntityNotFoundException.class,()-> matchManager.isValidMatch("prova1","prova2"));
    }


    //da controlalre
    @Test
    public void eliminaMacth_valid(){
        Studente prova1 = new Studente();
        Studente prova2 = new Studente();
        prova1.setEmail("prova1");
        prova2.setEmail("prova2");

        Match matchuno = new Match(prova1,prova2);
        Match matchdue = new Match(prova2,prova1);

        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(prova1);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(prova2);
        assertTrue(matchManager.eliminaMatch("prova1","prova2"));
    }

    @Test
    public void eliminaMatch_notValid(){
        Mockito.when(utenteRepository.findByEmail("prova1")).thenReturn(null);
        Mockito.when(utenteRepository.findByEmail("prova2")).thenReturn(null);
        assertThrows(EntityNotFoundException.class,()-> matchManager.eliminaMatch("prova1","prova2"));
    }


}
