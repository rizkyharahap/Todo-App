package com.haphap.todoapp.Local.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.haphap.todoapp.Model.Task;
import com.haphap.todoapp.Model.User;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface TaskDao {

    @Insert(onConflict = IGNORE)
    Long inserTask(Task task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM tasks_table ORDER BY date DESC")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM tasks_table WHERE id_task =:id")
    LiveData<Task> getTaskById(Long id);
}
