package com.example.liteup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewId, textViewFulname, textViewUsername, textViewEmail;
    private Button buttonDelete, btnUpdate;
    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new Databasehelper(this);
        textViewId = findViewById(R.id.textViewId);
        textViewFulname = findViewById(R.id.textViewFullname);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        buttonDelete = findViewById(R.id.buttonDelete);
        btnUpdate= (Button)findViewById(R.id.btnUpdate);

        User user = db.getUserData(String.valueOf(MainActivity.id));

        textViewId.setText("User ID: "+user.getId());
        textViewEmail.setText("Email: "+user.getEmail());
        textViewFulname.setText("Full Name: "+user.getFullname());
        textViewUsername.setText("User Name: "+user.getUsername());


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = db.deleteUser(String.valueOf(MainActivity.id));
                if(deleteRows > 0)
                    Toast.makeText(ProfileActivity.this, "Profile is deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ProfileActivity.this, "Data has not found", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileActivity.this, UpdateUser.class);
                startActivity(intent);
            }
        });




    }

}
