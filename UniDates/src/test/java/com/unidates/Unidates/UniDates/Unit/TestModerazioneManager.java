package com.unidates.Unidates.UniDates.Unit;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class TestModerazioneManager {

    @Autowired
    ModerazioneManager moderazioneManager;

    @MockBean
    UtenteRepository utenteRepository;

    @MockBean
    SegnalazioniRepository segnalazioniRepository;

    @MockBean
    AmmonimentiRepository ammonimentiRepository;

    @MockBean
    SospensioniRepository sospensioniRepository;

    @MockBean
    FotoRepository fotoRepository;

    @Test
    public void inviaSegnalazione_valid(){
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.MODERATORE)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> mods = new ArrayList<>();
            Moderatore mod = new Moderatore("email", "password");
            mods.add(mod);
            return mods;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(new Foto());
        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenReturn(null);
        assertEquals(new Segnalazione(Motivazione.VIOLENZA, "prova"), moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.VIOLENZA, "prova"), 1L));
    }

    @Test
    public void inviaSegnalazione_fotoNonTrovata() {
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.MODERATORE)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> mods = new ArrayList<>();
            Moderatore mod = new Moderatore("email", "password");
            mods.add(mod);
            return mods;
        });

        Mockito.when(fotoRepository.findFotoById(1L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> moderazioneManager.inviaSegnalazione(new Segnalazione(), 1L));
    }

    @Test
    public void inviaSegnalazione_giàSegnalata() {
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.MODERATORE)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> mods = new ArrayList<>();
            Moderatore mod = new Moderatore("email", "password");
            mods.add(mod);
            return mods;
        });
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(new Foto());
        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenReturn(new Segnalazione());
        assertThrows(AlreadyExistException.class, () -> moderazioneManager.inviaSegnalazione(new Segnalazione(), 1L));
    }

    @Test
    public void inviaSegnalazioneCommunityManager_valid(){
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> cms = new ArrayList<>();
            Moderatore cm = new CommunityManager("email", "password");
            cms.add(cm);
            return cms;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(new Foto());
        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenReturn(null);
        assertEquals(new Segnalazione(Motivazione.VIOLENZA, "prova"), moderazioneManager.inviaSegnalazioneCommunityManager(new Segnalazione(Motivazione.VIOLENZA, "prova"), 1L));
    }

    @Test
    public void inviaSegnalazioneCommunityManager_fotoNonTrovata(){
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> cms = new ArrayList<>();
            Moderatore cm = new CommunityManager("email", "password");
            cms.add(cm);
            return cms;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> moderazioneManager.inviaSegnalazioneCommunityManager(new Segnalazione(), 1L));
    }

    @Test
    public void inviaSegnalazioneCommunityManager_giàSegnalata(){
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> cms = new ArrayList<>();
            Moderatore cm = new CommunityManager("email", "password");
            cms.add(cm);
            return cms;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(new Foto());
        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenReturn(new Segnalazione());
        assertThrows(AlreadyExistException.class, () -> moderazioneManager.inviaSegnalazioneCommunityManager(new Segnalazione(), 1L));
    }

    @Test
    public void inviaAmmonimento_valid(){
        Mockito.when(utenteRepository.findByEmail("provastudente")).thenAnswer(invocationOnMock -> {
            Studente s = new Studente();
            Foto f = new Foto();
            f.setId(2L);
            ArrayList<Ammonimento> ammonimenti = new ArrayList<>();
            Ammonimento a = new Ammonimento(Motivazione.VIOLENZA, "dettagli");
            a.setFoto(f);
            ammonimenti.add(a);
            s.setListaAmmonimenti(ammonimenti);
            return s;
        });
        Mockito.when(utenteRepository.findByEmail("provamod")).thenReturn(new Moderatore());
        Mockito.when(fotoRepository.findFotoById(1L)).thenAnswer(invocationOnMock -> {
            Foto foto = new Foto();
            foto.setId(1L);
            return foto;
        });

        Ammonimento b = new Ammonimento();
        Foto foto = new Foto();
        foto.setId(1L);
        b.setFoto(foto);

        assertEquals(b, moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.VIOLENZA, "dettagli"), "provamod", "provastudente",1L));

    }

    @Test
    public void inviaAmmonimento_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.VIOLENZA, "prova"), "provamod", "provastudente",1L));
    }

    @Test
    public void inviaAmmonimento_giàAmmonito(){
        Studente s = new Studente();
        Foto f = new Foto();
        f.setId(1L);
        ArrayList<Ammonimento> ammonimenti = new ArrayList<>();
        Ammonimento a = new Ammonimento(Motivazione.VIOLENZA, "dettagli");
        a.setFoto(f);
        ammonimenti.add(a);
        s.setListaAmmonimenti(ammonimenti);

        Mockito.when(utenteRepository.findByEmail("provastudente")).thenReturn(s);
        Mockito.when(utenteRepository.findByEmail("provamod")).thenReturn(new Moderatore());
        Mockito.when(fotoRepository.findFotoById(1L)).thenAnswer(invocationOnMock -> {
            Foto foto = new Foto();
            foto.setId(1L);
            return foto;
        });

        assertThrows(AlreadyExistException.class, () -> moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.VIOLENZA, "prova"), "provamod", "provastudente",1L));
    }

    @Test
    public void inviaSospensione_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente());

        assertEquals(new Sospensione(1, "prova"), moderazioneManager.inviaSospensione(new Sospensione(1, "prova"), "prova"));

    }

    @Test
    public void inviaSospensione_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> moderazioneManager.inviaSospensione(new Sospensione(1, "prova"), "prova"));
    }

    @Test
    public void nascondiFoto_valid(){
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(new Foto());
        assertTrue(moderazioneManager.nascondiFoto(1L));
    }

    @Test
    public void nascondiFoto_fotoNonTrovata(){
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, ()-> moderazioneManager.nascondiFoto(1L));
    }

    @Test
    public void checkAmmonimentiStudente_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            Studente s = new Studente();
            ArrayList<Ammonimento> ammonimenti = new ArrayList<>();
            Ammonimento a1 = new Ammonimento();
            Ammonimento a2 = new Ammonimento();
            Ammonimento a3 = new Ammonimento();
            ammonimenti.add(a1);
            ammonimenti.add(a2);
            ammonimenti.add(a3);
            s.setListaAmmonimenti(ammonimenti);
            return s;
        });

        assertTrue(moderazioneManager.checkAmmonimentiStudente("prova"));

    }

    @Test
    public void checkAmmonimentiStudente_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, ()-> moderazioneManager.checkAmmonimentiStudente("prova"));
    }
}

