package com.mycompany.mavenproject1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class config {
    private final String apiKey;

    public config(String apiKey) {
        this.apiKey = apiKey;
    }

    //Search movies
    public List<Movie> searchMovies(String query) {
        List<Movie> movies = new ArrayList<>();
        try {
            query = query.replace(" ", "%20");      //requesting 
            String urlStr = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + query;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();  //getting the response

            JSONObject json = new JSONObject(response.toString());
            JSONArray results = json.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                Movie movie = new Movie(
                    obj.getString("title"),
                    obj.optString("overview", "No overview available"),
                    obj.optString("release_date", "N/A"),
                    obj.isNull("poster_path") ? null :
                    "https://image.tmdb.org/t/p/w500" + obj.getString("poster_path")
                );
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    //Fetch trending movies
    public List<Movie> getTrendingMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            String urlStr = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + apiKey;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray results = json.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                Movie movie = new Movie(
                    obj.getString("title"),
                    obj.optString("overview", "No overview available"),
                    obj.optString("release_date", "N/A"),
                    obj.isNull("poster_path") ? null :
                    "https://image.tmdb.org/t/p/w500" + obj.getString("poster_path")
                );
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}
