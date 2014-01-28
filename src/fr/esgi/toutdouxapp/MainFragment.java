package fr.esgi.toutdouxapp;

import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainFragment extends Fragment {

    private ListView listView;
    private Spinner categoriesSpinner;
    private String spinnerHint = "Bitch please";

    public ArrayList<Category> categories;
    public ArrayList<Task> tasks;

    private enum StateFilters {
        all, todo, done;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_layout, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        initUI();
    }

    private void initUI() {
        listView = (ListView) getView().findViewById(R.id.list);
        tasks = getTaskList(getArguments().getString("filter"));
        listView.setAdapter(setTaskAdapter(tasks));
        listView.setOnItemClickListener(onListViewItemClickListener);

        categoriesSpinner = (Spinner) getView().findViewById(R.id.categories);
        categories = initCategories();
        categoriesSpinner.setAdapter(setSpinnerAdapter(categories));
        categoriesSpinner.setOnItemSelectedListener(spinnerOnSelectedItem);
    }

    private TaskArrayAdapter setTaskAdapter (ArrayList<Task> tasks) {
        return new TaskArrayAdapter(getActivity(), R.layout.activity_tasklist_row, tasks);
    }

    private OnItemClickListener onListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
            Intent taskActivityIntent = new Intent(getActivity(), TaskActivity.class);
            taskActivityIntent.putExtra("taskId", MainFragment.this.tasks.get(position).id);
            startActivity(taskActivityIntent);
        }
    };

    private ArrayList<Category> initCategories() {
        ArrayList<Category> categories = Category.findAll(getActivity());
        Category hint = new Category();
        hint.title = spinnerHint;
        categories.add(0, hint);
        return categories;
    }

    private ArrayAdapter<Category> setSpinnerAdapter (ArrayList<Category> categories) {
        return new ArrayAdapter<Category>(
            getActivity(),
            android.R.layout.simple_spinner_item,
            categories
        );
    }

    private OnItemSelectedListener spinnerOnSelectedItem =
        new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Category selected = (Category) categoriesSpinner.getSelectedItem();
                Boolean applyFilter = !(selected.title == spinnerHint);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        };

    private ArrayList<Task> getTaskList(String filter) {
        ArrayList<Task> tasks;
        switch(StateFilters.valueOf(filter)) {
        case all:
        default:
            tasks = Task.findAll(getActivity(), null);
            break;
        case todo:
            tasks = Task.findAll(getActivity(), "state = 0");
            break;
        case done:
            tasks = Task.findAll(getActivity(), "state = 1");
            break;
        }
        return tasks;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tasks_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case R.id.add_task:
              Intent intent = new Intent(getActivity(), AddTaskActivity.class);
              intent.putParcelableArrayListExtra("categories", this.categories);
              startActivity(intent);
          default:
             return super.onOptionsItemSelected(item);
       }
    }

}
