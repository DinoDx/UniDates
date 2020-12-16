package com.example.application.views.login;

import com.example.application.views.main.MainViewLogin;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.gatanaso.MultiselectComboBox;

@Route(value = "registrazione_due", layout = MainViewLogin.class)
@PageTitle("Registrazione_2")
@CssImport("./styles/views/registrazione/registrazione_due.css")
public class Registrazione_due extends VerticalLayout {


    private Select<String> interessi = new Select<>();
    private TextField città;
    private TextField luogo;
    private TextField capelli;
    private TextField occhi;
    private NumberField altezza;
    private FormLayout layout;
    private Button confirm;
    private MemoryBuffer image;
    private Anchor anchor;


    public Registrazione_due() {
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
        capelli = new TextField("Capelli");
        capelli.setPlaceholder("Colore capelli");
        occhi = new TextField("Occhi");
        occhi.setPlaceholder("Colore occhi");


        MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
        multiselectComboBox.setWidth("100%");
        multiselectComboBox.setLabel("Seleziona Topic");
        multiselectComboBox.setPlaceholder("Scelti...");
        multiselectComboBox.setItems("Musica","Cinema","Sport","Calcio","Anime","Manga/Fumetti","Serie Tv","Tv","Arte","Teatro","Politica","Videogiochji","Tecnologia","Viaggi","Storia","Informatica","Libri","Cucina","Natura","Fotografia","Disegno","Motori","Moda","Altro");

        altezza = new NumberField("Altezza (cm)");
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);

        interessi.setLabel("Interessi");
        interessi.setItems("Uomo","Donna","Altro");
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

        Button reset = new Button("Reset");

        confirm.setId("conferma");
        reset.setId("reset");
        buttons.setId("bottoni");
        buttons.add(anchor,reset);



        layout.add(città,luogo,capelli,occhi,altezza,multiselectComboBox,interessi,value,upload);
        layout.setColspan(multiselectComboBox,3);

        add(layout,buttons);

    }

}
