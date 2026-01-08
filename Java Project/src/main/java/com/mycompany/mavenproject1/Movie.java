package com.mycompany.mavenproject1;

public class Movie {
    private String title;
    private String overview;
    private String releaseDate;
    private String posterUrl;

    public Movie(String title, String overview, String releaseDate, String posterUrl) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
    }

    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getReleaseDate() { return releaseDate; }
    public String getPosterUrl() { return posterUrl; }

    @Override
    public String toString() {
        return title + " (" + releaseDate + ")";
    }
}
