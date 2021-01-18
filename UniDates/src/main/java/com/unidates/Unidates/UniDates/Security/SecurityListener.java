package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import org.apache.juli.logging.Log;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SecurityListener {


}
