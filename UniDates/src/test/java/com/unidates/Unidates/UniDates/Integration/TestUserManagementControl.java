package com.unidates.Unidates.UniDates.Integration;

import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.SecurityTestConfig;
import com.unidates.Unidates.UniDates.UniDatesApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest(classes = {UniDatesApplication.class, SecurityTestConfig.class})
public class TestUserManagementControl {

    //gli studenti vengono creati dalla classe populator

    @Autowired
    UserManagementControl userManagementControl;

    @Test
    @WithUserDetails("studenteprova1@gmail.com")
    public void testConfiguration(){
        assertEquals(SecurityUtils.getLoggedIn().getRuolo(), Ruolo.STUDENTE);
    }
}
