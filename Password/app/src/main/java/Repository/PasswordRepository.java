package Repository;

import java.util.Map;

import Api.PasswordApi;
import Api.PasswordApiService;
import Model.Password;
import retrofit2.Call;

public class PasswordRepository {
    private PasswordApiService passwordApiService = PasswordApi.create();
    public Call<Password[]> getPassword(Map<String,String> params) {
        return passwordApiService.getPassword(params);
    }

}
