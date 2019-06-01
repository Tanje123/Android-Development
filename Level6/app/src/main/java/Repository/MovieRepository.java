package Repository;

import java.util.Map;

import API.MovieApi;
import API.MovieApiService;
import Model.Movie;
import retrofit2.Call;

public class MovieRepository {
    private MovieApiService movieApiService = MovieApi.create();

    public Call<Movie> getPopularMovies(Map<String,String> params) {
        return movieApiService.getPopularMovies(params);
    }

}
