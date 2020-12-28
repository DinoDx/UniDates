package com.unidates.Unidates.UniDates.Enum;

public enum Hobby {
    MUSICA("MUSICA"),
    CINEMA("CINEMA"),
    SPORT("SPORT"),
    CALCIO("CALCIO"),
    ANIME("ANIME"),
    MANGA("MANGA"),
    FUMETTI("FUMETTI"),
    SERIE_TV("SERIE_TV"),
    TV("TV"),
    ARTE("ARTE"),
    TEATRO("TEATRO"),
    POLITICA("POLITICA"),
    VIDEOGIOCHI("VIDEOGIOCHI"),
    TECNOLOGIA("TECNOLOGIA"),
    VIAGGI("VIAGGI"),
    STORIA("STORIA"),
    INFORMATICA("INFORMATICA"),
    LIBRI("LIBRI"),
    CUCINA("CUCINA"),
    NATURA("NATURA"),
    FOTOGRAFIA("FOTOGRAFIA"),
    DISEGNO("DISEGNO"),
    MOTORI("MOTORI"),
    MODA("MODA"),
    ALTRO("ALTRO");

    private String topic;
    private Hobby(String topic){
        this.topic = topic;
    }

    @Override
    public String toString() {
        return topic;
    }
}
