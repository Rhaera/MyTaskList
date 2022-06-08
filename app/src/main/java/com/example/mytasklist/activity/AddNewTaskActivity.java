package com.example.mytasklist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mytasklist.R;
import com.example.mytasklist.helper.TaskDAO;
import com.example.mytasklist.model.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddNewTaskActivity extends AppCompatActivity {

    private TextInputEditText taskEdition;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        taskEdition = findViewById(R.id.editTask);

        // Task recover, in case of edition

        currentTask = (Task) getIntent().getSerializableExtra("chosenTask");

        // Setting task in text box

        if (currentTask != null) {

            taskEdition.setText(currentTask.getTaskName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.saveItem:

                TaskDAO taskDAO = new TaskDAO(getApplicationContext());

                // Edition

                if (currentTask != null) {

                    String taskName = Objects.requireNonNull(taskEdition.getText()).toString();

                    if (!taskName.isEmpty()) {

                        Task task = new Task();
                        task.setTaskName(taskName);
                        task.setId(currentTask.getId());

                        // Actualizing in Database

                        if (taskDAO.actualize(task)) {

                            Toast.makeText(getApplicationContext(), "Task actualized successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Error while changing task!", Toast.LENGTH_SHORT).show();
                    }

                // Saving

                } else {

                    String taskName = Objects.requireNonNull(taskEdition.getText()).toString();

                    if (!taskName.isEmpty()) {

                        Task newTask = new Task();
                        newTask.setTaskName(taskName);

                        if (taskDAO.save(newTask)) {

                            Toast.makeText(getApplicationContext(), "Task saved successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {

                            Toast.makeText(getApplicationContext(), "Error while saving task!", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Error while saving task!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
