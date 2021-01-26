package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Service.MatchService;
import com.unidates.Unidates.UniDates.Service.ModerazioneService;
import com.unidates.Unidates.UniDates.Service.ProfiloService;
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
    private ProfiloService profiloService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String LINK = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.miciogatto.it%2Fpreso-un-gattino-devo-comportarmi-nei-primi-giorni%2F&psig=AOvVaw1fDXog3w6XRCbgSWcrmk8i&ust=1611768863578000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjEzoeRuu4CFQAAAAAdAAAAABAJ";

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


        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(Utils.downloadUrl(LINK)) ,hobbyArrayList);
        p1.setNumeroTelefono("3333339900");
        p1.setNickInstagram("marco.prova1");
        p1.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p1.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p1.addFoto(new Foto(Utils.downloadUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.miciogatto.it%2Fpreso-un-gattino-devo-comportarmi-nei-primi-giorni%2F&psig=AOvVaw1fDXog3w6XRCbgSWcrmk8i&ust=1611768863578000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjEzoeRuu4CFQAAAAAdAAAAABAJ")));
        Profilo p2 = new Profilo("Paolo", "Prova2", "Napoli", "Napoli", LocalDate.of(1995,7,15), 185, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.ROSSI, Colore_Occhi.VERDI,new Foto(Utils.downloadUrl(LINK)), hobbyArrayList);
        p2.setNumeroTelefono("3335559900");
        p2.setNickInstagram("PaoloSonoBello.prova2");
        p2.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p2.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p2.addFoto(new Foto(Utils.downloadUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.miciogatto.it%2Fpreso-un-gattino-devo-comportarmi-nei-primi-giorni%2F&psig=AOvVaw1fDXog3w6XRCbgSWcrmk8i&ust=1611768863578000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjEzoeRuu4CFQAAAAAdAAAAABAJ")));;
        Profilo p3 = new Profilo("Lucia", "Prova3", "Napoli", "Napoli", LocalDate.of(1991,1,25), 164, Sesso.DONNA, Interessi.ENTRAMBI, Colori_Capelli.CASTANI, Colore_Occhi.CASTANI,new Foto(Utils.downloadUrl(LINK)), hobbyArrayList);
        p3.setNickInstagram("SimpyLucia");
        p3.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p3.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p3.addFoto(new Foto(Utils.downloadUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.miciogatto.it%2Fpreso-un-gattino-devo-comportarmi-nei-primi-giorni%2F&psig=AOvVaw1fDXog3w6XRCbgSWcrmk8i&ust=1611768863578000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjEzoeRuu4CFQAAAAAdAAAAABAJ")));

        utenteService.registrazioneStudente(s1, p1);
        utenteService.registrazioneStudente(s2, p2);
        utenteService.registrazioneStudente(s3, p3);

        matchService.aggiungiMatch(s1.getEmail(), s2.getEmail());
        matchService.aggiungiMatch(s2.getEmail(), s1.getEmail());
        matchService.aggiungiMatch(s1.getEmail(), s3.getEmail());
        matchService.aggiungiMatch(s3.getEmail(), s1.getEmail());

        //Aggiungo un moderatore
        Moderatore m1 = new Moderatore("moderatore@gmail.com", "moderatore");
        Profilo p4 = new Profilo("Marcello", "Moderatore", "Napoli", "Napoli", LocalDate.of(1999,6,12), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.GRIGI, Colore_Occhi.AZZURRI,new Foto(Utils.downloadUrl(LINK)),hobbyArrayList );
        p4.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p4.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p4.addFoto(new Foto(Utils.downloadUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.miciogatto.it%2Fpreso-un-gattino-devo-comportarmi-nei-primi-giorni%2F&psig=AOvVaw1fDXog3w6XRCbgSWcrmk8i&ust=1611768863578000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjEzoeRuu4CFQAAAAAdAAAAABAJ")));

        utenteService.registrazioneModeratore(m1, p4);

        //Aggiungo un communityManager

        CommunityManager cm1 = new CommunityManager("communitymanager@gmail.com","communitymanager");
        Profilo p5 = new Profilo("Francesca", "CM", "Napoli", "Napoli", LocalDate.of(1980,7,12), 170, Sesso.DONNA, Interessi.UOMINI, Colori_Capelli.CASTANI, Colore_Occhi.AZZURRI, new Foto(Utils.downloadUrl(LINK)),hobbyArrayList);
        p5.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p5.addFoto(new Foto(Utils.downloadUrl(LINK)));
        p5.addFoto(new Foto(Utils.downloadUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.miciogatto.it%2Fpreso-un-gattino-devo-comportarmi-nei-primi-giorni%2F&psig=AOvVaw1fDXog3w6XRCbgSWcrmk8i&ust=1611768863578000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjEzoeRuu4CFQAAAAAdAAAAABAJ")));

        utenteService.registrazioneCommunityManager(cm1, p5);



        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione1", "dettagli1"),utenteService.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione2", "dettagli2"),utenteService.trovaStudente(s2.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione3", "dettagli3"),utenteService.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione4", "dettagli4"),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(0).getId());
        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione5", "dettagli5"),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(1).getId());
        moderazioneService.inviaSegnalazione(new Segnalazione("motivazione6", "dettagli6"),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(2).getId());

        moderazioneService.inviaAmmonimento(new Ammonimento("motivazione1", "dettagli1"), m1.getEmail(), s1.getEmail(),utenteService.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneService.inviaAmmonimento(new Ammonimento("motivazione3", "dettagli3"), m1.getEmail(), s3.getEmail(),utenteService.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId());
        moderazioneService.inviaAmmonimento(new Ammonimento("motivazione3", "dettagli3"), m1.getEmail(), s3.getEmail(),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(0).getId());
        moderazioneService.inviaAmmonimento(new Ammonimento("motivazione3", "dettagli3"), m1.getEmail(), s3.getEmail(),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(1).getId());




    }

}
