package API;

import java.util.Map;

import Model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MovieApiService {
    @GET("/3/discover/movie")
    Call<Movie> getPopularMovies(@QueryMap Map<String, String> params);
}
