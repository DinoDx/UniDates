package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static UserManager userManager;


    public SecurityUtils(UserManager userManager) {
        this.userManager = userManager;
    }

    static boolean isFrameworkInternalRequest(HttpServletRequest request){
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public static Utente getLoggedIn() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente utente;
        if(o instanceof UserDetails) {
            utente = userManager.trovaUtente(((UserDetails) o).getUsername());
        } else {
            utente = userManager.trovaUtente(o.toString());
        }
        return utente;
    }

    public static void  forceLogout(Utente utente, SessionRegistry sessionRegistry){
        List<Object> userDetails =  sessionRegistry.getAllPrincipals();
        for (Object o : userDetails){
            if( o instanceof User){
                User user = (User) o;
                if(user.getUsername().equals(utente.getEmail())){
                    List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(user, false);
                    if(sessionInformations != null)
                        sessionInformations.forEach(SessionInformation::expireNow);
                }

            }
        }
    }

}
