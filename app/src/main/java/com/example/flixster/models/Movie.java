package com.example.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    public static final String TAG = "Movie";

    String title;
    String description;
    String posterPath;
    String backdropPath;
    float rating;
    int id;
    String releaseDate;
    boolean adultInfo;

    //Empty Constructor for the Parceler
    public Movie() {

    }

    public Movie(JSONObject jsonObject) {
        try {
            posterPath = jsonObject.getString("poster_path");
            title = jsonObject.getString("title");
            description = jsonObject.getString("overview");
            backdropPath = jsonObject.getString("backdrop_path");
            rating = (float) jsonObject.getDouble("vote_average");
            id = jsonObject.getInt("id");
            releaseDate = jsonObject.getString("release_date");
            adultInfo = jsonObject.getBoolean("adult");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
        }
    }

    public static List<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPathPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public float getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isAdultInfo() {
        return adultInfo;
    }
}
