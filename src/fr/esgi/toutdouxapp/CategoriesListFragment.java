package fr.esgi.toutdouxapp;

import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.Category;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoriesListFragment extends Fragment {

    ArrayList<Category> categories;
    private ListView listView;
    private ArrayAdapter<Category> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = (ListView) getView().findViewById(R.id.list);
        this.categories = Category.findAll(getActivity());
        adapter = new CategoryArrayAdapter(getActivity(), R.layout.activity_categorylist_row, CategoriesListFragment.this.categories);
        listView.setAdapter(adapter);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
 
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.categories_list, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
          case R.id.add_category:
              Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
              startActivity(intent);
          default:
             return super.onOptionsItemSelected(item);
       }
    }

}