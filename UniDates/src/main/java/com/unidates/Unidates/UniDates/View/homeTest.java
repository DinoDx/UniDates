package com.unidates.Unidates.UniDates.View;

import com.unidates.Unidates.UniDates.Controller.GestioneInterazioniController;
import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Match;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Service.GestioneUtenti.UtenteService;
import com.unidates.Unidates.UniDates.Model.Service.Publisher;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


@Route("")
public class homeTest extends VerticalLayout {

    @Autowired
    Publisher publisher;

    @Autowired
    UtenteService utenteService;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    GestioneProfiloController gestioneProfiloController;

    @Autowired
    GestioneInterazioniController gestioneInterazioniController;

    @Autowired
    GestioneModerazioneController gestioneModerazioneController;

    public homeTest() {
        Utente utente = SecurityUtils.getLoggedIn();  // Come prendere l'utente attualmente loggato
        TextField email = new TextField("Email");
        TextField email2 = new TextField("Email2");
        TextField password = new TextField("Password");
        Text emailRuolo = new Text(utente.getRuolo() +" "+utente.getEmail());
        add(emailRuolo);
        TextField messaggio = new TextField("Messaggio");

        Button aggiungiUtente = new Button("Aggiungi utente", buttonClickEvent -> {
            Studente userTest = new Studente(email.getValue(), password.getValue());

            Profilo profilo = new Profilo("Prova", "Prova", "Prova", "ResidenzaProva", LocalDate.now(), 160, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI, new ArrayList<Hobby>());
            profilo.addFoto(new Foto(new byte[]{(byte)0xe0}));
            gestioneUtentiController.registrazioneStudente(userTest, profilo, VaadinServletRequest.getCurrent());
        });

        Button bloccaUtente = new Button("BloccaUtente", buttonClickEvent -> {
            Studente bloccante = (Studente) gestioneUtentiController.trovaUtente(email.getValue());
            Studente bloccato = (Studente) gestioneUtentiController.trovaUtente(email2.getValue());
            gestioneUtentiController.bloccaStudente(bloccante,bloccato);
        });

        Button stampaListaBloccati = new Button("Stampa lista bloccati 1", buttonClickEvent -> {
            Studente trovato = (Studente) gestioneUtentiController.trovaUtente(email.getValue());
           for (Studente s: trovato.getListaBloccati())
              System.out.println(s.getEmail());
        });

       /* Button aggiungiNotifica = new Button("Aggiungi notifica", buttonClickEvent -> {
            Studente utente =  gestioneUtentiController.findByEmail(email.getValue()).get();
            gestioneUtentiController.addNotifica(new Notifica("Ciao come stai"),utente);
        });

        Button mostraNotifica = new Button("Stampa notifiche", buttonClickEvent -> {
            for(Notifica notifica: gestioneUtentiController.findByEmail(email.getValue()).get().getListNotifica())
                System.out.println(notifica.getText());
        }); */

        Button mostraUtenti = new Button("Mostra utenti", buttonClickEvent -> {
           // for(Studente u : gestioneUtentiController.findAll())
           //     System.out.println(u.getProfilo().getNome());

        });

        Button trovaUtente = new Button("Trova utente", buttonClickEvent -> System.out.println(gestioneUtentiController.trovaUtente(email.getValue()).getEmail()));

        Button removeUtente = new Button("Rimuovi utente", buttonClickEvent -> {
           gestioneProfiloController.eliminaProfilo(trovaStudente(email.getValue()).getProfilo(), password.getValue());
        });
        /*
        Button aggiungiChat = new Button("Aggiungi chat", buttonClickEvent -> {
            gestioneUtentiController.addChat(new Chat(), gestioneUtentiController.findByEmail(email.getValue()).get());
        });

        Button mostraChat = new Button("Stampa numero chat", buttonClickEvent -> {
            System.out.println(gestioneUtentiController.findByEmail(email.getValue()).get().getListaChat().size());
        });*/

        Button aggiungiFoto = new Button("Aggiungi Foto", buttonClickEvent -> {
            byte[] img = new byte[] {(byte)0xe0};
           gestioneProfiloController.aggiungiFoto(trovaStudente(email.getValue()).getProfilo(),new Foto(img));
            for(Foto f : gestioneProfiloController.visualizzaProfilo(trovaStudente(email.getValue())).getListaFoto())
                System.out.println(Arrays.toString(f.getImg()) + " " + gestioneProfiloController.visualizzaProfilo(trovaStudente(email.getValue())).getListaFoto().indexOf(f));
        });
        Button aggiungiFotoProfilo = new Button("Aggiungi Foto Profilo", buttonClickEvent -> {
            gestioneProfiloController.aggiungiFotoProfilo(gestioneProfiloController.visualizzaProfilo(trovaStudente(email.getValue())), gestioneProfiloController.visualizzaProfilo(trovaStudente(email.getValue())).getListaFoto().get(1));
            for(Foto f : gestioneProfiloController.visualizzaProfilo(trovaStudente(email.getValue())).getListaFoto())
                System.out.println(Arrays.toString(f.getImg()) + " " + gestioneProfiloController.visualizzaProfilo(trovaStudente(email.getValue())).getListaFoto().indexOf(f));
        });



       Button aggiungiModeratore = new Button("Aggiungi moderatore", buttonClickEvent -> {
            Moderatore moderatore = new Moderatore(email.getValue(), password.getValue());
           ArrayList<Foto> fotoArrayList = new ArrayList<Foto>();
           fotoArrayList.add(new Foto(new byte[]{(byte)0xe0}));
           Profilo profilo = new Profilo("Prova", "Prova", "Prova", "ResidenzaProva", LocalDate.now(), 160, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI, new ArrayList<Hobby>());

           gestioneUtentiController.registrazioneModeratore(moderatore, profilo);
          //  gestioneModerazioneController.inviaSegnalazione(new Foto("ciao"));
           
        });

        Button aggiungiCM = new Button("aggiungiCM", buttonClickEvent -> {
            CommunityManager cm = new CommunityManager(email.getValue(), password.getValue());
            ArrayList<Foto> fotoArrayList = new ArrayList<Foto>();
            fotoArrayList.add(new Foto(new byte[]{(byte)0xe0}));
            Profilo profilo = new Profilo("Prova", "Prova", "Prova", "ResidenzaProva", LocalDate.now(), 160, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI, new ArrayList<Hobby>());

            gestioneUtentiController.registrazioneCommunityManager(cm, profilo);
            //  gestioneModerazioneController.inviaSegnalazione(new Foto("ciao"));

        });



        /*
        Button modificaProfilo = new Button("Modifica Profilo", buttonClickEvent -> {
            Profilo nuovoProfilo = gestioneUtentiController.findByEmail(email.getValue()).getProfilo();
            nuovoProfilo.setCognome("Update");
            nuovoProfilo.setNome("Update");
            gestioneProfiloController.updateProfile(nuovoProfilo);

            System.out.println(gestioneUtentiController.findByEmail(email.getValue()).getProfilo().getNome());
            System.out.println(gestioneUtentiController.findByEmail(email.getValue()).getProfilo().getCognome());
        });
        */

        Button aggiungiMatch1 = new Button("Aggiungi Match 1", buttonClickEvent ->{
            gestioneInterazioniController.aggiungiMatch(trovaStudente(email.getValue()),trovaStudente(email2.getValue()));
        });


        Button sendMessage = new Button("Invio messaggio", buttonClickEvent -> {
            Messaggio toSend = new Messaggio();
            toSend.setTestoMessaggio(messaggio.getValue());
            gestioneInterazioniController.inviaMessaggio(trovaStudente(email.getValue()),trovaStudente(email2.getValue()),toSend);

            /* Utente mittente = gestioneUtentiController.trovaUtente(email.getValue());
            Utente destinatario = gestioneUtentiController.trovaUtente(email2.getValue());

            gestioneUtentiController.trovaUtente(email.getValue()).getMittente().forEach(chat -> { //.getMittente() tutte le chat che l'utente ha iniziato -- .getDestinatario() // tutte le chat che l'utente ha ricevuto
                chat.getMessaggi().forEach(messaggio1 -> System.out.println(messaggio1.getTestoMessaggio()));
            });          // in questo caso la funzione stampa tutti i messaggi di tutte le chat che l'utente nel campo email, ha INIZIATO
            */
        });

        Button inviaAmmonimento = new Button("inviaAmmonimento", e ->{
            Ammonimento a = new Ammonimento();
            a.setMotivazione(messaggio.getValue());
            a.setDettagli(messaggio.getValue());
            Studente studente = (Studente) gestioneUtentiController.trovaUtente(email2.getValue());
            gestioneModerazioneController.inviaAmmonimento(a, (Moderatore) gestioneUtentiController.trovaUtente(email.getValue()), studente , studente.getProfilo().getListaFoto().get(0));
        });



        add(email);
        add(email2);
        add(password);
        add(messaggio);
        add(aggiungiUtente);
        add(bloccaUtente);
        add(stampaListaBloccati);
        add(aggiungiMatch1);
        add(mostraUtenti);
        add(trovaUtente);
        add(removeUtente);
        add(inviaAmmonimento);
        add(aggiungiFoto);
        //add(modificaProfilo);
        add(aggiungiModeratore);
        add(aggiungiCM);
        add(sendMessage);
        add(aggiungiFotoProfilo);
        /*
        add(aggiungiNotifica);
        add(mostraNotifica);
        add(aggiungiChat);
        add(mostraChat); */

    }

    Studente trovaStudente(String email){
        return utenteService.trovaStudente(email);
    }


}
