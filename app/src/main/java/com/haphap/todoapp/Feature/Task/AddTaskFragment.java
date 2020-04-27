package com.haphap.todoapp.Feature.Task;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.haphap.todoapp.Model.Task;
import com.haphap.todoapp.R;
import com.haphap.todoapp.Utils.Constants;
import com.haphap.todoapp.ViewModel.TaskViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTaskFragment extends DialogFragment implements View.OnClickListener{

    @BindView(R.id.title_editText)
    EditText etTitle;
    @BindView(R.id.description_editText)
    EditText etDescription;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.btn_get_datetime)
    ImageButton btnDateTime;
    @BindView(R.id.add_btn)
    Button btnAdd;
    @BindView(R.id.cancel_btn)
    Button btnCencel;
    @BindView(R.id.progress_bar_layout)
    LinearLayout linearLayout;

    private TaskViewModel viewModel;
    private Long userId;
    private Long mTaskId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        ButterKnife.bind(this, view);

        btnDateTime.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnCencel.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        Intent intent = getActivity().getIntent();
        userId = intent.getExtras().getLong(Constants.USER_ID_KEY);

        if (intent != null && intent.hasExtra(Constants.ID_EXTRA_STRING)) {
            btnAdd.setText("Update");

            mTaskId = intent.getExtras().getLong(Constants.ID_EXTRA_STRING);
            viewModel.getTaskById(mTaskId).observe(this, task -> {
                etTitle.setText(task.getTitle());
                etDescription.setText(task.getDescription());
                etDate.setText(task.getDate());
            });
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_datetime :
                setDate();
                break;
            case R.id.add_btn :
                showProgressbar();
                addData();
                break;
            case R.id.cancel_btn :
                cencel();
                break;
        }
    }

    private void setDate(){
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),
                (datePicker, year, month, day) -> etDate.setText(day + "-" + (month + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void cencel() {
        Intent intent = new Intent();
        getActivity().setResult(Constants.CANCELED_TASK_RESPONSE, intent);
        getActivity().finish();
    }

    private void addData() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();

        if (validateField(title, description, date)) {
            Intent intent = new Intent();

            if (mTaskId != null) {
                Task task = new Task(mTaskId, userId, title, description, date);
                intent.putExtra(Constants.UPDATED_TASK_STRING, task);
                getActivity().setResult(Constants.UPDATED_TASK_RESPONSE, intent);

            } else {
                Task task = new Task(userId, title, description, date);
                intent.putExtra(Constants.UPDATED_TASK_STRING, task);
                getActivity().setResult(Constants.ADDED_TASK_RESPONSE, intent);
            }
            getActivity().finish();
        }
        hideProgressbar();
    }

    private boolean validateField(String title, String description, String date) {
        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(description)) {
            etDescription.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(date)) {
            etDate.setError("Required");
            return false;
        }

        return true;
    }

    private void showProgressbar() {
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        linearLayout.setVisibility(View.GONE);
    }

    public static AddTaskFragment createNewInstance() {
        return new AddTaskFragment();
    }
}
