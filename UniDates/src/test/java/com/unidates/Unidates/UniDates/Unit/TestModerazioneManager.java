package com.unidates.Unidates.UniDates.Unit;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Repository.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

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
            Moderatore mod = new Moderatore("email@gmail.com", "password");
            mods.add(mod);
            return mods;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto f = new Foto(img);
            f.setId(invocationOnMock.getArgument(0, Long.class));
            Studente s = new Studente("emailS@gmail.com", "password");
            Profilo p = new Profilo();
            p.setStudente(s);
            f.setProfilo(p);
            return f;
        });

        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenReturn(null);

        Mockito.when(segnalazioniRepository.save(any(Segnalazione.class))).thenAnswer(invocation -> {
            Segnalazione s = invocation.getArgument(0, Segnalazione.class);
            s.setId(1L);
            return s;
        });

        Segnalazione oracolo = new Segnalazione(Motivazione.VIOLENZA, "dettagli");
        byte[] img = {1,2,3};
        Foto f = new Foto(img);
        f.setId(1L);
        oracolo.setFoto(f);

        assertEquals(oracolo.getFoto().getId(), moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.VIOLENZA, "prova"), 1L).getFoto().getId());
    }

    @Test
    public void inviaSegnalazione_fotoNonTrovata() {
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.MODERATORE)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> mods = new ArrayList<>();
            Moderatore mod = new Moderatore("email@gmail.com", "password");
            mods.add(mod);
            return mods;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> moderazioneManager.inviaSegnalazione(new Segnalazione(), 1L));
    }

    @Test
    public void inviaSegnalazione_giàSegnalata() {
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.MODERATORE)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> mods = new ArrayList<>();
            Moderatore mod = new Moderatore("email@gmail.com", "password");
            mods.add(mod);
            return mods;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto f = new Foto(img);
            f.setId(invocationOnMock.getArgument(0, Long.class));
            Studente s = new Studente("emailS@gmail.com", "password");
            Profilo p = new Profilo();
            p.setStudente(s);
            f.setProfilo(p);
            return f;
        });

        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenAnswer(invocationOnMock -> {
            Segnalazione oracolo = new Segnalazione(Motivazione.VIOLENZA, "dettagli");
            byte[] img = {1,2,3};
            Foto f = new Foto(img);
            f.setId(1L);
            oracolo.setFoto(f);
            return oracolo;
        });

        assertThrows(AlreadyExistException.class, () -> moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.VIOLENZA, "dettagli"), 1L));
    }

    @Test
    public void inviaSegnalazioneCommunityManager_valid(){
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> cms = new ArrayList<>();
            Moderatore cm = new CommunityManager("email@gmail.com", "password");
            cms.add(cm);
            return cms;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto f = new Foto(img);
            f.setId(invocationOnMock.getArgument(0, Long.class));
            Studente s = new Studente("emailS@gmail.com", "password");
            Profilo p = new Profilo();
            p.setStudente(s);
            f.setProfilo(p);
            return f;
        });

        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenReturn(null);

        Mockito.when(segnalazioniRepository.save(any(Segnalazione.class))).thenAnswer(invocation -> {
            Segnalazione s = invocation.getArgument(0, Segnalazione.class);
            s.setId(1L);
            return s;
        });

        Segnalazione oracolo = new Segnalazione(Motivazione.VIOLENZA, "dettagli");
        byte[] img = {1,2,3};
        Foto f = new Foto(img);
        f.setId(1L);
        oracolo.setFoto(f);

        assertEquals(oracolo.getFoto().getId(), moderazioneManager.inviaSegnalazioneCommunityManager(new Segnalazione(Motivazione.VIOLENZA, "prova"), 1L).getFoto().getId());
    }

    @Test
    public void inviaSegnalazioneCommunityManager_fotoNonTrovata(){
        Mockito.when(utenteRepository.findAllByRuolo(Ruolo.COMMUNITY_MANAGER)).thenAnswer(invocationOnMock -> {
            ArrayList<Moderatore> cms = new ArrayList<>();
            Moderatore cm = new CommunityManager("email@gmail.com", "password");
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

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto f = new Foto(img);
            f.setId(invocationOnMock.getArgument(0, Long.class));
            Studente s = new Studente("emailS@gmail.com", "password");
            Profilo p = new Profilo();
            p.setStudente(s);
            f.setProfilo(p);
            return f;
        });

        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenAnswer(invocationOnMock -> {
            Segnalazione oracolo = new Segnalazione(Motivazione.VIOLENZA, "dettagli");
            byte[] img = {1,2,3};
            Foto f = new Foto(img);
            f.setId(1L);
            oracolo.setFoto(f);
            return oracolo;
        });

        assertThrows(AlreadyExistException.class, () -> moderazioneManager.inviaSegnalazioneCommunityManager(new Segnalazione(Motivazione.VIOLENZA, "dettagli"), 1L));
    }

    @Test
    public void inviaAmmonimento_valid(){
        Mockito.when(utenteRepository.findByEmail("studente@gmail.com")).thenReturn(new Studente("studente@gmail.com", "password"));

        Mockito.when(utenteRepository.findByEmail("mod@gmail.com")).thenReturn(new Moderatore("mod@gmail.com", "password"));

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setId(1L);
            return foto;
        });

        Ammonimento oracolo = new Ammonimento(Motivazione.VIOLENZA, "dettagli");
        byte[] img = {1,2,3};
        Foto foto = new Foto(img);
        foto.setId(1L);
        oracolo.setFoto(foto);

        Mockito.when(ammonimentiRepository.save(any(Ammonimento.class))).thenAnswer(invocation -> {
            Ammonimento s = invocation.getArgument(0, Ammonimento.class);
            //s.setId(1L);
            return s;
        });

        assertEquals(oracolo.getFoto().getId(), moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.VIOLENZA, "dettagli"), "mod@gmail.com", "studente@gmail.com",1L).getFoto().getId());

    }

    @Test
    public void inviaAmmonimento_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.VIOLENZA, "dettagli"), "mod@gmail.com", "studente@gmail.com",1L));
    }

    @Test
    public void inviaAmmonimento_giàAmmonito(){
        Mockito.when(utenteRepository.findByEmail("studente@gmail.com")).thenAnswer(invocationOnMock -> {
            Studente s = new Studente();
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setId(1L);
            ArrayList<Ammonimento> ammonimenti = new ArrayList<>();
            Ammonimento a = new Ammonimento(Motivazione.VIOLENZA, "dettagli");
            a.setFoto(foto);
            ammonimenti.add(a);
            s.setListaAmmonimenti(ammonimenti);
            return s;
        });

        Mockito.when(utenteRepository.findByEmail("mod@gmail.com")).thenReturn(new Moderatore());

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setId(1L);
            return foto;
        });

        assertThrows(AlreadyExistException.class, () -> moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.VIOLENZA, "dettagli"), "mod@gmail.com", "studente@gmail.com",1L));
    }

    @Test
    public void inviaSospensione_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(new Studente("email@gmail.com", "password"));

        Sospensione oracolo = new Sospensione(1, "prova");
        oracolo.setStudente(new Studente("email@gmail.com", "password"));

        Mockito.when(sospensioniRepository.save(any(Sospensione.class))).thenAnswer(invocation -> {
            Sospensione s = invocation.getArgument(0, Sospensione.class);
            s.setId(1L);
            return s;
        });

        assertEquals(oracolo.getStudente(), moderazioneManager.inviaSospensione(new Sospensione(1, "prova"), "email@gmail.com").getStudente());

    }

    @Test
    public void inviaSospensione_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> moderazioneManager.inviaSospensione(new Sospensione(1, "prova"), "email@gmail.com"));
    }

    @Test
    public void nascondiFoto_valid(){
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setId(1L);
            return foto;
        });

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

        assertTrue(moderazioneManager.checkAmmonimentiStudente("prova@gmail.com"));

    }

    @Test
    public void checkAmmonimentiStudente_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, ()-> moderazioneManager.checkAmmonimentiStudente("prova@gmail.com"));
    }
}

