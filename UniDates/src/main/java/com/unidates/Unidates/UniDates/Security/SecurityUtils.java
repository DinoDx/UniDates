package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Service.GestioneUtenti.UtenteService;
import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Stream;

@Component
public class SecurityUtils {


    @Autowired
    private static UtenteService utenteService;


    public SecurityUtils(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    static boolean isFrameworkInternalRequest(HttpServletRequest request){
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public static Utente getLoggedIn() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente utente;
        if(o instanceof UserDetails) {
            utente = utenteService.trovaUtente(((UserDetails) o).getUsername());
        } else {
            utente = utenteService.trovaUtente(o.toString());
        }
        return utente;
    }

    public static List<SessionInformation> getListActiveSession(Utente utente, SessionRegistry sessionRegistry){
        List<Object> userDetails =  sessionRegistry.getAllPrincipals();
        for (Object o : userDetails){
            if( o instanceof User){
                User user = (User) o;
                if(user.getUsername().equals(utente.getEmail())){
                    return sessionRegistry.getAllSessions(user, false);
                }

            }
        }
        return null;
    }
}
