package com.unidates.Unidates.UniDates.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Exception.MatchNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.ChatRepository;
import com.unidates.Unidates.UniDates.Model.Repository.MessageRepository;
import com.unidates.Unidates.UniDates.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Logger;

@Service
public class MessageService {

    private static final Logger logger = Logger.getLogger("Global");
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MatchService matchService;

    public void sendMessage(Utente mittente, Utente destinatario, Messaggio messaggio) throws MatchNotFoundException {
        if(mittente.getRuolo() == Ruolo.STUDENTE && destinatario.getRuolo() == Ruolo.STUDENTE){
            if(matchService.isValidMatch((Studente) mittente, (Studente) destinatario)){
                if(!chatService.chatIsPresent(mittente, destinatario)){
                    logger.info("Chat non presente, creo");
                    Chat created = new Chat(mittente, destinatario, new ArrayList<Messaggio>());
                    chatService.saveChat(created);
                    messaggio.setChat(created);
                    messageRepository.save(messaggio);
                    logger.info("Chat creata, messaggio inviato");
                }
                else{
                    logger.info("Chat trovata");
                    Chat founded = chatService.findChat(mittente, destinatario);
                    messaggio.setChat(founded);
                    messageRepository.save(messaggio);
                    logger.info("Messaggio inviato a chat trovata");
                }
            }
            else throw new MatchNotFoundException();
        }
    }

}
