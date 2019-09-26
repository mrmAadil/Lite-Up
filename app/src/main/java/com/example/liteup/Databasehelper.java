package com.example.liteup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    Context context;

    private static final String DATABASE_NAME = "Reservation.db";
    private static final String TABLE_NAME = "account_table";
    private static final String col_1 = "id";
    private static final String col_2 = "fullname";
    private static final String col_3 = "username";
    private static final String col_4 = "email";
    private static final String col_5 = "password";


    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, fullname TEXT, username TEXT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long create(String fullname, String username, String email, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, fullname);
        contentValues.put(col_3, username);
        contentValues.put(col_4, email);
        contentValues.put(col_5, password);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return result;
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME, null );
        return res;
    }

    public User getUserData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("Select * from " + TABLE_NAME + " where id = " + id, null );
        do{
            if(!res.moveToFirst()){
                return null;
            }
            User user = new User(res.getInt(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4));
            return user;
        }while (res.moveToNext());
    }

    public int checkUser(String username, String password){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username = '" + username + "' and password = '" + password + "'", null);
        do{
            if(!cursor.moveToFirst())
                return -1;

            return cursor.getInt(0);

        }while (cursor.moveToNext());
    }

    public Integer deleteUser(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "id = ?", new String[] {id});
    }
    public boolean updateData(String id,String fullname,String username,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1,id);
        contentValues.put(col_2,fullname);
        contentValues.put(col_3,username);
        contentValues.put(col_4,email);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }
}
