package com.haphap.todoapp.Feature.Task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.haphap.todoapp.Model.Task;
import com.haphap.todoapp.R;
import com.haphap.todoapp.Utils.Constants;
import com.haphap.todoapp.ViewModel.TaskViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.haphap.todoapp.Utils.Constants.*;
import static com.haphap.todoapp.Utils.Constants.ADDED_TASK_RESPONSE;
import static com.haphap.todoapp.Utils.Constants.ADD_TASK_REQUEST;
import static com.haphap.todoapp.Utils.Constants.CANCELED_TASK_RESPONSE;
import static com.haphap.todoapp.Utils.Constants.ID_EXTRA_STRING;
import static com.haphap.todoapp.Utils.Constants.UPDATED_TASK_RESPONSE;
import static com.haphap.todoapp.Utils.Constants.USER_ID_KEY;
import static com.haphap.todoapp.Utils.Constants.USER_NAME_KEY;

public class TaskFragment extends Fragment{

    @BindView(R.id.main_activity_username)
    TextView tvName;
    @BindView(R.id.blank_task)
    LinearLayout layoutBlank;
    @BindView(R.id.tasks_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fb_add_task)
    FloatingActionButton floatingActionButton;

    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;
    private Task task;

    Long userId;
    String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        Intent mIntent = Objects.requireNonNull(getActivity()).getIntent();
        userId = mIntent.getLongExtra(USER_ID_KEY, 1);
        name = mIntent.getStringExtra(USER_NAME_KEY);

        loadData();

        adapter.setOnTaskInteractionListener(new TaskAdapter.OnTaskInteractionListener() {

            @Override
            public void onItemClicked(Long itemId) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                intent.putExtra(USER_ID_KEY, userId);
                intent.putExtra(ID_EXTRA_STRING, itemId);
                startActivityForResult(intent, 0);
            }

            @Override
            public void onItemDeleted(Task task) {
                taskViewModel.deleteTask(task);
                Toast.makeText(getActivity(), "Task deleted !", Toast.LENGTH_SHORT).show();
            }
        });


        floatingActionButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            intent.putExtra(USER_ID_KEY, userId);
            startActivityForResult(intent, 0);
        });
        return view;
    }

    private void loadData() {
        taskViewModel.getallTask().observe(this, task -> {
            adapter.setTasks(task);
            int amount = task.size();
            if (amount == 0) {
                layoutBlank.setVisibility(View.VISIBLE);
            } else {
                layoutBlank.setVisibility(View.GONE);
            }
        });
    }

    public static TaskFragment createNewInstance() {
        return new TaskFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == ADDED_TASK_RESPONSE) {
            Toast.makeText(getActivity(), "Task added !", Toast.LENGTH_SHORT).show();

            assert data != null;
            task = data.getParcelableExtra(UPDATED_TASK_STRING);
            Log.d("ADD", String.valueOf(task.getId_task()));
            taskViewModel.insertTask(task);

        } else if (requestCode == ADD_TASK_REQUEST && resultCode == UPDATED_TASK_RESPONSE) {
            Toast.makeText(getActivity(), "Task updated !", Toast.LENGTH_SHORT).show();

            assert data != null;
            task = data.getParcelableExtra(UPDATED_TASK_STRING);
            Log.d("UPDATE", task.getTitle());
            taskViewModel.updateTask(task);
        } else if (requestCode == ADD_TASK_REQUEST && resultCode == CANCELED_TASK_RESPONSE){
            Toast.makeText(getActivity(), "Task Cencel", Toast.LENGTH_SHORT).show();
        }
    }

}
