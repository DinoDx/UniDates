package com.unidates.Unidates.UniDates.DTOs;

public class MatchDTO {
    private Long id;
    private String studente1Email;
    private String studente2Email;
    private boolean likedByStudent1;
    private boolean likeByStudent2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudente1Email() {
        return studente1Email;
    }

    public void setStudente1Email(String studente1Email) {
        this.studente1Email = studente1Email;
    }

    public String getStudente2Email() {
        return studente2Email;
    }

    public void setStudente2Email(String studente2Email) {
        this.studente2Email = studente2Email;
    }

    public boolean isLikedByStudent1() {
        return likedByStudent1;
    }

    public void setLikedByStudent1(boolean likedByStudent1) {
        this.likedByStudent1 = likedByStudent1;
    }

    public boolean isLikeByStudent2() {
        return likeByStudent2;
    }

    public void setLikeByStudent2(boolean likeByStudent2) {
        this.likeByStudent2 = likeByStudent2;
    }
}
