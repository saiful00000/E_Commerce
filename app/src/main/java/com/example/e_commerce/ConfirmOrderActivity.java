package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {

    private TextView totalPriceTv;
    private EditText nameEt, phoneEt, cityEt, areaEt, sectorEt, roadEt, houseEt;
    private Button confirmOrderBtn;

    private String price;
    private String currentUserId, currentDate, currentTime;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference orderReference, userSideCartRef, adminSideCartRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        totalPriceTv = findViewById(R.id.total_price_tv_id);
        nameEt = findViewById(R.id.riceiver_name_et_id);
        phoneEt = findViewById(R.id.riceiver_phone_et_id);
        cityEt = findViewById(R.id.riceiver_city_et_id);
        areaEt = findViewById(R.id.riceiver_area_et_id);
        sectorEt = findViewById(R.id.riceiver_sector_et_id);
        roadEt = findViewById(R.id.riceiver_roadnumber_et_id);
        houseEt = findViewById(R.id.riceiver_housenumber_et_id);
        confirmOrderBtn = findViewById(R.id.order_confirm_button_id);


        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getUid();
        orderReference = FirebaseDatabase.getInstance().getReference().child("OrderOnAdminSide");
        userSideCartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child("UserView");
        adminSideCartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child("AdminView");

        price = getIntent().getStringExtra("price");


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });

    }

    private void confirmOrder() {
        String name = nameEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String city = cityEt.getText().toString().trim();
        String area = areaEt.getText().toString().trim();
        String sector = sectorEt.getText().toString().trim();
        String road = roadEt.getText().toString().trim();
        String house = houseEt.getText().toString().trim();

        if (name.equals("") || phone.equals("") || city.equals("") || area.equals("") || road.equals("") || house.equals("")) {
            Toast.makeText(this, "Enter your All information correctly", Toast.LENGTH_SHORT).show();
        } else {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormate = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = dateFormate.format(calendar.getTime());
            SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss a");
            currentTime = timeFormate.format(calendar.getTime());

            String address = city +", "+ area +", "+ sector +", "+ road +", "+ house;

            Map<String, Object> order = new HashMap<>();
            order.put("name", name);
            order.put("phone", phone);
            order.put("adress", address);
            order.put("date", currentDate);
            order.put("time", currentTime);


            orderReference.child(currentUserId).updateChildren(order)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                removeProductFromCart();
                                Toast.makeText(ConfirmOrderActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
                                finish();
                                //TODO delete cart list
                            } else {
                                Log.e("productUploadError", task.getException().toString());
                                Toast.makeText(ConfirmOrderActivity.this, "Problem during uploading product", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    }

    private void removeProductFromCart() {
           userSideCartRef
                   .child(currentUserId)
                   .removeValue()
                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               Log.i("user_cart_removal", "order placed and product removed from user side cart");
                           }

                       }
                   });
    }
}
