package com.unidates.Unidates.UniDates.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.ChatRepository;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public void saveChat(Chat chat){
        chatRepository.save(chat);
    }

    public Chat findChat(Utente mittente, Utente destinatario) {
        Chat chatMittente = chatRepository.findByMittenteAndDestinatario(mittente, destinatario);

        Chat chatDestinatario = chatRepository.findByMittenteAndDestinatario(destinatario, mittente);

        return chatMittente == null? chatDestinatario: chatMittente;
    }

    public boolean chatIsPresent(Utente mittente, Utente destinatario){
        Chat founded = findChat(mittente, destinatario);
        return founded != null;
    }
}
