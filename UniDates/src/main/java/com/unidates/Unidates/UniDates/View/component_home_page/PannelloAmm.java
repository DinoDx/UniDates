package com.unidates.Unidates.UniDates.View.component_home_page;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.ClickableCard;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;


@Route(value = "ammi")
public class PannelloAmm extends VerticalLayout {

    public PannelloAmm(){
        HorizontalLayout root = new HorizontalLayout();
        VerticalLayout uno = FirstV();
        root.add(uno);
        add(new H1("Pannello"),root);
    }

    public VerticalLayout FirstV(){
        VerticalLayout first = new VerticalLayout();
        for (int i = 0;i<10;i++){
            first.add(createCard());
        }
        return first;
    }

    public ClickableCard createCard(){
        ClickableCard card = new ClickableCard(componentEvent -> {
            Notification notifica = new Notification();
            VerticalLayout layout = new VerticalLayout();
            Image image = new Image("","immagine");
            Button amm = new Button("Ammonisci");
            notifica.setPosition(Notification.Position.MIDDLE);
            layout.add(image,amm);
            notifica.add(layout);
            notifica.open();
        });


        HorizontalLayout info = new HorizontalLayout();
        VerticalLayout sin = new VerticalLayout();
        VerticalLayout des = new VerticalLayout();

        Image image = new Image("https://randomuser.me/api/portraits/men/42.jpg","ciao");
        //post getnotify
        image.getStyle().set("height","5em");
        image.getStyle().set("width","6em");

        Span span = new Span("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        sin.add(image);
        des.add(span);
        info.add(sin,des);

        card.add(info);
        return card;
    }
}
