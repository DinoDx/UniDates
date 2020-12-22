package com.unidates.Unidates.UniDates.View.component_home_page;

import com.github.appreciated.card.ClickableCard;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(value = "ammi")
@CssImport("./styles/views/home/pannelloamm.css")
public class PannelloAmm extends VerticalLayout {

    public PannelloAmm(){
        Div div = new Div();
        div.setId("div");
        setAlignItems(Alignment.CENTER);
        HorizontalLayout root = new HorizontalLayout();
        VerticalLayout uno = FirstV();
        VerticalLayout due = FirstV();
        root.add(uno,due);
        div.add(root);
        add(new H1("Pannello Amministrativo"),div);
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
            Button ann = new Button("Annulla",buttonClickEvent -> {
                notifica.close();
            });
            notifica.setPosition(Notification.Position.MIDDLE);
            layout.add(image,amm,ann);
            notifica.add(layout);
            notifica.open();
        });
        card.setId("singlecard");


        HorizontalLayout info = new HorizontalLayout();
        info.setSpacing(false);
        info.getThemeList().add("spacing-s");
        VerticalLayout sin = new VerticalLayout();
        VerticalLayout des = new VerticalLayout();
        des.setId("des");
        sin.setId("sin");

        Image image = new Image("https://randomuser.me/api/portraits/men/42.jpg","ciao");
        //post getnotify


        Span span = new Span("In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).");


        sin.add(image);
        des.add(new H6("Motivo ammonimento"),span);
        info.add(sin,des);

        card.add(info);
        return card;
    }
}
