package com.unidates.Unidates.UniDates.Integration;

import com.unidates.Unidates.UniDates.Control.ModerationControl;
import com.unidates.Unidates.UniDates.DTOs.*;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Exception.NotAuthorizedException;
import com.unidates.Unidates.UniDates.Manager.MatchManager;
import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Manager.NotificaManager;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.SecurityTestConfig;
import com.unidates.Unidates.UniDates.UniDatesApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ActiveProfiles("test")
@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestModerationControl {

    @Autowired
    ModerationControl moderationControl;

    @Autowired
    UserManager userManager;

    @Autowired
    NotificaManager notificaManager;

    @Autowired
    ModerazioneManager moderazioneManager;

    @Autowired
    MatchManager matchManager;



    @Test
    public void inviaSegnalazione_valid(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);
        SegnalazioneDTO oracolo = new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli");
        oracolo.setFotoId(f.getId());

        assertEquals(oracolo.getFotoId(), moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli"), f.getId()).getFotoId());
    }

    @Test
    public void inviaSegnalazione_formatoSegnalazioneNonValido(){
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.VIOLENZA, ""), f.getId()).getFotoId());
    }

    @Test
    public void inviaSegnalazione_idFotoNonValido(){
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
    public void inviaSegnalazioneCommunityManager_formatoSegnalazioneNonValido() {
        Studente stud = userManager.trovaStudente("studenteprova1@gmail.com");
        Foto f = userManager.trovaStudente(stud.getEmail()).getProfilo().getListaFoto().get(1);

        assertThrows(InvalidFormatException.class, () -> moderationControl.inviaSegnalazioneCommunityManager(new SegnalazioneDTO(Motivazione.VIOLENZA, ""), f.getId()).getFotoId());
    }

    @Test
    public void inviaSegnalazioneCommunityManager_idFotoNonValido(){
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

    @BeforeEach
    public void populateDB(){
        Studente s1 = new Studente("studenteprova1@gmail.com","studenteprova1");
        s1.setActive(true);
        Studente s2 = new Studente("studenteprova2@gmail.com","studenteprova2");
        s2.setActive(true);
        Studente s3 = new Studente("studenteprova3@gmail.com", "studenteprova3");
        s3.setActive(false);

        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        byte[] img = {1,2,3};

        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(img) ,hobbyArrayList);
        p1.setNumeroTelefono("3333339900");
        p1.setNickInstagram("marco.prova1");
        p1.addFoto(new Foto(img),false);
        p1.addFoto(new Foto(img), false);
        p1.addFoto(new Foto(img), false);
        Profilo p2 = new Profilo("Paolo", "Prova2", "Napoli", "Napoli", LocalDate.of(1995,7,15), 185, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.ROSSI, Colore_Occhi.VERDI,new Foto(img), hobbyArrayList);
        p2.setNumeroTelefono("3335559900");
        p2.setNickInstagram("PaoloSonoBello.prova2");
        p2.addFoto(new Foto(img), false);
        p2.addFoto(new Foto(img), false);
        p2.addFoto(new Foto(img),false);
        Profilo p3 = new Profilo("Lucia", "Prova3", "Napoli", "Napoli", LocalDate.of(1991,1,25), 164, Sesso.DONNA, Interessi.ENTRAMBI, Colori_Capelli.CASTANI, Colore_Occhi.CASTANI,new Foto(img), hobbyArrayList);
        p3.setNickInstagram("SimpyLucia");
        p3.addFoto(new Foto(img),false);
        p3.addFoto(new Foto(img), false);
        p3.addFoto(new Foto(img), false);




        //Aggiungo un moderatore
        Moderatore m1 = new Moderatore("moderatore@gmail.com", "moderatore");
        Profilo p4 = new Profilo("Marcello", "Moderatore", "Napoli", "Napoli", LocalDate.of(1999,6,12), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.GRIGI, Colore_Occhi.AZZURRI,new Foto(img),hobbyArrayList );
        p4.addFoto(new Foto(img), false);
        p4.addFoto(new Foto(img), false);
        p4.addFoto(new Foto(img), false);




        //Aggiungo un communityManager

        CommunityManager cm1 = new CommunityManager("communitymanager@gmail.com","communitymanager");
        Profilo p5 = new Profilo("Francesca", "CM", "Napoli", "Napoli", LocalDate.of(1980,7,12), 170, Sesso.DONNA, Interessi.UOMINI, Colori_Capelli.CASTANI, Colore_Occhi.AZZURRI, new Foto(img),hobbyArrayList);
        p5.addFoto(new Foto(img), false);
        p5.addFoto(new Foto(img), false);
        p5.addFoto(new Foto(img), false);

        userManager.registrazioneStudente(s1, p1); //usati solo per skippare l'invio email di conferma
        userManager.registrazioneStudente(s2, p2);
        userManager.registrazioneStudente(s3, p3);

        userManager.registrazioneCommunityManager(cm1, p5);
        userManager.registrazioneModeratore(m1, p4);




        matchManager.aggiungiMatch(s1.getEmail(), s2.getEmail());
        matchManager.aggiungiMatch(s2.getEmail(), s1.getEmail());
        notificaManager.generateNotificaMatch(s1.getEmail(), s2.getEmail());
        matchManager.aggiungiMatch(s1.getEmail(), s3.getEmail());
        matchManager.aggiungiMatch(s3.getEmail(), s1.getEmail());
        notificaManager.generateNotificaMatch(s1.getEmail(), s3.getEmail());

        moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli1"), userManager.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.NUDITA, "dettagli2"), userManager.trovaStudente(s2.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.VIOLENZA , "dettagli3"), userManager.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.NUDITA, "dettagli5"), userManager.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(1).getId());
        moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.VIOLENZA, "dettagli6"), userManager.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(2).getId());

        moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli1"), m1.getEmail(), s1.getEmail(), userManager.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
        notificaManager.genereateNotificaWarning(s1.getEmail(), userManager.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.SPAM, "dettagli3"), m1.getEmail(), s3.getEmail(), userManager.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId());
        notificaManager.genereateNotificaWarning(s3.getEmail(), userManager.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId());
    }
}
