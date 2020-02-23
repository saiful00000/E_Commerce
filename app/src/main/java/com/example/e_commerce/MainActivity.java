package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.models.Product;
import com.example.e_commerce.viewHolders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private FloatingActionButton cartButton;

    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootReference;
    private DatabaseReference productsRreReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize all firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        productsRreReference = FirebaseDatabase.getInstance().getReference().child("Products");

        // initialize views
        toolbar = findViewById(R.id.toolbarId);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        navigationView = findViewById(R.id.nav_view_id);
        recyclerView = findViewById(R.id.main_recyclerView_id);
        cartButton = findViewById(R.id.cart_btn_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("E Commerce");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.nav_drawer_open,
                R.string.nav_drawer_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // check user existence
        checkCurrentUserLogin();

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productsRreReference, Product.class)
                .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Product product) {
                        Picasso.get().load(product.getImage()).into(holder.productImageView);
                        holder.productNameTv.setText(product.getName());
                        holder.productPriceTv.setText(product.getPrice() + " tk");
                        holder.productDescriptionTv.setText(product.getDescription());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, ProductsDetailsActivity.class);
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

    private void checkCurrentUserLogin() {
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, StartingActivity.class));
        } else {
            verifyUserExistance();
        }
    }

    private void verifyUserExistance() {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        if (currentUserId.equals("dXjBngp0PkNiMedYhOU7v5mFxhK2")) {
            // check if the user is a admin or not
            startActivity(new Intent(MainActivity.this, AdminPannelActivity.class));
            finish();
            return;
        } else {
            rootReference = FirebaseDatabase.getInstance().getReference();
            rootReference.child("Users").child(currentUserId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // if profile information is empty then send user to update profile activity
                            if ((!dataSnapshot.child("name").getValue().equals(""))) {
                                Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                            } else {
                                sendUserToUpdateProfileActivity();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

    }

    private void sendUserToUpdateProfileActivity() {
        startActivity(new Intent(MainActivity.this, UpdateProfileActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_log_out_id:
                userSignOut();
            case R.id.menu_setting_id:
                //TODO setting
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void userSignOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, StartingActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.d_menu_cart_id:
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
                return true;
            case R.id.d_menu_category_id:
                toast("category");
                return true;
            case R.id.d_menu_order_id:
                toast("order");
                return true;
            case R.id.d_menu_settings_id:
                toast("settings");
                return true;
            case R.id.d_menu_logout_id:
                toast("logout");
                return true;
            default:
                return false;
        }

    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
