package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Entity.Match;
import com.unidates.Unidates.UniDates.Model.Entity.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Model.Enum.Hobby;

import java.util.ArrayList;
import java.util.List;

public class EntityToDto {

    public static UtenteDTO toDTO(Utente u){
        UtenteDTO utenteDTO = new UtenteDTO();
        utenteDTO.setEmail(u.getEmail())
                .setPassword(u.getPassword())
                .setRuolo(u.getRuolo());

        List<NotificaDTO> notificaDTO = new ArrayList<NotificaDTO>();
        u.getListaNotifica().forEach(notifica -> notificaDTO.add(toDTO(notifica)));
        utenteDTO.setListaNotifica(notificaDTO);

        return utenteDTO;
    }

    public static NotificaDTO toDTO(Notifica notifica) {
        NotificaDTO notificaDTO = new NotificaDTO();
        notificaDTO.setUtenteEmail(notifica.getUtente().getEmail());

        notificaDTO.setId(notifica.getId());

        if(notifica.getAmmonimento() != null)
            notificaDTO.setAmmonimento(toDTO(notifica.getAmmonimento()));

        notificaDTO.setCreationTime(notifica.getCreationTime());

        if(notifica.getEmailToMatchWith() != null)
            notificaDTO.setEmailToMatchWith(notifica.getEmailToMatchWith());

        notificaDTO.setFoto(toDTO(notifica.getFoto()));
        notificaDTO.setTipo_notifica(notifica.getTipo_notifica());

        notificaDTO.setTestoNotifica(notifica.getTestoNotifica());

        return notificaDTO;
    }

    public static ModeratoreDTO toDTO(Moderatore m){
        ModeratoreDTO moderatoreDTO = (ModeratoreDTO) toDTO((Studente) m);


        List<SegnalazioneDTO> segnalazioneDTO = new ArrayList<SegnalazioneDTO>();
        m.getSegnalazioneRicevute().forEach(segnalazione -> segnalazioneDTO.add(toDTO(segnalazione)));
        moderatoreDTO.setSegnalazioneRicevute(segnalazioneDTO);

        List<AmmonimentoDTO> ammonimentoDTO = new ArrayList<AmmonimentoDTO>();
        m.getAmmonimentoInviati().forEach(ammonimento -> ammonimentoDTO.add(toDTO(ammonimento)));
        moderatoreDTO.setAmmonimentoInviati(ammonimentoDTO);

         return moderatoreDTO;
    }

    public static StudenteDTO toDTO(Studente s){
       StudenteDTO sdto = (StudenteDTO) toDTO((Utente) s);

               sdto.setAmmonimentiAttivi(s.getAmmonimentiAttivi())
               .setBanned(s.isBanned());

       ArrayList<AmmonimentoDTO> ammonimentoDTO  = new ArrayList<AmmonimentoDTO>();
       s.getListaAmmonimenti().forEach(ammonimento -> ammonimentoDTO.add(EntityToDto.toDTO(ammonimento)));
       sdto.setListaAmmonimenti(ammonimentoDTO);

       ArrayList<SospensioneDTO> sospensioneDTO  = new ArrayList<SospensioneDTO>();
       s.getListaSospensioni().forEach(sospensione -> sospensioneDTO.add(EntityToDto.toDTO(sospensione)));
       sdto.setListaSospensione(sospensioneDTO);

       ArrayList<MatchDTO> matchDTO = new ArrayList<MatchDTO>();
       s.getListaMatch().forEach(match -> matchDTO.add(EntityToDto.toDTO(match)));
       sdto.setListaMatch(matchDTO);

        ArrayList<MatchDTO> matchRicevutiDTO = new ArrayList<MatchDTO>();
        s.getListaMatch().forEach(match -> matchRicevutiDTO.add(EntityToDto.toDTO(match)));
        sdto.setListaMatchRicevuti(matchRicevutiDTO);

        ArrayList<String> listaBloccatiEmail = new ArrayList<java.lang.String>();
        s.getListaBloccati().forEach(studente -> listaBloccatiEmail.add(studente.getEmail()));
        sdto.setListaBloccatiEmail(listaBloccatiEmail);

        sdto.setProfilo(toDTO(s.getProfilo()));

        return sdto;

    }

