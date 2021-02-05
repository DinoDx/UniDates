package com.unidates.Unidates.UniDates.Control;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.*;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Service.UtenteService;

import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Service.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/api/UserManager")
public class UserManagementControl {

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    Publisher publisher;

    @Autowired
    UtenteService utenteService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @RequestMapping("/registrazioneStudente")
    public void registrazioneStudente(@RequestBody StudenteDTO studenteDTO, @RequestBody HttpServletRequest request) throws InvalidFormatException {

        ProfiloDTO profiloDTO = studenteDTO.getProfilo();

        Profilo p = new Profilo(profiloDTO.getNome(), profiloDTO.getCognome(), profiloDTO.getLuogoNascita(), profiloDTO.getResidenza(),
        profiloDTO.getDataDiNascita(), profiloDTO.getAltezza(), profiloDTO.getSesso(), profiloDTO.getInteressi(), profiloDTO.getColori_capelli(), profiloDTO.getColore_occhi(),
        new Foto(profiloDTO.getFotoProfilo().getImg()),profiloDTO.getHobbyList());
        if(profiloDTO.getNumeroTelefono()!= null)
            p.setNickInstagram(profiloDTO.getNickInstagram());
        if(profiloDTO.getNickInstagram() != null)
            p.setNumeroTelefono(profiloDTO.getNumeroTelefono());


        Studente s = new Studente(studenteDTO.getEmail(), studenteDTO.getPassword());

        s.setProfilo(p);

        if(checkStudente(s) && checkProfilo(p)) {
            if(!utenteService.isPresent(s.getEmail())){
                utenteService.registrazioneStudente(s, p);
                String appUrl = request.getContextPath();
                publisher.publishOnRegistrationEvent(s, request.getLocale(), appUrl);
            }
            else throw new AlreadyExistUserException();
        }
        else throw new InvalidFormatException("Uno o piú campi inseriti non rispettano il formato");
    }



    @RequestMapping("/isAlreadyRegistered")
    public boolean isAlreadyRegistered(@RequestParam String email){
        return utenteService.isPresent(email);
    }

    @RequestMapping("/studenteInSessione")
    public StudenteDTO studenteInSessione(){
        if(SecurityUtils.getLoggedIn() != null)
            return EntityToDto.toDTO((Studente) SecurityUtils.getLoggedIn());
        else throw new NotAuthorizedException();
    }


    @GetMapping("/registrationConfirm")
    public String confermaRegistrazione(@RequestParam("token") String token) {

        VerificationToken verificationToken = utenteService.getVerificationToken(token);
        System.out.println(token);
        if (verificationToken == null) {
            return "Token non valido";
        }

        Utente utente = utenteService.getUtenteByVerificationToken(token);
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            utenteService.deleteUtente(utente);
            return "Token scaduto";
        }
        System.out.println("Utente attivato: " + utente);
        utente.setActive(true);
        utenteService.salvaUtenteRegistrato(utente);
        return "Utente confermato";
    }

    @RequestMapping("/trovaTuttuStudenti")
    public List<StudenteDTO> trovaTuttiStudenti(){
        List<StudenteDTO> lista = new ArrayList<StudenteDTO>();
        utenteService.findAllStudenti().forEach(studente -> lista.add(EntityToDto.toDTO(studente)));
        return lista;
    }


    private boolean checkEmail(String email){
        if (email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
            return true;

        return false;
    }
    private boolean checkProfilo(Profilo p) {
        if (p.getNome() != null && p.getCognome() != null && p.getLuogoNascita() != null && p.getResidenza() != null && p.getDataDiNascita() != null && p.getAltezza() != 0 && p.getSesso() != null && p.getInteressi() != null && p.getColori_capelli() != null && p.getColore_occhi() != null && p.getHobbyList().size() > 0){
            if (p.getNome().length() > 0 && p.getCognome().length() > 0 && p.getLuogoNascita().length() > 0 && p.getResidenza().length() > 0){
                if(p.getSesso() == Sesso.UOMO || p.getSesso() == Sesso.DONNA || p.getSesso() == Sesso.ALTRO){
                    if(p.getInteressi() == Interessi.UOMINI || p.getInteressi() == Interessi.DONNE || p.getInteressi() == Interessi.ENTRAMBI || p.getInteressi() == Interessi.ALTRO){
                        // Controlli su colore occhi e capelli
                        //if(!p.getHobbyList().contains(null))
                        return true;
                    }
                }
            }
        }

        return false;
    }
    private boolean checkStudente(Studente s) {
        if(s.getEmail() != null && s.getPassword() != null){
            if(checkEmail(s.getEmail())){
                return s.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
            }
        }
         return false;
    }


}
