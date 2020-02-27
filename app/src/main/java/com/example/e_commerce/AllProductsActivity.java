package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.e_commerce.models.Product;
import com.example.e_commerce.viewHolders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class AllProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private DatabaseReference productsRreReference;

    private String category = "";

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

        Query query;

        if (category.equals("")) {
            query = productsRreReference;
        } else {
            query = productsRreReference.orderByChild("category").equalTo(category);
        }

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_panel_all_products_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_category_menu_item_id:
                View  view = findViewById(R.id.admin_category_menu_item_id);
                openCategoryPopUpMenu(view);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCategoryPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(AllProductsActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.category_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.laptop_menu_item_id) {
                    category = "laptop";
                    onStart();
                    showToast("laptop");
                    return true;
                } else if (id == R.id.pc_menu_item_id) {
                    category = "desktop";
                    onStart();
                    showToast("PC");
                    return true;
                } else if (id == R.id.iPhone_menu_item_id) {
                    category = "iphone";
                    onStart();
                    showToast("iPhone");
                    return true;
                } else if (id == R.id.android_menu_item_id) {
                    showToast("android");
                    category = "android";
                    onStart();
                    return true;
                } else {
                    return false;
                }

            }
        });

        popupMenu.show();
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
