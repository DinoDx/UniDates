package com.unidates.Unidates.UniDates.Security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
        String messaggioErrore = "genericerror";
        switch (exception.getMessage()) {
            case "Utente non trovato!":
                messaggioErrore = "usernotfound";
                break;
            case "Utente non attivo!":
                messaggioErrore = "notactiveuser";
                break;
            case "Utente bannato!":
                messaggioErrore = "banneduser";
                break;
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, messaggioErrore);
    }
}
