package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Service.GestioneUtenti.UtenteService;
import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public final class SecurityUtils {
    @Autowired
    private static UtenteService utenteService;

    public SecurityUtils() {
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
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(p instanceof UserDetails) {
            Utente utente =  utenteService.trovaUtente(((UserDetails) p).getUsername());
            return utente;
        } else {
            Utente utente =  utenteService.trovaUtente(p.toString());
            return utente;
        }
    }
}
