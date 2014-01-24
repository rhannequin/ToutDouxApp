package fr.esgi.toutdouxapp.db;

import java.util.Date;
import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TasksDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
        MySQLiteHelper.COLUMN_TASK_ID,
        MySQLiteHelper.COLUMN_TASK_TITLE,
        MySQLiteHelper.COLUMN_TASK_DESCRIPTION,
        MySQLiteHelper.COLUMN_TASK_DUE_DATE,
        MySQLiteHelper.COLUMN_TASK_CATEGORY_ID };

    private CategoriesDataSource categoriesDatasource;

    private String TAG = "TasksDataSource";

    public TasksDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.categoriesDatasource = new CategoriesDataSource(context);
    }

    public void open() throws SQLException {
        categoriesDatasource.open();
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Task createTask(String title, String description, Date dueDate, long categoryId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_TASK_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_TASK_DUE_DATE, dueDate.getTime());
        values.put(MySQLiteHelper.COLUMN_TASK_CATEGORY_ID, categoryId);
        long insertId = database.insert(MySQLiteHelper.TABLE_TASKS, null, values);
        return getOneTask(insertId);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

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

    public Task getOneTask(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
            allColumns, MySQLiteHelper.COLUMN_TASK_ID + " = " + id, null,
            null, null, null);
        cursor.moveToFirst();
        Task task = cursorToTask(cursor);
        cursor.close();
        return task;
    }

    public void deleteTask(Task task) {
        long id = task.getId();
        System.out.println("Task deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.COLUMN_TASK_ID
            + " = " + id, null);
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setTitle(cursor.getString(1));
        task.setDescription(cursor.getString(2));
        task.setDueDate(new Date(cursor.getLong(3)));
        task.setCategory(this.categoriesDatasource.getOneCategory(cursor.getInt(4)));
        return task;
    }

    public void resetTable() {
        database.delete(MySQLiteHelper.TABLE_TASKS, null, null);
    }

}
