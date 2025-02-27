package com.unidates.Unidates.UniDates.View.profilo;


import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.Control.ModifyProfileControl;
import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Exception.PasswordMissmatchException;
import com.unidates.Unidates.UniDates.Model.Enum.Colore_Occhi;
import com.unidates.Unidates.UniDates.Model.Enum.Colori_Capelli;
import com.unidates.Unidates.UniDates.Model.Enum.Hobby;
import com.unidates.Unidates.UniDates.Model.Enum.Interessi;
import com.unidates.Unidates.UniDates.View.navbar.Navbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Route(value = "profilo-personale", layout = Navbar.class)
@PageTitle("Profilo")
@CssImport("./styles/views/home/profilopersonale.css")
public class ProfiloPersonalePage extends VerticalLayout {

    FotoDTO foto;
    StudenteDTO studente;
    ProfiloDTO profilo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    InteractionControl interactionControl;

    @Autowired
    UserManagementControl userManagementControl;

    @Autowired
    ModifyProfileControl modifyProfileControl;

    public ProfiloPersonalePage(){
        addAttachListener(event -> create());
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
    private CheckboxGroup<String> topicBoxes = new CheckboxGroup<>();

    public TextField numero = new TextField("Numero cellulare");
    public TextField instagram = new TextField("Contatto Instagram");;


    public void create(){
        studente = (StudenteDTO) userManagementControl.studenteInSessione();
        profilo = studente.getProfilo();

        topicBoxes.setLabel("Seleziona Topic");
        topicBoxes.setId("topic-mod");
        Hobby [] topic = Hobby.values();
        Set<String> topiclist = new HashSet<>();
        for(Hobby h : topic) topiclist.add(h.toString());
        topicBoxes.setItems(topiclist);


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
                topicBoxes,
                Info4());
        add(padre);
    }






    public HorizontalLayout NomeUtente(){
        HorizontalLayout nome = new HorizontalLayout();
        H3 nome_utente = new H3(profilo.getNome());
        H3 cognome_utente = new H3(profilo.getCognome());
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
        image.setAlignItems(Alignment.CENTER);
        return image;
    }


    ;

    public VerticalLayout Info1(){
        VerticalLayout info1 = new VerticalLayout();
        nome.setValue(profilo.getNome());
        nome.setId("nome-mod");
        nome.setEnabled(false);
        cognome.setValue(profilo.getCognome());
        cognome.setEnabled(false);
        cognome.setId("cognome-mod");
        compleanno.setValue(profilo.getDataDiNascita());
        compleanno.setEnabled(false);
        compleanno.setId("compleanno-mod");
        altezza.setValue(profilo.getAltezza());
        altezza.setEnabled(false);
        altezza.setId("altezza-mod");
        info1.add(nome,cognome,compleanno,email,altezza);
        return info1;
    }

    public VerticalLayout Info2(){
        VerticalLayout info2 = new VerticalLayout();

        città.setValue(profilo.getResidenza());
        città.setId("citta-mod");
        città.setEnabled(false);
        luogo_di_nascita.setValue(profilo.getLuogoNascita());
        luogo_di_nascita.setId("luogo-mod");
        luogo_di_nascita.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setValue(studente.getProfilo().getInteressi().toString());
        Interessi [] arrayInteressi = Interessi.values();
        interessi.setItems(arrayInteressi[0].toString(),arrayInteressi[1].toString(),arrayInteressi[2].toString());
        interessi.setEnabled(false);
        interessi.setId("interessi-mod");


        capelli.setLabel("Capelli");
        capelli.setPlaceholder("Colore capelli");
        capelli.setEnabled(false);
        capelli.setId("capelli-mod");
        Colori_Capelli [] colore_cap = Colori_Capelli.values();
        capelli.setItems(colore_cap[0].toString(),colore_cap[1].toString(),colore_cap[2].toString(),colore_cap[3].toString(),colore_cap[4].toString(),colore_cap[5].toString(),colore_cap[6].toString());

        occhi.setLabel("Occhi");
        occhi.setId("occhi-mod");
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
            if(!f.isFotoProfilo()) {
                StreamResource resource = new StreamResource("fotoprofilo", () -> new ByteArrayInputStream(f.getImg()));
                Image img = new Image(resource, "");
                img.getStyle().set("width", "200px");
                img.getStyle().set("height", "200px");

                VerticalLayout singolaFoto = new VerticalLayout();
                Button deleteFoto = new Button(new Icon(VaadinIcon.CLOSE));
                deleteFoto.addClickListener(buttonClickEvent -> {
                        modifyProfileControl.eliminaFotoLista(f.getId(), studente.getEmail());
                        UI.getCurrent().getPage().reload();
                });

                Button setFotoProfilo = new Button("Imposta come foto profilo",new Icon(VaadinIcon.USER));
                setFotoProfilo.addClickListener(event -> {
                    modifyProfileControl.setFotoProfilo( studente.getEmail(), f.getId());
                    UI.getCurrent().getPage().reload();
                });

                singolaFoto.setAlignItems(Alignment.CENTER);
                HorizontalLayout pulsanti = new HorizontalLayout();
                pulsanti.add(deleteFoto, setFotoProfilo);
                singolaFoto.add(img,pulsanti );

                listaFoto.add(singolaFoto);
            }
        }

