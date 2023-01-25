package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.Tipo_Notifica;

import java.time.LocalDate;

public class NotificaDTO {
    private Long id;
    private String utenteEmail;
    private String emailToMatchWith;
    private FotoDTO foto;
    private String testoNotifica;
    private Tipo_Notifica tipo_notifica;
    private AmmonimentoDTO ammonimento;
    private LocalDate creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtenteEmail() {
        return utenteEmail;
    }

    public void setUtenteEmail(String utenteEmail) {
        this.utenteEmail = utenteEmail;
    }

    public String getEmailToMatchWith() {
        return emailToMatchWith;
    }

    public void setEmailToMatchWith(String emailToMatchWith) {
        this.emailToMatchWith = emailToMatchWith;
    }

    public FotoDTO getFoto() {
        return foto;
    }

    public void setFoto(FotoDTO foto) {
        this.foto = foto;
    }

    public String getTestoNotifica() {
        return testoNotifica;
    }

    public void setTestoNotifica(String testoNotifica) {
        this.testoNotifica = testoNotifica;
    }

    public Tipo_Notifica getTipo_notifica() {
        return tipo_notifica;
    }

    public void setTipo_notifica(Tipo_Notifica tipo_notifica) {
        this.tipo_notifica = tipo_notifica;
    }

    public AmmonimentoDTO getAmmonimento() {
        return ammonimento;
    }

    public void setAmmonimento(AmmonimentoDTO ammonimento) {
        this.ammonimento = ammonimento;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "NotificaDTO{" +
                "id=" + id +
                ", utenteEmail='" + utenteEmail + '\'' +
                ", emailToMatchWith='" + emailToMatchWith + '\'' +
                ", foto=" + foto +
                ", testoNotifica='" + testoNotifica + '\'' +
                ", tipo_notifica=" + tipo_notifica +
                ", ammonimento=" + ammonimento +
                ", creationTime=" + creationTime +
                '}';
    }
}
