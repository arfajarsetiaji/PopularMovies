package com.arfaststudio.prototype.popularmovies;

public class MovieModel {

    private String judul, poster;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPoster() {
        return "https://image.tmdb.org/t/p/w500" + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
