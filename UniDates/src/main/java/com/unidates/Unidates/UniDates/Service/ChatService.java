package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Model.Entity.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    public void createChat(Chat chat, Utente utente){
        chat.setUtente(utente);
        chat.setMessaggi(new ArrayList<Messaggio>());
        chatRepository.save(chat);
    }
}
