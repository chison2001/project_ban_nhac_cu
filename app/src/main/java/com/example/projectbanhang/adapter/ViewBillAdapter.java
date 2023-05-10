package com.example.projectbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectbanhang.R;
import com.example.projectbanhang.model.Bill;

import java.text.DecimalFormat;
import java.util.List;

public class ViewBillAdapter extends RecyclerView.Adapter<ViewBillAdapter.MyViewHolder> {
    Context context;
    List<Bill> bills;

    public ViewBillAdapter(Context context, List<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bill bill = bills.get(position);
        holder.madon.setText("Đơn hàng : "+bill.getId());
        holder.ngaydat.setText("Ngày đặt: "+bill.getOrderDate());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String tien = decimalFormat.format(bill.getTongtien());
        holder.tiendon.setText("Tiền thanh toán: "+ tien+ " vnd");
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerView.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(bill.getBillDetails().size());
        BillDetailAdapter billDetailAdapter = new BillDetailAdapter(context, bill.getBillDetails());
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(billDetailAdapter);
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView madon, ngaydat, tiendon;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            madon = itemView.findViewById(R.id.tvmadon);
            ngaydat = itemView.findViewById(R.id.tvngaydat);
            tiendon = itemView.findViewById(R.id.tvtiendon);
            recyclerView = itemView.findViewById(R.id.recylviewbilldetail);
        }
    }
}
