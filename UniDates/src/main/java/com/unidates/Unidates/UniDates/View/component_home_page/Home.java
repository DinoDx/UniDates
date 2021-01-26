package com.unidates.Unidates.UniDates.View.component_home_page;


import com.unidates.Unidates.UniDates.Controller.GestioneInterazioniController;
import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.DTOs.UtenteDTO;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.component.Card_Utente_Home_Component;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import java.util.List;


@Route(value = "", layout = MainView.class)
@CssImport("./styles/views/home/home.css")
@PageTitle("Home")

public class Home extends VerticalLayout {

    @Autowired
    GestioneInterazioniController gestioneInterazioniController;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    @Autowired
    GestioneModerazioneController gestioneModerazioneController;



    List<StudenteDTO> listaStudenti;

    public Home(){
            addAttachListener(event -> create());
    }

//controllare bloccati

    public void create() {
        StudenteDTO s = (StudenteDTO) gestioneUtentiController.utenteInSessione();
        listaStudenti = gestioneUtentiController.trovaTuttiStudenti();
        setAlignItems(Alignment.CENTER);
        VerticalLayout utenti = new VerticalLayout();
        utenti.setAlignItems(Alignment.CENTER);

        Button pannelloAmministrativo = new Button("Pannello amministrazione");
        Anchor pannello = new Anchor("/pannellomoderatore");
        pannello.add(pannelloAmministrativo);


        UtenteDTO utente = gestioneUtentiController.utenteInSessione();


        utente = gestioneUtentiController.utenteInSessione();


        if(utente.getRuolo() == Ruolo.MODERATORE || utente.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            utenti.add(pannello);
        }

        for(StudenteDTO studenteDTO: listaStudenti){
            if(!(studenteDTO.getEmail().equals(utente.getEmail()))) {
               // for(String e : s.getListaBloccatiEmail()) {
                    //if (!(studenteDTO.getEmail().equals(e)))
                        utenti.add(new Card_Utente_Home_Component(gestioneUtentiController, gestioneInterazioniController, studenteDTO, gestioneModerazioneController));
        //        }
            }
        }
        add(utenti);
    }
}


