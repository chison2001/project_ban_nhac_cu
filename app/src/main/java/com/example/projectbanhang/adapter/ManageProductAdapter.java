package com.example.projectbanhang.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectbanhang.Interface.ItemClickListener;
import com.example.projectbanhang.R;

import com.example.projectbanhang.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class ManageProductAdapter extends BaseAdapter {
    final List<Product> productList;

    public ManageProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.item_product, null);
        } else viewProduct = convertView;
        ImageView imageView = viewProduct.findViewById(R.id.itemproduct_image);
        TextView name, price, description;
        name = viewProduct.findViewById(R.id.itemproduct_ten);
        price = viewProduct.findViewById(R.id.itemproduct_gia);
        description = viewProduct.findViewById(R.id.itemproduct_mota);
        //Bind sữ liệu phần tử vào View
        Product product = (Product) getItem(position);
        Glide.with(parent.getContext()).load(product.getImage()).into(imageView);
        name.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        price.setText("Giá: "+ decimalFormat.format(Double.parseDouble(String.valueOf(product.getPrice())))+" vnd");
        description.setText(product.getMota());
        return viewProduct;

    }
}
