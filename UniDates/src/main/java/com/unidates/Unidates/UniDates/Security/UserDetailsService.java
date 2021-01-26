package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Exception.BannedUserException;
import com.unidates.Unidates.UniDates.Exception.NotConfirmedAccountException;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
        Studente utente = (Studente) utenteRepository.findByEmail(email);
        if(utente == null){
            throw new BadCredentialsException("Utente non trovato!");
        } else if(!utente.isActive()){
            throw new BadCredentialsException("Utente non attivo!");
        }else if(utente.isBanned()){
            throw new BadCredentialsException("Utente bannato!");
        }
        else return User.withUsername(utente.getEmail()).password(utente.getPassword()).roles(utente.getRuolo().toString()).build();
    }

}
