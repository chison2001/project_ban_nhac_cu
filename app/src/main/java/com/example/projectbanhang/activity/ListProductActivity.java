package com.example.projectbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.example.projectbanhang.R;
import com.example.projectbanhang.adapter.NewProductAdapter;
import com.example.projectbanhang.adapter.ProductAdapter;
import com.example.projectbanhang.database.ProductDbHelper;
import com.example.projectbanhang.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    String category;
    ProductAdapter productAdapter;
    List<Product> productListcategory;
    ProductDbHelper productDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        getViews();
        actionToolBar();
        category = getIntent().getStringExtra("category");

        getDataByCategory();
        toolbar.setTitle(category);
    }

    private void getDataByCategory() {
        productListcategory = new ArrayList<>();
        productDbHelper = new ProductDbHelper(this);
        productListcategory.addAll(productDbHelper.getProductByCategory(category));
        productDbHelper.close();
        productAdapter = new ProductAdapter(getApplicationContext(), productListcategory);
        recyclerView.setAdapter(productAdapter);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finishAndRemoveTask();
            }
        });
    }

    private void getViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_product);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }


}
