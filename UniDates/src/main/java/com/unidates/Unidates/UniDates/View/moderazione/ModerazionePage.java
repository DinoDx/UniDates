package com.unidates.Unidates.UniDates.View.moderazione;

import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.Control.ModerationControl;
import com.unidates.Unidates.UniDates.Control.ModifyProfileControl;
import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.ModeratoreDTO;
import com.unidates.Unidates.UniDates.View.navbar.Navbar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "pannellomoderatore", layout = Navbar.class)
public class ModerazionePage extends VerticalLayout {

    @Autowired
    ModerationControl moderationControl;

    @Autowired
    UserManagementControl userManagementControl;

    @Autowired
    ModifyProfileControl modifyProfileControl;

    @Autowired
    InteractionControl interactionControl;

    public ModerazionePage(){
        addAttachListener(event -> create());
    }

    public void create(){

        ModeratoreDTO moderatore =  moderationControl.moderatoreInSessione();

        VerticalLayout verticalLayout =  new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(new ListaSegnalazioni(moderatore, userManagementControl, moderationControl, modifyProfileControl,interactionControl));

        verticalLayout.add(new InfoModeratoreCard(moderatore),horizontalLayout);
        add(verticalLayout);
    }

}
