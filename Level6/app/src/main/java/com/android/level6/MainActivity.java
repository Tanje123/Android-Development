package com.android.level6;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import API.MovieApiService;
import Model.Movie;
import Model.Result;
import RecyclerView.ResultAdapter;
import ViewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel viewModel;
    List<Result> fetchedMovies = new ArrayList<>();
    private EditText editTextYear;
    private Button buttonSubmit;
    private String resultYear;
    private ResultAdapter mAdapter;
    private RecyclerView mRecyclerView;
    Map<String,String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.RecyclerView);
        params = new HashMap<String, String>();
        mAdapter = new ResultAdapter(this,fetchedMovies);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {
            fetchedMovies.clear();
            fetchedMovies = movie.getResults();
            if (movie.getResults().size() == 0) {
                Toast.makeText(MainActivity.this, "No data available"
                        , Toast.LENGTH_LONG)
                        .show();
            }
        });

        viewModel.getError().observe(this, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG)
                .show());
        editTextYear = findViewById(R.id.EditTextYear);
        buttonSubmit = findViewById(R.id.ButtonSubmit);
        buttonSubmit.setOnClickListener(v -> {
            if (editTextYear.getText().length() != 4) {
                Toast.makeText(MainActivity.this, "Please fill in a valid year"
                        , Toast.LENGTH_LONG)
                        .show();
            } else {
                resultYear = editTextYear.getText().toString();
                setupParams();
                viewModel.getPopularMovies(params);
                mAdapter.setmResults(fetchedMovies);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    public void setupParams() {
        params.put("api_key", "5a8d89661bb0adeca58cbed4e870e9a1");
        params.put("language", "en-US");
        params.put("sort_by", "popularity.desc");
        params.put("include_adult", "false");
        params.put("include_video", "false");
        params.put("page", "1");
        params.put("year", resultYear);
    }
}
