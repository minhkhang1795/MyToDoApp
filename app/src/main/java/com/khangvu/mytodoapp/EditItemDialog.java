package com.khangvu.mytodoapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by duyvu on 2/22/16.
 */
public class EditItemDialog extends DialogFragment {
    private EditText mEditText;
    private ToDoItem item;
    private RadioGroup radioGroup;
    private RadioButton rbu1, rbu2, rbu3;
    private int priorityInt;

    // Defines the listener interface with a method passing back data result.
    public interface EditItemDialogListener {
        void onFinishEditDialog(ToDoItem item);
    }

    private EditItemDialogListener listener;

    public EditItemDialog() {
        this.listener = null;
    }

    public void setFinishDialogListener(EditItemDialogListener listener) {
        this.listener = listener;
    }



    public static EditItemDialog newInstance(String title, ToDoItem todoItem) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        if (todoItem != null) {
            args.putString("description", todoItem.getToDoItemText());
            args.putInt("priority", todoItem.getPriority().ordinal());
        } else {
            args.putString("description", "");
            args.putInt("priority", 0);
        }

        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_edit_item, container);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "My To-Do Task");
        getDialog().setTitle(title);

        item = new ToDoItem();

        // Set task description
        mEditText = (EditText) view.findViewById(R.id.txt_item_name);
        mEditText.setText(getArguments().getString("description", ""));
        mEditText.setSelection(mEditText.getText().length());

        // Set priority
        priorityInt = getArguments().getInt("priority");
        rbu1 = (RadioButton) view.findViewById(R.id.rb_priority_low);
        rbu2 = (RadioButton) view.findViewById(R.id.rb_priority_medium);
        rbu3 = (RadioButton) view.findViewById(R.id.rb_priority_high);
        switch (priorityInt) {
            case 0:
                rbu1.setChecked(true);
                break;
            case 1:
                rbu2.setChecked(true);
                break;
            case 2:
                rbu3.setChecked(true);
                break;
        }
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_priority);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.rb_priority_low)
                    priorityInt = 0;
                else if (checkedId == R.id.rb_priority_medium)
                    priorityInt = 1;
                else priorityInt = 2;
            }
        });

        final Button buttonDone = (Button) view.findViewById(R.id.btn_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                item.setTodoItemText(mEditText.getText().toString());
                item.setPriority(ToDoItem.Priority.values()[priorityInt]);
                listener.onFinishEditDialog(item);
                dismiss();
            }
        });

        final Button buttonCancel = (Button) view.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
