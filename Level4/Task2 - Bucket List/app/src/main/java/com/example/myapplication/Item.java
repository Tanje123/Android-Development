package com.example.myapplication;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
//item class
@Entity(tableName = "item_table")
public class Item implements Serializable {
    //atribrutes
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "titel")
    private String titel;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "completed")
    private Boolean completed;
    //constructor
    public Item(String titel, String description, Boolean completed) {
        this.titel = titel;
        this.description = description;
        this.completed = completed;
    }
    //getters and setters
    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String name) {
        this.titel = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}