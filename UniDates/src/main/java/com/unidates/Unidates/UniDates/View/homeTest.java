package com.unidates.Unidates.UniDates.View;

import com.unidates.Unidates.UniDates.Service.UtenteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("homeTest")
public class homeTest extends VerticalLayout {

    @Autowired
    UtenteService utenteService;

    public homeTest() {
        Button button = new Button("Ciao", event -> {
            utenteService.testPython();
        });
            add(button);
    }


}
