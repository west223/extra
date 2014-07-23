package com.west.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usr1 on 7/15/14.
 */
public class TaskDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    //Data base name
    private static final String DATABASE_NAME = "taskerManager";
    //tasks table name
    private static final String TABLE_TASKS = "tasks";
    //Table columns
    private static final String KEY_ID = "id";
    private static final String KEY_TASKNAME = "taskName";
    private static final String KEY_STATUS = "status";

    public TaskDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TASKNAME + " TEXT, "
                + KEY_STATUS + " INTEGER)";
        db.execSQL(sql);
        //mentain close?
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IS EXISTS" + TABLE_TASKS );
        //Create tables again
        onCreate(db);
    }
    // Add new Task
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        //status = 0 (non), 1 (done)
        values.put(KEY_STATUS, task.getStatus());
        //Insert row in DB
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public List<Task>getAllTasks(){
        List<Task> taskList = new ArrayList<Task>();

        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setTaskName(cursor.getString(1));
                task.setStatus(cursor.getInt(2));

                taskList.add(task);
            }while (cursor.moveToNext());
        }
        return taskList;
    }


    public void updateTask(Task task){
        //Update DB
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        values.put(KEY_STATUS, task.getStatus());
        db.update(TABLE_TASKS, values, KEY_ID + " = ?", new String[] {String.valueOf(task.getId())});
        db.close();
    }

    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + "=?", new String[] {String.valueOf(task.getId())});
        db.close();
    }

    public void deleteAllTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("BD-------------------------", "deleteng all" );
        db.delete(TABLE_TASKS, "" , null);
        db.close();
    }

}
