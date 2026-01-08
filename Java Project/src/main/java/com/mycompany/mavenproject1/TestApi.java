package com.mycompany.mavenproject1;

import java.util.List;

public class TestApi {
    public static void main(String[] args) {
        
        config client = new config("a47d66dcad20b616da0c76f004acedbd");

        
        List<Movie> movies = client.searchMovies("Inception");

        
        for (Movie m : movies) {
            System.out.println("Title: " + m.getTitle());
            System.out.println("Overview: " + m.getOverview());
            System.out.println("Release Date: " + m.getReleaseDate());
            System.out.println("Poster: " + m.getPosterUrl());
        }
    }
}
