package Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import Model.Password;
//Password room database class
@Database(entities = Password.class, version = 1, exportSchema = false)
public abstract class PasswordRoomDatabase extends RoomDatabase {
    private final static String NAME_DATABASE = "password_database";

    public abstract PasswordDao passwordDao();

    private static volatile PasswordRoomDatabase INSTANCE;

    public static PasswordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PasswordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PasswordRoomDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
