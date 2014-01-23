package fr.esgi.toutdouxapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class TaskActivity extends Fragment {

    Task task;
    private TextView titleView, descriptionView, dueDateView, categoryView;

    private String TAG = "TaskActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        
        titleView = (TextView) getView().findViewById(R.id.title);
        descriptionView = (TextView) getView().findViewById(R.id.description);
        dueDateView = (TextView) getView().findViewById(R.id.due_date);
        categoryView = (TextView) getView().findViewById(R.id.category);
        
        final Intent intent =  getActivity().getIntent();
        task = (Task) intent.getParcelableExtra("task");

        titleView.setText(task.getTitle());
        descriptionView.setText(task.getDescription());
        dueDateView.setText(task.getTimeLeft());
        Category category = task.getCategory();
        categoryView.setText("Category: " + category.getTitle() + " (" + category.getColor() + ")");
    }

}
