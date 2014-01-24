package fr.esgi.toutdouxapp;

import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.CategoriesDataSource;
import fr.esgi.toutdouxapp.db.Category;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoriesListActivity extends Activity {

    ArrayList<Category> categories;
    private ListView listView;
    private ArrayAdapter<Category> adapter;
    private CategoriesDataSource categoriesDatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_categorylist);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

        categoriesDatasource = new CategoriesDataSource(this);
        categoriesDatasource.open();

        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.categories = categoriesDatasource.getAllCategories();
        adapter = new CategoryArrayAdapter(this, R.layout.activity_categorylist_row, this.categories);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        categoriesDatasource.open();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        categoriesDatasource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categories_list, menu);
        return true;
    }

    public void addCategoryHandler(View v) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }

}
