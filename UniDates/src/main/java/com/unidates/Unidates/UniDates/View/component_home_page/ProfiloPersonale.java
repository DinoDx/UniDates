package com.unidates.Unidates.UniDates.View.component_home_page;


import com.example.application.views.Person;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.Colore_Occhi;
import com.unidates.Unidates.UniDates.Enum.Colori_Capelli;
import com.unidates.Unidates.UniDates.Enum.Hobby;
import com.unidates.Unidates.UniDates.Enum.Interessi;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.main.MainViewProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.util.*;


@Route(value = "profilo-personale", layout = MainViewProfile.class)
@PageTitle("Profilo")
@CssImport("./styles/views/home/profilopersonale.css")
public class ProfiloPersonale extends VerticalLayout {
    Utente utente = SecurityUtils.getLoggedIn();
    Studente studente = (Studente) utente;
    Profilo profilo = studente.getProfilo();

    @Autowired
    GestioneProfiloController controller;

    private Select<String> interessi = new Select<>();

    public ProfiloPersonale(GestioneProfiloController controller){
        this.controller = controller;
        VerticalLayout padre = new VerticalLayout();

        HorizontalLayout sotto_padre = new HorizontalLayout();
        VerticalLayout totale_info = new VerticalLayout();
        totale_info.add(Info1());


        sotto_padre.add(ImageUtente(),totale_info);
        padre.add(NomeUtente(),sotto_padre);
    }

    public HorizontalLayout NomeUtente(){
        HorizontalLayout nome = new HorizontalLayout();
        Span nome_utente = new Span(profilo.getNome());
        Span cognome_utente = new Span(profilo.getCognome());
        nome.add(nome_utente,cognome_utente);
        return nome;
    }

    public VerticalLayout ImageUtente(){
        VerticalLayout image = new VerticalLayout();
        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
        Image img = new Image(resource,"");
        image.add(img);
        return image;
    }

    public TextField nome = new TextField("Nome");
    public TextField cognome = new TextField("Cognome");
    public DatePicker compleanno = new DatePicker("Data di nascita");
    public EmailField email = new EmailField("Email");

