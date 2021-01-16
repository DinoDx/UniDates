package com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Exception.MatchNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.ChatRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.MessageRepository;
import com.unidates.Unidates.UniDates.Model.Service.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    Publisher publisher;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MatchService matchService;

    public void creaMessaggio(Utente mittente, Utente destinatario, Messaggio messaggio) throws MatchNotFoundException {

        messaggio.setEmailMittente(mittente.getEmail());
        messaggio.setEmailDestinatario(destinatario.getEmail());

        if (mittente.getRuolo() == Ruolo.STUDENTE && destinatario.getRuolo() == Ruolo.STUDENTE) { // se due studenti si contatano a vicenda
            if (matchService.isValidMatch((Studente) mittente, (Studente) destinatario)) {
                inviaMessaggio(mittente, destinatario, messaggio);
            } else throw new MatchNotFoundException();
        }
        else  { // se un moderatore contatta uno studente o viceversa o due moderatori si contattano tra di loro
            inviaMessaggio(mittente, destinatario, messaggio);
        }
    }

    private void inviaMessaggio(Utente mittente, Utente destinatario, Messaggio messaggio) {
        if (!isPresent(mittente, destinatario)) {
            Chat created = creaChat(messaggio, mittente, destinatario);
            messaggio.setChat(created);
            messageRepository.save(messaggio);
            publisher.publishMessage(messaggio);
        } else {
            Chat founded = trovaChat(mittente, destinatario);
            messaggio.setChat(founded);
            messageRepository.save(messaggio);
            publisher.publishMessage(messaggio);
        }
    }

    public List<Chat> visualizzaArchivioChat(Utente u) {
        List<Chat> archivio = u.getMittente();
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


    private Chat creaChat(Messaggio messaggio, Utente mittente, Utente destinatario) {
        Chat created = new Chat(mittente, destinatario, new ArrayList<Messaggio>());
        chatRepository.save(created);
        return  created;
    }
}


