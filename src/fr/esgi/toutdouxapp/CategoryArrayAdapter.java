package fr.esgi.toutdouxapp;

import java.util.List;

import fr.esgi.toutdouxapp.db.Category;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        final Category category = list.get(position);

        final TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(list.get(position).title);

        view.findViewById(R.id.category_icon).setBackgroundColor(Color.parseColor(list.get(position).color));


        /** Action buttons **/

        final LinearLayout categoryContent = (LinearLayout) view.findViewById(R.id.category_content);
        categoryContent.setOnClickListener(onCategoryContentClickListener);

        final Button deleteButton = (Button) view.findViewById(R.id.deleteCategory);
        deleteButton.setOnClickListener(deleteCategoryHandler(category));

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

    private OnClickListener deleteCategoryHandler(final Category category) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Category.deleteOne(context, category);
                list.remove(category);
                Toast.makeText(context, "Category deleted", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        };
    }

}