    public HorizontalLayout Info1(){
        HorizontalLayout info1 = new HorizontalLayout();
        nome.setValue(profilo.getNome());
        nome.setEnabled(false);
        cognome.setValue(profilo.getCognome());
        cognome.setEnabled(false);
        compleanno.setValue(profilo.getDataDiNascita());
        compleanno.setEnabled(false);
        email.setValue(utente.getEmail());
        email.setEnabled(false);
        info1.add(nome,cognome,compleanno,email);
        return info1;
    }


/*
    public ProfiloPersonale(GestioneProfiloController controller){
        this.controller = controller;
        setId("personal-profile");
        addClassName("personal-profile");
        setSizeFull();
        add(createSingleUser(studente));
    }

    private VerticalLayout createSingleUser(Studente studente){
        VerticalLayout utente = new VerticalLayout();
        utente.addClassName("card-home");
        utente.setSpacing(false);
        utente.getThemeList().add("spacing-s");

        //First Layout
        HorizontalLayout nome = new HorizontalLayout();
        nome.addClassName("top-layout");
        nome.setSpacing(false);
        nome.getThemeList().add("spacing-s");

        Span nome_utente = new Span(studente.getProfilo().getNome());
        nome.addClassName("nome");
        nome.add(nome_utente);

        //Second Layout
        HorizontalLayout middle = new HorizontalLayout();
        middle.addClassName("middle-layout");
        middle.setSpacing(false);
        middle.getThemeList().add("spacing-s");

        //IMMAGINE DA VEDERE PER LA SESSIONE
        VerticalLayout image = new VerticalLayout();
        image.addClassName("photo");
        Image image_profilo = new Image();
        ArrayList<Foto> list = (ArrayList<Foto>) registrato.getProfilo().getListaFoto();
        image_profilo.setSrc(list.get(0).getUrl());
        image_profilo.setAlt("Foto non presente");
        image.setId("foto");
        image.add(image_profilo);



        VerticalLayout all_info = new VerticalLayout();

        //info 1
        HorizontalLayout info_uno = new HorizontalLayout();
        TextField name = new TextField("Nome");
        name.setValue(studente.getProfilo().getNome());
        name.setEnabled(false);

        TextField cognome = new TextField("Cognome");
        cognome.setValue(studente.getProfilo().getCognome());
        cognome.setEnabled(false);

        //DATA DA VEDERE PER LA SESSIONE
        DatePicker date = new DatePicker("Data di nascita");
        date.setValue(studente.getProfilo().getDataDiNascita());
        date.setEnabled(false);

        EmailField email_profilo = new EmailField("Email");
        email_profilo.setValue(studente.getEmail());
        email_profilo.setEnabled(false);

        info_uno.add(name,cognome,date,email_profilo);

        //info 2
        HorizontalLayout info_due = new HorizontalLayout();

        NumberField altezza = new NumberField("Altezza (cm)");
        altezza.setValue(studente.getProfilo().getAltezza());
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);
        altezza.setEnabled(false);

        TextField città = new TextField("Città");
        città.setValue(studente.getProfilo().getResidenza());
        città.setEnabled(false);
        TextField luogo = new TextField("Luogo di nascita");
        luogo.setValue(studente.getProfilo().getLuogoNascita());
        luogo.setEnabled(false);

        info_due.add(altezza,città,luogo);

        //info 3
        HorizontalLayout info_tre = new HorizontalLayout();



        TextField capelli = new TextField("Capelli");
        capelli.setValue(studente.getProfilo().getColori_capelli().toString());
        capelli.setEnabled(false);

        TextField occhi = new TextField("Occhi");
        occhi.setValue(studente.getProfilo().getColore_occhi().toString());
        occhi.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setValue(studente.getProfilo().getInteressi().toString());
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());
        interessi.setEnabled(false);

        MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
        multiselectComboBox.setWidth("100%");
        multiselectComboBox.setLabel("Topic Selezionati..");
        Hobby [] topic = Hobby.values();
        List<String> topiclist = new ArrayList<String>();
        for(Hobby h : topic) topiclist.add(h.toString());
        multiselectComboBox.setItems(topiclist);
        //multiselectComboBox.setEnabled(false);

        info_tre.add(capelli,occhi,interessi,multiselectComboBox);


        MemoryBuffer img = new MemoryBuffer();
        Upload upload = new Upload(img);
        upload.setMaxFiles(1);
        upload.getStyle().set("margin-top","4em");


        all_info.add(info_uno,info_due,info_tre,upload);
        middle.add(image,all_info);

        //Thirs layout
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addClassName("bottom-layout");
        buttons.setSpacing(false);
        buttons.getThemeList().add("spacing-s");

        Button conferma = new Button("Conferma", buttonClickEvent -> {
            name.setEnabled(false);
            cognome.setEnabled(false);
            date.setEnabled(false);
            email_profilo.setEnabled(false);
            città.setEnabled(false);
            luogo.setEnabled(false);
            occhi.setEnabled(false);
            capelli.setEnabled(false);
            multiselectComboBox.setEnabled(false);
            altezza.setEnabled(false);
            interessi.setEnabled(false);
            //VEDERE IMMAGINE

            studente.getProfilo().setAltezza(altezza.getValue());
            studente.getProfilo().setNome(name.getValue());
            studente.getProfilo().setCognome(cognome.getValue());
            studente.getProfilo().setDataDiNascita(date.getValue());
            studente.setEmail(email_profilo.getValue());
            studente.getProfilo().setResidenza(città.getValue());
            studente.getProfilo().setLuogoNascita(luogo.getValue());
            studente.getProfilo().setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
            studente.getProfilo().setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
            studente.getProfilo().setInteressi(Interessi.valueOf(interessi.getValue()));
            //hobby
            ArrayList<Hobby> hob = new ArrayList<Hobby>();
            for(String s : multiselectComboBox.getValue()) hob.add(Hobby.valueOf(s));
            studente.getProfilo().setHobbyList(hob);

            controller.modificaProfilo(studente.getProfilo(),studente.getPassword());

        });
        conferma.setId("confirm");

        Button modifica = new Button("Modifica",buttonClickEvent -> {
            name.setEnabled(true);
            cognome.setEnabled(true);
            date.setEnabled(true);
            email_profilo.setEnabled(true);
            città.setEnabled(true);
            luogo.setEnabled(true);
            occhi.setEnabled(true);
            capelli.setEnabled(true);
            multiselectComboBox.setEnabled(true);
            altezza.setEnabled(true);
            interessi.setEnabled(true);
            multiselectComboBox.setEnabled(true);
        });
        modifica.setId("modifica");


        Notification notifica = new Notification();
        notifica.setId("notifica");
        PasswordField propria = new PasswordField("Inserisci password corrente");
        propria.getStyle().set("margin-left","2em");
        PasswordField prima = new PasswordField("Nuova password:");
        prima.getStyle().set("margin-left","2em");
        PasswordField secondo = new PasswordField("Conferma password");
        secondo.getStyle().set("margin-left","2em");
        Button annulla = new Button("Annulla",buttonClickEvent -> {
            notifica.close();
        });
        annulla.getStyle().set("margin-left","1em");
        Button conf = new Button("Conferma",buttonClickEvent -> {
            //implmenmtare invio notifica
        });
        conf.getStyle().set("margin-left","2em");
        notifica.add(propria,prima,secondo,conf,annulla);


        Button chpassword = new Button("Cambia Password",buttonClickEvent -> {
            notifica.open();
        });
        chpassword.setId("pass");


        buttons.add(modifica,conferma,chpassword);


        utente.add(nome,middle,buttons);
        return  utente;
    }


    private Button CheckModifyButton(){

    }*/
}
