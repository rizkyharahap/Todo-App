package com.haphap.todoapp.Feature.Task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haphap.todoapp.Model.Task;
import com.haphap.todoapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private OnTaskInteractionListener mListener;
    private List<Task> tasks;
    private Context context;

    TaskAdapter(Context context) {
        this.context = context;
    }

    void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        if (tasks == null)
            return 0;
        return tasks.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_task_title)
        TextView tvTitle;
        @BindView(R.id.tv_task_description)
        TextView tvDescription;
        @BindView(R.id.tv_task_date)
        TextView tvDate;
        @BindView(R.id.btn_task_button_done)
        ImageButton btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            btnDelete.setOnClickListener(view -> mListener.onItemDeleted(tasks.get(getAdapterPosition())));
        }


        void bind(Task task) {
            tvTitle.setText(task.getTitle());
            tvDescription.setText(task.getDescription());
            tvDate.setText(task.getDate());
            long elementId = task.getId_task();
            itemView.setOnClickListener(view -> mListener.onItemClicked(elementId));
        }
    }

    public interface OnTaskInteractionListener {
        void onItemClicked(Long itemId);

        void onItemDeleted(Task task);
    }

    public void setOnTaskInteractionListener(OnTaskInteractionListener mListener) {
        this.mListener = mListener;
    }
}
