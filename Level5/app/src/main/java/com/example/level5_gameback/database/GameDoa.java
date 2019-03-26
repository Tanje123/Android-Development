package com.example.level5_gameback.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.level5_gameback.Model.Game;

import java.util.List;

@Dao
public interface GameDoa {
    //Insert a new game
    @Insert
    void insert(Game game);

    //Delete a game
    @Delete
    void delete(Game game);

    //Delete multiple games
    @Delete
    void delete(List<Game> games);

    //Update a existing game
    @Update
    void update(Game game);

    //Get all the games from the db
    @Query("SELECT * from game_table")
    public LiveData<List<Game>> getAllGames();



}
