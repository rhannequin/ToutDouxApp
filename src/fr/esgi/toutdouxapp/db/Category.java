package fr.esgi.toutdouxapp.db;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Category implements Parcelable {

    private long id;
    private String title;
    private String color;

    public Category() {
        super();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getId());
        dest.writeString(this.getTitle());
        dest.writeString(this.getColor());
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
        this.setId(in.readLong());
        this.setTitle(in.readString());
        this.setColor(in.readString());
    }

    public String toString() {
        return this.getTitle();
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

    public static void deleteAll (Context context) {
        CategoriesDataSource categoriesDatasource = new CategoriesDataSource(context);
        categoriesDatasource.open();
        categoriesDatasource.resetTable();
        categoriesDatasource.close();
    }

}