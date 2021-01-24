package com.unidates.Unidates.UniDates.View.component_home_page;


import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Model.Enum.Colore_Occhi;
import com.unidates.Unidates.UniDates.Model.Enum.Colori_Capelli;
import com.unidates.Unidates.UniDates.Model.Enum.Hobby;
import com.unidates.Unidates.UniDates.Model.Enum.Interessi;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Route(value = "profilo-personale", layout = MainView.class)
@PageTitle("Profilo")
@CssImport("./styles/views/home/profilopersonale.css")
public class ProfiloPersonale extends VerticalLayout {

    StudenteDTO studente;
    ProfiloDTO profilo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    GestioneProfiloController gestioneProfiloController;

    public ProfiloPersonale(){
        addAttachListener(event -> create());
    }
    public void create(){
        studente = (StudenteDTO) gestioneUtentiController.utenteInSessione();
        profilo = studente.getProfilo();

        multiselectComboBox.setLabel("Seleziona Topic");
        multiselectComboBox.setPlaceholder("Scelti...");
        Hobby [] topic = Hobby.values();
        List<String> topiclist = new ArrayList<String>();
        for(Hobby h : topic) topiclist.add(h.toString());
        multiselectComboBox.setItems(topiclist);
        multiselectComboBox.getStyle().set("margin-bottom","30px");


        VerticalLayout padre = new VerticalLayout();
        padre.setAlignItems(Alignment.CENTER);

        HorizontalLayout sotto_padre = new HorizontalLayout();
        VerticalLayout totale_info = new VerticalLayout();
        totale_info.add(Info5());


        sotto_padre.add(ImageUtente(),totale_info);
        padre.add(NomeUtente(),sotto_padre,Info1(),Info2(),Info3(),Info4(),multiselectComboBox,Pulsanti());
        add(padre);
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
        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(studente.getProfilo().getFotoProfilo().getImg()));
        Image img = new Image(resource,"");
        img.getStyle().set("width","200px");
        img.getStyle().set("height","200px");
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
        email.setValue(studente.getEmail());
        email.setEnabled(false);
        info1.add(nome,cognome,compleanno,email);
        return info1;
    }

    public NumberField altezza = new NumberField("Altezza (cm)");
    public TextField città = new TextField("Città");
    private Select<String> interessi = new Select<>();
    public TextField luogo_di_nascita = new TextField("Luogo di nascita");

    public HorizontalLayout Info2 (){
        HorizontalLayout info2 = new HorizontalLayout();
        altezza.setValue(profilo.getAltezza());
        altezza.setEnabled(false);
        città.setValue(profilo.getResidenza());
        città.setEnabled(false);
        luogo_di_nascita.setValue(profilo.getLuogoNascita());
        luogo_di_nascita.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setValue(studente.getProfilo().getInteressi().toString());
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());
        interessi.setEnabled(false);
        info2.add(altezza,città,luogo_di_nascita,interessi);

        return info2;
    }


    private Select<String> capelli = new Select<>();
    private Select<String> occhi = new Select<>();
    private MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();

    public HorizontalLayout Info3(){
        HorizontalLayout info3 = new HorizontalLayout();

        capelli.setLabel("Capelli");
        capelli.setPlaceholder("Colore capelli");
        capelli.setEnabled(false);
        Colori_Capelli [] colore_cap = Colori_Capelli.values();
        capelli.setItems(colore_cap[0].toString(),colore_cap[1].toString(),colore_cap[2].toString(),colore_cap[3].toString(),colore_cap[4].toString(),colore_cap[5].toString(),colore_cap[6].toString());

        occhi.setLabel("Occhi");
        occhi.setPlaceholder("Colore occhi");
        occhi.setEnabled(false);
        Colore_Occhi [] colore_occhi = Colore_Occhi.values();
        occhi.setItems(colore_occhi[0].toString(),colore_occhi[1].toString(),colore_occhi[2].toString(),colore_occhi[3].toString(),colore_occhi[4].toString(),colore_occhi[5].toString(),colore_occhi[6].toString());

        info3.add(capelli,occhi);
        return info3;
    }

    public HorizontalLayout Info4(){
        HorizontalLayout info4 = new HorizontalLayout();
        //Image
        return info4;
    }

    public VerticalLayout Info5(){
        VerticalLayout info5 =  new VerticalLayout();
        Span interessi = new Span("Interessi : " + studente.getProfilo().getInteressi().toString());
        Span capelli = new Span("Capelli : " + studente.getProfilo().getColori_capelli().toString());
        Span occhi = new Span("Occhi : " + studente.getProfilo().getColore_occhi().toString());
        Span topic = new Span("Topic : " + studente.getProfilo().getHobbyList().toString());

        info5.add(capelli, occhi, interessi, topic);
        return info5;
    }

    public HorizontalLayout Pulsanti(){
        HorizontalLayout pulsanti = new HorizontalLayout();
        pulsanti.add(Modifica(),Conferma(),CambiaPassword());
        return pulsanti;
    }


    public Button Modifica(){
        Button modifica = new Button("Modifica",buttonClickEvent -> {
            nome.setEnabled(true);
            cognome.setEnabled(true);
            compleanno.setEnabled(true);
            email.setEnabled(true);
            città.setEnabled(true);
            luogo_di_nascita.setEnabled(true);
            occhi.setEnabled(true);
            capelli.setEnabled(true);
            multiselectComboBox.setEnabled(true);
            altezza.setEnabled(true);
            interessi.setEnabled(true);
            multiselectComboBox.setEnabled(true);
        });
        return modifica;
    }

    public Button Conferma(){
        Button conferma = new Button("Conferma", buttonClickEvent -> {
            if(nome.isEmpty()){
                new Notification("Campo Nome vuoto",2000).open();
            }else if(cognome.isEmpty()){
                new Notification("Campo Cognome vuoto",2000).open();
            }else if(compleanno.isEmpty()){
                new Notification("Campo Data di nascita vuoto",2000).open();
            }else if(email.isEmpty()){
                new Notification("Campo Email vuoto",2000).open();
            }else if(altezza.isEmpty()){
                new Notification("Campo Altezza vuoto",2000).open();
            }else if(città.isEmpty()){
                new Notification("Campo vuoto").open();
            }else if(luogo_di_nascita.isEmpty()){
                new Notification("Campo Luogo di nascita vuoto",2000).open();
            }else {
                studente.getProfilo().setAltezza(altezza.getValue());
                studente.getProfilo().setNome(nome.getValue());
                studente.getProfilo().setCognome(cognome.getValue());
                studente.getProfilo().setDataDiNascita(compleanno.getValue());
                studente.setEmail(email.getValue());
                studente.getProfilo().setResidenza(città.getValue());
                studente.getProfilo().setLuogoNascita(luogo_di_nascita.getValue());
                if(!(interessi.isEmpty()))
                    studente.getProfilo().setInteressi(Interessi.valueOf(interessi.getValue()));
                if(!(occhi.isEmpty()))
                    studente.getProfilo().setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
                if(!(capelli.isEmpty()))
                    studente.getProfilo().setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
                //hobby
                ArrayList<Hobby> hob = new ArrayList<Hobby>();
                for (String s : multiselectComboBox.getValue()) hob.add(Hobby.valueOf(s));
                if(!(multiselectComboBox.isEmpty()))
                    studente.getProfilo().setHobbyList(hob);

                nome.setEnabled(false);
                cognome.setEnabled(false);
                compleanno.setEnabled(false);
                email.setEnabled(false);
                città.setEnabled(false);
                luogo_di_nascita.setEnabled(false);
                occhi.setEnabled(false);
                capelli.setEnabled(false);
                multiselectComboBox.setEnabled(false);
                altezza.setEnabled(false);
                interessi.setEnabled(false);
                //VEDERE IMMAGINE


                gestioneProfiloController.modificaProfilo(studente.getEmail(),studente.getProfilo());
                Page pagina = UI.getCurrent().getPage();

                pagina.reload();

            }

        });
        return conferma;
    }



    public Button CambiaPassword(){
        Button cambia_password = new Button("Cambia password");

        Notification cambio = new Notification();
        cambio.setPosition(Notification.Position.MIDDLE);

        VerticalLayout corpo_notifica = new VerticalLayout();
        corpo_notifica.setAlignItems(Alignment.CENTER);

        Span testo = new Span("Cambia la tua password!");
        PasswordField password_attuale = new PasswordField("Password corrente");
        PasswordField prima_password = new PasswordField("Nuova password");
        PasswordField seconda_password = new PasswordField("Conferma password");
        Button conferma = new Button("Conferma");
        conferma.addClickListener(buttonClickEvent -> {
           if(encoder.matches(password_attuale.getValue(),studente.getPassword())){
               if(prima_password.getValue().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")){
                   if (prima_password.getValue().equals(seconda_password.getValue())) {
                       gestioneUtentiController.cambiaPassword(studente.getEmail(), prima_password.getValue(), password_attuale.getValue());
                       cambio.close();
                   } else {
                       Notification errore_password = new Notification("Le password nuove non corrispondono", 3000, Notification.Position.MIDDLE);
                       errore_password.open();
                   }
               }else {
                   Notification errore_password = new Notification("Le nuova password deve avere minimo 8 carrateri tra cui un nuemro e un carattere speciale", 3000, Notification.Position.MIDDLE);
                   errore_password.open();
               }
           }else {
               Notification errore_password_attuale = new Notification("La password attuale non corrisponde",3000, Notification.Position.MIDDLE);
               errore_password_attuale.open();
           }
        });
        Button annulla = new Button("Annulla");
        annulla.addClickListener(buttonClickEvent -> {
           cambio.close();
        });


        corpo_notifica.add(testo,password_attuale,prima_password,seconda_password,conferma,annulla);
        cambio.add(corpo_notifica);

        cambia_password.addClickListener(buttonClickEvent -> {
            cambio.open();
        });

        return cambia_password;
    }


    private boolean checkMaggiorenne(LocalDate value) {
        return Period.between(value,LocalDate.now()).getYears() >= 18;
    }

}
