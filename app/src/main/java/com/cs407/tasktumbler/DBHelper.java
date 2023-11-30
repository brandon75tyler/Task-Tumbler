package com.cs407.tasktumbler;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DBHelper {
    static SQLiteDatabase sqLiteDatabase;
    public DBHelper (SQLiteDatabase sqLiteDatabase) { this.sqLiteDatabase = sqLiteDatabase; }

    public static void createTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS toDoItems " + "(id INTEGER PRIMARY KEY, itemid INTEGER, name TEXT, details TEXT, date TEXT, time TEXT, category TEXT)");
    }

    public ArrayList<ToDoItem> readToDoItems() {

        createTable();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM toDoItems",
                new String[]{});
        int nameIndex = c.getColumnIndex("name");
        int detailsIndex = c.getColumnIndex("details");
        int dateIndex = c.getColumnIndex("date");
        int timeIndex = c.getColumnIndex("time");
        int categoryIndex = c.getColumnIndex("category");
        c.moveToFirst();
        ArrayList<ToDoItem> toDoItemList = new ArrayList<>();

        while (!c.isAfterLast()) {
            String name = c.getString(nameIndex);
            String details = c.getString(detailsIndex);
            String date = c.getString(dateIndex);
            String time = c.getString(timeIndex);
            String category = c.getString(categoryIndex);

            ToDoItem toDoItem = new ToDoItem(name, details, date, time, category);
            toDoItemList.add(toDoItem);
            c.moveToNext();
        }

        c.close();
        sqLiteDatabase.close();
        return toDoItemList;
    }

    public void saveToDoItem(String name, String details, String date, String time, String category) {
        createTable();
        sqLiteDatabase.execSQL("INSERT INTO toDoItems (name, details, date, time, category) VALUES (?, ?, ?, ?, ?)",
                new String[]{name, details, date, time, category});
    }

    public void updateNotes(String name, String details, String date, String time, String category) {
        createTable();
        ToDoItem note = new ToDoItem(name, details, date, time, category);
        sqLiteDatabase.execSQL("UPDATE toDoItems set details=?, date=?, time=?, category=? where name=?",
                new String[]{details, date, time, category, name});
    }

    public void deleteToDoItem(String name, String details) {
        createTable();
        sqLiteDatabase.execSQL("DELETE FROM toDoItems WHERE name = ? AND details = ?",
                new String[]{name, details});
    }
}