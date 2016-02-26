package com.khangvu.mytodoapp;

import java.util.GregorianCalendar;

/**
 * Created by duyvu on 2/18/16.
 */
public class ToDoItem {
    private long id;
    private String item;
    public enum Priority {
        LOW_PRIORITY,
        MEDIUM_PRIORITY,
        HIGH_PRIORITY
    }
    private Priority priority;
    private GregorianCalendar dueDate;

    public ToDoItem() {}
    public ToDoItem(String item, Priority priority, GregorianCalendar dueDate) {
        this.item = item;
        this.priority = priority;
        this.dueDate = dueDate;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public GregorianCalendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(GregorianCalendar dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return item;
    }

}