        MemoryBuffer image = new MemoryBuffer();
        Span dropIcon = new Span("Inserisci una nuova foto!");
        Upload upload = new Upload(image);
        upload.setDropLabel(dropIcon);
        upload.setId("upload-mod");
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
        insertFoto.setId("insert-button-mod");
        insertFoto.addClickListener(buttonClickEvent -> {
            if (toadd.getImg() != null) {
                try {
                    modifyProfileControl.aggiungiFotoLista(studente.getEmail(), toadd);
                    UI.getCurrent().getPage().reload();
                }
                catch(InvalidFormatException e){
                    new Notification("La foto non rispetta le dimensioni, massimo 10MB!", 2000, Notification.Position.MIDDLE).open();
                }
            }
            else {
                Notification errore = new Notification("Formato foto o dimensioni non valide!", 2000);
                errore.setId("errore-mod");
                errore.open();
            }
        });

        HorizontalLayout UploadAndInser = new HorizontalLayout();
        UploadAndInser.add(upload,insertFoto);

        info4.add(listaFoto,UploadAndInser);
        return info4;
    }



    public HorizontalLayout Info5(){
        Image interessiImage = new Image();
        interessiImage.setHeight("50px");
        interessiImage.setWidth("50px");

        HorizontalLayout totaleInfo5 = new HorizontalLayout();

        VerticalLayout info5Uno =  new VerticalLayout();

        Span capelli = new Span("Capelli : " + studente.getProfilo().getColori_capelli().toString());
        Span occhi = new Span("Occhi : " + studente.getProfilo().getColore_occhi().toString());

        if (studente.getProfilo().getInteressi().equals(Interessi.UOMINI))
            interessiImage.setSrc("./images/icons/male.png");
        else if (studente.getProfilo().getInteressi().equals(Interessi.DONNE))
            interessiImage.setSrc("./images/icons/female.png");
        else if (studente.getProfilo().getInteressi().equals(Interessi.ENTRAMBI))
            interessiImage.setSrc("./images/icons/bisex.png");


        VerticalLayout info5Due = new VerticalLayout();
        String iter = "";
        for(Hobby  h : studente.getProfilo().getHobbyList())
            iter += h.toString().toLowerCase()+ ", " ;

        Span topic = new Span("Topic : " + iter);
        info5Due.setId("list-topic");


        Span didascalia = new Span("Interessi:");

        HorizontalLayout didascaliaInteressi = new HorizontalLayout();
        didascaliaInteressi.add(didascalia,interessiImage);

        info5Uno.add(capelli,occhi,didascaliaInteressi);
        info5Due.add(topic);
        totaleInfo5.add(info5Uno,info5Due);
        return totaleInfo5;
    }

    public VerticalLayout Contatti(){
        VerticalLayout contatti  = new VerticalLayout();
        contatti.setAlignItems(Alignment.CENTER);

        H4 testo = new H4("Sezione Contatti");
        contatti.getStyle().set("margin-top","40px");

        email.setValue(studente.getEmail());
        email.setEnabled(false);

        numero.setId("numero-mod");
        if(studente.getProfilo().getNumeroTelefono() == null)
            numero.setPlaceholder("Numero non presente!");
        else numero.setValue(studente.getProfilo().getNumeroTelefono());
        numero.setEnabled(false);

        instagram.setId("contatto-mod");
        if(studente.getProfilo().getNickInstagram() == null)
            instagram.setPlaceholder("Nickname instagram non presente!");
        else instagram.setValue(studente.getProfilo().getNickInstagram());

        instagram.setEnabled(false);
        contatti.add(testo,numero,instagram,email);
        return contatti;
    }

    public HorizontalLayout Pulsanti(){
        HorizontalLayout pulsanti = new HorizontalLayout();
        pulsanti.add(Modifica(),Conferma(),CambiaPassword(),DeleteAccount(),ListaBloccati());
        return pulsanti;
    }


    Notification listaBloccati = new Notification();

    public Button ListaBloccati(){
        Button lista = new Button("Lista Bloccati");
        lista.setId("lista-bloccati");
        lista.addClickListener(buttonClickEvent -> {

            if(!listaBloccati.isOpened()){
                listaBloccati = new Notification();
                listaBloccati.setId("bloccati-notification");
                listaBloccati.setPosition(Notification.Position.MIDDLE);

                VerticalLayout utentiBloccati = new VerticalLayout();
                utentiBloccati.setAlignItems(Alignment.CENTER);

                for(String s : studente.getListaBloccatiEmail()){
                    HorizontalLayout nome = new HorizontalLayout();
                    nome.setAlignItems(Alignment.CENTER);
                    ProfiloDTO profiloDTO = null;
                    try {
                        profiloDTO = interactionControl.ricercaStudente(s).getProfilo();
                    } catch (EntityNotFoundException e) {
                        e.printStackTrace();
                    }
                    Span nomeBloccato = new Span(profiloDTO.getNome() +" " +profiloDTO.getCognome());
                    Button sblocca = new Button("Sblocca");
                    sblocca.setId("sblocca-"+ profiloDTO.getEmailStudente());
                    sblocca.addClickListener(buttonClickEvent1 -> {
                        interactionControl.sbloccaStudente(studente.getEmail(),s);
                        UI.getCurrent().getPage().reload();
                        listaBloccati.close();
                    });
                    nome.add(nomeBloccato,sblocca);
                    utentiBloccati.add(nome);
                }
                listaBloccati.add(utentiBloccati);
                if(!studente.getListaBloccatiEmail().isEmpty()){
                    listaBloccati.open();
                }

            }else listaBloccati.close();

        });
        return lista;
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
                    try {
                        if (nuovaFoto.getImg() != null) {
                            modifyProfileControl.aggiungiFotoProfilo(studente.getEmail(), nuovaFoto);
                            UI.getCurrent().getPage().reload();
                        }
                        } catch (InvalidFormatException e) {
                            Notification.show("La foto non rispetta le dimensioni! Massimo 10MB", 2000 , Notification.Position.MIDDLE).open();
                        }

                });

                Button annulla = new Button("Annulla");
                annulla.addClickListener(buttonClickEvent1 -> {
                    uploadFotoProfilo.close();
                });

                HorizontalLayout pulsanti = new HorizontalLayout();
                pulsanti.add(insertFoto,annulla);
                uploadFotoProfilo.add(upload, pulsanti);
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
        delete.setId("cancella-account");
        delete.addClickListener(buttonClickEvent -> {
            if(!cancellazione.isOpened()){
                cancellazione = new Notification();
                cancellazione.setPosition(Notification.Position.MIDDLE);
                VerticalLayout internoC = new VerticalLayout();
                internoC.setAlignItems(Alignment.CENTER);

                Span attenzione = new Span("ATTENZIONE!!!");
                Span testo = new Span("Il sistema avvisa che confermando la richiesta, i dati allegati al profilo verranno eliminati definitivamente dalla piattaforma UniDates.");
                PasswordField passwordField = new PasswordField("Password");
                passwordField.setId("cancella-account-pw");

                Button confrema = new Button("Conferma");
                confrema.setId("conferma-cancellazione");
                confrema.addClickListener(buttonClickEvent1 -> {
                    try {
                        modifyProfileControl.cancellaAccountPersonale(email.getValue(),passwordField.getValue());
                        UI.getCurrent().getPage().reload();
                    }catch (PasswordMissmatchException e){
                        Notification errore = new Notification("La password non corrisponde",3000);
                        errore.setId("errore-mod");
                        errore.open();
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
            città.setEnabled(true);
            luogo_di_nascita.setEnabled(true);
            occhi.setEnabled(true);
            capelli.setEnabled(true);
            topicBoxes.setEnabled(true);
            altezza.setEnabled(true);
            interessi.setEnabled(true);
            numero.setEnabled(true);
            instagram.setEnabled(true);
            topicBoxes.setEnabled(true);
        });
        modifica.setId("modifica-button-mod");
        return modifica;
    }

    public Button Conferma(){
        Button conferma = new Button("Conferma", buttonClickEvent -> {
            Notification errore;
            try {
                if (nome.isEmpty()) {
                    errore = new Notification("Campo Nome vuoto", 2000);
                    errore.setId("errore-mod");
                    errore.open();
                } else if (cognome.isEmpty()) {
                    errore = new Notification("Campo Cognome vuoto", 2000);
                    errore.setId("errore-mod");
                    errore.open();
                } else if (compleanno.isEmpty() || (!checkMaggiorenne(compleanno.getValue()))) {
                    errore = new Notification("Campo Data di nascita vuoto o non valida", 2000);
                    errore.setId("errore-mod");
                    errore.open();
                } else if (altezza.isEmpty()) {
                    errore = new Notification("Campo Altezza vuoto", 2000);
                    errore.setId("errore-mod");
                    errore.open();
                } else if (città.isEmpty()) {
                    errore = new Notification("Campo vuoto");
                    errore.setId("errore-mod");
                    errore.open();
                } else if (luogo_di_nascita.isEmpty()) {
                    errore = new Notification("Campo Luogo di nascita vuoto", 2000);
                    errore.setId("errore-mod");
                    errore.open();
                }else if(!instagram.isEmpty() && instagram.getValue().length() > 100){
                    errore = new Notification("Nickname instagram non valido!", 2000);
                    errore.setId("errore-mod");
                    errore.open();
                }else if(!numero.isEmpty() && !numero.getValue().trim().matches("^\\d{10}$")) {
                    errore = new Notification("Numero di telefono inserito non valido!", 3000, Notification.Position.MIDDLE);
                    errore.setId("errore-mod");
                    errore.open();
                }

                else{
                    studente.getProfilo().setAltezza(altezza.getValue());
                    studente.getProfilo().setNome(nome.getValue());
                    studente.getProfilo().setCognome(cognome.getValue());
                    studente.getProfilo().setDataDiNascita(compleanno.getValue());
                    studente.getProfilo().setResidenza(città.getValue());
                    studente.getProfilo().setLuogoNascita(luogo_di_nascita.getValue());
                    if (!(interessi.isEmpty()))
                        studente.getProfilo().setInteressi(Interessi.valueOf(interessi.getValue()));
                    if (!(occhi.isEmpty()))
                        studente.getProfilo().setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
                    if (!(capelli.isEmpty()))
                        studente.getProfilo().setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
                    if (!(numero.isEmpty()))
                        studente.getProfilo().setNumeroTelefono(numero.getValue());
                    if (!(instagram.isEmpty()))
                        studente.getProfilo().setNickInstagram(instagram.getValue());
                    //hobby
                    ArrayList<Hobby> hob = new ArrayList<Hobby>();
                    for (String s : topicBoxes.getValue()) hob.add(Hobby.valueOf(s));
                    if (!(topicBoxes.isEmpty()))
                        studente.getProfilo().setHobbyList(hob);

                    nome.setEnabled(false);
                    cognome.setEnabled(false);
                    compleanno.setEnabled(false);
                    città.setEnabled(false);
                    luogo_di_nascita.setEnabled(false);
                    occhi.setEnabled(false);
                    capelli.setEnabled(false);
                    topicBoxes.setEnabled(false);
                    altezza.setEnabled(false);
                    interessi.setEnabled(false);
                    numero.setEnabled(false);
                    instagram.setEnabled(false);

                    modifyProfileControl.modificaProfilo(studente.getEmail(), studente.getProfilo());
                    Page pagina = UI.getCurrent().getPage();

                    pagina.reload();

                }
            }
            catch (InvalidFormatException ex){
                Notification.show("I dati inseriti non sono validi! Riprovare").open();
            }

        });
        conferma.setId("conferma-mod-button");
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
                                try {
                                    modifyProfileControl.cambiaPassword(studente.getEmail(), prima_password.getValue(), password_attuale.getValue());
                                }catch (InvalidFormatException ex){
                                    Notification.show("La password inserita non rispetta il formato! Riprova.", 2000, Notification.Position.MIDDLE);
                                }
                                finally {
                                    cambio.close();
                                }
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
