package com.example.liteup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    ImageButton btnOrderFoods, btnViewOrders, viewUserProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnOrderFoods  = findViewById(R.id.imgBtnAddRes);
        btnViewOrders = findViewById(R.id.imgbtnViewRes);
        viewUserProfile =(ImageButton)findViewById(R.id.viewUserProfile);

    }

    @Override
    protected void onResume() {
        super.onResume();

       /* btnOrderFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SecondActivity.this,OrderFoods.class);
                startActivity(intent1);*
            }

            // View Order intent

        });*/
      viewUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,ProfileActivity.class));
            }
        });
    }
}
