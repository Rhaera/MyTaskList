package com.example.mytasklist.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "Task.DB";
    public static int VERSION    = 1;
    public static String TABLE_TASK = "tasks";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlDb = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);";

        try {

            sqLiteDatabase.execSQL(sqlDb);
            Log.i("DB INFO", "App installed successfully!");

        } catch (Exception e) {

            Log.i("DB INFO", "Installation error " + e.getMessage());

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sqlDb = "DROP TABLE IF EXISTS " + TABLE_TASK + ";";

        try {

            sqLiteDatabase.execSQL(sqlDb);
            onCreate(sqLiteDatabase);
            Log.i("DB INFO", "App actualized successfully!");

        } catch (Exception e) {

            Log.i("DB INFO", "Actualization error " + e.getMessage());

        }
    }
}
