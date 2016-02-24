package com.khangvu.mytodoapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.khangvu.mytodoapp.EditItemDialog.EditItemDialogListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ToDoItem> todoItems;
    ArrayAdapter<ToDoItem> customAdapter;
    private ItemDataSource dataSource;
    ListView lvItems;
//    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new ItemDataSource(this);
        dataSource.open();

        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(customAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataSource.deleteToDoItem(todoItems.get(i));
                todoItems.remove(i);
                customAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showEditItemDialog(todoItems.get(i), i);
            }
        });
    }

    public void populateArrayItems() {
        todoItems = dataSource.getAllItems();
        customAdapter = new CustomToDoItemAdapter(this, todoItems);
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    // This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    private void showAddItemDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance("Create A To-Do Task", null);
        editItemDialog.setFinishDialogListener(new EditItemDialogListener() {
            @Override
            public void onFinishEditDialog(ToDoItem item) {
                ToDoItem newItem = new ToDoItem(item.getToDoItemText(), item.getPriority());
                newItem = dataSource.createToDoItem(newItem);
                todoItems.add(newItem);
                customAdapter.notifyDataSetChanged();
            }
        });
        editItemDialog.show(fm, "fragment_todo_item");
    }

    private void showEditItemDialog(ToDoItem item, final int index) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance("Edit My To-Do Task", item);
        editItemDialog.setFinishDialogListener(new EditItemDialogListener() {
            @Override
            public void onFinishEditDialog(ToDoItem item) {
                ToDoItem newItem = new ToDoItem(item.getToDoItemText(), item.getPriority());
                newItem = dataSource.updateToDoItem(todoItems.get(index), newItem);
                todoItems.remove(index);
                todoItems.add(index, newItem);
                customAdapter.notifyDataSetChanged();
            }
        });
        editItemDialog.show(fm, "fragment_create_edit_item");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_to_do_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showAddItemDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        // REQUEST_CODE is defined above
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            dataSource.open();
//            // Extract name value from result extras
//            String itemContent = data.getExtras().getString("item_content");
//            String itemPriority = "MEDIUM";
//            int index = data.getExtras().getInt("index");
//
//            ToDoItem newItem = new ToDoItem(itemContent, itemPriority);
//            newItem = dataSource.updateToDoItem(todoItems.get(index), newItem);
//
//            todoItems.remove(index);
//            todoItems.add(index, newItem);
//            customAdapter.notifyDataSetChanged();
//
//            // Toast the name to display temporarily on screen
//            Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
//        }
//    }

//    public void onAddItem(View view) {
//        ToDoItem newItem = new ToDoItem(edEditText.getText().toString(), "MEDIUM");
//        newItem = dataSource.createToDoItem(newItem);
//        todoItems.add(newItem);
//        customAdapter.notifyDataSetChanged();
//        edEditText.setText("");
//    }

//    public void launchEditItemView(ToDoItem item, int index) {
//        // first parameter is the context, second is the class of the activity to launch
//        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//        i.putExtra("item_content", item.getToDoItemText());
//        i.putExtra("index", index);
//        i.putExtra("priority", item.getPriority());
//        startActivityForResult(i, REQUEST_CODE); // brings up the second activity
//    }

//    private void showEditItemDialog(final TodoItem item) {
//        FragmentManager fm = getSupportFragmentManager();
//        TodoItemDialog todoItemDialog = TodoItemDialog.newInstance(
//                "Edit Todo Item", item);
//        todoItemDialog.setFinishDialogListener(new TodoItemDialogListener() {
//            public void onFinishDialog(String description, Date date,
//                                       TodoItem.Priority priority) {
//                item.setDescription(description);
//                item.setDueDate(date);
//                item.setPriority(priority);
//                itemsAdapter.notifyDataSetChanged();
//                item.save();
//            }
//        });
//
//        todoItemDialog.show(fm, "fragment_todo_item");
//    }

//    private void readItems() {
//        File filesDir = getFilesDir();
//        File file = new File(filesDir, "todo.txt");
//        try {
//            todoItems = new ArrayList<ToDoItem>();
//            ArrayList<String> tempItemList =  new ArrayList<String>(FileUtils.readLines(file));
//            for (int i = 0; i < tempItemList.size(); i++) {
//                ToDoItem tempItem = new ToDoItem(tempItemList.get(i), "MEDIUM");
//                todoItems.add(tempItem);
//            }
//        } catch(IOException e) {
//            todoItems = new ArrayList<ToDoItem>();
//        }
//    }

//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File file = new File(filesDir, "todo.txt");
//        ArrayList<String> items =  new ArrayList<String>();
//        for (int i = 0; i < todoItems.size(); i++) {
//            items.add(i, todoItems.get(i).getToDoItem());
//        }
//        try {
//            FileUtils.writeLines(file, items);
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }

