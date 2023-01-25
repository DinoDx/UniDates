package com.unidates.Unidates.UniDates.View.home;


import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.Control.ModerationControl;
import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.DTOs.UtenteDTO;
import com.unidates.Unidates.UniDates.View.navbar.Navbar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "", layout = Navbar.class)
@CssImport("./styles/views/home/home.css")
@PageTitle("Home")

public class HomePage extends VerticalLayout {

    @Autowired
    InteractionControl interactionControl;

    @Autowired
    UserManagementControl userManagementControl;

    @Autowired
    ModerationControl moderationControl;



    List<StudenteDTO> listaStudenti;

    public HomePage(){
            addAttachListener(event -> create());
    }

//controllare bloccati

    public void create() {
        StudenteDTO s = (StudenteDTO) userManagementControl.studenteInSessione();
        listaStudenti = userManagementControl.trovaStudentiAffini(userManagementControl.studenteInSessione().getEmail());
        setAlignItems(Alignment.CENTER);
        VerticalLayout utenti = new VerticalLayout();
        utenti.setAlignItems(Alignment.CENTER);

        UtenteDTO utente = userManagementControl.studenteInSessione();


        utente = userManagementControl.studenteInSessione();

        for(StudenteDTO studenteDTO: listaStudenti){
            if(!(studenteDTO.getEmail().equals(utente.getEmail())) && !(s.getListaBloccatiEmail().contains(studenteDTO.getEmail())) && !(studenteDTO.isBanned()) && !(studenteDTO.getListaBloccatiEmail().contains(s.getEmail()))) {
                        utenti.add(new CardUtenteHome(userManagementControl, interactionControl, studenteDTO, moderationControl));
            }
        }
        add(utenti);
    }
}


