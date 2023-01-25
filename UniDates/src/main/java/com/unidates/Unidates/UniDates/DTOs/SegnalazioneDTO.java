package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;

public class SegnalazioneDTO {
    private Long id;
    private Long fotoId;
    private String moderatoreEmail;
    private Motivazione motivazione;
    private String dettagli;

    public SegnalazioneDTO(Motivazione motivazione, String dettagli) {
        this.motivazione = motivazione;
        this.dettagli = dettagli;
    }

    public SegnalazioneDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFotoId() {
        return fotoId;
    }

    public void setFotoId(Long fotoId) {
        this.fotoId = fotoId;
    }

    public String getModeratoreEmail() {
        return moderatoreEmail;
    }

    public void setModeratoreEmail(String moderatoreEmail) {
        this.moderatoreEmail = moderatoreEmail;
    }

    public Motivazione getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(Motivazione motivazione) {
        this.motivazione = motivazione;
    }

    public String getDettagli() {
        return dettagli;
    }

    public void setDettagli(String dettagli) {
        this.dettagli = dettagli;
    }
}
