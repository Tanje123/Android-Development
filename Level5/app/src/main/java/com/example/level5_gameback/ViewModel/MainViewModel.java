package com.example.level5_gameback.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.level5_gameback.Model.Game;
import com.example.level5_gameback.Model.GameRepository;

import java.util.List;

//MainViewModel class which will handle all of the calls from the mainActivity

public class MainViewModel extends AndroidViewModel {
    private GameRepository mRepository;
    private LiveData<List<Game>> mGames;

    //constructor
    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new GameRepository(application.getApplicationContext());
        mGames = mRepository.getAllGames();
    }

    //delete, update, get and insert methods
    public LiveData<List<Game>> getGames() {
        return mGames;
    }


    public void insert(Game game) {
        mRepository.insert(game);
    }


    public void update(Game game) {
        mRepository.update(game);
    }


    public void delete(Game game) {
        mRepository.delete(game);
    }
    //delete method which takes in the position of the item in the list
    public void delete(int pos) {
        mRepository.delete(mGames.getValue().get(pos));
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
