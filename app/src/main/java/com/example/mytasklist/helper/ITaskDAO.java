package com.example.mytasklist.helper;

import com.example.mytasklist.model.Task;

import java.util.List;

public interface ITaskDAO {

    public boolean save(Task task);
    public boolean actualize(Task task);
    public boolean delete(Task task);
    public List<Task> listingTasks();
}
