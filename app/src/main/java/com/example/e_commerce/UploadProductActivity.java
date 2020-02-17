package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UploadProductActivity extends AppCompatActivity {

    private ImageView productImageEv;
    private EditText productNameEt, productBrandNameEt, productPriceEt, productDescriptionEt;
    private Button productUploadButton;

    private ProgressDialog progressDialog;

    private static final int IMAGE_REQUEST = 1;
    private Uri prodImageUri;

    private String prodName, prodBrandName, prodPrice, prodDescription, currentDate, currentTime;
    private String productRandomKey, imageDownloadUrl;
    String category;

    private StorageReference productImageReference;
    private DatabaseReference productReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        productImageReference = FirebaseStorage.getInstance().getReference().child("Product Images");
        productReference = FirebaseDatabase.getInstance().getReference().child("Products");

        productImageEv = findViewById(R.id.prod_image_imageView_id);
        productNameEt = findViewById(R.id.prod_name_tv_id);
        productBrandNameEt = findViewById(R.id.prod_brand_name_et_id);
        productPriceEt = findViewById(R.id.prod_price_et_id);
        productDescriptionEt = findViewById(R.id.prod_description_et_id);
        productUploadButton = findViewById(R.id.prod_upload_btn_id);

        progressDialog = new ProgressDialog(this);

        productImageEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProductImage();
            }
        });

        productUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProduct();
            }
        });

    }

    private void selectProductImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            prodImageUri = data.getData();
            productImageEv.setImageURI(prodImageUri);
        }
    }

    private void uploadProduct() {
        prodName = productNameEt.getText().toString().trim();
        prodBrandName = productBrandNameEt.getText().toString().trim();
        prodPrice = productPriceEt.getText().toString().trim();
        prodDescription = productDescriptionEt.getText().toString();

        if (prodImageUri != null && !prodName.equals("") && !prodBrandName.equals("") && !prodPrice.equals("")) {
            showProgressDialog();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormate = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = dateFormate.format(calendar.getTime());
            SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss a");
            currentTime = timeFormate.format(calendar.getTime());

            // set product random key
            productRandomKey = currentDate + currentTime;

            final StorageReference filePath = productImageReference.child(prodImageUri.getLastPathSegment() + productRandomKey + ".jpg");

            final UploadTask uploadTask = filePath.putFile(prodImageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String error = e.toString();
                    Toast.makeText(UploadProductActivity.this, "Error: ", Toast.LENGTH_SHORT).show();
                    Log.e("productImageUploadError", error);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("produCtImageUpload", "Image uploaded successfuly");
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                progressDialog.dismiss();
                                throw task.getException();
                            }
                            imageDownloadUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                uploadProductDataToDatabase();
                                progressDialog.dismiss();
                                refreshTheCurrentActivity();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    private void refreshTheCurrentActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void uploadProductDataToDatabase() {
        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        Map<String, Object> product = new HashMap<>();
        product.put("key", productRandomKey);
        product.put("image", imageDownloadUrl);
        product.put("category", category);
        product.put("name", prodName);
        product.put("brand", prodBrandName);
        product.put("price", prodPrice);
        product.put("description", prodDescription);
        product.put("date", currentDate);
        product.put("time", currentTime);


        productReference.child(productRandomKey).updateChildren(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UploadProductActivity.this, "Product Uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("productUploadError", task.getException().toString());
                            Toast.makeText(UploadProductActivity.this, "Problem during uploading product", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showProgressDialog() {
        progressDialog.setTitle("Login in process....");
        progressDialog.setMessage("Wait until finished...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

}



















