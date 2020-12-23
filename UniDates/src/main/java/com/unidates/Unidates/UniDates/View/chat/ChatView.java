package com.unidates.Unidates.UniDates.View.chat;


import com.github.appreciated.card.ClickableCard;
import com.unidates.Unidates.UniDates.View.main.MainViewChats;
import com.vaadin.componentfactory.Chat;
import com.vaadin.componentfactory.model.Message;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
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
@RouteAlias("chat")
public class ChatView extends VerticalLayout {
    private static int MESSAGE_LOAD_NUMBER = 100;
    private int messageStartNum1 = 0;
    private int messageStartNum2 = 0;

    public ChatView() {
        add(createCard());
    }

            public ClickableCard createCard(){
                ClickableCard card = new ClickableCard(componentEvent -> {
                    Notification notifica = new Notification();
                    VerticalLayout layout = new VerticalLayout();
                    layout.setAlignItems(FlexComponent.Alignment.CENTER);
                    layout.getStyle().set("width","40em");
                    Button ann = new Button("Annulla",buttonClickEvent -> {
                        notifica.close();
                    });
                    notifica.setPosition(Notification.Position.MIDDLE);
                    layout.add(createChat(),ann);
                    notifica.add(layout);
                    notifica.open();
                });

                card.getStyle().set("width","10em");
                card.getStyle().set("height","10em");
                card.add("CIAOOOOOOOOOOOOOOOOOO");
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