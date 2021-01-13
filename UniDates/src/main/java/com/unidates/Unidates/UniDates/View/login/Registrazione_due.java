package com.unidates.Unidates.UniDates.View.login;

import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    private MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();

    public Registrazione_due(){
        httpSession.removeAttribute("utente_reg");
        setSizeFull();
        VerticalLayout padre = new VerticalLayout();
        padre.setAlignItems(Alignment.CENTER);
        padre.setId("tuamadre");
        padre.setWidth("70%");
        setAlignItems(Alignment.CENTER);

        HorizontalLayout verticals = new HorizontalLayout();
        verticals.setId("layouts-vertical");
        VerticalLayout sinistra = new VerticalLayout(primo());
        VerticalLayout destra = new VerticalLayout(secondo());

        verticals.add(sinistra,destra);

        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Acconsenti il trattamento dei dati");

        Button conferma = new Button("Conferma",buttonClickEvent -> {
            //Sessione
            Profilo profilo = da_registrare.getProfilo();
            profilo.setResidenza(residenza.getValue());
            profilo.setLuogoNascita(luogo_di_nascita.getValue());
            profilo.setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
            profilo.setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
            profilo.setAltezza(altezza.getValue());
            profilo.setInteressi(Interessi.valueOf(interessi.getValue()));
            //hobby
            ArrayList<Hobby> hobby = new ArrayList<Hobby>();
            for(String s : multiselectComboBox.getValue()) hobby.add(Hobby.valueOf(s));
            profilo.setHobbyList(hobby);
            //image

            gestioneUtentiController.registrazioneStudente(da_registrare, profilo, VaadinServletRequest.getCurrent());
        });

        anchor = new Anchor("/login");
        anchor.add(conferma);
        H2 titolo = new H2("Inserisci i dati del profilo!");
        titolo.setId("titolo-registrazione");

        padre.add(titolo,verticals,checkbox,anchor);
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


    private RadioButtonGroup<String> sessi = new RadioButtonGroup<>();
    public VerticalLayout secondo(){
        VerticalLayout destra = new VerticalLayout();
        destra.setId("layout-destra");

        multiselectComboBox.setWidth("100%");
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

        destra.add(interessi,capelli,occhi,altezza,multiselectComboBox);
        return destra;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(da_registrare == null){
            beforeEnterEvent.rerouteTo(Registrazione.class);
        }
    }
}
