package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.e_commerce.models.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductsDetailsActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTv, productPriceTv, productDescriptionTv, productBrandTv;
    private FloatingActionButton cartButton;
    private ElegantNumberButton numberButton;

    private String productKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        productImageView = findViewById(R.id.dprod_image_view_id);
        productNameTv = findViewById(R.id.dprod_name_tv_id);
        productBrandTv = findViewById(R.id.dprod_brand_tv_id);
        productPriceTv = findViewById(R.id.dprod_price_tv_id);
        productDescriptionTv = findViewById(R.id.dprod_description_tv_id);
        cartButton = findViewById(R.id.cart_fab_btn_id);
        numberButton = findViewById(R.id.delegation_number_button_id);

        productKey = getIntent().getStringExtra("prodKey");

        getProductDetails();

    }

    private void getProductDetails() {
        DatabaseReference prodReference = FirebaseDatabase.getInstance().getReference().child("Products");

        prodReference.child(productKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    Picasso.get().load(product.getImage()).into(productImageView);
                    productNameTv.setText(product.getName());
                    productBrandTv.setText(product.getBrand());
                    productPriceTv.setText(product.getPrice());
                    productDescriptionTv.setText(product.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
