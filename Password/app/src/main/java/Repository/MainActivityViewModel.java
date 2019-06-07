package Repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.Map;

import Model.Password;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//main activity
public class MainActivityViewModel extends AndroidViewModel {
    private PasswordRepository passwordRepository = new PasswordRepository();
    private MutableLiveData<Password[]> password = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }


    public MutableLiveData<Password[]> getPassword() {
        return password;
    }

    public void fetchPassword(Map<String,String> params) {
//fetch password reposityo data
        passwordRepository
                .getPassword(params)
                .enqueue(new Callback<Password[]>() {
                    @Override
                    public void onResponse(Call<Password[]> call, Response<Password[]> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            password.setValue(response.body());
                        } else {
                            error.setValue("Api Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Password[]> call, Throwable t) {
                        error.setValue("Api Error: " + t.getMessage());
                    }
                });

    }
}
