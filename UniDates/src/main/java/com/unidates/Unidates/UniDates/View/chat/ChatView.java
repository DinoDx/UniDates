package com.unidates.Unidates.UniDates.View.chat;


import com.github.appreciated.card.ClickableCard;
import com.unidates.Unidates.UniDates.View.main.MainViewChats;
import com.vaadin.componentfactory.Chat;
import com.vaadin.componentfactory.model.Message;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Route( value = "chat",layout = MainViewChats.class)
@CssImport("./styles/views/home/chatview.css")
@RouteAlias("chat")
public class ChatView extends VerticalLayout {

    private static int MESSAGE_LOAD_NUMBER = 100;
    private int messageStartNum1 = 0;
    private int messageStartNum2 = 0;

    public ChatView() {
        setAlignItems(Alignment.CENTER);
        add(createCard());
    }

            public ClickableCard createCard(){
                ClickableCard card = new ClickableCard(componentEvent -> {
                    Notification notifica = new Notification();
                    HorizontalLayout layout = new HorizontalLayout();
                    layout.setAlignItems(FlexComponent.Alignment.CENTER);
                    Button ann = new Button("Chiudi Chat",buttonClickEvent -> {
                        notifica.close();
                    });
                    notifica.setPosition(Notification.Position.MIDDLE);
                    layout.add(createChat(),ann);
                    notifica.add(layout);
                    notifica.open();
                });
                card.setId("openchat");

                HorizontalLayout hori = new HorizontalLayout();
                Image chatimg = new Image("https://randomuser.me/api/portraits/men/42.jpg","ciao");

                HorizontalLayout hori1 = new HorizontalLayout();
                Icon icon = new Icon(VaadinIcon.COMMENT_O);
                Span span = new Span("Entra nella chat di .....");

                hori1.add(icon,span);
                hori1.setId("hori1");
                hori.add(chatimg,hori1);
                hori.setId("hori");
                card.add(hori);
                return card;
            }



                    private Chat createChat(){
                        Chat chat = new Chat();
                        chat.setMessages(generateMessages(messageStartNum1));
                        chat.setLazyLoadTriggerOffset(0);
                        chat.scrollToBottom();

                        chat.addChatNewMessageListener(event -> {
                            chat.addNewMessage(new Message(event.getMessage(),
                                    "https://mir-s3-cdn-cf.behance.net/project_modules/disp/ce54bf11889067.562541ef7cde4.png",
                                    "Ben Smith", true));
                            chat.clearInput();
                            chat.scrollToBottom();
                        });

                        chat.addLazyLoadTriggerEvent(e -> {
                            messageStartNum1 += MESSAGE_LOAD_NUMBER;
                            List<Message> list = generateMessages(messageStartNum1);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {}
                            chat.setLoading(false);
                            chat.addMessagesToTop(list);
                        });
                        return chat;
                    }




                                    private List<Message> generateMessages(int start) {
                                        List<Message> list = new ArrayList<>();

                                        for(int i = start; i < start + MESSAGE_LOAD_NUMBER; i++) {
                                            String body = i +  " Lorem Ipsum on yksinkertaisesti testausteksti, jota tulostus- ja ladontateollisuudet käyttävät. Lorem Ipsum on ollut teollisuuden normaali testausteksti jo 1500-luvulta asti, jolloin tuntematon tulostaja otti kaljuunan ja sekoitti sen tehdäkseen esimerkkikirjan. ";
                                            if (i % 2 == 0) {
                                                list.add(new Message(body, "", "Johana Livingstone", false));
                                            } else {
                                                list.add(new Message(body, "https://mir-s3-cdn-cf.behance.net/project_modules/disp/ce54bf11889067.562541ef7cde4.png", "Ben Smith", true));
                                            }
                                        }

                                        Collections.reverse(list);
                                        return list;
                                    }

}