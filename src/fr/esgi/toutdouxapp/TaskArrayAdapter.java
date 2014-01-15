package fr.esgi.toutdouxapp;

import java.util.List;

import android.app.Activity;
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
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) view.findViewById(R.id.title);
        view.setTag(viewHolder);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getTitle());
        return view;
    }

}