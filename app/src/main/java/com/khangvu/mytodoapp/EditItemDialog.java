package com.khangvu.mytodoapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by duyvu on 2/22/16.
 */
public class EditItemDialog extends DialogFragment {
    private ToDoItem item = new ToDoItem();
    private int priorityInt;
    private DatePicker dueDatePicker;
    private EditText etDescription;

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
            GregorianCalendar dueDate = todoItem.getDueDate();
            args.putInt("date", dueDate.get(Calendar.DATE));
            args.putInt("month", dueDate.get(Calendar.MONTH));
            args.putInt("year", dueDate.get(Calendar.YEAR));
        } else {
            args.putString("description", "");
            args.putInt("year", 0);
        }

        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // Request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setNavigationBarColor(ContextCompat.getColor(dialog.getContext(), R.color.dark_blue));
        }
        // Show Keyboard with ugly UI ---> STUPID ANDROID!!
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(getView(), InputMethodManager.SHOW_IMPLICIT);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDescription = (EditText) view.findViewById(R.id.txt_item_name);
        final Button buttonSave = (Button) view.findViewById(R.id.btn_save);
        final Button buttonDiscard = (Button) view.findViewById(R.id.btn_discard);
        RadioButton rbu1 = (RadioButton) view.findViewById(R.id.rb_priority_low);
        RadioButton rbu2 = (RadioButton) view.findViewById(R.id.rb_priority_medium);
        RadioButton rbu3 = (RadioButton) view.findViewById(R.id.rb_priority_high);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_priority);
        dueDatePicker = (DatePicker) view.findViewById(R.id.dp_due_date);

        // Disable/Enable Save Button ---> STUPID ANDROID!
        if (getArguments().getString("description", "").equals("")) {
            buttonSave.setEnabled(false);
            buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        } else {
            buttonSave.setEnabled(true);
            buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
        }

        // Set task description
        etDescription.requestFocus();
        etDescription.setText(getArguments().getString("description", ""));
        etDescription.setSelection(etDescription.getText().length());
        etDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    hideKeyboardFrom(getContext(), getView());
                else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
            }
        });
        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                //Check if 'text' is empty
                if (text.toString().isEmpty()) {
                    buttonSave.setEnabled(false);
                    buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                } else {
                    buttonSave.setEnabled(true);
                    buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
                }
            }
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                // TODO Auto-generated method stub
                //Check if 'text' is empty
                if (text.toString().isEmpty()) {
                    buttonSave.setEnabled(false);
                    buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                } else {
                    buttonSave.setEnabled(true);
                    buttonSave.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
                }
            }
            @Override
            public void afterTextChanged(Editable text) {
                // TODO Auto-generated method stub
            }
        });


        // Set priority
        priorityInt = getArguments().getInt("priority");
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideKeyboardFrom(getContext(), getView());
                // find which radio button is selected
                if (checkedId == R.id.rb_priority_low)
                    priorityInt = 0;
                else if (checkedId == R.id.rb_priority_medium)
                    priorityInt = 1;
                else priorityInt = 2;
            }
        });

        // Set date
        int year = getArguments().getInt("year", 0);
        if (year != 0) {
            int month = getArguments().getInt("month", 0) - 1;
            int day = getArguments().getInt("date", 0);
            dueDatePicker.updateDate(year, month, day);
        } else {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);
            dueDatePicker.updateDate(year, month, day);
        }

        // Button done
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GregorianCalendar date = new GregorianCalendar(dueDatePicker.getYear(), dueDatePicker
                        .getMonth() + 1, dueDatePicker.getDayOfMonth());
                item.setDueDate(date);
                item.setTodoItemText(etDescription.getText().toString());
                item.setPriority(ToDoItem.Priority.values()[priorityInt]);
                listener.onFinishEditDialog(item);
                dismiss();
            }
        });

        // Button Canceled
        buttonDiscard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_edit_item, container);
    }

    public void onResume() {
        super.onResume();
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    // Dismiss keyboard ---> STUPID ANDROID!!!
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
