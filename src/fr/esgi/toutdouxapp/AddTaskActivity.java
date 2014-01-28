package fr.esgi.toutdouxapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTaskActivity extends ActionBarActivity {

    private ArrayList<Category> categories;
    private EditText titleInput, descriptionInput;
    private EditText datePicker;
    private EditText timePicker;
    private Spinner categoriesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#333333")));
        setContentView(R.layout.activity_add_task);

        final Intent intent = getIntent();
        this.categories = intent.getParcelableArrayListExtra("categories");

        initFormFields();
    }

    public void submitForm(View v) {
        final String title = this.titleInput.getText().toString();
        final String description = this.descriptionInput.getText().toString();
        final String date = this.datePicker.getText().toString();
        final String time = this.timePicker.getText().toString();
        final String dateTime = date+" "+time;
        
        try {

            Calendar cal = Calendar.getInstance();

            SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US); 
            Date dateObj = curFormater.parse(dateTime); 
            cal.setTime(dateObj);

            final Date dueDate = cal.getTime();
            Category category = (Category) categoriesSpinner.getSelectedItem();

            Task.create(this, title, description, dueDate, category.id);
            Toast.makeText(getApplicationContext(), "Task created!", Toast.LENGTH_SHORT).show();
            finish();

        } catch (java.text.ParseException e) {
            Toast.makeText(getApplicationContext(), "Error in parsing date!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void initFormFields() {
        this.titleInput = (EditText) findViewById(R.id.editText1);
        this.descriptionInput = (EditText) findViewById(R.id.editText2);
        this.datePicker = (EditText) findViewById(R.id.datePicker1);
        this.timePicker = (EditText) findViewById(R.id.timePicker1);
        this.categoriesSpinner = (Spinner)this.findViewById(R.id.spinner1);

        ArrayAdapter<Category> spinnerArrayAdapter = new ArrayAdapter<Category>(
            this,
            android.R.layout.simple_spinner_item, this.categories);
        this.categoriesSpinner.setAdapter(spinnerArrayAdapter);
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
    
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
