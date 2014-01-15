package fr.esgi.toutdouxapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Task> tasks = setListTasks();

        final ArrayAdapter<Task> adapter = new TaskArrayAdapter(this, R.layout.activity_tasklist_row, tasks);
        setListAdapter(adapter);
    }

    public void onClickTaskItem(View v) {
        Intent taskActivityIntent = new Intent(this, TaskActivity.class);
        taskActivityIntent.putExtra("task", setListTasks().get(0));
        startActivity(taskActivityIntent);
    }

    private List<Task> setListTasks() {
        List<Task> tasks = new ArrayList<Task>();
        for(int i = 1; i <= 5; i++) {
            Task task = new Task("This is my task #" + i);
            tasks.add(task);
        }
        return tasks;
    }

}
