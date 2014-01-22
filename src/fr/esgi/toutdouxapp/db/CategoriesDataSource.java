package fr.esgi.toutdouxapp.db;

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
        MySQLiteHelper.COLUMN_CATEGORY_TITLE,
        MySQLiteHelper.COLUMN_CATEGORY_COLOR };

    private String TAG = "CategoriesDataSource";

    public CategoriesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Category createCategory(String title, String color) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CATEGORY_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_CATEGORY_COLOR, color);
        long insertId = database.insert(MySQLiteHelper.TABLE_CATEGORIES, null, values);
        return getOneCategory(insertId);
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

    public Category getOneCategory(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORIES,
            allColumns, MySQLiteHelper.COLUMN_CATEGORY_ID + " = " + id, null,
            null, null, null);
        cursor.moveToFirst();
        Category category = cursorToCategory(cursor);
        cursor.close();
        return category;
    }

    public void deleteCategory(Category category) {
        long id = category.getId();
        System.out.println("Category deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CATEGORIES, MySQLiteHelper.COLUMN_CATEGORY_ID
            + " = " + id, null);
    }

    public void resetTable() {
        database.delete(MySQLiteHelper.TABLE_CATEGORIES, null, null);
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getLong(0));
        category.setTitle(cursor.getString(1));
        category.setColor(cursor.getString(2));
        return category;
    }

}
