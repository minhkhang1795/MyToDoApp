package com.khangvu.mytodoapp;

/**
 * Created by duyvu on 2/18/16.
 */
public class ToDoItem {
    private long id;
    private String item;
    private String priority;

    public ToDoItem() {}
    public ToDoItem(String item, String priority) {
        this.item = item;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToDoItemText() {
        return item;
    }

    public void setTodoItemText(String item) {
        this.item = item;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return item;
    }

}
