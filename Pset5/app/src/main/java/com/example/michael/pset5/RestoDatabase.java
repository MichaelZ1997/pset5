package com.example.michael.pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Michael on 4-12-2017.
 */

public class RestoDatabase extends SQLiteOpenHelper {

    private static RestoDatabase instance;

    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static RestoDatabase getInstance(Context context) {
        if(instance == null) {
            instance = new RestoDatabase(context, "restdb", null, 1);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table restdb (_id INTEGER PRIMARY KEY, title TEXT, price REAL, amount INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "restdb");
        onCreate(sqLiteDatabase);
    }

    public Cursor selectAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM restdb", null);
        return cursor;
    }

    public void addItem(String title, double price, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if ( db.rawQuery("SELECT amount FROM restdb WHERE _id = "+ id, null).moveToFirst()) {
            db.execSQL("UPDATE restdb SET amount = amount + 1 WHERE _id =" + id);
        } else {
            cv.put("title", title);
            cv.put("_id", id);
            cv.put("price", price);
            cv.put("amount", 1);

            db.insert("restdb", "null", cv);
        }

    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS" + " restdb");
        onCreate(db);
    }

}
