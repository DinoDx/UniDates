package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Service.MatchService;
import com.unidates.Unidates.UniDates.Service.ModerazioneService;
import com.unidates.Unidates.UniDates.Service.UtenteService;
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


        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(Utils.downloadUrl("https://source.unsplash.com/random")) ,hobbyArrayList);
        p1.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p1.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p1.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        Profilo p2 = new Profilo("Paolo", "Prova2", "Napoli", "Napoli", LocalDate.of(1995,7,15), 185, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.ROSSI, Colore_Occhi.VERDI,new Foto(Utils.downloadUrl("https://source.unsplash.com/random")), hobbyArrayList);
        p2.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p2.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p2.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));;
        Profilo p3 = new Profilo("Lucia", "Prova3", "Napoli", "Napoli", LocalDate.of(1991,1,25), 164, Sesso.DONNA, Interessi.ENTRAMBI, Colori_Capelli.CASTANI, Colore_Occhi.CASTANI,new Foto(Utils.downloadUrl("https://source.unsplash.com/random")), hobbyArrayList);
        p3.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p3.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p3.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));

        utenteService.registrazioneStudente(s1, p1);
        utenteService.registrazioneStudente(s2, p2);
        utenteService.registrazioneStudente(s3, p3);

        matchService.aggiungiMatch(s1.getEmail(), s2.getEmail());
        matchService.aggiungiMatch(s2.getEmail(), s1.getEmail());
        matchService.aggiungiMatch(s1.getEmail(), s3.getEmail());
        matchService.aggiungiMatch(s3.getEmail(), s1.getEmail());

        //Aggiungo un moderatore
        Moderatore m1 = new Moderatore("moderatore@gmail.com", "moderatore");
        Profilo p4 = new Profilo("Marcello", "Moderatore", "Napoli", "Napoli", LocalDate.of(1999,6,12), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.GRIGI, Colore_Occhi.AZZURRI,new Foto(Utils.downloadUrl("https://source.unsplash.com/random")),hobbyArrayList );
        p4.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p4.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p4.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));

        utenteService.registrazioneModeratore(m1, p4);

        //Aggiungo un communityManager

        CommunityManager cm1 = new CommunityManager("communitymanager@gmail.com","communitymanager");
        Profilo p5 = new Profilo("Francesca", "CM", "Napoli", "Napoli", LocalDate.of(1980,7,12), 170, Sesso.DONNA, Interessi.UOMINI, Colori_Capelli.CASTANI, Colore_Occhi.AZZURRI, new Foto(Utils.downloadUrl("https://source.unsplash.com/random")),hobbyArrayList);
        p5.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p5.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));
        p5.addFoto(new Foto(Utils.downloadUrl("https://source.unsplash.com/random")));

        utenteService.registrazioneCommunityManager(cm1, p5);


       /* gestioneModerazioneController.inviaSegnalazione(new SegnalazioneDTO("motivazione1", "dettagli1") , new FotoDTO(p1.getFotoProfilo().getImg()));
        gestioneModerazioneController.inviaSegnalazione(new SegnalazioneDTO("motivazione2", "dettagli2") , new FotoDTO(p2.getFotoProfilo().getImg()));
        gestioneModerazioneController.inviaSegnalazione(new SegnalazioneDTO("motivazione3", "dettagli3") , new FotoDTO(p3.getFotoProfilo().getImg()));
        gestioneModerazioneController.inviaSegnalazione(new SegnalazioneDTO("motivazione1", "dettagli1") , new FotoDTO(p3.getListaFoto().get(0).getImg()));
        gestioneModerazioneController.inviaSegnalazione(new SegnalazioneDTO("motivazione1", "dettagli1") , new FotoDTO(p3.getListaFoto().get(0).getImg()));
        gestioneModerazioneController.inviaSegnalazione(new SegnalazioneDTO("motivazione1", "dettagli1") , new FotoDTO(p1.getListaFoto().get(0).getImg()));

        gestioneModerazioneController.inviaAmmonimento(new AmmonimentoDTO("motivazione1", "dettagli1"), m1.getEmail(), s1.getEmail(),( s1.getProfilo().getFotoProfilo()));
        moderazioneService.inviaAmmonimento(new Ammonimento("motivazione2", "dettagli2"), m1.getEmail(), s2.getEmail(), s2.getProfilo();
        gestioneModerazioneController.inviaAmmonimento("motivazione3", "dettagli3", m1, s3, s3.getProfilo().getFotoProfilo());

        Moderatore m2 = (Moderatore) utenteService.trovaUtente("moderatore@gmail.com");
        gestioneModerazioneController.inviaSegnalazioneCommunityManager(m2.getSegnalazioneRicevute().get(0));

        gestioneModerazioneController.inviaAmmonimento("motivazione3", "dettagli3", m1, s3, s3.getProfilo().getListaFoto().get(0));
        gestioneModerazioneController.inviaAmmonimento("motivazione3", "dettagli3", m1, s3, s3.getProfilo().getListaFoto().get(0)); */



    }

}
