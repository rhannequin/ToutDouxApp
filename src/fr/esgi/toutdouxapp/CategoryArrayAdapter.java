package fr.esgi.toutdouxapp;

import java.util.List;

import fr.esgi.toutdouxapp.db.Category;
import fr.esgi.toutdouxapp.db.Task;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
        view.findViewById(R.id.category_icon).setBackgroundColor(Color.parseColor(list.get(position).color));
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).title);

        /** Action buttons **/

        final LinearLayout categoryContent = (LinearLayout) view.findViewById(R.id.category_content);
        categoryContent.setOnClickListener(onCategoryContentClickListener);

        //final Button editButton = (Button) view.findViewById(R.id.editTask);
        // TODO: editButton.setOnClickListener();

        //final Button deleteButton = (Button) view.findViewById(R.id.deleteTask);
        // TODO: deleteButton.setOnClickListener();

        return view;
    }

    private OnClickListener onCategoryContentClickListener = new OnClickListener(){
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
