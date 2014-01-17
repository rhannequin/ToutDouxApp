package fr.esgi.toutdouxapp.db;

import java.util.ArrayList;
import java.util.List;

import fr.esgi.toutdouxapp.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TasksDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TASK };

    public TasksDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Task createTask(String task) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK, task);
        long insertId = database.insert(MySQLiteHelper.TABLE_TASKS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
            allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void deleteTask(Task task) {
        long id = task.getId();
        System.out.println("Task deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.COLUMN_ID
            + " = " + id, null);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
            allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setTitle(cursor.getString(1));
        return task;
    }

}
