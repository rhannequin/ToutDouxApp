package fr.esgi.toutdouxapp;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.CategoriesDataSource;
import fr.esgi.toutdouxapp.db.TasksDataSource;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";
    private ListView listView;
    private ArrayAdapter<Task> adapter;
    private TasksDataSource tasksDatasource;
    private CategoriesDataSource categoriesDatasource;

    public ArrayList<Category> categories;
    public ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);

        tasksDatasource = new TasksDataSource(this);
        tasksDatasource.open();

        categoriesDatasource = new CategoriesDataSource(this);
        categoriesDatasource.open();

        listView = (ListView) findViewById(R.id.list);
        this.categories = setListCategories();
        this.tasks = setListTasks();

        adapter = new TaskArrayAdapter(this, R.layout.activity_tasklist_row, tasks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                Intent taskActivityIntent = new Intent(MainActivity.this, TaskActivity.class);
                taskActivityIntent.putExtra("task", MainActivity.this.tasks.get(position));
                startActivity(taskActivityIntent);
            }
        });
    }

    public void addTaskHandler(View v) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putParcelableArrayListExtra("categories", this.categories);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        tasksDatasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        tasksDatasource.close();
        super.onPause();
    }

    private ArrayList<Task> setListTasks() {
        ArrayList<Task> oldTasks = tasksDatasource.getAllTasks();
        for(Task task : oldTasks) {
            tasksDatasource.deleteTask(task);
        }
//        ArrayList<Category> categories = this.categories;
        for(int i = 1; i <= 5; i++) {
            tasksDatasource.createTask("This is my task #" + i, "This is my description #" + i, getOneDay(i));
        }
        return tasksDatasource.getAllTasks();
    }

    private Date getOneDay(int dayBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayBefore);
        return cal.getTime();
    }

    private ArrayList<Category> setListCategories() {
        ArrayList<Category> oldCategories = categoriesDatasource.getAllCategories();
        for(Category category : oldCategories) {
            categoriesDatasource.deleteCategory(category);
        }
        for(int i = 1; i <= 5; i++) {
            categoriesDatasource.createCategory("This is my category #" + i);
        }
        return categoriesDatasource.getAllCategories();
    }

}
