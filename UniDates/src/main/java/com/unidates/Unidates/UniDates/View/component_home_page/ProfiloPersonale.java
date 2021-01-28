package com.unidates.Unidates.UniDates.View.component_home_page;


import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidPhotoException;
import com.unidates.Unidates.UniDates.Exception.PasswordMissmatchException;
import com.unidates.Unidates.UniDates.Model.Enum.Colore_Occhi;
import com.unidates.Unidates.UniDates.Model.Enum.Colori_Capelli;
import com.unidates.Unidates.UniDates.Model.Enum.Hobby;
import com.unidates.Unidates.UniDates.Model.Enum.Interessi;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Route(value = "profilo-personale", layout = MainView.class)
@PageTitle("Profilo")
@CssImport("./styles/views/home/profilopersonale.css")
public class ProfiloPersonale extends VerticalLayout {

    FotoDTO foto;
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

        VerticalLayout totale_info = new VerticalLayout();
        totale_info.add(Info5());

        HorizontalLayout sotto_padre = new HorizontalLayout();
        sotto_padre.add(ImageUtente(),totale_info);

        HorizontalLayout textfield = new HorizontalLayout();
        textfield.add(Info1(),Info2(),Contatti());

        padre.add(NomeUtente(),
                sotto_padre,
                Pulsanti(),
                textfield,
                multiselectComboBox,
                Info4());
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
        image.add(img, cambiaFotoProfilo());
        return image;
    }



    public TextField nome = new TextField("Nome");
    public TextField cognome = new TextField("Cognome");
    public DatePicker compleanno = new DatePicker("Data di nascita");
    public EmailField email = new EmailField("Email");

    public NumberField altezza = new NumberField("Altezza (cm)");
    public TextField città = new TextField("Città");
    private Select<String> interessi = new Select<>();
    public TextField luogo_di_nascita = new TextField("Luogo di nascita");


    private Select<String> capelli = new Select<>();
    private Select<String> occhi = new Select<>();
    private MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();

    public TextField numero = new TextField("Numero cellulare");
    public TextField instagram = new TextField("Contatto Instagram");;

    public VerticalLayout Info1(){
        VerticalLayout info1 = new VerticalLayout();
        nome.setValue(profilo.getNome());
        nome.setEnabled(false);
        cognome.setValue(profilo.getCognome());
        cognome.setEnabled(false);
        compleanno.setValue(profilo.getDataDiNascita());
        compleanno.setEnabled(false);
        email.setValue(studente.getEmail());
        email.setEnabled(false);
        altezza.setValue(profilo.getAltezza());
        altezza.setEnabled(false);
        info1.add(nome,cognome,compleanno,email,altezza);
        return info1;
    }

    public VerticalLayout Info2(){
        VerticalLayout info2 = new VerticalLayout();

        città.setValue(profilo.getResidenza());
        città.setEnabled(false);
        luogo_di_nascita.setValue(profilo.getLuogoNascita());
        luogo_di_nascita.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setValue(studente.getProfilo().getInteressi().toString());
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());
        interessi.setEnabled(false);


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

        info2.add(città,luogo_di_nascita,interessi,capelli,occhi);
        return info2;
    }

    public VerticalLayout Info4(){
        VerticalLayout info4 = new VerticalLayout();
        info4.setAlignItems(Alignment.CENTER);

        HorizontalLayout listaFoto = new HorizontalLayout();

        for(FotoDTO f : studente.getProfilo().getListaFoto()) {
            StreamResource resource = new StreamResource("fotoprofilo", () -> new ByteArrayInputStream(f.getImg()));
            Image img = new Image(resource, "");
            img.getStyle().set("width", "200px");
            img.getStyle().set("height", "200px");

            VerticalLayout singolaFoto = new VerticalLayout();
            Button deleteFoto = new Button(new Icon(VaadinIcon.CLOSE));
            deleteFoto.addClickListener(buttonClickEvent -> {
                gestioneProfiloController.eliminaFoto(f);
                UI.getCurrent().getPage().reload();
            });
            singolaFoto.setAlignItems(Alignment.CENTER);
            singolaFoto.add(img,deleteFoto);

            listaFoto.add(singolaFoto);
        }

        MemoryBuffer image = new MemoryBuffer();
        Span dropIcon = new Span("Inserisci una nuova foto!");
        Upload upload = new Upload(image);
        upload.setDropLabel(dropIcon);
        upload.setMaxFiles(1);

        FotoDTO toadd = new FotoDTO();
        upload.addSucceededListener(event -> {
            try {
                toadd.setImg(image.getInputStream().readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
    });

        Button insertFoto = new Button("Inserisci");
        insertFoto.addClickListener(buttonClickEvent -> {
            if (toadd.getImg() != null) {
                try {
                    gestioneProfiloController.aggiungiFoto(studente.getEmail(), toadd);
                    UI.getCurrent().getPage().reload();
                }
                catch(InvalidPhotoException e){
                    new Notification("La foto non rispetta le dimensioni", 2000, Notification.Position.MIDDLE);
                }
            }
        });

        HorizontalLayout UploadAndInser = new HorizontalLayout();
        UploadAndInser.add(upload,insertFoto);

        info4.add(listaFoto,UploadAndInser);
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

    public VerticalLayout Contatti(){
        VerticalLayout contatti  = new VerticalLayout();
        contatti.setAlignItems(Alignment.CENTER);

        H4 testo = new H4("Sezione Contatti");
        contatti.getStyle().set("margin-top","40px");

        if(studente.getProfilo().getNumeroTelefono() == null)
            numero.setValue("Numero non presente!");
        else numero.setValue(studente.getProfilo().getNumeroTelefono());
        numero.setEnabled(false);

        if(studente.getProfilo().getNickInstagram() == null)
            instagram.setValue("Nickname instagram non presente!");
        else instagram.setValue(studente.getProfilo().getNickInstagram());

        instagram.setEnabled(false);
        contatti.add(testo,numero,instagram);
        return contatti;
    }

    public HorizontalLayout Pulsanti(){
        HorizontalLayout pulsanti = new HorizontalLayout();
        pulsanti.add(Modifica(),Conferma(),CambiaPassword(),DeleteAccount());
        return pulsanti;
    }


    Notification uploadFotoProfilo = new Notification();

    public Button cambiaFotoProfilo(){
        Button modifica = new Button("Cambia Foto Profilo");
        modifica.addClickListener(buttonClickEvent -> {

            if(!uploadFotoProfilo.isOpened()) {
                uploadFotoProfilo = new Notification();


                MemoryBuffer image = new MemoryBuffer();
                Span dropIcon = new Span("Inserisci una nuova foto!");
                Upload upload = new Upload(image);
                upload.setDropLabel(dropIcon);
                upload.setMaxFiles(1);

                FotoDTO nuovaFoto = new FotoDTO();
                upload.addSucceededListener(event -> {
                    try {
                        nuovaFoto.setImg(image.getInputStream().readAllBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                Button insertFoto = new Button("Inserisci");
                insertFoto.addClickListener(clickEvent -> {
                    if (nuovaFoto.getImg() != null) {
                        gestioneProfiloController.aggiungiFotoProfilo(studente.getEmail(), nuovaFoto);
                        UI.getCurrent().getPage().reload();
                    }
                });

                Button annulla = new Button("Annulla");
                annulla.addClickListener(buttonClickEvent1 -> {
                    uploadFotoProfilo.close();
                });
                uploadFotoProfilo.add(upload, insertFoto, annulla);
                uploadFotoProfilo.setPosition(Notification.Position.MIDDLE);

                uploadFotoProfilo.open();
            }
            else
                uploadFotoProfilo.close();


        });

        return modifica;
    }


    Notification cancellazione = new Notification();

    public Button DeleteAccount(){
        Button delete = new Button("Cancella Account");
        delete.addClickListener(buttonClickEvent -> {
            if(!cancellazione.isOpened()){
                cancellazione = new Notification();
                cancellazione.setPosition(Notification.Position.MIDDLE);
                VerticalLayout internoC = new VerticalLayout();
                internoC.setAlignItems(Alignment.CENTER);

                Span attenzione = new Span("ATTENZIONE!!!");
                Span testo = new Span("Il sistema avvisa che confermando la richiesta, i dati allegati al profilo verranno eliminati definitivamente dalla piattaforma UniDates.");
                PasswordField passwordField = new PasswordField("Password");

                Button confrema = new Button("Conferma");
                confrema.addClickListener(buttonClickEvent1 -> {
                    try {
                        gestioneUtentiController.cancellaAccountPersonale(email.getValue(),passwordField.getValue());
                        UI.getCurrent().getPage().reload();
                    }catch (PasswordMissmatchException e){
                        new Notification("La password non corrisponde",3000).open();
                    }
                    cancellazione.close();
                });

                Button annulla = new Button("Annulla");
                annulla.addClickListener(buttonClickEvent1 -> {
                    cancellazione.close();
                });

                internoC.add(attenzione,testo,passwordField,confrema,annulla);
                cancellazione.add(internoC);
                cancellazione.open();
            }else cancellazione.close();


        });
        return delete;
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
            numero.setEnabled(true);
            instagram.setEnabled(true);
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
            }else if(compleanno.isEmpty() || (!checkMaggiorenne(compleanno.getValue()))){
                new Notification("Campo Data di nascita vuoto o non valida",2000).open();
            }else if(email.isEmpty()){
                new Notification("Campo Email vuoto",2000).open();
            }else if(altezza.isEmpty()){
                new Notification("Campo Altezza vuoto",2000).open();
            }else if(città.isEmpty()){
                new Notification("Campo vuoto").open();
            }else if(luogo_di_nascita.isEmpty()){
                new Notification("Campo Luogo di nascita vuoto",2000).open();
            }else{
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
                if(!(numero.isEmpty()))
                    studente.getProfilo().setNumeroTelefono(numero.getValue());
                if(!(instagram.isEmpty()))
                    studente.getProfilo().setNickInstagram(instagram.getValue());
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
                numero.setEnabled(false);
                instagram.setEnabled(false);
                //VEDERE IMMAGINE

                gestioneProfiloController.modificaProfilo(studente.getEmail(),studente.getProfilo());
                Page pagina = UI.getCurrent().getPage();

                pagina.reload();

            }

        });
        return conferma;
    }


    Notification cambio = new Notification();
    public Button CambiaPassword(){
        Button cambia_password = new Button("Cambia password");
        cambia_password.addClickListener(buttonClickEvent -> {
            if(!cambio.isOpened()){
                cambio = new Notification();
                cambio.setPosition(Notification.Position.MIDDLE);

                VerticalLayout corpo_notifica = new VerticalLayout();
                corpo_notifica.setAlignItems(Alignment.CENTER);

                Span testo = new Span("Cambia la tua password!");
                PasswordField password_attuale = new PasswordField("Password corrente");
                PasswordField prima_password = new PasswordField("Nuova password");
                PasswordField seconda_password = new PasswordField("Conferma password");
                Button conferma = new Button("Conferma");

                conferma.addClickListener(buttonClickEvent1 -> {
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
                annulla.addClickListener(buttonClickEvent2 -> {
                    cambio.close();
                });

                corpo_notifica.add(testo,password_attuale,prima_password,seconda_password,conferma,annulla);
                cambio.add(corpo_notifica);

                cambio.open();

            }else cambio.close();
        });
        return cambia_password;
    }


    private boolean checkMaggiorenne(LocalDate value) {
        return Period.between(value,LocalDate.now()).getYears() >= 18;
    }

}
