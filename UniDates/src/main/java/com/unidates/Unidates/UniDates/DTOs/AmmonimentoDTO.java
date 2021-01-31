package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;

public class AmmonimentoDTO {
    private Long id;
    private String emailStudente;
    private String emailModeratore;
    private FotoDTO foto;
    private Motivazione motivazione;
    private String dettagli;

    public AmmonimentoDTO(Motivazione motivazione, String dettagli) {
        this.motivazione = motivazione;
        this.dettagli = dettagli;
    }

    public AmmonimentoDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailStudente() {
        return emailStudente;
    }

    public void setEmailStudente(String emailStudente) {
        this.emailStudente = emailStudente;
    }

    public String getEmailModeratore() {
        return emailModeratore;
    }

    public void setEmailModeratore(String emailModeratore) {
        this.emailModeratore = emailModeratore;
    }

    public FotoDTO getFoto() {
        return foto;
    }

    public void setFoto(FotoDTO foto) {
        this.foto = foto;
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
