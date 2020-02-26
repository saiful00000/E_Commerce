package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.models.Product;
import com.example.e_commerce.viewHolders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AllProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private DatabaseReference productsRreReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        productsRreReference = FirebaseDatabase.getInstance().getReference().child("Products");

        toolbar = findViewById(R.id.all_toolbarId);
        recyclerView = findViewById(R.id.recycler_view_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Products");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productsRreReference.orderByChild("category").equalTo("laptop"), Product.class)
                .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Product product) {
                        //if (product.getCategory().equals("laptop"))

                        Picasso.get().load(product.getImage()).into(holder.productImageView);
                        holder.productNameTv.setText(product.getName());
                        holder.productPriceTv.setText(product.getPrice() + " tk");
                        holder.productDescriptionTv.setText(product.getDescription());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(AllProductsActivity.this, ProductsDetailsActivity.class);
                                intent.putExtra("prodKey", product.getKey());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
