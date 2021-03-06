package fr.esgi.toutdouxapp.db;

import java.util.Date;
import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TasksDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private Context context;
    private String[] allColumns = {
        MySQLiteHelper.COLUMN_TASK_ID,
        MySQLiteHelper.COLUMN_TASK_TITLE,
        MySQLiteHelper.COLUMN_TASK_DESCRIPTION,
        MySQLiteHelper.COLUMN_TASK_DUE_DATE,
        MySQLiteHelper.COLUMN_TASK_STATE,
        MySQLiteHelper.COLUMN_TASK_CATEGORY_ID };

    public TasksDataSource(Context c) {
        context = c;
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Task createTask(String title, String description, Date dueDate, long categoryId) {
        final ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_TASK_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_TASK_DUE_DATE, dueDate.getTime());
        values.put(MySQLiteHelper.COLUMN_TASK_STATE, 0);
        values.put(MySQLiteHelper.COLUMN_TASK_CATEGORY_ID, categoryId);
        final long insertId = database.insert(MySQLiteHelper.TABLE_TASKS, null, values);
        return getOneTask(insertId);
    }

    public void updateTask(Task task) {
        final ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK_TITLE, task.title);
        values.put(MySQLiteHelper.COLUMN_TASK_DESCRIPTION, task.description);
        values.put(MySQLiteHelper.COLUMN_TASK_DUE_DATE, task.dueDate.getTime());
        values.put(MySQLiteHelper.COLUMN_TASK_STATE, 0);
        values.put(MySQLiteHelper.COLUMN_TASK_CATEGORY_ID, task.category.id);
        database.update(MySQLiteHelper.TABLE_TASKS, values, MySQLiteHelper.COLUMN_TASK_ID + " = " + task.id, null);
    }

    public ArrayList<Task> getAllTasks(String where) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        final Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
            allColumns, where, null, null, null, MySQLiteHelper.COLUMN_TASK_DUE_DATE);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public Task getOneTask(long id) {
        final Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
            allColumns, MySQLiteHelper.COLUMN_TASK_ID + " = " + id, null,
            null, null, null);
        cursor.moveToFirst();
        final Task task = cursorToTask(cursor);
        cursor.close();
        return task;
    }

    public void deleteTask(Task task) {
        final long id = task.id;
        System.out.println("Task deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.COLUMN_TASK_ID
            + " = " + id, null);
    }

    public int toggleState(Task task) {
        final ContentValues cv = new ContentValues();
        final int newState = task.isDone() ? 0 : 1;
        cv.put("state", newState);
        database.update(
            MySQLiteHelper.TABLE_TASKS,
            cv,
            MySQLiteHelper.COLUMN_TASK_ID + " = " + task.id,
            null);
        return newState;
    }

    private Task cursorToTask(Cursor cursor) {
        final Task task = new Task();
        task.id = cursor.getLong(0);
        task.title = cursor.getString(1);
        task.description = cursor.getString(2);
        task.dueDate = new Date(cursor.getLong(3));
        task.state = cursor.getInt(4);
        task.category = Category.findOne(context, cursor.getInt(5));
        return task;
    }

    public void resetTable() {
        database.delete(MySQLiteHelper.TABLE_TASKS, null, null);
    }

}
