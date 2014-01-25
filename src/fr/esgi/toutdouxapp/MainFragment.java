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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainFragment extends Fragment {

    private final String TAG = "MainFragment";
    private ListView listView;
    private ArrayAdapter<Task> adapter;

    public ArrayList<Category> categories;
    public ArrayList<Task> tasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        
        listView = (ListView) getView().findViewById(R.id.list);

        this.categories = Category.findAll(getActivity());
        if(this.categories.size() == 0) {
            this.categories = setListCategories();
        }

        this.tasks = Task.findAll(getActivity());
        if(this.tasks.size() == 0) {
            this.tasks = setListTasks();
        }

        adapter = new TaskArrayAdapter(getActivity(), R.layout.activity_tasklist_row, tasks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                Intent taskActivityIntent = new Intent(getActivity(), TaskActivity.class);
                taskActivityIntent.putExtra("task", MainFragment.this.tasks.get(position));
                startActivity(taskActivityIntent);
            }
        });
    }
    
    public void addTaskHandler(View v) {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        intent.putParcelableArrayListExtra("categories", this.categories);
        startActivity(intent);
    }

    public void categoriesListHandler(View v) {
        Intent intent = new Intent(getActivity(), CategoriesListActivity.class);
        startActivity(intent);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    private ArrayList<Task> setListTasks() {
        ArrayList<Category> categories = this.categories;
        Task.deleteAll(getActivity());
        for(int i = 1; i <= 5; i++) {
            Task.create(
                getActivity(),
                "This is my task #" + i,
                "This is my description #" + i,
                getOneDay(i),
                categories.get(new Random().nextInt(categories.size())).getId());
        }
        return Task.findAll(getActivity());
    }

    private Date getOneDay(int dayBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayBefore);
        return cal.getTime();
    }

    private ArrayList<Category> setListCategories() {
        Category.deleteAll(getActivity());
        for(int i = 1; i <= 5; i++) {
            String title = "This is my category #" + i;
            Random r = new Random();
            int color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256));
            String hexa = String.format("#%06X", 0xFFFFFF & color);
            Category.create(getActivity(), title, hexa);
        }
        return Category.findAll(getActivity());
    }

}
