package Api;

import java.util.Map;

import Model.Password;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

//interface of the api
public interface PasswordApiService {
    //api method to get a password
    @GET("/api")
    Call<Password[]> getPassword(@QueryMap Map<String, String> params);
}
