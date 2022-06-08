package com.example.mytasklist.model;

import java.io.Serializable;

public class Task implements Serializable {

    private long id;
    private String taskName;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
