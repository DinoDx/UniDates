package com.unidates.Unidates.UniDates.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Exception.MatchNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.ChatRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MatchService matchService;

    public void inviaMessaggio(Utente mittente, Utente destinatario, Messaggio messaggio) throws MatchNotFoundException {
        if (mittente.getRuolo() == Ruolo.STUDENTE && destinatario.getRuolo() == Ruolo.STUDENTE) {
            if (matchService.isValidMatch((Studente) mittente, (Studente) destinatario)) {
                if (!isPresent(mittente, destinatario)) {
                    aggiungiChat(messaggio, mittente, destinatario);
                } else {
                    Chat founded = trovaChat(mittente, destinatario);
                    messaggio.setChat(founded);
                    messageRepository.save(messaggio);
                }
            } else throw new MatchNotFoundException();
        }
    }

    public List<Chat> visualizzaArchivioChat(Utente u) {
        List<Chat> archivio = (List<Chat>) u.getMittente();
        archivio.addAll(u.getDestinatario());

        return archivio;
    }

    public boolean eliminaChat(Chat c) {
        chatRepository.delete(c);
        return true;
    }

    private Chat trovaChat(Utente mittente, Utente destinatario) {
        Chat chatMittente = chatRepository.findByMittenteAndDestinatario(mittente, destinatario);
        Chat chatDestinatario = chatRepository.findByMittenteAndDestinatario(destinatario, mittente);
        return chatMittente == null ? chatDestinatario : chatMittente;
    }

    private boolean isPresent(Utente mittente, Utente destinatario) {
        Chat founded = trovaChat(mittente, destinatario);
        return founded != null;
    }


    private void aggiungiChat(Messaggio messaggio, Utente mittente, Utente destinatario) {
        Chat created = new Chat(mittente, destinatario, new ArrayList<Messaggio>());
        chatRepository.save(created);
        messaggio.setChat(created);
        messageRepository.save(messaggio);
    }
}


