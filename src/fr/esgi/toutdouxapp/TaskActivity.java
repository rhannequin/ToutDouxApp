package fr.esgi.toutdouxapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class TaskActivity extends Activity {

  Task task;
  TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task);

    textView = (TextView) findViewById(R.id.title);

    Intent intent = getIntent();
    task = (Task) intent.getParcelableExtra("task");
  }

  @Override
  protected void onStart() {
    super.onStart();

    textView.setText(task.getTitle());
  }

}
