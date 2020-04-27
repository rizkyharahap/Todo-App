package com.haphap.todoapp.Local.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.haphap.todoapp.Model.Task;
import com.haphap.todoapp.Model.User;

@Database(entities = {User.class, Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract TaskDao taskDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (INSTANCE == null){
            return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "todo_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
