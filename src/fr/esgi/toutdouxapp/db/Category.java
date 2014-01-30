package fr.esgi.toutdouxapp.db;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Category implements Parcelable {

    public long id;
    public String title, color;

    public Category() {
        super();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(color);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public Category(Parcel in) {
        id = in.readLong();
        title = in.readString();
        color = in.readString();
    }


    /** Instance methods **/

    public String toString() {
        return title;
    }


    /** Class methods **/

    public static Boolean isValidColor(String color) {
        return color.matches("^#([A-Fa-f0-9]{6})$");
    }

    public static ArrayList<Category> findAll (Context context) {
        final CategoriesDataSource categoriesDatasource = new CategoriesDataSource(context);
        categoriesDatasource.open();
        final ArrayList<Category> categories = categoriesDatasource.getAllCategories();
        categoriesDatasource.close();
        return categories;
    }

    public static Category findOne (Context context, long id) {
        final CategoriesDataSource categoriesDatasource = new CategoriesDataSource(context);
        categoriesDatasource.open();
        final Category category = categoriesDatasource.getOneCategory(id);
        categoriesDatasource.close();
        return category;
    }

    public static Category create (Context context, String title, String color) {
        final CategoriesDataSource categoriesDatasource = new CategoriesDataSource(context);
        categoriesDatasource.open();
        final Category category = categoriesDatasource.createCategory(title, color);
        categoriesDatasource.close();
        return category;
    }

    public static void deleteOne (Context context, Category category) {
        final CategoriesDataSource categoriesDataSource = new CategoriesDataSource(context);
        categoriesDataSource.open();
        final ArrayList<Task> tasks = Task.findAll(context, null);
        for(final Task task : tasks) {
            if(task.category.id == category.id) {
                Task.deleteOne(context, task);
            }
        }
        categoriesDataSource.deleteCategory(category);
        categoriesDataSource.close();
    }

}
