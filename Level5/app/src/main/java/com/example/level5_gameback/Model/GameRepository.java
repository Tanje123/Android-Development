package com.example.level5_gameback.Model;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.level5_gameback.database.GameDoa;
import com.example.level5_gameback.database.GameRoomDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GameRepository {
    private GameRoomDatabase mAppDatabase;
    private GameDoa mGameDao;
    private LiveData<List<Game>> mGames;
    private Executor mExecutor = Executors.newSingleThreadExecutor();
    public GameRepository (Context context) {
        mAppDatabase = GameRoomDatabase.getDatabase(context);
        mGameDao = mAppDatabase.gameDoa();
        mGames = mGameDao.getAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return mGames;
    }

    public void insert(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mGameDao.insert(game);
                } catch (SQLiteConstraintException ex) {
                    update(game);
                }
            }
        });
    }


    public void update(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.update(game);
            }
        });
    }


    public void delete(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.delete(game);
            }
        });
    }

    public void deleteAll() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.deleteAll();
            }
        });
    }
}
