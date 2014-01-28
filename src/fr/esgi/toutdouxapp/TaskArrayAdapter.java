package fr.esgi.toutdouxapp;

import java.util.List;

import fr.esgi.toutdouxapp.db.Task;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
        int daysLeft = (int) task.getDaysLeft();
        if(daysLeft < 1) {
            dueDateView.setTextColor(Color.parseColor("#FF0000"));
        }
        dueDateView.setText(timeLeft);

        view.findViewById(R.id.category_icon).setBackgroundColor(Color.parseColor(task.category.color));

        LinearLayout taskContent = (LinearLayout) view.findViewById(R.id.task_content);
        taskContent.setOnClickListener(onTaskContentClickListener);

        return view;
    }

    private OnClickListener onTaskContentClickListener = new OnClickListener(){

        @Override
        public void onClick(View view) {
            LinearLayout taskPannel = null;
            ViewGroup row = (ViewGroup) view.getParent();

            View v = row.getChildAt(1);
            if (v instanceof LinearLayout) {
                taskPannel = (LinearLayout) v;
                if (taskPannel.getVisibility() == View.VISIBLE) {
                    taskPannel.setVisibility(View.GONE);
                } else {
                    taskPannel.setVisibility(View.VISIBLE);
                }
            }

        }
    };

}
