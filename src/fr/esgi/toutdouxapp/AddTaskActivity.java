package fr.esgi.toutdouxapp;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddTaskActivity extends Activity {

  final private String TAG = "AddTaskActivity";

  EditText titleInput, descriptionInput;
  DatePicker datePicker;
  TimePicker timePicker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.activity_add_task);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

        initFormFields();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.add_task, menu);
    return true;
  }

  public void submitForm(View v) {
    final String title = this.titleInput.getText().toString();
    final String description = this.descriptionInput.getText().toString();
    Calendar cal = Calendar.getInstance();
      cal.set(
          datePicker.getYear(),
          datePicker.getMonth(),
          datePicker.getDayOfMonth(),
          timePicker.getCurrentHour(),
          timePicker.getCurrentMinute(),
          00);
      final Date dueDate = cal.getTime();

      Task newTask = new Task(title, description, dueDate, new Category("my category"));
  }

  private void initFormFields() {
    this.titleInput = (EditText) findViewById(R.id.editText1);
    this.descriptionInput = (EditText) findViewById(R.id.editText2);
    this.datePicker = (DatePicker) findViewById(R.id.datePicker1);
    this.timePicker = (TimePicker) findViewById(R.id.timePicker1);
  }

}
