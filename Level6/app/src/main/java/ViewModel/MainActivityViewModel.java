package ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;


import java.util.Map;

import Model.Movie;
import Repository.MovieRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository = new MovieRepository();
    private MutableLiveData<Movie> movie = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void getPopularMovies(Map<String,String> params) {
                movieRepository
                        .getPopularMovies(params)
                        .enqueue(new Callback<Movie>() {
                            @Override
                            public void onResponse(Call<Movie> call, Response<Movie> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    movie.setValue(response.body());


                                } else {
                                    error.setValue("Api Error: " + response.message());

                                }
                            }

                            @Override
                            public void onFailure(Call<Movie> call, Throwable t) {
                                error.setValue("Api Error: " + t.getMessage());

                            }
                        });
            }


    public MutableLiveData<Movie> getMovie() {
        return movie;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
