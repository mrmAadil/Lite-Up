package com.example.liteup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {

    ImageButton btnAddRes, btnViewRes, viewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnAddRes  = findViewById(R.id.imgBtnAddRes);
        btnViewRes = findViewById(R.id.imgbtnViewRes);

    }

    @Override
    protected void onResume() {
        super.onResume();

        btnViewRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AdminHome.this, ViewRestaurant.class);
                startActivity(intent1);
            }

            // Add Restaurant intent

        });

    }
}
