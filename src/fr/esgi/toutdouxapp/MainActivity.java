
package fr.esgi.toutdouxapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.list);

        List<Task> tasks = setListTasks();

        final ArrayAdapter<Task> adapter = new TaskArrayAdapter(this, tasks);
        listview.setAdapter(adapter);
                
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        	@Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {	            
	            Intent taskActivityIntent = new Intent(MainActivity.this, TaskActivity.class);
	      		startActivity(taskActivityIntent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private List<Task> setListTasks() {
        List<Task> tasks = new ArrayList<Task>();
        for(int i = 1; i <= 5; i++) {
            Task task = new Task();
            task.setTitle("This is my task #" + i);
            tasks.add(task);
        }
        return tasks;
    }

}
