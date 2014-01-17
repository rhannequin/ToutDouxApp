package fr.esgi.toutdouxapp.db;

import java.util.Date;
import java.util.ArrayList;

import fr.esgi.toutdouxapp.Category;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoriesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
        MySQLiteHelper.COLUMN_CATEGORY_ID,
        MySQLiteHelper.COLUMN_CATEGORY_TITLE };

    private String TAG = "CategoriesDataSource";

    public CategoriesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Category createCategory(String title) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CATEGORY_TITLE, title);
        long insertId = database.insert(MySQLiteHelper.TABLE_CATEGORIES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORIES,
            allColumns, MySQLiteHelper.COLUMN_CATEGORY_ID + " = " + insertId, null,
            null, null, null);
        cursor.moveToFirst();
        Category newCategory = cursorToCategory(cursor);
        cursor.close();
        return newCategory;
    }

    public void deleteCategory(Category category) {
        long id = category.getId();
        System.out.println("Category deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CATEGORIES, MySQLiteHelper.COLUMN_CATEGORY_ID
            + " = " + id, null);
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORIES,
            allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory(cursor);
            categories.add(category);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return categories;
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getLong(0));
        category.setTitle(cursor.getString(1));
        return category;
    }

}
