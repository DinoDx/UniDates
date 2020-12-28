package com.unidates.Unidates.UniDates.View.login;

import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.Colore_Occhi;
import com.unidates.Unidates.UniDates.Enum.Colori_Capelli;
import com.unidates.Unidates.UniDates.Enum.Interessi;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.servlet.http.HttpSession;

@Route(value = "registrazione_due", layout = MainViewLogin.class)
@PageTitle("Registrazione_2")
@CssImport("./styles/views/registrazione/registrazione_due.css")
public class Registrazione_due extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);

    Studente da_registrare = (Studente) httpSession.getAttribute("utente_reg");



    private Select<String> interessi = new Select<>();
    private TextField città;
    private TextField luogo;
    private Select<String> capelli = new Select<>();
    private Select<String> occhi = new Select<>();
    private NumberField altezza;
    private FormLayout layout;
    private Button confirm;
    private MemoryBuffer image;
    private Anchor anchor;


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
        multiselectComboBox.setItems("Musica","Cinema","Sport","Calcio","Anime","Manga","Fumetti","Serie Tv","Tv","Arte","Teatro","Politica","Videogiochji","Tecnologia","Viaggi","Storia","Informatica","Libri","Cucina","Natura","Fotografia","Disegno","Motori","Moda","Altro");

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
            /*vedere il metodo per far visualzzare l'immagine caricata*/
        });


        confirm = new Button("Conferma");
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

        //Sessione
        Profilo profilo = da_registrare.getProfilo();
        profilo.setResidenza(città.getValue());
        profilo.setLuogoNascita(luogo.getValue());
        profilo.setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
        profilo.setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
        profilo.setAltezza(altezza.getValue());
        profilo.setInteressi(Interessi.valueOf(interessi.getValue()));
        //profilo.setHobbyList(multiselectComboBox.getValue());

        gestioneUtentiController.registrazioneStudente(da_registrare, profilo);

        layout.add(città,luogo,capelli,occhi,altezza,multiselectComboBox,interessi,value,upload);
        layout.setColspan(multiselectComboBox,3);

        add(layout,buttons);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(da_registrare == null){
            beforeEnterEvent.rerouteTo(Registrazione.class);
        }
    }
}
