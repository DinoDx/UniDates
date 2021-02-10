package com.unidates.Unidates.UniDates.Integration;

import com.unidates.Unidates.UniDates.Control.ModerationControl;
import com.unidates.Unidates.UniDates.DTOs.*;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;
import com.unidates.Unidates.UniDates.SecurityTestConfig;
import com.unidates.Unidates.UniDates.UniDatesApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})
public class TestModerationControl {

    @Autowired
    ModerationControl moderationControl;

    @Autowired
    UserManager userManager;


    @Test
    public void inviaSegnalazione_valid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);
        SegnalazioneDTO oracolo = new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli");
        oracolo.setFotoId(f.getId());

        assertEquals(oracolo.getFotoId(), moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli"), f.getId()).getFotoId());
    }

    @Test
    public void inviaSegnalazione_segnalazioneNotValid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.VIOLENZA, ""), f.getId()).getFotoId());
    }

    @Test
    public void inviaSegnalazione_idFotoNotValid(){
        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli"), null));
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void inviaSegnalazioneCommunityManager_valid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);
        SegnalazioneDTO oracolo = new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli");
        oracolo.setFotoId(f.getId());

        assertEquals(oracolo.getFotoId(), moderationControl.inviaSegnalazioneCommunityManager(new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli"), f.getId()).getFotoId());
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void inviaSegnalazioneCommunityManager_nonAutorizzato(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);

        assertThrows(NotAuthorizedException.class, () -> moderationControl.inviaSegnalazioneCommunityManager(new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli"), f.getId()).getFotoId());
    }

    @Test
    public void inviaSegnalazioneCommunityManager_segnalazioneNotValid() {
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSegnalazioneCommunityManager(new SegnalazioneDTO(Motivazione.VIOLENZA, ""), f.getId()).getFotoId());
    }

    @Test
    public void inviaSegnalazioneCommunityManager_idFotoNotValid(){
        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSegnalazioneCommunityManager(new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli"), null));
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void inviaAmmonimento_valid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(2);
        AmmonimentoDTO oracolo = new AmmonimentoDTO(Motivazione.VIOLENZA, "dettagli");
        oracolo.setEmailStudente("studenteprova1@gmail.com");
        oracolo.setEmailModeratore("moderatore@gmail.com");
        oracolo.setFoto(EntityToDto.toDTO(f));

        assertEquals(oracolo.getEmailStudente(), moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.VIOLENZA, "dettagli"), "moderatore@gmail.com", "studenteprova1@gmail.com", EntityToDto.toDTO(f)).getEmailStudente());
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void inviaAmmonimento_ammonimentoNotValid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(2);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.VIOLENZA, ""), "moderatore@gmail.com", "studenteprova1@gmail.com", EntityToDto.toDTO(f)));
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void inviaAmmonimento_nonAutorizzato(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(2);

        assertThrows(NotAuthorizedException.class, () -> moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.VIOLENZA, "dettagli"), "moderatore@gmail.com", "studenteprova1@gmail.com", EntityToDto.toDTO(f)));
    }

    @Test
    public void inviaAmmonimento_emailStudenteNotValid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(2);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.VIOLENZA, "dettagli"), "moderatore@gmail.com", "studenteprova1", EntityToDto.toDTO(f)));
    }

    @Test
    public void inviaAmmonimento_emailModeratoreNotValid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(2);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.VIOLENZA, "dettagli"), "moderatore", "studenteprova1@gmail.com", EntityToDto.toDTO(f)));
    }

    @Test
    public void inviaAmmonimento_emailsNotValid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(2);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.VIOLENZA, "dettagli"), "moderatore", "studenteprova1", EntityToDto.toDTO(f)));
    }

    @Test
    @WithUserDetails("communitymanager@gmail.com")
    public void inviaSospensione_valid(){
        SospensioneDTO oracolo = new SospensioneDTO(1, "dettagli");
        oracolo.setStudente("studenteprova1@gmail.com");

        assertEquals(oracolo.getStudente(), moderationControl.inviaSospensione(new SospensioneDTO(1, "dettagli"), "studenteprova1@gmail.com").getStudente());
    }

    @Test
    @WithUserDetails("communitymanager@gmail.com")
    public void inviaSospensione_sospensioneNotValid(){
        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSospensione(new SospensioneDTO(1, ""), "studenteprova1@gmail.com"));
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void inviaSospensione_nonAutorizzato(){
        assertThrows(NotAuthorizedException.class, () -> moderationControl.inviaSospensione(new SospensioneDTO(1, "dettagli"), "studenteprova1@gmail.com"));
    }

    @Test
    public void inviaSospensione_emailNotValid(){
        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSospensione(new SospensioneDTO(1, "dettagli"), "studenteprova1"));
    }

    @Test
    @WithUserDetails("moderatore@gmail.com")
    public void moderatoreInSessione(){
        Moderatore mod = (Moderatore) userManager.trovaUtente("moderatore@gmail.com");
        ModeratoreDTO oracolo = EntityToDto.toDTO(mod);

        assertEquals(oracolo.getEmail(), moderationControl.moderatoreInSessione().getEmail());
    }

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void moderatoreInSessione_notValid(){
        assertThrows(NotAuthorizedException.class, () -> moderationControl.moderatoreInSessione());
    }

}
