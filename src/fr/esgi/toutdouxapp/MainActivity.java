package fr.esgi.toutdouxapp;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.Random;

import fr.esgi.toutdouxapp.db.CategoriesDataSource;
import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import fr.esgi.toutdouxapp.db.TasksDataSource;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

        listView = (ListView) findViewById(R.id.list);
    }

    public void addTaskHandler(View v) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putParcelableArrayListExtra("categories", this.categories);
        startActivity(intent);
    }

    public void categoriesListHandler(View v) {
        Intent intent = new Intent(this, CategoriesListActivity.class);
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
    protected void onStart() {
        super.onStart();

        categoriesDatasource = new CategoriesDataSource(this);
        categoriesDatasource.open();
        this.categories = categoriesDatasource.getAllCategories();
        if(this.categories.size() == 0) {
            this.categories = setListCategories();
        }

        tasksDatasource = new TasksDataSource(this);
        tasksDatasource.open();
        this.tasks = tasksDatasource.getAllTasks();
        if(this.tasks.size() == 0) {
            this.tasks = setListTasks();
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tasksDatasource != null) {
            tasksDatasource.close();
        }
    }

    private ArrayList<Task> setListTasks() {
        ArrayList<Category> categories = this.categories;
        tasksDatasource.resetTable();
        for(int i = 1; i <= 5; i++) {
            tasksDatasource.createTask(
                "This is my task #" + i,
                "This is my description #" + i,
                getOneDay(i),
                categories.get(new Random().nextInt(categories.size())).getId());
        }
        return tasksDatasource.getAllTasks();
    }

    private Date getOneDay(int dayBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayBefore);
        return cal.getTime();
    }

    private ArrayList<Category> setListCategories() {
        categoriesDatasource.resetTable();
        for(int i = 1; i <= 5; i++) {
            String title = "This is my category #" + i;
            Random r = new Random();
            int color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
            String hexa = String.format("#%06X", 0xFFFFFF & color);
            categoriesDatasource.createCategory(title, hexa);
        }
        return categoriesDatasource.getAllCategories();
    }

}
