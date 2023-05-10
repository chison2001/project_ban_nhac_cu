package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectbanhang.R;
import com.example.projectbanhang.adapter.CartAdapter;
import com.example.projectbanhang.model.Cart;
import com.example.projectbanhang.model.EvenBus.TinhTongEvent;
import com.example.projectbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView giohangtrong, txttongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnbuy;
    CartAdapter cartAdapter;
    List<Cart> cartList;
    long tongtien = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getViews();
        initControl();
        tinhTongTien();
    }

    private void tinhTongTien() {
        tongtien = 0L;
        for (int i =0 ; i< Utils.carts.size(); i++){
            tongtien+= (Utils.carts.get(i).getCount()*Utils.carts.get(i).getProductPrice());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(Double.parseDouble(String.valueOf(tongtien)))+" vnd");
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
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.carts.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else {
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.carts);
            recyclerView.setAdapter(cartAdapter);
        }
        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                intent.putExtra("tongtien", tongtien);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        giohangtrong = findViewById(R.id.txtnullcart);
        toolbar = findViewById(R.id.carttoolbar);
        recyclerView = findViewById(R.id.recycleview_cart);
        txttongtien = findViewById(R.id.txttongtien);
        btnbuy = findViewById(R.id.btnbuy);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            tinhTongTien();
        }
    }
}