package fr.esgi.toutdouxapp;

import java.util.ArrayList;

import fr.esgi.toutdouxapp.db.CategoriesDataSource;
import fr.esgi.toutdouxapp.db.Category;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

    public void addCategoryHandler(View v) {
        Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
        startActivity(intent);
    }

}