package com.unidates.Unidates.UniDates.View.login;

import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Route(value = "registrazione_due", layout = MainViewLogin.class)
@PageTitle("Registrazione_2")
@CssImport("./styles/views/registrazione/registrazione_due.css")
public class Registrazione_due extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);

    Studente da_registrare = (Studente) httpSession.getAttribute("utente_reg");

/*
    public Registrazione_due() {

        httpSession.removeAttribute("utente_reg");

        setId("registrazione_2");

        HorizontalLayout buttons = new HorizontalLayout();

        layout = new FormLayout();
        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("15em",1),
                new FormLayout.ResponsiveStep("30em",2),
                new FormLayout.ResponsiveStep("40em",3)
        );

        città = new TextField("Città");
        città.setPlaceholder("Città dove vivi");

        luogo = new TextField("Luogo di nascita");
        luogo.setPlaceholder("Città di nascita");

        capelli.setLabel("Capelli");
        capelli.setPlaceholder("Colore capelli");
        Colori_Capelli [] colore_cap = Colori_Capelli.values();
        capelli.setItems(colore_cap[0].toString(),colore_cap[1].toString(),colore_cap[2].toString(),colore_cap[3].toString(),colore_cap[4].toString(),colore_cap[5].toString(),colore_cap[6].toString());

        occhi.setLabel("Occhi");
        occhi.setPlaceholder("Colore occhi");
        Colore_Occhi [] colore_occhi = Colore_Occhi.values();
        occhi.setItems(colore_occhi[0].toString(),colore_occhi[1].toString(),colore_occhi[2].toString(),colore_occhi[3].toString(),colore_occhi[4].toString(),colore_occhi[5].toString(),colore_occhi[6].toString());

        MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
        multiselectComboBox.setWidth("100%");
        multiselectComboBox.setLabel("Seleziona Topic");
        multiselectComboBox.setPlaceholder("Scelti...");
        Hobby [] topic = Hobby.values();
        List<String> topiclist = new ArrayList<String>();
        for(Hobby h : topic) topiclist.add(h.toString());
        multiselectComboBox.setItems(topiclist);

        altezza = new NumberField("Altezza (cm)");
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);

        interessi.setLabel("Interessi");
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());
        Div value = new Div();
        value.setText("Selezione un opzione");
        interessi.addValueChangeListener(event -> value.setText("Hai selezionato: "+interessi.getValue()));


        image = new MemoryBuffer();
        Upload upload = new Upload(image);
        upload.setMaxFiles(1);
        upload.setDropLabel(new Label("Caricare un immagine che non supera la dimensione 512x384 pixel "));
        upload.setAcceptedFileTypes("image/jpg");
        upload.setMaxFiles(600000);
        Div output = new Div();
        upload.addSucceededListener(event -> {
            vedere il metodo per far visualzzare l'immagine caricata
        });


        confirm = new Button("Conferma",buttonClickEvent -> {
            //Sessione
            Profilo profilo = da_registrare.getProfilo();
            profilo.setResidenza(città.getValue());
            profilo.setLuogoNascita(luogo.getValue());
            profilo.setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
            profilo.setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
            profilo.setAltezza(altezza.getValue());
            profilo.setInteressi(Interessi.valueOf(interessi.getValue()));
            //hobby
            ArrayList<Hobby> hobby = new ArrayList<Hobby>();
            for(String s : multiselectComboBox.getValue()) hobby.add(Hobby.valueOf(s));
            profilo.setHobbyList(hobby);
            //image

            gestioneUtentiController.registrazioneStudente(da_registrare, profilo);
        });
        anchor = new Anchor("/login");
        anchor.add(confirm);

        Button reset = new Button("Reset",buttonClickEvent -> {
            città.setValue("");
            luogo.setValue("");
            capelli.setValue("");
            occhi.setValue("");
            altezza.setValue(null);
            interessi.setValue(null);
            multiselectComboBox.setValue(null);
            //reset image
        });

        confirm.setId("conferma");
        reset.setId("reset");
        buttons.setId("bottoni");
        buttons.add(anchor,reset);

        layout.add(città,luogo,capelli,occhi,altezza,multiselectComboBox,interessi,value,upload);
        layout.setColspan(multiselectComboBox,3);

        add(layout,buttons);

    }
*/


    private Select<String> interessi = new Select<>();
    private TextField nome;
    private TextField residenza;
    private TextField luogo_di_nascita;
    private  TextField cognome;
    private DatePicker picker = new DatePicker();
    private Select<String> capelli = new Select<>();
    private Select<String> occhi = new Select<>();
    private NumberField altezza;
    private Anchor anchor;
    private RadioButtonGroup<String> sessi = new RadioButtonGroup<>();
    private MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
    private Checkbox checkbox;
    private MemoryBuffer image;
    private ArrayList<Foto> foto = new ArrayList<Foto>();


    public Registrazione_due(){
        httpSession.removeAttribute("utente_reg");
        setSizeFull();

        VerticalLayout padre = new VerticalLayout();
        padre.setAlignItems(Alignment.CENTER);
        padre.setId("principale");
        setAlignItems(Alignment.CENTER);

        HorizontalLayout verticals = new HorizontalLayout();
        verticals.setId("layouts-vertical");
        VerticalLayout sinistra = new VerticalLayout(primo());
        VerticalLayout destra = new VerticalLayout(secondo());

        verticals.add(sinistra,destra);

        checkbox = new Checkbox();
        checkbox.setLabel("Acconsenti il trattamento dei dati");

        Button conferma = new Button("Conferma",buttonClickEvent -> {
            //Sessione
                    if(nome.isEmpty()){
                        Notification nome_errore = new Notification("Inserisci il campo Nome",3000, Notification.Position.MIDDLE);
                        nome_errore.open();
                    }
                    else if(cognome.isEmpty()){
                        Notification cognome_errore = new Notification("Inserisci il campo Cognome",3000, Notification.Position.MIDDLE);
                        cognome_errore.open();
                    }
                    else if(picker.isEmpty()){
                        Notification picker_errore = new Notification("Inserisci il campo Data di nascita",3000, Notification.Position.MIDDLE);
                        picker_errore.open();
                    }
                    else if(luogo_di_nascita.isEmpty()){
                        Notification luogo_di_nascita_errore = new Notification("Inserisci il campo Luogo di nascita",3000, Notification.Position.MIDDLE);
                        luogo_di_nascita_errore.open();
                    }
                    else if(residenza.isEmpty()){
                        Notification residenza_errore = new Notification("Inserisci il campo Residenza",3000, Notification.Position.MIDDLE);
                        residenza_errore.open();
                    }
                    else if(sessi.isEmpty()){
                        Notification sessi_errore = new Notification("Inserisci il campo Sessi",3000, Notification.Position.MIDDLE);
                        sessi_errore.open();
                    }
                    else if(interessi.isEmpty()){
                        Notification interessi_errore = new Notification("Inserisci il campo Interessi",3000, Notification.Position.MIDDLE);
                        interessi_errore.open();
                    }
                    else if(capelli.isEmpty()){
                        Notification capelli_errore = new Notification("Inserisci il campo Capelli",3000, Notification.Position.MIDDLE);
                        capelli_errore.open();

                    }
                    else if(occhi.isEmpty()){
                        Notification occhi_errore = new Notification("Inserisci il campo Occhi",3000, Notification.Position.MIDDLE);
                        occhi_errore.open();
                    }
                    else if(altezza.isEmpty()){
                        Notification altezza_errore = new Notification("Inserisci il campo Altezza",3000, Notification.Position.MIDDLE);
                        altezza_errore.open();
                    }
                    else if(multiselectComboBox.isEmpty()){
                        Notification topic_errore = new Notification("Inserisci il campo Topic",3000, Notification.Position.MIDDLE);
                        topic_errore.open();
                    }
                    else if(checkbox.isEmpty()){
                        Notification dati_errore = new Notification("Acconsenti al trattamento dati",3000, Notification.Position.MIDDLE);
                        dati_errore.open();
                    }
                        else {
                            Profilo profilo = new Profilo();
                            profilo.setListaFoto(foto);
                            profilo.setResidenza(residenza.getValue());
                            profilo.setLuogoNascita(luogo_di_nascita.getValue());
                            profilo.setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
                            profilo.setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
                            profilo.setAltezza(altezza.getValue());
                            profilo.setInteressi(Interessi.valueOf(interessi.getValue()));
                            //hobby
                            ArrayList<Hobby> hobby = new ArrayList<Hobby>();
                            for (String s : multiselectComboBox.getValue()) hobby.add(Hobby.valueOf(s));
                            profilo.setHobbyList(hobby);
                            gestioneUtentiController.registrazioneStudente(da_registrare, profilo, VaadinServletRequest.getCurrent());
                            UI.getCurrent().navigate("login");
                        }
        });

        H2 titolo = new H2("Inserisci i dati del profilo!");
        titolo.setId("titolo-registrazione");

        padre.add(titolo,verticals,checkbox,conferma);
        add(padre);
    }

    public VerticalLayout primo(){
        VerticalLayout sinistra = new VerticalLayout();
        sinistra.setId("layout-sinistra");
        nome = new TextField("Nome");
        cognome = new TextField("Cognome");
        picker = new DatePicker("Data di nascita");
        luogo_di_nascita = new TextField("Luogo di nascita");
        residenza = new TextField("Residenza");

        sessi.setLabel("Il tuo sesso:");
        Sesso[] sess = Sesso.values();
        sessi.setItems(sess[0].toString(),sess[1].toString(),sess[2].toString());
        sessi.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        sinistra.add(nome,cognome,picker,luogo_di_nascita,residenza,sessi);
        return sinistra;
    }



    public VerticalLayout secondo(){
        VerticalLayout destra = new VerticalLayout();
        destra.setId("layout-destra");

        multiselectComboBox.setLabel("Seleziona Topic");
        multiselectComboBox.setPlaceholder("Scelti...");
        Hobby [] topic = Hobby.values();
        List<String> topiclist = new ArrayList<String>();
        for(Hobby h : topic) topiclist.add(h.toString());
        multiselectComboBox.setItems(topiclist);

        interessi.setLabel("Interessi");
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());

        capelli.setLabel("Capelli");
        capelli.setPlaceholder("Colore capelli");
        Colori_Capelli [] colore_cap = Colori_Capelli.values();
        capelli.setItems(colore_cap[0].toString(),colore_cap[1].toString(),colore_cap[2].toString(),colore_cap[3].toString(),colore_cap[4].toString(),colore_cap[5].toString(),colore_cap[6].toString());

        occhi.setLabel("Occhi");
        occhi.setPlaceholder("Colore occhi");
        Colore_Occhi [] colore_occhi = Colore_Occhi.values();
        occhi.setItems(colore_occhi[0].toString(),colore_occhi[1].toString(),colore_occhi[2].toString(),colore_occhi[3].toString(),colore_occhi[4].toString(),colore_occhi[5].toString(),colore_occhi[6].toString());

        altezza = new NumberField("Altezza (cm)");
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);

        image = new MemoryBuffer();
        Upload upload = new Upload(image);
        upload.setMaxFiles(1);
        Div output = new Div();
        output.getStyle().set("max-width","20px");
        output.getStyle().set("max-heght","20px");
        upload.addSucceededListener(event -> {
            try{
                foto.add(new Foto(image.getInputStream().readAllBytes()));
                Component component = createComponent(event.getMIMEType(),event.getFileName(),image.getInputStream());
                component.setId("component");
                showOutput(event.getFileName(), component, output); ///where is this defined???
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        destra.add(interessi,capelli,occhi,altezza,multiselectComboBox,upload,output);
        return destra;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(da_registrare == null){
            beforeEnterEvent.rerouteTo(Registrazione.class);
        }
    }

    private Component createComponent(String mimeType, String fileName, InputStream stream) {
        if (mimeType.startsWith("text")) {
            return createTextComponent(stream);
        } else if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {
                byte[] bytes = IOUtils.toByteArray(stream);
                image.getElement().setAttribute("src", new StreamResource(
                        fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setWidth(reader.getWidth(0) + "px");
                            image.setHeight(reader.getHeight(0) + "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }

    private Component createTextComponent(InputStream stream) {
        String text;
        try {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            text = "exception reading stream";
        }
        return new Text(text);
    }

    private void showOutput(String text, Component content, HasComponents outputContainer) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }
}
