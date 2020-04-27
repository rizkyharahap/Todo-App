package com.haphap.todoapp.Local.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haphap.todoapp.Model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    Long insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

}
