package com.haphap.todoapp.Feature.Task;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.haphap.todoapp.BaseActivity;
import com.haphap.todoapp.R;

public class TaskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskFragment taskFragment = (TaskFragment) getSupportFragmentManager().findFragmentById(R.id.task_fragment_container);

        if (taskFragment == null) {
            taskFragment = TaskFragment.createNewInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.task_fragment_container, taskFragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_menu_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        getSharedPrefence().setLoggedUserId((long) -1);
        if (getSharedPrefence().getUserId() == -1) {
            gotoLoginActivity(TaskActivity.this);
            finish();
        }
    }
}
