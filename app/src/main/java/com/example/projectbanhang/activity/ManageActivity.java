package com.example.projectbanhang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.example.projectbanhang.R;
import com.example.projectbanhang.activity.config.MyConfiguration;
import com.example.projectbanhang.adapter.ManageProductAdapter;
import com.example.projectbanhang.adapter.MenuListAdapter;
import com.example.projectbanhang.database.ProductDbHelper;
import com.example.projectbanhang.model.Menu;
import com.example.projectbanhang.model.Product;
import com.example.projectbanhang.model.User;
import com.example.projectbanhang.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {
    ListView listView, listViewnavigation;
    Toolbar toolbar;
    ManageProductAdapter productAdapter;
    ProductDbHelper productDbHelper = new ProductDbHelper(this);;
    DrawerLayout drawerLayout;
    List<Product> productList;
    Button btnUpdate, btnDel, btnAdd;
    List<Menu> menu;
    Cloudinary cloudinary = new Cloudinary(MyConfiguration.getMyConfig());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        getView();
        actionBar();
        loadData();
        initControl();
        loadDataListView();
    }

    private void restoringPreferences() {
        SharedPreferences pre = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor= pre.edit();
        editor.clear();
        editor.commit();
    }
    private void loadDataListView() {
        menu = new ArrayList<>();
        menu.add(new Menu("Quản lí sản phẩm", "https://res.cloudinary.com/dwbnrx0mg/image/upload/v1683110814/projectbannhaccu/hang-hoa_aopnah.png"));
        menu.add(new Menu("Xem hoá đơn","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682937683/projectbannhaccu/pngtree-invoice-icon-design-vector-png-image_1586820_qosvg8.jpg"));
        menu.add(new Menu("Đăng xuất","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1683007450/projectbannhaccu/inside-logout-icon_qncegz.png"));
        MenuListAdapter menuListAdapter = new MenuListAdapter(menu, getApplicationContext());
        listViewnavigation.setAdapter(menuListAdapter);
        listViewnavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent quanli = new Intent(getApplicationContext(), ManageActivity.class);
                        startActivity(quanli);
                    case 1:
                        Intent hoadon = new Intent(getApplicationContext(), ViewBillActivity.class);
                        hoadon.putExtra("activity", "manage");
                        startActivity(hoadon);
                        break;
                    case 2:
                        Intent logout = new Intent(getApplicationContext(),SplashActivity.class);
                        restoringPreferences();
                        Utils.user_current = new User();
                        startActivity(logout);
                        break;
                }

            }
        });
    }

    private void initControl() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageActivity.this, AddProductActivity.class);
                intent.putExtra("activity", "add");
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        productList = new ArrayList<>();
        productList.addAll(productDbHelper.getAllProduct());
        productDbHelper.close();
        productAdapter = new ManageProductAdapter(productList);
        listView.setAdapter(productAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAlertDialog(productList.get(position), position);
            }
        });

    }
    private void showAlertDialog(Product product, int postion) {
        AlertDialog.Builder alertdel = new AlertDialog.Builder(ManageActivity.this);
        alertdel.setMessage("Bạn muốn cập nhật sản phẩm này");
        alertdel.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ManageActivity.this, AddProductActivity.class);
                intent.putExtra("activity", "update");
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
        alertdel.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alertdel.create();
        dialog.show();
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getView() {
        listView = findViewById(R.id.listviewallproduct);
        toolbar = findViewById(R.id.toolbarmanage);
        drawerLayout = findViewById(R.id.drawerlayoutmanage);
        btnAdd = findViewById(R.id.btnaddproduct);
        listViewnavigation = findViewById(R.id.listviewmanage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }
}