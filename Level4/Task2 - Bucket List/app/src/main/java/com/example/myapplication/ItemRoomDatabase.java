package com.example.myapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//Database
@Database(entities = {Item.class}, version = 3, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {
    //setup database name
    private final static String NAME_DATABASE = "product_database";
    public abstract ItemDoa productDao();
    private static volatile ItemRoomDatabase INSTANCE;

    //setupd b
    public static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, NAME_DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
