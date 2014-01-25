package fr.esgi.toutdouxapp;

import fr.esgi.toutdouxapp.db.CategoriesDataSource;
import fr.esgi.toutdouxapp.db.TasksDataSource;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategoryActivity extends Activity {

    final private String TAG = "AddCategoryActivity";
    private CategoriesDataSource categoriesDatasource;

    EditText titleInput, colorInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_category);

        categoriesDatasource = new CategoriesDataSource(this);
        categoriesDatasource.open();

        this.titleInput = (EditText) findViewById(R.id.title);
        this.colorInput = (EditText) findViewById(R.id.color);
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

    public void submitForm(View v) {
        final String title = this.titleInput.getText().toString();
        final String color = this.colorInput.getText().toString();

        categoriesDatasource.createCategory(title, color);

        Toast.makeText(getApplicationContext(), "Category created!", Toast.LENGTH_SHORT).show();
        finish();
    }

}