package fr.esgi.toutdouxapp;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {

    private final List<Category> list;
    private final Activity context;

    public CategoryArrayAdapter(Activity context, int resource, List<Category> list) {
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
        View view = inflator.inflate(R.layout.activity_categorylist_row, null);
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = (TextView) view.findViewById(R.id.title);
        view.setTag(viewHolder);
        view.setBackgroundColor(Color.parseColor(list.get(position).getColor()));
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getTitle());
        return view;
    }

}
