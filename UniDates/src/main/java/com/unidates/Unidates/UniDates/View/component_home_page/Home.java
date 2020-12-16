package com.unidates.Unidates.UniDates.View.component_home_page;


import com.example.application.views.Person;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.List;


@Route(value = "home", layout = MainView.class)
@CssImport("./styles/views/home/home.css")
@PageTitle("Home")
public class Home extends Div implements AfterNavigationObserver {

    Grid<Person> grid = new Grid<>();

    public Home(){
        setId("card-home-view");
        addClassName("card-home-view");
        setSizeFull();
        grid.setWidth("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn((person -> createCard(person)));
        add(grid);
    }

    private VerticalLayout createCard(Person person){
        VerticalLayout card = new VerticalLayout();
        card.addClassName("card-home");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        //HEADER
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header-card-home");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span nome = new Span(person.getName());
        nome.addClassName("nome");
        Span data = new Span(person.getDate());
        data.addClassName("data");
        header.add(nome,data);

        //MIDDLE
        VerticalLayout middle = new VerticalLayout();
        middle.addClassName("middle-card-home");
        middle.setSpacing(false);
        middle.getThemeList().add("spacing-s");
        Image image = new Image();
        image.setId("image");
        image.setSrc(person.getImage());
        middle.add(image);

        //FOOTER
        HorizontalLayout footer = new HorizontalLayout();
        footer.addClassName("footer-card-home");
        footer.setSpacing(false);
        footer.getThemeList().add("spacing-s");

        //Bottone like
        Button like = getLikeButton();

        //Notifica bottone report
        Notification notifica = new Notification();
        TextArea reporting = new TextArea("Inserisci moticazione segnalazione:");
        Button invio = new Button("Invia report",buttonClickEvent -> {
            //implmentare invio segnalazione
        });
        Button annulla = new Button("Annulla",buttonClickEvent -> {
            notifica.close();
        });
        invio.getStyle().set("margin-left","2em");
        annulla.getStyle().set("margin-left","3em");

        //bottone report
        Button report = new Button("Report",new Icon(VaadinIcon.PENCIL),buttonClickEvent->{
            notifica.open();
        });
        report.getStyle().set("color","white");

        notifica.add(reporting,invio,annulla);
        footer.add(like,report);
        card.add(header,middle,footer);

        return card;
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

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

        // Set some data when this view is displayed.
        List<Person> persons = Arrays.asList( //
                createPerson("https://randomuser.me/api/portraits/men/42.jpg", "John Smith", "May 8",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/42.jpg", "Abagail Libbie", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/24.jpg", "Emmy Elsner", "Apr 22",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/76.jpg", "Alf Huncoot", "Apr 21",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/76.jpg", "Lidmila Vilensky", "Apr 17",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/94.jpg", "Jarrett Cawsey", "Apr 17",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/94.jpg", "Tania Perfilyeva", "Mar 8",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/16.jpg", "Ivan Polo", "Mar 5",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/16.jpg", "Emelda Scandroot", "Mar 5",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/67.jpg", "Marcos Sá", "Mar 4",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/67.jpg", "Jacqueline Asong", "Mar 2",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20")

        );

        grid.setItems(persons);
    }



    private static Person createPerson(String image, String name, String date, String post, String likes,
                                       String comments, String shares) {
        Person p = new Person();
        p.setImage(image);
        p.setName(name);
        p.setDate(date);
        p.setPost(post);
        p.setLikes(likes);
        p.setComments(comments);
        p.setShares(shares);

        return p;
    }
}


