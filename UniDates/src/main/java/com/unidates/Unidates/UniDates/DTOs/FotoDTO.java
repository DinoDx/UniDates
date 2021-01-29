package com.unidates.Unidates.UniDates.DTOs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FotoDTO {
    private Long Id;
    private Long profiloId;
    private List<SegnalazioneDTO> segnalazioneRicevute;
    private byte[] img;
    private boolean isVisible;
    private LocalDateTime creazione;

    public FotoDTO(){
        this.segnalazioneRicevute = new ArrayList<SegnalazioneDTO>();
        this.isVisible = true;
    }
    public FotoDTO(byte[] img) {
        this.segnalazioneRicevute = new ArrayList<SegnalazioneDTO>();
        this.img = img;
        this.isVisible = true;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getProfiloId() {
        return profiloId;
    }

    public void setProfiloId(Long profiloId) {
        this.profiloId = profiloId;
    }

    public List<SegnalazioneDTO> getSegnalazioneRicevute() {
        return segnalazioneRicevute;
    }

    public void setSegnalazioneRicevute(List<SegnalazioneDTO> segnalazioneRicevute) {
        this.segnalazioneRicevute = segnalazioneRicevute;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public LocalDateTime getCreazione() {
        return creazione;
    }

    public void setCreazione(LocalDateTime creazione) {
        this.creazione = creazione;
    }
}
