package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.Control.ModerationControl;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class Populator implements ApplicationRunner {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ModerationControl moderationControl;

    @Autowired
    private InteractionControl interactionControl;

    private final String LINK = "https://source.unsplash.com/random";

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


        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(downloadUrl(LINK)) ,hobbyArrayList);
        p1.setNumeroTelefono("3333339900");
        p1.setNickInstagram("marco.prova1");
        p1.addFoto(new Foto(downloadUrl(LINK)),false);
        p1.addFoto(new Foto(downloadUrl(LINK)), false);
        p1.addFoto(new Foto(downloadUrl(LINK)), false);
        Profilo p2 = new Profilo("Paolo", "Prova2", "Napoli", "Napoli", LocalDate.of(1995,7,15), 185, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.ROSSI, Colore_Occhi.VERDI,new Foto(downloadUrl(LINK)), hobbyArrayList);
        p2.setNumeroTelefono("3335559900");
        p2.setNickInstagram("PaoloSonoBello.prova2");
        p2.addFoto(new Foto(downloadUrl(LINK)), false);
        p2.addFoto(new Foto(downloadUrl(LINK)), false);
        p2.addFoto(new Foto(downloadUrl(LINK)),false);
        Profilo p3 = new Profilo("Lucia", "Prova3", "Napoli", "Napoli", LocalDate.of(1991,1,25), 164, Sesso.DONNA, Interessi.ENTRAMBI, Colori_Capelli.CASTANI, Colore_Occhi.CASTANI,new Foto(downloadUrl(LINK)), hobbyArrayList);
        p3.setNickInstagram("SimpyLucia");
        p3.addFoto(new Foto(downloadUrl(LINK)),false);
        p3.addFoto(new Foto(downloadUrl(LINK)), false);
        p3.addFoto(new Foto(downloadUrl(LINK)), false);

        utenteService.registrazioneStudente(s1, p1); //usati solo per skippare l'invio email di conferma
        utenteService.registrazioneStudente(s2, p2);
        utenteService.registrazioneStudente(s3, p3);

        /*interactionControl.aggiungiMatch(s1.getEmail(), s2.getEmail());
        interactionControl.aggiungiMatch(s2.getEmail(), s1.getEmail());
        interactionControl.aggiungiMatch(s1.getEmail(), s3.getEmail());
        interactionControl.aggiungiMatch(s3.getEmail(), s1.getEmail());

         */

        //Aggiungo un moderatore
        Moderatore m1 = new Moderatore("moderatore@gmail.com", "moderatore");
        Profilo p4 = new Profilo("Marcello", "Moderatore", "Napoli", "Napoli", LocalDate.of(1999,6,12), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.GRIGI, Colore_Occhi.AZZURRI,new Foto(downloadUrl(LINK)),hobbyArrayList );
        p4.addFoto(new Foto(downloadUrl(LINK)), false);
        p4.addFoto(new Foto(downloadUrl(LINK)), false);
        p4.addFoto(new Foto(downloadUrl(LINK)), false);

        utenteService.registrazioneModeratore(m1, p4);

        //Aggiungo un communityManager

        CommunityManager cm1 = new CommunityManager("communitymanager@gmail.com","communitymanager");
        Profilo p5 = new Profilo("Francesca", "CM", "Napoli", "Napoli", LocalDate.of(1980,7,12), 170, Sesso.DONNA, Interessi.UOMINI, Colori_Capelli.CASTANI, Colore_Occhi.AZZURRI, new Foto(downloadUrl(LINK)),hobbyArrayList);
        p5.addFoto(new Foto(downloadUrl(LINK)), false);
        p5.addFoto(new Foto(downloadUrl(LINK)), false);
        p5.addFoto(new Foto(downloadUrl(LINK)), false);

        utenteService.registrazioneCommunityManager(cm1, p5);

        /*
        try {

            moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli1"),EntityToDto.toDTO(utenteService.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo()));
            moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.NUDITA, "dettagli2"),EntityToDto.toDTO(utenteService.trovaStudente(s2.getEmail()).getProfilo().getFotoProfilo()));
            moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.VIOLENZA , "dettagli3"),EntityToDto.toDTO(utenteService.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo()));
            moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli4"),EntityToDto.toDTO(utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(0)));
            moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.NUDITA, "dettagli5"),EntityToDto.toDTO(utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(1)));
            moderationControl.inviaSegnalazione(new SegnalazioneDTO(Motivazione.VIOLENZA, "dettagli6"),EntityToDto.toDTO(utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(2)));

            moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli1"), m1.getEmail(), s1.getEmail(), EntityToDto.toDTO(utenteService.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo()));
            moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.SPAM, "dettagli3"), m1.getEmail(), s3.getEmail(),EntityToDto.toDTO(utenteService.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo()));
            moderationControl.inviaAmmonimento(new AmmonimentoDTO(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli3"), m1.getEmail(), s3.getEmail(),EntityToDto.toDTO(utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(0)));

        } catch (InvalidFormatException ex){
            ex.printStackTrace();
        }
         */

       // moderazioneService.inviaAmmonimento(new Ammonimento("motivazione3", "dettagli3"), m1.getEmail(), s3.getEmail(),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(1).getId());




    }
    public static byte[] downloadUrl(String stringDownload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            URL toDownload = new URL(stringDownload);
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();
            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toByteArray();
    }

}
