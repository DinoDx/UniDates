package com.unidates.Unidates.UniDates.Unit;

import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Model.Entity.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazione;
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
        Mockito.when(segnalazioniRepository.findByModeratoreAndFoto(any(Moderatore.class), any(Foto.class))).thenReturn(null);


        assertEquals(new Segnalazione(Motivazione.VIOLENZA, "prova"), moderazioneManager.inviaSegnalazioneCommunityManager(new Segnalazione(Motivazione.VIOLENZA, "prova"), 1L));

    }

}













