package fr.esgi.toutdouxapp;

import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class TaskActivity extends ActionBarActivity {

    Task task;
    private TextView titleView, descriptionView, dueDateView, stateView, categoryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#333333")));
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
