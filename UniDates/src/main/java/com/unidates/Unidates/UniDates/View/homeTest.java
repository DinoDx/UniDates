package com.unidates.Unidates.UniDates.View;

import com.unidates.Unidates.UniDates.Controller.GestioneUtenti.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
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


@Route("testaRobe")
public class homeTest extends VerticalLayout {

    @Autowired
    GestioneUtentiController gestioneUtentiController;



    public homeTest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);

        Utente utente =(Utente) httpSession.getAttribute("utente");
        TextField email = new TextField("Email");
        TextField email2 = new TextField("Email2");
        TextField password = new TextField("Password");
        Text text;
        if(utente != null) {
            text = new Text(utente.getEmail());
            add(text);
        }
        Button aggiungiUtente = new Button("Aggiungi utente", buttonClickEvent -> {
            Studente userTest = new Studente();
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


        add(email);
        add(email2);
        add(password);
        add(aggiungiUtente);
        add(bloccaUtente);
        add(stampaListaBloccati);

        add(mostraUtenti);
        add(trovaUtente);
        add(removeUtente);
        /*
        add(aggiungiNotifica);
        add(mostraNotifica);
        add(aggiungiChat);
        add(mostraChat); */

    }
}
