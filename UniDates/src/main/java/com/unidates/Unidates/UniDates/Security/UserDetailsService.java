package com.unidates.Unidates.UniDates.Security;

import com.unidates.Unidates.UniDates.Exception.BannedUserException;
import com.unidates.Unidates.UniDates.Exception.NotConfirmedAccountException;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        Studente toLog = (Studente) utente;
        if(utente == null)
            throw new UsernameNotFoundException(email);

        else if (toLog.isBanned()){
            throw new BannedUserException();
        }

        else if (utente.isActive()) return User.withUsername(utente.getEmail()).password(utente.getPassword()).roles(utente.getRuolo().toString()).build();
        else throw new NotConfirmedAccountException();
    }
}
