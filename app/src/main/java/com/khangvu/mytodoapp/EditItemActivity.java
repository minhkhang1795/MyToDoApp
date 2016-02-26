package com.khangvu.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etDescription;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etDescription = (EditText) findViewById(R.id.etEditItemEditText);
        etDescription.setText(getIntent().getStringExtra("item_description"));
        // Move cursor to the end of text field
        etDescription.setSelection(etDescription.getText().length());
        index = getIntent().getIntExtra("index", 0);
    }

    public void onSave(View v) {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("item_description", etDescription.getText().toString());
        data.putExtra("index", index); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
