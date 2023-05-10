package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.projectbanhang.R;
import com.example.projectbanhang.adapter.MenuListAdapter;
import com.example.projectbanhang.adapter.NewProductAdapter;
import com.example.projectbanhang.database.ProductDbHelper;
import com.example.projectbanhang.model.Menu;
import com.example.projectbanhang.model.Product;
import com.example.projectbanhang.model.User;
import com.example.projectbanhang.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationViewManHinhChinh;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    ProductDbHelper productDbHelper;
    NewProductAdapter newProductAdapter;
    List<Product> mangsanpham;
    MenuListAdapter menuListAdapter;
    List<Menu> menu;
    FrameLayout frame;
    NotificationBadge badge;
    EditText etserach;
    ImageView imgsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        actionBar();
        actionViewFlipper();
        loadDataListView();
        loadDataRecycleView();
        getEventClick();
        searchControl();
    }

    private void searchControl() {
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etserach.getText().toString();
                if (TextUtils.isEmpty(keyword)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập từ khoá tìm kiếm",Toast.LENGTH_LONG).show();
                }   else {
                    Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                    search.putExtra("keyword", keyword);
                    startActivity(search);
                }
            }
        });
    }

    private void getEventClick() {
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent guitar = new Intent(getApplicationContext(),ListProductActivity.class);
                        guitar.putExtra("category", "Guitar");
                        startActivity(guitar);
                        break;
                    case 2:
                        Intent okulele = new Intent(getApplicationContext(),ListProductActivity.class);
                        okulele.putExtra("category", "Okulele");
                        startActivity(okulele);
                        break;
                    case 3:
                        Intent violin = new Intent(getApplicationContext(),ListProductActivity.class);
                        violin.putExtra("category", "Violin");
                        startActivity(violin);
                        break;
                    case 4:
                        Intent tyba = new Intent(getApplicationContext(),ListProductActivity.class);
                        tyba.putExtra("category", "Tỳ bà");
                        startActivity(tyba);
                        break;
                    case 5:
                        Intent dantranh = new Intent(getApplicationContext(),ListProductActivity.class);
                        dantranh.putExtra("category", "Đàn tranh");
                        startActivity(dantranh);
                        break;
                    case 6:
                        Intent organ = new Intent(getApplicationContext(),ListProductActivity.class);
                        organ.putExtra("category", "Organ");
                        startActivity(organ);
                        break;
                    case 7:
                        Intent bill = new Intent(getApplicationContext(),ViewBillActivity.class);
                        bill.putExtra("activity", "main");
                        startActivity(bill);
                        break;
                    case 8:
                        Intent logout = new Intent(getApplicationContext(),SplashActivity.class);
                        restoringPreferences();
                        Utils.carts = new ArrayList<>();
                        Utils.user_current = new User();
                        startActivity(logout);
                        break;
                    case 9:
                        Intent thongtin = new Intent(getApplicationContext(),ListProductActivity.class);
                        startActivity(thongtin);
                        break;
                }
            }
        });
    }

    private void restoringPreferences() {
        SharedPreferences pre = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor= pre.edit();
        editor.clear();
        editor.commit();
    }

    private void loadDataRecycleView(){
        productDbHelper = new ProductDbHelper(this);
        mangsanpham.addAll(productDbHelper.getSomeNewProduct());
        productDbHelper.close();
        newProductAdapter = new NewProductAdapter(getApplicationContext(), mangsanpham);
        recyclerViewManHinhChinh.setAdapter(newProductAdapter);
    }
    private void loadDataListView(){
        menu.add(new Menu("Trang chủ", "https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682043605/projectbannhaccu/home_u4uzmf.png"));
        menu.add(new Menu("Guitar","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1681870962/projectbannhaccu/takamine-guitar_vrji2r.jpg"));
        menu.add(new Menu("Okulele","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682043605/projectbannhaccu/okulele_xxhjr6.jpg"));
        menu.add(new Menu("Violin","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1681871337/projectbannhaccu/Kapok-violin_kt1atp.jpg"));
        menu.add(new Menu("Tỳ bà","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682043605/projectbannhaccu/ty-ba_tta3is.png"));
        menu.add(new Menu("Đàn tranh","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682043605/projectbannhaccu/dan-tranh-150714_tug59a.jpg"));
        menu.add(new Menu("Organ","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682043605/projectbannhaccu/organ_ovwqst.jpg"));
        menu.add(new Menu("Xem hoá đơn","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682937683/projectbannhaccu/pngtree-invoice-icon-design-vector-png-image_1586820_qosvg8.jpg"));
        menu.add(new Menu("Đăng xuất","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1683007450/projectbannhaccu/inside-logout-icon_qncegz.png"));
        menu.add(new Menu("Thông tin","https://res.cloudinary.com/dwbnrx0mg/image/upload/v1682043605/projectbannhaccu/info_myhf3m.png"));
        menuListAdapter = new MenuListAdapter(menu, getApplicationContext());
        listViewManHinhChinh.setAdapter(menuListAdapter);

    }

    private void actionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://res.cloudinary.com/dwbnrx0mg/image/upload/v1681791566/projectbannhaccu/banner-qc-1_yl0xfp.jpg");
        mangquangcao.add("https://res.cloudinary.com/dwbnrx0mg/image/upload/v1681791567/projectbannhaccu/banner-qc-2_rnfa24.jpg");
        mangquangcao.add("https://res.cloudinary.com/dwbnrx0mg/image/upload/v1681791567/projectbannhaccu/banner-qc-3_nzugbf.jpg");
        for (int i = 0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
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

    @Override
    protected void onResume() {
        super.onResume();
        int totalitem = 0;
        for (int i =0;i< Utils.carts.size(); i++){
            totalitem += Utils.carts.get(i).getCount();
        }
        badge.setText(String.valueOf(totalitem));
    }

    public void getViews(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        navigationViewManHinhChinh = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout= findViewById(R.id.drawerlayout);
        frame= findViewById(R.id.framecartmain);
        badge= findViewById(R.id.menu_sl_main);
        etserach= findViewById(R.id.etsearch);
        imgsearch= findViewById(R.id.imgsearch);

        //khoi tao list
        menu = new ArrayList<>();
        mangsanpham = new ArrayList<>();
        if (Utils.carts == null){
            Utils.carts = new ArrayList<>();
        }else {
            int totalitem = 0;
            for (int i =0;i< Utils.carts.size(); i++){
                totalitem += Utils.carts.get(i).getCount();
            }
            badge.setText(String.valueOf(totalitem));
        }
        badge.setText(String.valueOf(Utils.carts.size()));
    }

}