package fr.esgi.toutdouxapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";

    private static final String DATABASE_NAME = "my_db.db";
    private static final int DATABASE_VERSION = 1;

    private String TAG = "MySQLiteHelper";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " +
        TABLE_TASKS + "(" + COLUMN_ID + " integer primary key autoincrement, " +
        COLUMN_TASK + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG,
            "Upgrading database from version " + oldVersion + " to " +
            newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

}
