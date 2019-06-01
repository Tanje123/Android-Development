package com.android.level6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Model.Result;

public class DetailActivity extends AppCompatActivity {
    private String posterPath;
    private String bannerPath;
    private ImageView posterImageView, bannerImageView;
    private TextView movieTitle, movieReleaseDate, movieRating, movieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        posterPath = "http://image.tmdb.org/t/p/w185";
        bannerPath = "http://image.tmdb.org/t/p/w500";
        posterImageView = findViewById(R.id.posterImageView);
        bannerImageView = findViewById(R.id.bannerImageView);
        movieTitle = findViewById(R.id.movieTitle);
        movieReleaseDate = findViewById(R.id.movieReleaseDate);
        movieRating = findViewById(R.id.movieRating);
        movieOverview = findViewById(R.id.movieOverview);

        Bundle extras = getIntent().getExtras();
        Result passedMovie = (Result) extras.get("passedMovie");
        Picasso.get().load(posterPath+passedMovie.getPosterPath()).into(posterImageView);
        Picasso.get().load(bannerPath+passedMovie.getBackdropPath()).into(bannerImageView);
        movieTitle.setText(passedMovie.getTitle());
        movieReleaseDate.setText(passedMovie.getReleaseDate());
        movieRating.setText(passedMovie.getVoteCount().toString());
        movieOverview.setText(passedMovie.getOverview());
    }
}
