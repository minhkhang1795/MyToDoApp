package com.khangvu.mytodoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by duyvu on 2/21/16.
 */
public class ItemDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TODOITEM, MySQLiteHelper.COLUMN_PRIORITY };

    public ItemDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ToDoItem createToDoItem(ToDoItem item) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TODOITEM, item.getToDoItemText());
        values.put(MySQLiteHelper.COLUMN_PRIORITY, item.getPriority());

        long insertId = database.insert(MySQLiteHelper.TABLE_TODOITEMS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TODOITEMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ToDoItem newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }

    public ToDoItem updateToDoItem (ToDoItem oldItem, ToDoItem newItem) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TODOITEM, newItem.getToDoItemText());
        values.put(MySQLiteHelper.COLUMN_PRIORITY, newItem.getPriority());

        database.update(
                MySQLiteHelper.TABLE_TODOITEMS,
                values,
                MySQLiteHelper.COLUMN_ID + " = " + oldItem.getId(),
                null);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TODOITEMS,
                allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + oldItem.getId(),
                null, null, null, null);
        cursor.moveToFirst();
        ToDoItem updatedItem = cursorToItem(cursor);
        cursor.close();
        return updatedItem;
    }

    public void deleteToDoItem(ToDoItem item) {
        long id = item.getId();
        System.out.println("Item deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TODOITEMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<ToDoItem> getAllItems() {
        ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TODOITEMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ToDoItem toDoItem = cursorToItem(cursor);
            toDoItems.add(toDoItem);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return toDoItems;
    }

    private ToDoItem cursorToItem(Cursor cursor) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId(cursor.getLong(0));
        toDoItem.setTodoItemText(cursor.getString(1));
        toDoItem.setPriority(cursor.getString(2));
        // Add more variables here!
        return toDoItem;
    }
}
