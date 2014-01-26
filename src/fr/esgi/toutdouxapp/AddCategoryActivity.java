package fr.esgi.toutdouxapp;

import fr.esgi.toutdouxapp.db.CategoriesDataSource;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategoryActivity extends ActionBarActivity {

    private CategoriesDataSource categoriesDatasource;

    EditText titleInput, colorInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#333333")));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
