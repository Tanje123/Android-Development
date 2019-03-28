package com.example.level5_gameback.Model;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.level5_gameback.database.GameDoa;
import com.example.level5_gameback.database.GameRoomDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//GameRepostory class handles all of the actions between the MainViewModel and the GameDoa
public class GameRepository {
    private GameRoomDatabase mAppDatabase;
    private GameDoa mGameDao;
    private LiveData<List<Game>> mGames;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    //Constructor
    public GameRepository (Context context) {
        mAppDatabase = GameRoomDatabase.getDatabase(context);
        mGameDao = mAppDatabase.gameDoa();
        mGames = mGameDao.getAllGames();
    }

    //getters
    public LiveData<List<Game>> getAllGames() {
        return mGames;
    }

    //insert new game intro database
    public void insert(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mGameDao.insert(game);
                } catch (SQLiteConstraintException ex) {
                    //when a SQLiteConstraintException occurs it means there is already a game with
                    //this title in the database. Because we are using title as primaire keys in the
                    //database, whe can use the update method to overwrite the old information
                    //with the new information
                    update(game);
                }
            }
        });
    }

    //Update method which updates a old game with a new game
    public void update(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.update(game);
            }
        });
    }

    //delete a specific game from the db
    public void delete(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.delete(game);
            }
        });
    }
    //delete all of the games from the db
    public void deleteAll() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.deleteAll();
            }
        });
    }
}
