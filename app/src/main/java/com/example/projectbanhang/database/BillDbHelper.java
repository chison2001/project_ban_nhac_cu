package com.example.projectbanhang.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.projectbanhang.model.Bill;
import com.example.projectbanhang.model.Product;

import java.util.ArrayList;
import java.util.List;

public class BillDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "OrderDbHelper";
    private static final String DATABASE_NAME = "bannhaccu.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ORDER = "bill";


    public BillDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER REFERENCES user(id), " +
                "adress VARCHAR (255) NOT NULL, " +
                "phoneNumber VARCHAR (255) NOT NULL, " +
                "orderDate VARCHAR (255) NOT NULL," +
                "tongtien NUMBER NOT NULL" +
                ")";

        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }
    public void insertBill(Bill bill) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+ TABLE_ORDER +" (userId,adress,  phoneNumber, orderDate,tongtien ) VALUES (?,?,?,?,?)",
                new String[]{bill.getUserId()+"", bill.getAddress(), bill.getPhoneNumber(), bill.getOrderDate()+"", bill.getTongtien() +""});
    }
    public List<Bill> getAllBill() {
        List<Bill> billList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDER,null,null,null,null,null,"id DESC",null);
        cursor.moveToFirst();
        //Đến dòng đầu của tập dữ liệu
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int userId = cursor.getInt(1);
            String adress = cursor.getString(2);
            String phoneNumber = cursor.getString(3);
            String orderDate = cursor.getString(4);
            Long price = cursor.getLong(5);
            billList.add(new Bill(id, userId ,adress,phoneNumber,orderDate,price));
            cursor.moveToNext();
        }
        cursor.close();
        return billList;
    }
    public Bill getNewestBill() {
        Bill bill = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDER,null,null,null,null,null,"id DESC","1");
        //Đến dòng đầu của tập dữ liệu
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            int userId = cursor.getInt(1);
            String adress = cursor.getString(2);
            String phoneNumber = cursor.getString(3);
            String orderDate = cursor.getString(4);
            Long price = cursor.getLong(5);
            bill = new Bill(id, userId ,adress,phoneNumber,orderDate,price);
            cursor.moveToNext();
        }
        cursor.close();
        return bill;
    }
    public List<Bill> getBillByUserId(int idKey){
        List<Bill> billList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_ORDER+" where userId = ? ORDER BY id DESC",
                new String[]{idKey + ""});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            int userId = cursor.getInt(1);
            String adress = cursor.getString(2);
            String phoneNumber = cursor.getString(3);
            String orderDate = cursor.getString(4);
            Long price = cursor.getLong(5);
            billList.add(new Bill(id, userId ,adress,phoneNumber,orderDate,price));
            cursor.moveToNext();
        }
        cursor.close();
        return billList;
    }
}
