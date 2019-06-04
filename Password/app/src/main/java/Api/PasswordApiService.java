package Api;

import java.util.Map;

import Model.Password;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PasswordApiService {
    @GET("/api")
    Call<Password[]> getPassword(@QueryMap Map<String, String> params);
}
