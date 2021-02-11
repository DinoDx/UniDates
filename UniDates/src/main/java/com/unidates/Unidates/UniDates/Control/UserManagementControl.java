package com.unidates.Unidates.UniDates.Control;

import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.*;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Manager.UserManager;

import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.Manager.Registrazione.Publisher;
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
    UserManager userManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;


    @RequestMapping("/registrazioneStudente")
    public void registrazioneStudente(@RequestBody StudenteDTO studenteDTO, @RequestBody HttpServletRequest request) throws InvalidFormatException, AlreadyExistException {

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
        if(checkStudente(studenteDTO)) {
            if(checkProfilo(profiloDTO)) {
                userManager.registrazioneStudente(s, p);
                String appUrl = request.getContextPath();
                publisher.publishOnRegistrationEvent(s, request.getLocale(), appUrl);
            } else throw new InvalidFormatException("Uno o piú campi del profilo non risultano validi");
        }else throw new InvalidFormatException("Email o password inderiti non rispettano il formato");
    }



    @RequestMapping("/isAlreadyRegistered")
    public boolean isAlreadyRegistered(@RequestParam String email){
        if(checkEmail(email)){
            return userManager.isPresent(email);
        }else throw new InvalidFormatException("Formato email non valido!");
    }

    @RequestMapping("/studenteInSessione")
    public StudenteDTO studenteInSessione(){
        return EntityToDto.toDTO((Studente) SecurityUtils.getLoggedIn());
    }


    @GetMapping("/registrationConfirm")
    public String confermaRegistrazione(@RequestParam("token") String token) {
        VerificationToken verificationToken = userManager.getVerificationToken(token);
        System.out.println(token);
        if (verificationToken == null) {
            throw new EntityNotFoundException("Token non valido!");
        }
        Utente utente = userManager.getUtenteByVerificationToken(token);
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            userManager.deleteUtente(utente.getEmail());
            throw new EntityNotFoundException("Token non piú valido!");
        }
        System.out.println("Utente attivato: " + utente);
        userManager.attivaUtenteRegistrato(utente.getEmail());
        return "";
    }

    @RequestMapping("/trovaTuttuStudenti")
    public List<StudenteDTO> trovaTuttiStudenti(){
        List<StudenteDTO> lista = new ArrayList<StudenteDTO>();
        userManager.findAllStudenti().forEach(studente -> lista.add(EntityToDto.toDTO(studente)));
        return lista;
    }


    private boolean checkEmail(String email){
        return email != null && email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    }
    private boolean checkProfilo(ProfiloDTO p) {
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
    private boolean checkStudente(StudenteDTO s) {
        if(checkEmail(s.getEmail()) && s.getPassword() != null){
            return s.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        }
         return false;
    }


}
