package fr.esgi.toutdouxapp;

import java.util.List;

import fr.esgi.toutdouxapp.db.Task;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskArrayAdapter extends ArrayAdapter<Task> {

    private final List<Task> list;
    private final Activity context;

    public TaskArrayAdapter(Activity context, int resource, List<Task> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();
        View view = inflator.inflate(R.layout.activity_tasklist_row, null);

        Task task = list.get(position);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(task.title);

        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        descriptionView.setText(task.description);

        TextView dueDateView = (TextView) view.findViewById(R.id.dueDate);
        String timeLeft = task.getTimeLeft();
        int daysLeft = task.getDaysLeft();
        if(daysLeft < 1) {
            dueDateView.setTextColor(Color.parseColor("#FF0000"));
        }
        dueDateView.setText(timeLeft);

        view.findViewById(R.id.category_icon).setBackgroundColor(Color.parseColor(task.category.color));

        return view;
    }

}
