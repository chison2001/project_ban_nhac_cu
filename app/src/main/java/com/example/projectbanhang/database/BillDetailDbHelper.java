package com.example.projectbanhang.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.projectbanhang.model.Bill;
import com.example.projectbanhang.model.BillDetail;

import java.util.ArrayList;
import java.util.List;

public class BillDetailDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "ProductDbHelper";
    private static final String DATABASE_NAME = "bannhaccu.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ORDER_DETAIL = "billDetail";
    public BillDetailDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER_DETAIL + " ( " +
                "billId INTEGER REFERENCES bill(id), " +
                "productId INTEGER REFERENCES product(id)," +
                "count INTEGER NOT NULL," +
                "price NUMBER NOT NULL" +
                ")";
        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAIL);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }
    public void insertBillDetail(BillDetail billDetail) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+ TABLE_ORDER_DETAIL +" (billId, productId, count ,price ) VALUES (?,?,?,?)",
                new String[]{billDetail.getBillId()+"", billDetail.getProductId()+"", billDetail.getCount()+"", billDetail.getPrice() +""});
    }
    public List<BillDetail> getBillDetailByBillId(int idKey){
        List<BillDetail> billDetailList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_ORDER_DETAIL+" where billId = ?",
                new String[]{idKey + ""});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int billId = cursor.getInt(0);
            int productId = cursor.getInt(1);
            int count = cursor.getInt(2);
            Long price = cursor.getLong(3);
            billDetailList.add(new BillDetail(billId, productId ,count,price));
            cursor.moveToNext();
        }
        cursor.close();
        return billDetailList;
    }
}
