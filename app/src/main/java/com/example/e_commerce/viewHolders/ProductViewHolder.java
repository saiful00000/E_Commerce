package com.example.e_commerce.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.interfaces.ProductItemClickListenner;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productNameTv, productDescriptionTv, productPriceTv;
    public ImageView productImageView;

    public ProductItemClickListenner itemClickListenner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        productNameTv = itemView.findViewById(R.id.product_name_tv_id);
        productDescriptionTv = itemView.findViewById(R.id.product_description_tv_id);
        productImageView = itemView.findViewById(R.id.product_image_view_id);
        productPriceTv = itemView.findViewById(R.id.product_price_tv_id);


    }

    public void setItemClickListenner(ProductItemClickListenner itemClickListenner) {
        this.itemClickListenner = itemClickListenner;
    }

    @Override
    public void onClick(View v) {
        itemClickListenner.onClick(v, getAdapterPosition(), false);
    }
}
