package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.projectbanhang.R;
import com.example.projectbanhang.adapter.ProductAdapter;
import com.example.projectbanhang.database.ProductDbHelper;
import com.example.projectbanhang.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    String category, keyword;
    ProductAdapter productAdapter;
    List<Product> productListcategory, productListname;
    ProductDbHelper productDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        getViews();
        actionToolBar();
        keyword = getIntent().getStringExtra("keyword");
        toolbar.setTitle("Sản phẩm có tên " + keyword);
        getDataByName();
    }
    private void getDataByName() {
        productListname = new ArrayList<>();
        productDbHelper = new ProductDbHelper(this);
        productListname.addAll(productDbHelper.getProductByName(keyword));
        productDbHelper.close();
        productAdapter = new ProductAdapter(getApplicationContext(), productListname);
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