package com.example.mytasklist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytasklist.R;
import com.example.mytasklist.adapter.TaskListAdapter;
import com.example.mytasklist.helper.RecyclerItemClickListener;
import com.example.mytasklist.helper.TaskDAO;
import com.example.mytasklist.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabTask;
    private RecyclerView recyclerTaskList;
    private TaskListAdapter taskListAdapter;
    private List<Task> taskList;
    private Task currentMainTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabTask = findViewById(R.id.fab);

        recyclerTaskList = findViewById(R.id.recyclerTaskList);

        // Click events

        recyclerTaskList.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerTaskList,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Task selectedTask = taskList.get(position);

                                Intent intentTask = new Intent(MainActivity.this, AddNewTaskActivity.class);
                                intentTask.putExtra("chosenTask", selectedTask);

                                startActivity(intentTask);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                // Recover of selected task

                                currentMainTask = taskList.get(position);

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                                // Setting title and message of dialog

                                alertDialog.setTitle("Deleting confirmation!");
                                alertDialog.setMessage("Do you want to delete the task " + currentMainTask.getTaskName() + "?");

                                alertDialog.setPositiveButton("Yes!", (dialogInterface, i) -> {

                                    TaskDAO dialogTaskDAO = new TaskDAO(getApplicationContext());

                                    if (dialogTaskDAO.delete(currentMainTask)) {

                                        taskListLoader();
                                        Toast.makeText(getApplicationContext(), "Task removed successfully!", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(getApplicationContext(), "Error while removing task!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialog.setNegativeButton("No!", null);

                                // Displaying the dialog

                                alertDialog.create();
                                alertDialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        taskList = new ArrayList<>();

        fabTask.setOnClickListener(view -> {

            Intent intentTask = new Intent(getApplicationContext(), AddNewTaskActivity.class);
            startActivity(intentTask);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        taskListLoader();
    }

    public void taskListLoader() {

        // List task

        TaskDAO mainTaskDAO = new TaskDAO(getApplicationContext());
        taskList = mainTaskDAO.listingTasks();

        // Show the task list on RecyclerView

        // Set the Adapter

        taskListAdapter = new TaskListAdapter(taskList);

        // Set the Recycler

        RecyclerView.LayoutManager layoutManagerTaskList = new LinearLayoutManager(getApplicationContext());
        recyclerTaskList.setLayoutManager(layoutManagerTaskList);
        recyclerTaskList.setHasFixedSize(true);
        recyclerTaskList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerTaskList.setAdapter(taskListAdapter);
    }
}
