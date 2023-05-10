package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.projectbanhang.R;
import com.example.projectbanhang.model.Cart;
import com.example.projectbanhang.model.Product;
import com.example.projectbanhang.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView detailname, detailprice, detailmota;
    Button btnaddcart;
    ImageView detailimage;
    Spinner spinner;
    Toolbar toolbar;
    Product product;
    FrameLayout frameLayout;
    NotificationBadge badgedetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getViews();
        actionToolBar();
        getData();
        initControl();
    }

    private void initControl() {
        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);

            }
        });
    }

    private void themGioHang() {
        if (Utils.carts.size() > 0){
            boolean flag = false;
            int count = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i<Utils.carts.size(); i++){
                Log.d("Utils.carts.get(i).getProductId()", "asdfasd");
                if (Utils.carts.get(i).getProductId() == product.getId()){
                    Utils.carts.get(i).setCount(count + Utils.carts.get(i).getCount());
                    Long gia = product.getPrice()*Utils.carts.get(i).getCount();
                    Utils.carts.get(i).setProductPrice(gia);
                    flag = true;
                }
            }
            if (flag == false){
                long gia = product.getPrice()*count;
                Cart cart = new Cart();
                cart.setCount(count);
                cart.setProductId(product.getId());
                cart.setProductImage(product.getImage());
                cart.setProductName(product.getName());
                cart.setProductPrice(gia);
                Utils.carts.add(cart);
            }
        }else {
            int count = Integer.parseInt(spinner.getSelectedItem().toString());
            long gia = product.getPrice()*count;
            Cart cart = new Cart();
            cart.setCount(count);
            cart.setProductId(product.getId());
            cart.setProductImage(product.getImage());
            cart.setProductName(product.getName());
            cart.setProductPrice(gia);
            Utils.carts.add(cart);
        }
        int totalitem = 0;
        for (int i =0;i< Utils.carts.size(); i++){
            totalitem += Utils.carts.get(i).getCount();
        }
        badgedetail.setText(String.valueOf(totalitem));
    }

    private void getData() {
        product = (Product) getIntent().getSerializableExtra("detail");
        detailname.setText(product.getName());
        Glide.with(this).load(product.getImage()).into(detailimage);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        detailprice.setText("Giá: "+ decimalFormat.format(Double.parseDouble(String.valueOf(product.getPrice())))+" vnd");
        detailmota.setText(product.getMota());
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }
    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getViews() {
        detailimage = findViewById(R.id.detailimage);
        btnaddcart = findViewById(R.id.btnaddcart);
        detailname = findViewById(R.id.detailname);
        detailprice = findViewById(R.id.detailprice);
        detailmota = findViewById(R.id.detailmota);
        spinner = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toolbardetail);
        badgedetail = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framecart);
        if (Utils.carts == null){
            badgedetail.setText(String.valueOf(Utils.carts.size()));
        }else {
            int totalitem = 0;
            for (int i =0;i< Utils.carts.size(); i++){
                totalitem += Utils.carts.get(i).getCount();
            }
            badgedetail.setText(String.valueOf(totalitem));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.carts != null){
            int totalitem = 0;
            for (int i =0;i< Utils.carts.size(); i++){
                totalitem += Utils.carts.get(i).getCount();
            }
            badgedetail.setText(String.valueOf(totalitem));
        }
    }
}