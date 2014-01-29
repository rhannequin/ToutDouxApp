package fr.esgi.toutdouxapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import android.os.Bundle;
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
    private Task task;
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

        categories = Category.findAll(this);
        task = (Task) getIntent().getParcelableExtra("task");

        initFormFields();
    }

    public void submitForm(View v) {
        final String title = titleInput.getText().toString();
        final String description = descriptionInput.getText().toString();
        final String date = datePicker.getText().toString();
        final String time = timePicker.getText().toString();
        final String dateTime = date + " " + time;
        final Category category = (Category) categoriesSpinner.getSelectedItem();

        try {

            Calendar cal = Calendar.getInstance();

            SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
            Date dateObj = curFormater.parse(dateTime);
            cal.setTime(dateObj);

            final Date dueDate = cal.getTime();

            if(task.id == 0) {
                Task.create(this, title, description, dueDate, category.id);
                Toast.makeText(getApplicationContext(), "Task created!", Toast.LENGTH_SHORT).show();
            } else {
                task.title = title;
                task.description = description;
                task.dueDate = dueDate;
                task.category = category;
                task.update(this);
                Toast.makeText(getApplicationContext(), "Task updated!", Toast.LENGTH_SHORT).show();
            }

            finish();

        } catch (java.text.ParseException e) {
            Toast.makeText(getApplicationContext(), "Error in parsing date!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void initFormFields() {
        titleInput = (EditText) findViewById(R.id.editText1);
        titleInput.setText(task.title);

        descriptionInput = (EditText) findViewById(R.id.editText2);
        descriptionInput.setText(task.description);

        datePicker = (EditText) findViewById(R.id.editText3);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        datePicker.setText(dateFormat.format(task.dueDate));

        timePicker = (EditText) findViewById(R.id.editText4);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
        timePicker.setText(timeFormat.format(task.dueDate));

        categoriesSpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<Category> spinnerArrayAdapter = new ArrayAdapter<Category>(
            this,
            android.R.layout.simple_spinner_item, this.categories);
        categoriesSpinner.setAdapter(spinnerArrayAdapter);

        int pos = 0;
        final long _id = task.category.id;
        for(int i = 0, _size = categories.size(); i < _size; i++) {
            Category c = categories.get(i);
            if(c.id == _id) { pos = i; break; }
        }
        categoriesSpinner.setSelection(spinnerArrayAdapter.getPosition(categories.get(pos)));
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
