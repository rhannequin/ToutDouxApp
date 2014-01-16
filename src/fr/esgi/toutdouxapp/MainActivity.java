package fr.esgi.toutdouxapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";
    private ListView listView;
    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        List<Task> tasks = setListTasks();

        adapter = new TaskArrayAdapter(this, R.layout.activity_tasklist_row, tasks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
	        public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        		Intent taskActivityIntent = new Intent(MainActivity.this, TaskActivity.class);
        		taskActivityIntent.putExtra("task", setListTasks().get(position));
        		startActivity(taskActivityIntent);
	        }
        });
    }

    private List<Task> setListTasks() {
        List<Task> tasks = new ArrayList<Task>();
        for(int i = 1; i <= 5; i++) {
        	String title = "This is my task #" + i;
        	String description = "This is my description #" + i;
            Task task = new Task(title, description);
            tasks.add(task);
        }
        return tasks;
    }

}
