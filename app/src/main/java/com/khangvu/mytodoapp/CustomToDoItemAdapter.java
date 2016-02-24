package com.khangvu.mytodoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by duyvu on 2/18/16.
 */
public class CustomToDoItemAdapter extends ArrayAdapter<ToDoItem> {
    public CustomToDoItemAdapter(Context context, ArrayList<ToDoItem> toDoItems) {
        super(context, 0, toDoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToDoItem ToDoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.to_do_item, parent, false);
        }
        // Lookup view for data population
        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        // Populate the data into the template view using the data object
        tvItemName.setText(ToDoItem.getToDoItemText());
        switch(ToDoItem.getPriority()) {
            case LOW_PRIORITY:
                tvPriority.setText("LOW");
                break;
            case MEDIUM_PRIORITY:
                tvPriority.setText("MEDIUM");
                break;
            case HIGH_PRIORITY:
                tvPriority.setText("HIGH");
                break;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}