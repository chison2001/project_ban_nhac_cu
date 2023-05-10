package com.example.projectbanhang.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.projectbanhang.model.Product;
import com.example.projectbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "UserDbHelper";
    private static final String DATABASE_NAME = "bannhaccu.db";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";


    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR (255) , " +
                "email VARCHAR (255) NOT NULL, " +
                "pass VARCHAR (255) NOT NULL," +
                "phone VARCHAR (255),  " +
                "role VARCHAR (255) NOT NULL " +
                ")";

        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == DATABASE_VERSION){
            //Xoá bảng cũ
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            //Tiến hành tạo bảng mới
            onCreate(db);
            DATABASE_VERSION = newVersion;
        }

    }
    public void insertUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+ TABLE_USER+" (username, email, pass, phone, role) VALUES (?,?,?,?,?)",
                new String[]{user.getUserName(), user.getEmail(), user.getPass(), user.getPhoneNumber(), user.getRole()+ ""});
    }
    public boolean checkUserEmail(String emailkey) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,null,"email = ?" ,new String[]{emailkey+""},null,null,null,null);
        if (cursor.getCount() == 0){
            return  false;
        }else {
            return true;
        }
    }
    public boolean checkUserName(String usernamekey) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,null,"username = ?" ,new String[]{usernamekey+""},null,null,null,null);
        if (cursor.getCount() == 0){
            return  false;
        }else {
            return true;
        }
    }
    public boolean checkUserPhone(String phonekey) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,null,"phone = ?" ,new String[]{phonekey+""},null,null,null,null);
        if (cursor.getCount() == 0){
            return  false;
        }else {
            return true;
        }
    }
    public User getUserByusername(String usernamekey){
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_USER+" where username = ?",
                new String[]{usernamekey + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String email = cursor.getString(2);
            String pass = cursor.getString(3);
            String phone = cursor.getString(4);
            String role = cursor.getString(5);
            user = new User(id, username,email,pass,phone, role);
        }
        cursor.close();
        return user;
    }

    public User getUserById(int idkey){
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_USER+" where id = ?",
                new String[]{idkey + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String email = cursor.getString(2);
            String pass = cursor.getString(3);
            String phone = cursor.getString(4);
            String role = cursor.getString(5);
            user = new User(id, username,email,pass,phone, role);
        }
        cursor.close();
        return user;
    }
}
