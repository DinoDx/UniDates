package com.unidates.Unidates.UniDates.Model.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Messaggio")
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private String testoMessaggio;

    public Messaggio() {
    }

    public Messaggio(Chat chat, String testoMessaggio) {
        this.chat = chat;
        this.testoMessaggio = testoMessaggio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getTestoMessaggio() {
        return testoMessaggio;
    }

    public void setTestoMessaggio(String testoMessaggio) {
        this.testoMessaggio = testoMessaggio;
    }
}
