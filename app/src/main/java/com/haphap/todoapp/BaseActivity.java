package com.haphap.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.haphap.todoapp.Feature.Task.TaskActivity;
import com.haphap.todoapp.Local.SharedPreferences.SharePrefenceHelper;
import com.haphap.todoapp.Model.User;
import com.haphap.todoapp.Feature.User.LoginActivity;
import com.haphap.todoapp.Feature.User.RegisterActivity;
import com.haphap.todoapp.Utils.Constants;

import static com.haphap.todoapp.Utils.Constants.USER_NAME_KEY;

public class BaseActivity extends AppCompatActivity {

    private SharePrefenceHelper sharePrefenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharePrefenceHelper = SharePrefenceHelper.getINSTANCE(this);
    }

    public SharePrefenceHelper getSharedPrefence() {
        return sharePrefenceHelper;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void openMainActivity(Context context, User user) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(Constants.USER_ID_KEY, user);
        intent.putExtra(USER_NAME_KEY, user.getFirstName());
        startActivity(intent);
    }

    protected void gotoLoginActivity(Context context) {
        startActivity(new Intent(context, LoginActivity.class));
    }

    protected void gotoRegisterActivity(Context context) {
        startActivity(new Intent(context, RegisterActivity.class));
    }
}
