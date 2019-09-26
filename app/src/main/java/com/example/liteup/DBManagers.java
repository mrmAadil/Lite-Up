package com.example.liteup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManagers {
    private DatabaseHelpers dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagers(Context c) {
        context = c;
    }

    public DBManagers open() throws SQLException {
        dbHelper = new DatabaseHelpers(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc, String address,String phone) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelpers.RESTAURANT, name);
        contentValue.put(DatabaseHelpers.EMAIL, desc);
        contentValue.put(DatabaseHelpers.ADDRESS, address);
        contentValue.put(DatabaseHelpers.PHONE, phone);

        database.insert(DatabaseHelpers.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelpers._ID, DatabaseHelpers.RESTAURANT, DatabaseHelpers.EMAIL, DatabaseHelpers.ADDRESS, DatabaseHelpers.PHONE };
        Cursor cursor = database.query(DatabaseHelpers.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc, String address,String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelpers.RESTAURANT, name);
        contentValues.put(DatabaseHelpers.EMAIL, desc);
        contentValues.put(DatabaseHelpers.ADDRESS, address);
        contentValues.put(DatabaseHelpers.PHONE, phone);

        int i = database.update(DatabaseHelpers.TABLE_NAME, contentValues, DatabaseHelpers._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelpers.TABLE_NAME, DatabaseHelpers._ID + "=" + _id, null);
    }

}
