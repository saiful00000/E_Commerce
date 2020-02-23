package com.example.e_commerce.viewHolders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.interfaces.ProductItemClickListenner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productNameTv, productQuantityTv, productPriceTv;
    private ProductItemClickListenner itemClickListenner;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        productNameTv = itemView.findViewById(R.id.cart_product_name_tv_id);
        productPriceTv = itemView.findViewById(R.id.cart_product_price_tv_id);
        productQuantityTv = itemView.findViewById(R.id.cart_product_quantity_tv_id);

    }

    @Override
    public void onClick(View v) {
        itemClickListenner.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListenner(ProductItemClickListenner itemClickListenner) {
        this.itemClickListenner = itemClickListenner;
    }

}
