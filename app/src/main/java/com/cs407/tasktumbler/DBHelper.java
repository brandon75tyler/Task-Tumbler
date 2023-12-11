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

    public void updateToDoItem(String name, String details, String date, String time, String category) {
        createTable();
        ToDoItem toDoItem = new ToDoItem(name, details, date, time, category);
        sqLiteDatabase.execSQL("UPDATE toDoItems set details=?, date=?, time=?, category=? where name=?",
                new String[]{details, date, time, category, name});
    }

    public void deleteToDoItem(String name, String details) {
        createTable();
        String date = "";
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT date from toDoItems where name = ?",
                new String[]{name});
        if (cursor.moveToNext()){
            date = cursor.getString(0);
        }
        sqLiteDatabase.execSQL("DELETE FROM toDoItems WHERE name = ? AND date = ?",
                new String[]{name, date});
        cursor.close();
    }

    public static void createCatTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS categories " + "(id INTEGER PRIMARY KEY, itemid INTEGER, name TEXT)");
    }

    public ArrayList<Category> readCategories() {

        createCatTable();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM categories",
                new String[]{});
        int nameIndex = c.getColumnIndex("name");
        c.moveToFirst();
        ArrayList<Category> categoryList = new ArrayList<>();

        while (!c.isAfterLast()) {
            String name = c.getString(nameIndex);

            Category category = new Category(name);
            categoryList.add(category);
            c.moveToNext();
        }

        c.close();
        sqLiteDatabase.close();
        return categoryList;
    }

    public void saveCategory(String name) {
        createCatTable();
        sqLiteDatabase.execSQL("INSERT INTO categories (name) VALUES (?)",
                new String[]{name});
    }

    public void deleteCategory(String name) {
        createCatTable();
        sqLiteDatabase.execSQL("DELETE FROM categories WHERE name = ?",
                new String[]{name});
    }
}
