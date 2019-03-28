package com.example.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.myapplication.Item;

import java.util.List;


//Doa interface that makes call to the db
@Dao
public interface ItemDoa {
    //insert a item
    @Insert
    void insert(Item product);
    //delete a item
    @Delete
    void delete(Item product);
    //delete a list of items
    @Delete
    void delete(List<Item> products);
    //update  a item
    @Update
    void update(Item product);
    //get all of the items
    @Query("SELECT * from item_table")
    List<Item> getAllProducts();

}