    public static MatchDTO toDTO(Match match) {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(match.getId());
        matchDTO.setLikedByStudent1(match.isLikedByStudent1());
        matchDTO.setLikeByStudent2(match.isLikeByStudent2());
        matchDTO.setStudente1Email(match.getStudente1().getEmail());
        matchDTO.setStudente2Email(match.getStudente2().getEmail());

        return matchDTO;
    }

    public static SospensioneDTO toDTO(Sospensione sospensione) {
        SospensioneDTO sospensioneDTO = new SospensioneDTO();
        sospensioneDTO.setStudente(sospensione.getStudente().getEmail());
        sospensioneDTO.setDettagli(sospensione.getDettagli());
        sospensioneDTO.setDurata(sospensione.getDurata());
        sospensioneDTO.setId(sospensione.getId());
        return sospensioneDTO;
    }

    public static AmmonimentoDTO toDTO(Ammonimento ammonimento) {
        AmmonimentoDTO ammonimentoDTO = new AmmonimentoDTO();
        ammonimentoDTO.setDettagli(ammonimento.getDettagli());
        ammonimentoDTO.setFoto(EntityToDto.toDTO(ammonimento.getFoto()));
        ammonimentoDTO.setEmailModeratore(ammonimento.getModeratore().getEmail());
        ammonimentoDTO.setEmailStudente(ammonimento.getStudente().getEmail());
        ammonimentoDTO.setMotivazione(ammonimento.getMotivazione());
        ammonimentoDTO.setId(ammonimento.getId());

        return ammonimentoDTO;
    }

    public static FotoDTO toDTO(Foto foto) {
        FotoDTO fotoDTO = new FotoDTO();
        fotoDTO.setImg(foto.getImg());
        fotoDTO.setProfiloId(foto.getProfilo().getId());
        fotoDTO.setVisible(foto.isVisible());
        fotoDTO.setId(foto.getId());

        ArrayList<SegnalazioneDTO> segnalazioneDTO = new ArrayList<SegnalazioneDTO>();
        foto.getSegnalazioneRicevute().forEach(segnalazione -> segnalazioneDTO.add(toDTO(segnalazione)));
        fotoDTO.setSegnalazioneRicevute(segnalazioneDTO);
        return fotoDTO;
    }

    public static SegnalazioneDTO toDTO(Segnalazione segnalazione) {
        SegnalazioneDTO segnalazioneDTO = new SegnalazioneDTO();
        segnalazioneDTO.setDettagli(segnalazione.getDettagli());
        segnalazioneDTO.setFotoId(segnalazione.getFoto().getId());
        segnalazioneDTO.setMotivazione(segnalazione.getMotivazione());
        segnalazioneDTO.setModeratoreEmail(segnalazione.getModeratore().getEmail());
        segnalazioneDTO.setId(segnalazione.getId());

        return segnalazioneDTO;
    }

    public static ProfiloDTO toDTO(Profilo profilo) {
        ProfiloDTO profiloDTO = new ProfiloDTO();
        profiloDTO.setEmailStudente(profilo.getStudente().getEmail());
        profiloDTO.setFotoProfilo(toDTO(profilo.getFotoProfilo()));
        profiloDTO.setId(profilo.getId());
        ArrayList<FotoDTO> fotoDTO = new ArrayList<FotoDTO>();
        profilo.getListaFoto().forEach(foto -> fotoDTO.add(toDTO(foto)));
        profiloDTO.setListaFoto(fotoDTO);

        profiloDTO.setNome(profilo.getNome()).setCognome(profilo.getCognome())
                .setAltezza(profilo.getAltezza()).setColore_occhi(profilo.getColore_occhi())
                .setColori_capelli(profilo.getColori_capelli()).setDataDiNascita(profilo.getDataDiNascita())
                .setLuogoNascita(profilo.getLuogoNascita()).setResidenza(profilo.getResidenza())
                .setSesso(profilo.getSesso()).setInteressi(profilo.getInteressi()).setHobbyList(new ArrayList<Hobby>(profilo.getHobbyList()));


        return profiloDTO;
    }


}
