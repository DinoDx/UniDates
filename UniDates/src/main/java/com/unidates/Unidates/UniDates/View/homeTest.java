package com.unidates.Unidates.UniDates.View;

import com.unidates.Unidates.UniDates.Controller.GestioneInterazioniController;
import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Match;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;


@Route("")
public class homeTest extends VerticalLayout {

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    GestioneProfiloController gestioneProfiloController;

    @Autowired
    GestioneInterazioniController gestioneInterazioniController;

    @Autowired
    GestioneModerazioneController gestioneModerazioneController;



    public homeTest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);
        Utente utente =(Utente) httpSession.getAttribute("utente");

        TextField email = new TextField("Email");
        TextField email2 = new TextField("Email2");
        TextField password = new TextField("Password");
        Text text;
        TextField messaggio = new TextField("Messaggio");
        if(utente != null) {
            text = new Text(utente.getEmail());
            add(text);
        }
        Button aggiungiUtente = new Button("Aggiungi utente", buttonClickEvent -> {
            Studente userTest = new Studente();
            userTest.setRuolo(Ruolo.STUDENTE);
            userTest.setEmail(email.getValue());
            userTest.setPassword(password.getValue());

            Profilo profilo = new Profilo("Prova", "Prova", "Prova", "ResidenzaProva", new Date(), 160, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI, Hobby.CALCIO);


            gestioneUtentiController.registrazioneStudente(userTest, profilo);
        });

        Button bloccaUtente = new Button("BloccaUtente", buttonClickEvent -> {
            gestioneUtentiController.bloccaStudente(gestioneUtentiController.findByEmail(email.getValue()),gestioneUtentiController.findByEmail(email2.getValue()));
        });

        Button stampaListaBloccati = new Button("Stampa lista bloccati 1", buttonClickEvent -> {
           for (Studente s: gestioneUtentiController.findByEmail(email.getValue()).getListaBloccati())
              System.out.println(s.toString());
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
            for(Studente u : gestioneUtentiController.findAll())
                System.out.println(u.getProfilo().getNome());

        });

        Button trovaUtente = new Button("Trova utente", buttonClickEvent -> System.out.println(gestioneUtentiController.findByEmail(email.getValue()).getEmail()));
        Button removeUtente = new Button("Rimuovi utente", buttonClickEvent -> {
            gestioneUtentiController.removeUtente(gestioneUtentiController.findByEmail(email.getValue()));
        });
        /*
        Button aggiungiChat = new Button("Aggiungi chat", buttonClickEvent -> {
            gestioneUtentiController.addChat(new Chat(), gestioneUtentiController.findByEmail(email.getValue()).get());
        });

        Button mostraChat = new Button("Stampa numero chat", buttonClickEvent -> {
            System.out.println(gestioneUtentiController.findByEmail(email.getValue()).get().getListaChat().size());
        });*/

        Button aggiungiFoto = new Button("Aggiungi Foto", buttonClickEvent -> {
            gestioneProfiloController.addFoto(gestioneUtentiController.findByEmail(email.getValue()).getProfilo(),new Foto("Url prova " ));
            for(Foto f : gestioneProfiloController.findAllFoto())
                System.out.println(f.getUrl());
        });




       Button segnalaFoto = new Button("Segnala Foto", buttonClickEvent -> {
            Foto foto = new Foto("url di prova");
            gestioneProfiloController.addFoto(gestioneUtentiController.findByEmail(email.getValue()).getProfilo(),foto);
            Moderatore moderatore = new Moderatore("ciaomod", "ciaomod");
            gestioneUtentiController.registrazioneModeratore(moderatore,gestioneUtentiController.findByEmail(email.getValue()));
            gestioneModerazioneController.segnalaFoto(moderatore, foto,"porcodio", "foto dimmerda");

            gestioneProfiloController.findAllFoto().forEach(f -> System.out.printf("id_Foto: %s, numero elementi: %S", f.getId(), f.getSegnalazioniRicevuto().size()));
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
            gestioneInterazioniController.aggiungiMatch(gestioneUtentiController.findByEmail(email.getValue()), gestioneUtentiController.findByEmail(email2.getValue()));

            for (Match m : gestioneUtentiController.findByEmail(email.getValue()).getListMatch())
                System.out.println(m.toString());

            for (Match m : gestioneUtentiController.findByEmail(email.getValue()).getListMatchRicevuti())
                System.out.println(m.toString());
        });


        Button sendMessage = new Button("Invio messaggio", buttonClickEvent -> {
            Messaggio toSend = new Messaggio();
            toSend.setTestoMessaggio(messaggio.getValue());
            gestioneInterazioniController.sendMessage(gestioneUtentiController.findByEmail(email.getValue()), gestioneUtentiController.findByEmail(email2.getValue()),toSend);
            Utente mittente = gestioneUtentiController.findByEmail(email.getValue());
            Utente destinatario = gestioneUtentiController.findByEmail(email2.getValue());

            gestioneUtentiController.findByEmail(email.getValue()).getMittente().forEach(chat -> { //.getMittente() tutte le chat che l'utente ha iniziato -- .getDestinatario() // tutte le chat che l'utente ha ricevuto
                chat.getMessaggi().forEach(messaggio1 -> System.out.println(messaggio1.getTestoMessaggio()));
            });          // in questo caso la funzione stampa tutti i messaggi di tutte le chat che l'utente nel campo email, ha INIZIATO

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
        add(aggiungiFoto);
        //add(modificaProfilo);
        add(segnalaFoto);
        add(sendMessage);
        /*
        add(aggiungiNotifica);
        add(mostraNotifica);
        add(aggiungiChat);
        add(mostraChat); */

    }
}
