package fr.esgi.toutdouxapp;

import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class TaskActivity extends Activity {

    Task task;
    private TextView titleView, descriptionView, dueDateView, stateView, categoryView;

    private String TAG = "TaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task);

        titleView = (TextView) findViewById(R.id.title);
        descriptionView = (TextView) findViewById(R.id.description);
        dueDateView = (TextView) findViewById(R.id.due_date);
        stateView = (TextView) findViewById(R.id.state);
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
        stateView.setText("State: " + (task.isDone() ? "done" : "todo"));
        Category category = task.getCategory();
        categoryView.setText("Category: " + category.getTitle() + " (" + category.getColor() + ")");
    }

}