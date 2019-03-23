package com.example.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.myapplication.Item;

import java.util.List;

@Dao

public interface ItemDoa {

    @Insert
    void insert(Item product);

    @Delete
    void delete(Item product);

    @Delete
    void delete(List<Item> products);

    @Update
    void update(Item product);

    @Query("SELECT * from item_table")
    List<Item> getAllProducts();

}