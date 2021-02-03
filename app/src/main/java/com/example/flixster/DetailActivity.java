package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyADQ6JIBHf3Msf6RTchImZyx-ELk9PdrMM";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String TAG = "DetailActivity";
    TextView detailActivityTvTitle;
    TextView detailActivityTvOverview;
    TextView releaseDate;
    TextView adultInfo;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(String.format(VIDEOS_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() == 0)
                        return;
                    else {
                        String youtubeKey = results.getJSONObject(0).getString("key");
                        Log.d(TAG, youtubeKey);
                        intializeYoutube(youtubeKey);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "jsonException", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        detailActivityTvTitle = findViewById(R.id.detailActivityTvTitle);
        detailActivityTvOverview = findViewById(R.id.detailActivityTvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        releaseDate = findViewById(R.id.releaseDate);
        adultInfo = findViewById(R.id.adultInfo);
        youTubePlayerView = findViewById(R.id.player);

        detailActivityTvTitle.setText(movie.getTitle());
        detailActivityTvOverview.setText(movie.getDescription());
        ratingBar.setRating(movie.getRating());
        releaseDate.setText(String.format("Release Date: %s", movie.getReleaseDate()));
        if(movie.isAdultInfo()) {
            adultInfo.setText("Adult supervision is required for this movie.");
        }
        else {
            adultInfo.setText("Adult supervision is not required for this movie.");
        }
    }

    private void intializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onInitializationSuccess");
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitializationFailure");
            }
        });
    }

}