package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.util.Util;
import com.example.projectbanhang.R;
import com.example.projectbanhang.database.BillDbHelper;
import com.example.projectbanhang.database.BillDetailDbHelper;
import com.example.projectbanhang.model.Bill;
import com.example.projectbanhang.model.BillDetail;
import com.example.projectbanhang.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PayActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttienthanhtoan, txtsdt, txtemail;
    EditText etdiachi;
    AppCompatButton btnthanhtoan;
    long tongtien;
    BillDbHelper billDbHelper;
    BillDetailDbHelper billDetailDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getView();
        initControl();

    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien =getIntent().getLongExtra("tongtien", 0);
        txttienthanhtoan.setText( decimalFormat.format(tongtien));
        txtemail.setText(Utils.user_current.getEmail());
        txtsdt.setText(Utils.user_current.getPhoneNumber());
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi = etdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    String oderDate = getDateNow();
                    String str_sdt = txtsdt.getText().toString();
                    int userId = Utils.user_current.getId();
                    Bill billIn = new Bill(userId, str_diachi,str_sdt, oderDate, tongtien);
                    billDbHelper = new BillDbHelper(getApplicationContext());
                    billDbHelper.onCreate(SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.projectbanhang/databases/bannhaccu.db", null));
                    billDbHelper.insertBill(billIn);
                    Bill billout = billDbHelper.getNewestBill();
                    billDbHelper.close();
                    billDetailDbHelper = new BillDetailDbHelper(getApplicationContext());
                    billDetailDbHelper.onCreate(SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.projectbanhang/databases/bannhaccu.db", null));
                    for (int i =0 ; i< Utils.carts.size(); i++){
                        int billId = billout.getId();
                        int productId = Utils.carts.get(i).getProductId();
                        int count = Utils.carts.get(i).getCount();
                        long gia = count * Utils.carts.get(i).getProductPrice();
                        billDetailDbHelper.insertBillDetail(new BillDetail(billId, productId,count , gia));
                    }
                    billDetailDbHelper.close();
                    Utils.carts = new ArrayList<>();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private String getDateNow() {
        DateTimeFormatter formatter = null;
        LocalDateTime now;
        String date ="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date = formatter.format(now);
        }
        return date;
    }

    private void getView() {
        toolbar =findViewById(R.id.paytoolbar);
        txttienthanhtoan =findViewById(R.id.txttienthanhtoan);
        txtsdt =findViewById(R.id.txtsdt);
        txtemail =findViewById(R.id.txtemail);
        etdiachi =findViewById(R.id.etdiachi);
        btnthanhtoan =findViewById(R.id.btndathang);

    }
}