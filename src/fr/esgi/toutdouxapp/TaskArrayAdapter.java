package fr.esgi.toutdouxapp;

import java.util.List;

import fr.esgi.toutdouxapp.db.Task;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
        final LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.activity_tasklist_row, null);
        final Task task = list.get(position);

        final TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(task.title);

        final TextView descriptionView = (TextView) view.findViewById(R.id.description);
        descriptionView.setText(task.description);

        if(task.isDone()){
            final ImageView doneIcon = (ImageView) view.findViewById(R.id.icon_done);
            doneIcon.setVisibility(View.VISIBLE);
        }

        final TextView dueDateView = (TextView) view.findViewById(R.id.dueDate);
        manageDueDateView(dueDateView, task);

        final TextView categoryView = (TextView) view.findViewById(R.id.category);
        categoryView.setTextColor(Color.parseColor(task.category.color));
        categoryView.setTypeface(null, Typeface.ITALIC);
        categoryView.setText(task.category.title);

        /** Action buttons **/

        final LinearLayout taskContent = (LinearLayout) view.findViewById(R.id.task_content);
        taskContent.setOnClickListener(onTaskContentClickListener);

        final Button validateButton = (Button) view.findViewById(R.id.validateTask);
        validateButton.setOnClickListener(validateTaskHandler(task));

        final Button editButton = (Button) view.findViewById(R.id.editTask);
        editButton.setOnClickListener(editTaskHandler(task));

        final Button deleteButton = (Button) view.findViewById(R.id.deleteTask);
        deleteButton.setOnClickListener(deleteTaskHandler(task));

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

    private void manageDueDateView(TextView dueDateView, final Task task) {
        final String timeLeft = task.getTimeLeft();
        final int daysLeft = (int) task.getDaysLeft();
        if(daysLeft < 1) {
            dueDateView.setTextColor(Color.parseColor("#FF0000"));
        }
        dueDateView.setText(timeLeft);
    }

    private OnClickListener validateTaskHandler(final Task task) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!task.isDone()) {
                    task.toggleState(context);
                    list.remove(task);
                    notifyDataSetChanged();
                }
            }
        };
    }

    private OnClickListener editTaskHandler(final Task task) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MOFO", "Edit " + task.title);
                Intent intent = new Intent(context, AddTaskActivity.class);
                intent.putExtra("task", task);
                context.startActivity(intent);
            }
        };
    }

    private OnClickListener deleteTaskHandler(final Task task) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Task.deleteOne(context, task);
                list.remove(task);
                notifyDataSetChanged();
            }
        };
    }

}
