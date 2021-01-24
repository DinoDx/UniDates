package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import java.util.ArrayList;
import java.util.List;

public class ModeratoreDTO extends StudenteDTO{
    private List<AmmonimentoDTO> ammonimentoInviati;
    private List<SegnalazioneDTO> segnalazioneRicevute;

    public ModeratoreDTO(String email, String password, ProfiloDTO profilo) {
        super(email, password, profilo);
        setRuolo(Ruolo.MODERATORE);
        ammonimentoInviati = new ArrayList<AmmonimentoDTO>();
        segnalazioneRicevute = new ArrayList<SegnalazioneDTO>();
    }
    public ModeratoreDTO() {
        setRuolo(Ruolo.MODERATORE);
        ammonimentoInviati = new ArrayList<AmmonimentoDTO>();
        segnalazioneRicevute = new ArrayList<SegnalazioneDTO>();
    }


    public ModeratoreDTO(List<AmmonimentoDTO> ammonimentoInviati, List<SegnalazioneDTO> segnalazioneRicevute) {
        this.ammonimentoInviati = ammonimentoInviati;
        this.segnalazioneRicevute = segnalazioneRicevute;
    }

    public List<AmmonimentoDTO> getAmmonimentoInviati() {
        return ammonimentoInviati;
    }

    public void setAmmonimentoInviati(List<AmmonimentoDTO> ammonimentoInviati) {
        this.ammonimentoInviati = ammonimentoInviati;
    }

    public List<SegnalazioneDTO> getSegnalazioneRicevute() {
        return segnalazioneRicevute;
    }

    public void setSegnalazioneRicevute(List<SegnalazioneDTO> segnalazioneRicevute) {
        this.segnalazioneRicevute = segnalazioneRicevute;
    }

}
