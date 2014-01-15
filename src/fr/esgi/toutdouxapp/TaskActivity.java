package fr.esgi.toutdouxapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class TaskActivity extends Activity {

  Task task;
  TextView titleView;
  TextView descriptionView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task);

    titleView = (TextView) findViewById(R.id.title);
    descriptionView = (TextView) findViewById(R.id.description);

    Intent intent = getIntent();
    task = (Task) intent.getParcelableExtra("task");
  }

  @Override
  protected void onStart() {
    super.onStart();

    titleView.setText(task.getTitle());
    descriptionView.setText(task.getDescription());
  }

}
