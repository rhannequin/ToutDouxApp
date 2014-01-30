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

    public String toString() {
        return title;
    }

    public static Boolean isValidColor(String color) {
        return color.matches("^#([A-Fa-f0-9]{6})$");
    }

    public static ArrayList<Category> findAll (Context context) {
        CategoriesDataSource categoriesDatasource = new CategoriesDataSource(context);
        categoriesDatasource.open();
        ArrayList<Category> categories = categoriesDatasource.getAllCategories();
        categoriesDatasource.close();
        return categories;
    }

    public static Category findOne (Context context, long id) {
        CategoriesDataSource categoriesDatasource = new CategoriesDataSource(context);
        categoriesDatasource.open();
        Category category = categoriesDatasource.getOneCategory(id);
        categoriesDatasource.close();
        return category;
    }

    public static Category create (Context context, String title, String color) {
        CategoriesDataSource categoriesDatasource = new CategoriesDataSource(context);
        categoriesDatasource.open();
        Category category = categoriesDatasource.createCategory(title, color);
        categoriesDatasource.close();
        return category;
    }

    public static void deleteOne (Context context, Category category) {
        CategoriesDataSource categoriesDataSource = new CategoriesDataSource(context);
        categoriesDataSource.open();
        categoriesDataSource.deleteCategory(category);
        categoriesDataSource.close();
    }

}
