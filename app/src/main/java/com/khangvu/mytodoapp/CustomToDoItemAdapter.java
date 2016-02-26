package com.khangvu.mytodoapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

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
        ToDoItem toDoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.to_do_item, parent, false);
        }
        // Lookup view for data population
        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        // Populate the data into the template view using the data object
        tvItemName.setText(toDoItem.getToDoItemText());
        tvItemName.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
        tvPriority.setTypeface(null, Typeface.BOLD);
        switch(toDoItem.getPriority()) {
            case LOW_PRIORITY:
                tvPriority.setText(R.string.rb_priority_low_capital);
                tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.medium_green));
                break;
            case MEDIUM_PRIORITY:
                tvPriority.setText(R.string.rb_priority_medium_capital);
                tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.medium_yellow));
                break;
            case HIGH_PRIORITY:
                tvPriority.setText(R.string.rb_priority_high_capital);
                tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.medium_red));
                break;
        }

        String day = String.valueOf(toDoItem.getDueDate().get(Calendar.DATE));
        String month = String.valueOf(toDoItem.getDueDate().get(Calendar.MONTH));
        switch (month) {
            case "0":
                month = "Dec";
                break;
            case "1":
                month = "Jan";
                break;
            case "2":
                month = "Feb";
                break;
            case "3":
                month = "Mar";
                break;
            case "4":
                month = "Apr";
                break;
            case "5":
                month = "May";
                break;
            case "6":
                month = "Jun";
                break;
            case "7":
                month = "Jul";
                break;
            case "8":
                month = "Aug";
                break;
            case "9":
                month = "Sep";
                break;
            case "10":
                month = "Oct";
                break;
            case "11":
                month = "Nov";
                break;
        }
        String year = String.valueOf(toDoItem.getDueDate().get(Calendar.YEAR));
        tvDate.setText(getContext().getResources().getString(R.string.item_date, month, day, year));
        return convertView;
    }
}