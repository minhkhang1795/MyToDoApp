package com.khangvu.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ToDoItem> todoItems;
    ArrayAdapter<ToDoItem> customAdapter;
//    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText edEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(customAdapter);
        edEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoItems.remove(i);
                customAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemContent = todoItems.get(i).name.toString();
                launchEditItemView(itemContent, i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String itemContent = data.getExtras().getString("item_content");
            int index = data.getExtras().getInt("index", 0);
            todoItems.remove(index);

            ToDoItem tempItem = new ToDoItem(itemContent, "MEDIUM");
            todoItems.add(index, tempItem);
            customAdapter.notifyDataSetChanged();
            writeItems();
            // Toast the name to display temporarily on screen
            Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
        }
    }

    public void populateArrayItems() {
        readItems();
//        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        customAdapter = new CustomToDoItemAdapter(this, todoItems);
    }

    public void launchEditItemView(String itemContent, int index) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("item_content", itemContent);
        i.putExtra("index", index);
        startActivityForResult(i, REQUEST_CODE);; // brings up the second activity
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<ToDoItem>();
            ArrayList<String> items =  new ArrayList<String>(FileUtils.readLines(file));
            for (int i = 0; i < items.size(); i++) {
                ToDoItem tempItem = new ToDoItem(items.get(i).toString(), "MEDIUM");
                todoItems.add(tempItem);
            }
        } catch(IOException e) {
            todoItems = new ArrayList<ToDoItem>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        ArrayList<String> items =  new ArrayList<String>();
        for (int i = 0; i < todoItems.size(); i++) {
            items.add(i, todoItems.get(i).name.toString());
        }
        try {
            FileUtils.writeLines(file, items);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        ToDoItem tempItem = new ToDoItem(edEditText.getText().toString(), "MEDIUM");
        todoItems.add(tempItem);
        edEditText.setText("");
        writeItems();
    }
}
