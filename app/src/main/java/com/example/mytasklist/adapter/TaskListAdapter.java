package com.example.mytasklist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasklist.R;
import com.example.mytasklist.model.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private List<Task> taskList;

    public TaskListAdapter(List<Task> list) {

        this.taskList = list;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_adapter, parent, false);

        return new TaskListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {

        Task task = taskList.get(position);
        holder.txtTask.setText(task.getTaskName());
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {

        TextView txtTask;

        public TaskListViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTask = itemView.findViewById(R.id.taskText);
        }
    }
}
