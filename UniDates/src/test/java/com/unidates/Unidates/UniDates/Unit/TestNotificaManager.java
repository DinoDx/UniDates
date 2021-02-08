package com.unidates.Unidates.UniDates.Unit;

import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.NotificaManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
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
    public void generateNotificaMatch_valid(){

        Studente s= new Studente();
        s.setEmail("pippo");
        Profilo p= new Profilo();
        p.setNome("pippo");
        ArrayList<Foto> foto= new ArrayList<>();
        Foto f=new Foto();
        f.setFotoProfilo(true);
        foto.add(f);
        p.setListaFoto(foto);
        s.setProfilo(p);

        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(s);

        assertDoesNotThrow(() -> notificaManager.generateNotificaMatch("pippo", "pluto"));

    }

    @Test
    public void generateNotificaMatch_NotValid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.generateNotificaMatch("ciao","ciao"));
    }

    //controllare
    @Test
    public void eliminaNotificaMatch_valid() {
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente());
        Mockito.when(notificaRepository.findByUtenteAndEmailToMatchWith(any(Studente.class),anyString())).thenAnswer(invocation ->{
            return new Notifica();
        });
        assertDoesNotThrow(() -> notificaManager.eliminaNotificaMatch("pippo", "pluto"));

    }

    @Test
    public void eliminaNotificaMatch_NotValidUtente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.eliminaNotificaMatch("pippo", "pluto"));
    }


    @Test
    public void eliminaNotificaMatch_NotValidNotifica(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente());
        Mockito.when(notificaRepository.findByUtenteAndEmailToMatchWith(any(Utente.class),anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.eliminaNotificaMatch("pippo", "pluto"));
    }

    @Test
    public void generateNotificaWarning_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocation ->{
            Studente s= new Studente();
            Profilo p= new Profilo();
            p.setNome("pippo");
            s.setProfilo(p);
            return s;
        });
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(new Foto());
        Mockito.when(ammonimentiRepository.findByFoto(any(Foto.class))).thenReturn(new Ammonimento());
        assertDoesNotThrow(() -> notificaManager.genereateNotificaWarning("pippo",Long.valueOf(1L)));

    }

    @Test
    public void generateNotificaWarning_NotValidStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.genereateNotificaWarning("pippo", 1L));
    }

    @Test
    public void generateNotificaWarning_NotValidAmmonimento(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente());
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(new Foto());
        Mockito.when(ammonimentiRepository.findByFoto(any(Foto.class))).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.genereateNotificaWarning("pluto", 1L));
    }

    @Test
    public void generateNotificaWarning_NotValidFoto(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente());
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> notificaManager.genereateNotificaWarning("pluto", 2L));

    }


}
