package com.haphap.todoapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.haphap.todoapp.Local.Database.AppDatabase;
import com.haphap.todoapp.Local.Database.TaskDao;
import com.haphap.todoapp.Model.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskDao taskDao;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(application);
        taskDao = appDatabase.taskDao();
    }


    public Long insertTask(Task task){
        return taskDao.inserTask(task);
    }

    public void updateTask(Task task){
        taskDao.updateTask(task);
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }

    public LiveData<List<Task>> getallTask(){
        return taskDao.getAllTask();
    }

    public LiveData<Task> getTaskById(Long taskId) {
        return taskDao.getTaskById(taskId);
    }

}
