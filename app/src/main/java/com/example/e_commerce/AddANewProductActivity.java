package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddANewProductActivity extends AppCompatActivity {

    private ImageView laptopBtn, desktopBtn, androidBtn, iphoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anew_product);

        // initialize fields
        laptopBtn = findViewById(R.id.laptop_image_btn_id);
        desktopBtn = findViewById(R.id.desktop_image_btn_id);
        androidBtn = findViewById(R.id.android_image_btn_id);
        iphoneBtn = findViewById(R.id.iphone_image_btn_id);

        laptopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddANewProductActivity.this, UploadProductActivity.class);
                intent.putExtra("category", "laptop");
                startActivity(intent);
            }
        });

        desktopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddANewProductActivity.this, UploadProductActivity.class);
                intent.putExtra("category", "desktop");
                startActivity(intent);
            }
        });

        androidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddANewProductActivity.this, UploadProductActivity.class);
                intent.putExtra("category", "android");
                startActivity(intent);
            }
        });

        iphoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddANewProductActivity.this, UploadProductActivity.class);
                intent.putExtra("category", "iphone");
                startActivity(intent);
            }
        });

    }
}
