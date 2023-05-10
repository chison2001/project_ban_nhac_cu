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

public class ProductDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "ProductDbHelper";
    private static final String DATABASE_NAME = "bannhaccu.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "product";


    public ProductDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR (255) NOT NULL, " +
                "image VARCHAR (255) NOT NULL," +
                "price NUMBER NOT NULL," +
                "mota TEXT ," +
                "category VARCHAR (255) NOT NULL" +
                ")";

        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }
    public List<Product> getSomeNewProduct() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT,null,null,null,null,null,"id DESC","6");
        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            Long price = cursor.getLong(3);
            String mota = cursor.getString(4);
            String category = cursor.getString(5);
            products.add(new Product(id, name, image,price,mota, category));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT,null,null,null,null,null,"id DESC",null);
        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            Long price = cursor.getLong(3);
            String mota = cursor.getString(4);
            String category = cursor.getString(5);
            products.add(new Product(id, name, image,price,mota, category));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
    public void insertProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+ TABLE_PRODUCT+" (name, image, price,mota, category ) VALUES (?,?,?,?,?)",
                new String[]{product.getName(), product.getImage(),product.getPrice()+"",product.getMota(),product.getCategory()+ ""});
    }
    public List<Product> getProductByCategory(String categoryKey){
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT,null,"category = ?" ,new String[]{categoryKey+""},null,null,"id DESC",null);
        //Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_PRODUCT +" WHERE category like?", new String[]{categoryKey+""});
        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            Long price = cursor.getLong(3);
            String mota = cursor.getString(4);
            String category = cursor.getString(5);
            products.add(new Product(id, name, image,price,mota, category));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
    public List<Product> getProductByName(String nameKey){
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT,null,"name LIKE ?" ,new String[]{"%" +nameKey+ "%"},null,null,"id DESC",null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            Long price = cursor.getLong(3);
            String mota = cursor.getString(4);
            String category = cursor.getString(5);
            products.add(new Product(id, name, image,price,mota, category));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
    public Product getProductById(int idKey){
        Product product = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_PRODUCT+" where id = ?",
                new String[]{idKey + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            Long price = cursor.getLong(3);
            String mota = cursor.getString(4);
            String category = cursor.getString(5);
            product=new Product(id, name, image,price,mota, category);
        }
        cursor.close();
        return product;
    }
    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " +TABLE_PRODUCT+ " SET name=?, image = ?, price = ?, mota = ?, category = ? where id = ?",
                new String[]{product.getName(), product.getImage(),product.getPrice()+"",product.getMota(),product.getCategory(), product.getId()+""});
    }
    public void deleteProductByID(int ProductID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE_PRODUCT+ " where id = ?", new String[]{String.valueOf(ProductID)});
    }
}
