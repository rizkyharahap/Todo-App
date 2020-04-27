package com.haphap.todoapp.ViewModel;

        import android.app.Application;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;

        import com.haphap.todoapp.Local.Database.AppDatabase;
        import com.haphap.todoapp.Local.Database.UserDao;
        import com.haphap.todoapp.Model.User;

        import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserDao userDao;

    public UserViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(application);
        userDao = appDatabase.userDao();
    }

    public Long inserUser(User user){
        return userDao.insertUser(user);
    }

    public List<User> getallUsers(){
        return userDao.getAllUsers();
    }

}
