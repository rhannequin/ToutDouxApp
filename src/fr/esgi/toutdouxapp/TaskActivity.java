package fr.esgi.toutdouxapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class TaskActivity extends Activity {

    Task task;
    private TextView titleView, descriptionView, dueDateView, categoryView;

    private String TAG = "TaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_task);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

        titleView = (TextView) findViewById(R.id.title);
        descriptionView = (TextView) findViewById(R.id.description);
        dueDateView = (TextView) findViewById(R.id.due_date);
        categoryView = (TextView) findViewById(R.id.category);

        final Intent intent = getIntent();
        task = (Task) intent.getParcelableExtra("task");
    }

    @Override
    protected void onStart() {
        super.onStart();

        titleView.setText(task.getTitle());
        descriptionView.setText(task.getDescription());
        dueDateView.setText(task.getTimeLeft());
        Category category = task.getCategory();
        categoryView.setText("Category: " + category.getTitle() + " (" + category.getColor() + ")");
    }

}