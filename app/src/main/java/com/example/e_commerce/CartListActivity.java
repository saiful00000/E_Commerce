package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.interfaces.ProductItemClickListenner;
import com.example.e_commerce.models.CartItem;
import com.example.e_commerce.viewHolders.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartListActivity extends AppCompatActivity {

    private TextView prodPriceTv;
    private Button prodOrderButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference cartReference;

    private String currentUserId;
    private int itemCnt, totalPriceOfAllProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getUid();
        cartReference = FirebaseDatabase.getInstance().getReference().child("Cart");

        prodPriceTv = findViewById(R.id.c_price_tv_id);
        prodOrderButton = findViewById(R.id.c_order_button_id);
        recyclerView = findViewById(R.id.c_recycler_view_id);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        prodOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccessOrder();
            }
        });

    }

    private void proccessOrder() {
        Toast.makeText(this, Integer.toString(itemCnt), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CartItem> options =
                new FirebaseRecyclerOptions.Builder<CartItem>()
                .setQuery(cartReference.child("UserView")
                        .child(currentUserId)
                        .child("Products"),
                        CartItem.class)
                .build();

        FirebaseRecyclerAdapter<CartItem, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<CartItem, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final CartItem cartItem) {

                        int price = Integer.valueOf(cartItem.getPrice()) * Integer.valueOf(cartItem.getQuantity());
                        totalPriceOfAllProduct += price;

                        prodPriceTv.setText("Total = " + totalPriceOfAllProduct + " tk");

                        cartViewHolder.productNameTv.setText(cartItem.getName());
                        cartViewHolder.productPriceTv.setText("Price "+ Integer.toString(price) +" tk");
                        cartViewHolder.productQuantityTv.setText(cartItem.getQuantity() + " piece");
                        
                        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(CartListActivity.this, "", Toast.LENGTH_SHORT).show();
                            }
                        });
                        
                        cartViewHolder.removeCartItemButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String productKey = cartItem.getKey();
                                deleteCartItem(productKey);
                            }
                        });
                    }

                    @Override
                    public int getItemCount() {
                        itemCnt = super.getItemCount();
                        return super.getItemCount();
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                        CartViewHolder viewHolder = new CartViewHolder(view);
                        return viewHolder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void deleteCartItem(String productKey) {
        cartReference.child("UserView")
                .child(currentUserId)
                .child("Products")
                .child(productKey)
                .removeValue();
        cartReference.child("AdminView")
                .child(currentUserId)
                .child("Products")
                .child(productKey)
                .removeValue();
    }
}
