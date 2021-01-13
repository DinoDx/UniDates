package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Exception.NotConfirmedAccountException;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.CommunityManagerRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.ModeratoreRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private StudenteRepository studenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Studente utente = studenteRepository.findByEmail(email);

        if(utente == null){
            throw new UsernameNotFoundException(email);
        }
        if (utente.isActive()){
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);
            httpSession.setAttribute("utente", utente);

            return User.withUsername(utente.getEmail()).password(utente.getPassword()).roles(utente.getRuolo().toString()).build();
        }
        else throw new NotConfirmedAccountException();
    }
}
