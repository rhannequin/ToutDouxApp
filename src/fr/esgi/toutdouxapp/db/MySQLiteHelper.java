package fr.esgi.toutdouxapp.db;

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
            COLUMN_TASK_CATEGORY_ID + " integer not null);";

    private static final String DATABASE_CREATE_TABLE_CATEGORIES =
        "CREATE TABLE " + TABLE_CATEGORIES + "(" +
            COLUMN_CATEGORY_ID + " integer primary key autoincrement, " +
            COLUMN_CATEGORY_TITLE + " VARCHAR(255) not null, " +
            COLUMN_CATEGORY_COLOR + " VARCHAR(10) not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_TABLE_TASKS);
        database.execSQL(DATABASE_CREATE_TABLE_CATEGORIES);
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

}
