package com.unidates.Unidates.UniDates;

import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService testUserConfiguration(){
        Studente studente = new Studente("studenteprova1@gmail.com", "studenteprova1");
        Moderatore moderatore = new Moderatore("moderatore@gmail.com", "moderatore");
        CommunityManager communityManager = new CommunityManager("communitymanager@gmail.com", "communitymanager");


        return new InMemoryUserDetailsManager(Arrays.asList(
                User.withUsername(studente.getEmail()).password(studente.getPassword()).roles(studente.getRuolo().toString()).build(),
                User.withUsername(moderatore.getEmail()).password(moderatore.getPassword()).roles(moderatore.getRuolo().toString()).build(),
                User.withUsername(communityManager.getEmail()).password(communityManager.getPassword()).roles(communityManager.getRuolo().toString()).build()
        ));
    }
}
