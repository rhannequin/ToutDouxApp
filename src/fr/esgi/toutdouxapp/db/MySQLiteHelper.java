package fr.esgi.toutdouxapp.db;

import java.util.Calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Tables
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_CATEGORIES = "categories";

    // Task attributes
    public static final String COLUMN_TASK_ID = "_id";
    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_TASK_DESCRIPTION = "description";
    public static final String COLUMN_TASK_DUE_DATE = "due_date";
    public static final String COLUMN_TASK_STATE = "state";
    public static final String COLUMN_TASK_CATEGORY_ID = "category_id";

    // Category attributes
    public static final String COLUMN_CATEGORY_ID = "_id";
    public static final String COLUMN_CATEGORY_TITLE = "title";
    public static final String COLUMN_CATEGORY_COLOR = "color";

    // Database values
    private static final String DATABASE_NAME = "my_db.db";
    private static final int DATABASE_VERSION = 1;

    private String TAG = "MySQLiteHelper";

    // Database creation sql statement
    private static final String DATABASE_CREATE_TABLE_TASKS =
        "CREATE TABLE " + TABLE_TASKS + "(" +
            COLUMN_TASK_ID + " integer primary key autoincrement, " +
            COLUMN_TASK_TITLE + " VARCHAR(255) not null, " +
            COLUMN_TASK_DESCRIPTION + " VARCHAR(255) not null, " +
            COLUMN_TASK_DUE_DATE + " DATETIME not null, " +
            COLUMN_TASK_STATE + " integer not null, " +
            COLUMN_TASK_CATEGORY_ID + " integer not null);";

    private static final String DATABASE_FILL_TABLE_TASKS_FIRST =
        "INSERT INTO " + TABLE_TASKS + " (" + COLUMN_TASK_TITLE + ", " + COLUMN_TASK_DESCRIPTION + ", " + COLUMN_TASK_DUE_DATE + ", " + COLUMN_TASK_STATE + ", " + COLUMN_TASK_CATEGORY_ID + ") VALUES (" +
            "'Task todo', 'Description of task todo', '" + getOneDay(2) + "', 0, 1);";

    private static final String DATABASE_FILL_TABLE_TASKS_SECOND =
        "INSERT INTO " + TABLE_TASKS + " (" + COLUMN_TASK_TITLE + ", " + COLUMN_TASK_DESCRIPTION + ", " + COLUMN_TASK_DUE_DATE + ", " + COLUMN_TASK_STATE + ", " + COLUMN_TASK_CATEGORY_ID + ") VALUES (" +
            "'Task done', 'Description of task done', '" + getOneDay(-3) + "', 1, 2);";

    private static final String DATABASE_CREATE_TABLE_CATEGORIES =
        "CREATE TABLE " + TABLE_CATEGORIES + "(" +
            COLUMN_CATEGORY_ID + " integer primary key autoincrement, " +
            COLUMN_CATEGORY_TITLE + " VARCHAR(255) not null, " +
            COLUMN_CATEGORY_COLOR + " VARCHAR(10) not null);";

    private static final String DATABASE_FILL_TABLE_CATEGORIES_FIRST =
        "INSERT INTO '" + TABLE_CATEGORIES + "' ('" + COLUMN_CATEGORY_TITLE + "', '" + COLUMN_CATEGORY_COLOR + "') VALUES (" +
            "'Important', '#E04646')";

    private static final String DATABASE_FILL_TABLE_CATEGORIES_SECOND =
        "INSERT INTO '" + TABLE_CATEGORIES + "' ('" + COLUMN_CATEGORY_TITLE + "', '" + COLUMN_CATEGORY_COLOR + "') VALUES (" +
             "'Not important', '#64BF5C');";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_TABLE_CATEGORIES);
        database.execSQL(DATABASE_FILL_TABLE_CATEGORIES_FIRST);
        database.execSQL(DATABASE_FILL_TABLE_CATEGORIES_SECOND);
        database.execSQL(DATABASE_CREATE_TABLE_TASKS);
        database.execSQL(DATABASE_FILL_TABLE_TASKS_FIRST);
        database.execSQL(DATABASE_FILL_TABLE_TASKS_SECOND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG,
            "Upgrading database from version " + oldVersion + " to " +
            newVersion + ", which will destroy all old data");
        dropDatabase(db);
        onCreate(db);
    }

    public void dropDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
    }

    private static long getOneDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return cal.getTime().getTime();
    }

}
