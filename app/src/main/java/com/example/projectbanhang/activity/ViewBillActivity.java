package com.example.projectbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.projectbanhang.R;
import com.example.projectbanhang.adapter.ViewBillAdapter;
import com.example.projectbanhang.database.BillDbHelper;
import com.example.projectbanhang.database.BillDetailDbHelper;
import com.example.projectbanhang.model.Bill;
import com.example.projectbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ViewBillActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ViewBillAdapter viewBillAdapter;
    BillDbHelper billDbHelper;
    BillDetailDbHelper billDetailDbHelper;
    List<Bill> billList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        getView();
        initControl();
    }

    private void getView() {
        toolbar = findViewById(R.id.toolbarviewbill);
        recyclerView = findViewById(R.id.recycleview_bill);
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
        String activitivy = getIntent().getStringExtra("activity");
        if (activitivy.equals("manage")){
            billDbHelper = new BillDbHelper(getApplicationContext());
            billList = billDbHelper.getAllBill();
            for (int i =  0; i<billList.size(); i++){
                billDetailDbHelper = new BillDetailDbHelper(getApplicationContext());
                int id = billList.get(i).getId();
                billList.get(i).setBillDetails(billDetailDbHelper.getBillDetailByBillId(id));
                billDetailDbHelper.close();
            }
            billDbHelper.close();
        }else if (activitivy.equals("main")){
            billDbHelper = new BillDbHelper(getApplicationContext());
            billList = billDbHelper.getBillByUserId(Utils.user_current.getId());
            for (int i =  0; i<billList.size(); i++){
                billDetailDbHelper = new BillDetailDbHelper(getApplicationContext());
                int id = billList.get(i).getId();
                billList.get(i).setBillDetails(billDetailDbHelper.getBillDetailByBillId(id));
                billDetailDbHelper.close();
            }
            billDbHelper.close();
        }
        viewBillAdapter = new ViewBillAdapter(getApplicationContext(), billList );
        recyclerView.setAdapter(viewBillAdapter);

    }
}