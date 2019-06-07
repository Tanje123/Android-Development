package Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Model.User;

@Dao
//user interface
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

}
