package com.unidates.Unidates.UniDates.Manager.Registrazione;

import org.springframework.mail.SimpleMailMessage;

public interface ConfermaRegistrazione {
    SimpleMailMessage confermaRegistrazione(OnRegistrationCompleteEvent event);
}
