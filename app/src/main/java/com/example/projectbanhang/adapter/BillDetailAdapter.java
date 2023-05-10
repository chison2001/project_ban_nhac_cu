package com.example.projectbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectbanhang.R;
import com.example.projectbanhang.database.ProductDbHelper;
import com.example.projectbanhang.model.BillDetail;
import com.example.projectbanhang.model.Product;

import java.util.List;


public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.MyViewHolder> {
    Context context;
    List<BillDetail> billDetails;
    ProductDbHelper productDbHelper;

    public BillDetailAdapter(Context context, List<BillDetail> billDetails) {
        this.context = context;
        this.billDetails = billDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BillDetail billDetail = billDetails.get(position);
        holder.count.setText(billDetail.getCount()+"");
        productDbHelper = new ProductDbHelper(context);
        Product product = productDbHelper.getProductById(billDetail.getProductId());
        Glide.with(context).load(product.getImage()).into(holder.image);
        holder.name.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return billDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image_detail);
            name = itemView.findViewById(R.id.bill_name);
            count = itemView.findViewById(R.id.bill_count);
        }
    }
}
