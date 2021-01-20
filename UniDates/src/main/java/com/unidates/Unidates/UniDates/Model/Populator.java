package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni.MatchService;
import com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione.ModerazioneService;
import com.unidates.Unidates.UniDates.Model.Service.GestioneUtenti.UtenteService;
import com.unidates.Unidates.UniDates.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class Populator implements ApplicationRunner {
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private ModerazioneService moderazioneService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Aggiungo 3 utenti
        Studente s1 = new Studente("studenteprova1@gmail.com","studenteprova1");
        s1.setActive(true);
        Studente s2 = new Studente("studenteprova2@gmail.com","studenteprova2");
        s2.setActive(true);
        Studente s3 = new Studente("studenteprova3@gmail.com", "studenteprova3");
        s3.setActive(true);

        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);
        ;


        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI, new Foto(Utils.downloadUrl("https://source.unsplash.com/random")) ,hobbyArrayList);
        p1.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p1.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p1.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        Profilo p2 = new Profilo("Paolo", "Prova2", "Napoli", "Napoli", LocalDate.of(1995,7,15), 185, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.ROSSI, Colore_Occhi.VERDI,new Foto(Utils.downloadUrl("https://source.unsplash.com/random")), hobbyArrayList);
        p2.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        Profilo p3 = new Profilo("Lucia", "Prova3", "Napoli", "Napoli", LocalDate.of(1991,1,25), 164, Sesso.DONNA, Interessi.ENTRAMBI, Colori_Capelli.CASTANI, Colore_Occhi.CASTANI,new Foto(Utils.downloadUrl("https://source.unsplash.com/random")), hobbyArrayList);
        p3.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));

        utenteService.registrazioneStudente(s1, p1);
        utenteService.registrazioneStudente(s2, p2);
        utenteService.registrazioneStudente(s3, p3);

        matchService.aggiungiMatch(s1, s2);
        matchService.aggiungiMatch(s2, s1);
        matchService.aggiungiMatch(s1, s3);
        matchService.aggiungiMatch(s3, s1);

        //Aggiungo un moderatore
        Moderatore m1 = new Moderatore("moderatore@gmail.com", "moderatore");
        Profilo p4 = new Profilo("Marcello", "Moderatore", "Napoli", "Napoli", LocalDate.of(1999,6,12), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.GRIGI, Colore_Occhi.AZZURRI,new Foto(Utils.downloadUrl("https://source.unsplash.com/random")),hobbyArrayList );
        p4.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));

        utenteService.registrazioneModeratore(m1, p4);

        //Aggiungo un communityManager

        CommunityManager cm1 = new CommunityManager("communitymanager@gmail.com","communitymanager");
        Profilo p5 = new Profilo("Francesca", "CM", "Napoli", "Napoli", LocalDate.of(1980,7,12), 170, Sesso.DONNA, Interessi.UOMINI, Colori_Capelli.CASTANI, Colore_Occhi.AZZURRI, new Foto(Utils.downloadUrl("https://source.unsplash.com/random")),hobbyArrayList);
        p5.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));

        utenteService.registrazioneCommunityManager(cm1, p5);


        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione1","dettagli1"), p1.getFotoProfilo());
        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione2","dettagli2"), p2.getFotoProfilo());
        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione3","dettagli3"), p3.getFotoProfilo());


    }

}
