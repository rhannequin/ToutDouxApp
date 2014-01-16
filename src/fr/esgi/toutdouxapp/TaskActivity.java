package fr.esgi.toutdouxapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.widget.TextView;

public class TaskActivity extends Activity {

  Task task;
  private TextView titleView;
  private TextView descriptionView;
  private TextView dueDateView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.activity_task);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

    titleView = (TextView) findViewById(R.id.title);
    descriptionView = (TextView) findViewById(R.id.description);
    dueDateView = (TextView) findViewById(R.id.due_date);

    final Intent intent = getIntent();
    task = (Task) intent.getParcelableExtra("task");
  }

  @Override
  protected void onStart() {
    super.onStart();

    titleView.setText(task.getTitle());
    descriptionView.setText(task.getDescription());
    dueDateView.setText(task.getTimeLeft());
  }

}
