package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.e_commerce.models.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProductsDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView productImageView;
    private TextView productNameTv, productPriceTv, productDescriptionTv, productBrandTv;
    private FloatingActionButton cartButton;
    private ElegantNumberButton numberButton;
    private Button addToCartButton;

    private String productKey, productCategory;
    private String currentDate;
    private String currentTime;
    private String currentUserId;
    private String price;

    private DatabaseReference cartReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        toolbar = findViewById(R.id.prod_details_toolbarId);
        productImageView = findViewById(R.id.dprod_image_view_id);
        productNameTv = findViewById(R.id.dprod_name_tv_id);
        productBrandTv = findViewById(R.id.dprod_brand_tv_id);
        productPriceTv = findViewById(R.id.dprod_price_tv_id);
        productDescriptionTv = findViewById(R.id.dprod_description_tv_id);
        numberButton = findViewById(R.id.delegation_number_button_id);
        addToCartButton = findViewById(R.id.add_to_cart_btn_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        productKey = getIntent().getStringExtra("prodKey");

        cartReference = FirebaseDatabase.getInstance().getReference().child("Cart");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getUid();



        getProductDetails();

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToTheCart();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addItemToTheCart() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormate = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = dateFormate.format(calendar.getTime());
        SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss a");
        currentTime = timeFormate.format(calendar.getTime());

        final Map<String, Object> map = new HashMap<>();
        map.put("key", productKey);
        //map.put("image", );
        //map.put("category", );
        map.put("name", productNameTv.getText().toString());
        map.put("brand", productBrandTv.getText().toString());
        map.put("price", price);
        map.put("description", productDescriptionTv.getText().toString());
        map.put("quantity", numberButton.getNumber());
        map.put("date", currentDate);
        map.put("time", currentTime);

        cartReference.child("UserView").child(currentUserId).child("Products")
                .child(productKey)
                .updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartReference.child("AdminView").child(currentUserId).child("Products")
                                    .child(productKey)
                                    .updateChildren(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ProductsDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });

    }

    private void getProductDetails() {
        DatabaseReference prodReference = FirebaseDatabase.getInstance().getReference().child("Products");

        prodReference.child(productKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    price = product.getPrice();

                    Picasso.get().load(product.getImage()).into(productImageView);
                    productNameTv.setText(product.getName());
                    productBrandTv.setText("Brand: " + product.getBrand());
                    productPriceTv.setText("Price: "+ price + " tk");
                    productDescriptionTv.setText(product.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("databaseError", databaseError.toString());
                Toast.makeText(ProductsDetailsActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
