package com.unidates.Unidates.UniDates.View.component_home_page;


import com.github.appreciated.card.Card;
import com.unidates.Unidates.UniDates.View.main.MainViewGame;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import de.mekaso.vaadin.addons.Carousel;
import java.util.concurrent.TimeUnit;

@Route(value = "game",layout = MainViewGame.class)
@CssImport("./styles/views/home/game.css")
public class GameMatch extends VerticalLayout {

    Carousel carousel = Carousel.create();

    public GameMatch(){
        setId("principale");
        super.setAlignItems(Alignment.CENTER);

        carousel.setId("caro");
        carousel.withDuration(3,TimeUnit.MINUTES);
        carousel.withAutoplay();
        for(int i = 0; i < 10; i++){
            carousel.add(createCards());
        }

        HorizontalLayout bott = createHorizontal();
        add(carousel,bott);

    }

    public HorizontalLayout createHorizontal(){

        HorizontalLayout orient = new HorizontalLayout();
        Button conferma = new Button("Conferma");
        conferma.setId("c");
        Button skip = new Button("Skip Game",buttonClickEvent -> {
            UI.getCurrent().navigate(Home.class);
        });
        skip.setId("s");

        orient.add(conferma,skip);
        return orient;
    }

    public Card createCards(){
        Card person = new Card();
        person.setId("card");
        VerticalLayout totale = new VerticalLayout();
        Image imageprofilo = new Image("https://randomuser.me/api/portraits/men/42.jpg","MAMMT");

        HorizontalLayout buttons = new HorizontalLayout();
        Button a = getLikeButton();
        Button freccia = new Button("Skip",new Icon(VaadinIcon.BOMB),buttonClickEvent -> {
            carousel.next();
        });
        freccia.getStyle().set("color","white");
        buttons.add(a,freccia);

        totale.add(imageprofilo,buttons);
        person.add(totale);
        return person;
    }

    private Button getLikeButton() {
        Button like = new Button(new Icon(VaadinIcon.HEART));
        like.getStyle().set("color","white");
        like.addClickListener((buttonClickEvent)->{
            Style style = buttonClickEvent.getSource().getStyle();
            if(style.get("color").equals("white")) {
                buttonClickEvent.getSource().getStyle().set("color", "red");
            }else {
                style.set("color","white");
            }
        });
        return like;
    }

}
