package com.cs407.tasktumbler;

public class ToDoItem {
    private String name;
    private String details;
    private String date;
    private String time;
    private String category;

    public ToDoItem(String name, String details, String date, String time, String category){
        this.name = name;
        this.details = name;
        this.date = date;
        this.time = time;
        this.category = time;
    }

    public String getName() { return name; }
    public String getDetails() { return details; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getCategory() { return category; }
}
