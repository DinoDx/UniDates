package com.unidates.Unidates.UniDates.Unit;

import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.NotificaManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Repository.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.NotificaRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class TestNotificaManager {

    @Autowired
    NotificaManager notificaManager;

    @MockBean
    UtenteRepository utenteRepository;

    @MockBean
    NotificaRepository notificaRepository;

    @MockBean
    FotoRepository fotoRepository;

    @MockBean
    AmmonimentiRepository ammonimentiRepository;


    @Test
    public void generateNotificaMatch_valid1(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            Studente s= new Studente(invocationOnMock.getArgument(0, String.class), "password");

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);
            s.setProfilo(p);
            return s;
        });

        assertDoesNotThrow(() -> notificaManager.generateNotificaMatch("pippo@gmail.com", "pluto@gmail.com"));
    }


    @Test
    public void generateNotificaMatch_NotValid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.generateNotificaMatch("ciao@gmail.com","hello@gmail.com"));
    }


    @Test
    public void eliminaNotificaMatch_valid() {
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> new Studente(invocationOnMock.getArgument(0, String.class), "password"));

        Mockito.when(notificaRepository.findByUtenteAndEmailToMatchWith(any(Studente.class),anyString())).thenAnswer(invocationOnMock -> {
           byte[]img= {1,2,3};
            Foto s=new Foto(img);
            s.setId(1L);
            return new Notifica(invocationOnMock.getArgument(0, Studente.class), "testoNotifica", Tipo_Notifica.MATCH, s);
        });
        assertDoesNotThrow(() -> notificaManager.eliminaNotificaMatch("pippo@gmail.com", "pluto@gmail.com"));

    }

    @Test
    public void eliminaNotificaMatch_NotValidUtente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.eliminaNotificaMatch("pippo@gmail.com", "pluto@gmail.com"));
    }


    @Test
    public void eliminaNotificaMatch_NotValidNotifica(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            Studente s=new Studente(invocationOnMock.getArgument(0,String.class), "password");
            return s;
        });
        Mockito.when(notificaRepository.findByUtenteAndEmailToMatchWith(any(Utente.class),anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.eliminaNotificaMatch("pippo@gmail.com", "pluto@gmail.com"));
    }

    @Test
    public void generateNotificaWarning_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation -> {
            Studente s= new Studente(invocation.getArgument(0, String.class), "password");

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();

            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList);
            s.setProfilo(p);
            return s;

        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[]img= {1,2,3};
            Foto f=new Foto(img);
            f.setId(invocationOnMock.getArgument(0, Long.class));
            return f;
        });

        Mockito.when(ammonimentiRepository.findByFoto(any(Foto.class))).thenAnswer(invocationOnMock -> {
            Ammonimento a= new Ammonimento(Motivazione.VIOLENZA, "messaggio");
            a.setFoto(invocationOnMock.getArgument(0, Foto.class));
            return a;
        });
        assertDoesNotThrow(() -> notificaManager.genereateNotificaWarning("pippo@gmail.com", 1L));

    }

    @Test
    public void generateNotificaWarning_NotValidStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.genereateNotificaWarning("pippo@gmail.com", 1L));
    }

    @Test
    public void generateNotificaWarning_NotValidAmmonimento(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> new Studente(invocationOnMock.getArgument(0,String.class), "password"));

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
        byte[]img= {1,2,3};
        Foto f=new Foto(img);
        f.setId(invocationOnMock.getArgument(0, Long.class));
        return f;
        });

        Mockito.when(ammonimentiRepository.findByFoto(any(Foto.class))).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.genereateNotificaWarning("pluto@gmail.com", 1L));
    }

    @Test
    public void generateNotificaWarning_NotValidFoto(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> new Studente(invocationOnMock.getArgument(0, String.class), "password"));

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.genereateNotificaWarning("pluto@gmail.com", 2L));

    }


}
