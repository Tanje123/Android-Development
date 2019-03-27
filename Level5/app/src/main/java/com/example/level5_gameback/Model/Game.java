package com.example.level5_gameback.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//Entity game
@Entity(tableName = "game_table")
public class Game {
    //The attributes of the class
    //The primarykey of the table is going to be the title of the game
    @PrimaryKey
    @NonNull
    private String titel;
    //Other attributes
    @ColumnInfo
    private String console;
    @ColumnInfo
    private String status;
    @ColumnInfo
    private String date;

    //Constructor for the game class
    //The date variable is set to the current time
    public Game(String titel, String console, String status) {
        this.titel = titel;
        this.console = console;
        this.status = status;
        this.date = getCurrentDate();
    }

    //Method to get the current date in dd-mm-yy format
    //Return type is string
    public String getCurrentDate() {
        //Get the current date in the dd - mm - yy format
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        //Return the current date
        return timeStamp;
    }
    //Getters and setters for the atributes
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //ToString method that returns all the values of the game object
    @Override
    public String toString() {
        return "Game{" +
                "titel='" + titel + '\'' +
                ", console='" + console + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
