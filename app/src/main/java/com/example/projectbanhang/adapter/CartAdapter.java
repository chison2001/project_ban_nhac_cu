package com.example.projectbanhang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectbanhang.Interface.ImageClickListener;
import com.example.projectbanhang.R;
import com.example.projectbanhang.model.Cart;
import com.example.projectbanhang.model.EvenBus.TinhTongEvent;
import com.example.projectbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.item_cart_name.setText(cart.getProductName());
        holder.item_cart_count.setText(cart.getCount()+"");
        Glide.with(context).load(cart.getProductImage()).into(holder.item_cart_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_cart_price.setText(decimalFormat.format(Double.parseDouble(String.valueOf(cart.getProductPrice())))+" vnd");
        long gia = cart.getCount() * cart.getProductPrice();
        holder.item_cart_price2.setText(decimalFormat.format(Double.parseDouble(String.valueOf(gia))) +" vnd");
        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1){
                    if(cartList.get(pos).getCount() > 1){
                        int newCount = cartList.get(pos).getCount() - 1;
                        cartList.get(pos).setCount(newCount);

                        holder.item_cart_count.setText(cartList.get(pos).getCount() +"");
                        long gia = cartList.get(pos).getCount() * cartList.get(pos).getProductPrice();
                        holder.item_cart_price2.setText(decimalFormat.format(Double.parseDouble(String.valueOf(gia))) +" vnd");
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if (cartList.get(pos).getCount() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xoá sản phầm này khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.carts.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else if(giatri == 2){
                    if(cartList.get(pos).getCount() <10){
                        int newCount = cartList.get(pos).getCount() + 1;
                        cartList.get(pos).setCount(newCount);
                    }
                    holder.item_cart_count.setText(cartList.get(pos).getCount() +"");
                    long gia = cartList.get(pos).getCount() * cartList.get(pos).getProductPrice();
                    holder.item_cart_price2.setText(decimalFormat.format(Double.parseDouble(String.valueOf(gia))) +" vnd");
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView item_cart_image, item_cart_tru, item_cart_cong;
        TextView item_cart_name, item_cart_price, item_cart_count, item_cart_price2;
        ImageClickListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cart_image = itemView.findViewById(R.id.item_cart_image);
            item_cart_name = itemView.findViewById(R.id.item_cart_name);
            item_cart_price = itemView.findViewById(R.id.item_cart_price);
            item_cart_count = itemView.findViewById(R.id.item_cart_count);
            item_cart_price2 = itemView.findViewById(R.id.item_cart_price2);
            item_cart_tru = itemView.findViewById(R.id.item_cart_tru);
            item_cart_cong = itemView.findViewById(R.id.item_cart_cong);

            //event click
            item_cart_tru.setOnClickListener(this);
            item_cart_cong.setOnClickListener(this);
        }
        public void setListener(ImageClickListener listener){
            this.listener = listener;
        }
        public MyViewHolder(@NonNull View itemView, ImageClickListener listener) {
            super(itemView);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if(view == item_cart_tru){
                listener.onImageClick(view, getAdapterPosition(), 1);
            }else if (view == item_cart_cong){
                listener.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}
