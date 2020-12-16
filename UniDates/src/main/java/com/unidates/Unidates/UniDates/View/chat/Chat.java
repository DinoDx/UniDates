package com.example.application.views.chat;

import com.example.application.views.main.MainView;
import com.example.application.views.main.MainViewChats;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "chat",layout = MainViewChats.class)
@PageTitle("Chat")
public class Chat extends VerticalLayout {

    public Chat(){

        add(new H1("chat views"));
    }

}
