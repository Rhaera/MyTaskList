package com.example.mytasklist.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mytasklist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO {

    private SQLiteDatabase writer, reader;

    public TaskDAO(Context context) {

        DbHelper dbHelper = new DbHelper(context);

        writer = dbHelper.getWritableDatabase();
        reader = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean save(Task task) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", task.getTaskName());

        try {

            writer.insert(DbHelper.TABLE_TASK, null, contentValues);
        } catch (Exception e) {

            return false;
        }

        return true;
    }

    @Override
    public boolean actualize(Task task) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", task.getTaskName());

        try {

            String[] args = {"" + task.getId()};
            writer.update(DbHelper.TABLE_TASK, contentValues, "id=?", args);
        } catch (Exception e) {

            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Task task) {

        try {

            String[] args = {"" + task.getId()};
            writer.delete(DbHelper.TABLE_TASK, "id=?", args);
        } catch (Exception e) {

            return false;
        }

        return true;
    }

    @Override
    public List<Task> listingTasks() {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABLE_TASK + ";";

        @SuppressLint("Recycle") Cursor factoryCursor = reader.rawQuery(sql, null);

        while (factoryCursor.moveToNext()) {

            Task task = new Task();

            @SuppressLint("Range") long id         = factoryCursor.getLong(factoryCursor.getColumnIndex("id"));
            @SuppressLint("Range") String taskName = factoryCursor.getString(factoryCursor.getColumnIndex("name"));

            task.setId(id);
            task.setTaskName(taskName);

            tasks.add(task);
        }

        return tasks;
    }
}
