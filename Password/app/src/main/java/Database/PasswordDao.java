package Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Model.Password;
import Model.User;

@Dao
public interface PasswordDao {
    //methods for password database
    @Query("SELECT * FROM password")
    List<Password> getAllPassword();

    @Insert
    void instertPassword(Password password);

    @Delete
    void deletePassword(Password password);
    //delete all the passwords
    @Query("DELETE FROM password")
    void deleteAllPasswords();

    @Update
    void updatePassword(Password password);
}
