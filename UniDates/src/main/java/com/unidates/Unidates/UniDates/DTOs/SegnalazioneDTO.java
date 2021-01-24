package com.unidates.Unidates.UniDates.DTOs;

public class SegnalazioneDTO {
    private Long id;
    private Long fotoId;
    private String moderatoreEmail;
    private String motivazione;
    private String dettagli;

    public SegnalazioneDTO(String motivazione, String dettagli) {
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

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getDettagli() {
        return dettagli;
    }

    public void setDettagli(String dettagli) {
        this.dettagli = dettagli;
    }
}
