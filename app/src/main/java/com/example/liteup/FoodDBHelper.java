package com.example.liteup;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class FoodDBHelper extends SQLiteOpenHelper {

    public FoodDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    //insert data of food
    public void insertFood(String name,String price,byte[] image){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "INSERT INTO FOODS VALUES(NULL, ?, ?, ?)";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,price);
        statement.bindBlob(3,image);

        statement.executeInsert();
    }

    //Read food data from database
    public Cursor getFood(String sql){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }

    public void updateFood(String name,String price,byte[] image,int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE FOODS SET name = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,name);
        statement.bindString(2,price);
        statement.bindBlob(3,image);
        statement.bindDouble(4,(double)id);

        statement.execute();
        database.close();

    }

    public void deleteFood(int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM FOODS WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
