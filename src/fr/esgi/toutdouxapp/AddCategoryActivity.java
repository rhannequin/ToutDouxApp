package fr.esgi.toutdouxapp;

import fr.esgi.toutdouxapp.db.Category;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategoryActivity extends ActionBarActivity {

    EditText titleInput, colorInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#333333")));
        setContentView(R.layout.activity_add_category);

        this.titleInput = (EditText) findViewById(R.id.title);
        this.colorInput = (EditText) findViewById(R.id.color);
    }

    public void submitForm(View v) {
        final String title = this.titleInput.getText().toString().trim();
        String color = this.colorInput.getText().toString().trim();
        if(title.matches("")) {
            Toast.makeText(getApplicationContext(), "Title is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Category.isValidColor(color)) {
            color = "#FFFFFF";
        }
        Category.create(this, title, color);
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
