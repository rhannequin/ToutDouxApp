package fr.esgi.toutdouxapp;

import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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
    private MenuItem orderSpinner;
    private static String stateFilter;
    private String spinnerHint = "Filter by category";

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
        stateFilter = getArguments().getString("filter");
        listView = (ListView) getView().findViewById(R.id.list);
        tasks = getTaskList(getArguments().getString("filter"));
        listView.setAdapter(setTaskAdapter(tasks, stateFilter));
        listView.setOnItemClickListener(onListViewItemClickListener);
    }

    private TaskArrayAdapter setTaskAdapter (ArrayList<Task> tasks, String filter) {
        return new TaskArrayAdapter(getActivity(), R.layout.activity_tasklist_row, tasks, filter);
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

        orderSpinner = menu.findItem(R.id.order_task);
        View v = (Spinner) MenuItemCompat.getActionView(orderSpinner);

        if (v instanceof Spinner) {
            final Spinner spinner = (Spinner) v;
            categories = initCategories();
            spinner.setAdapter(setSpinnerAdapter(categories));

            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Category selected = (Category) spinner.getSelectedItem();
                    Boolean applyFilter = !(selected.title == spinnerHint);
                    tasks = getTaskList(MainFragment.stateFilter);
                    if(applyFilter) {
                        ArrayList<Task> newTasks = new ArrayList<Task>();
                        for(Task task : tasks) {
                            if(task.category.id == selected.id) {
                                newTasks.add(task);
                            }
                        }
                        tasks = newTasks;
                    }
                    listView.setAdapter(setTaskAdapter(tasks, MainFragment.stateFilter));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {}
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case R.id.add_task:
            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            Task task = new Task().empty(categories.get(0));
            intent.putExtra("task", task);
            startActivity(intent);
          default:
            return super.onOptionsItemSelected(item);
       }
    }

}
