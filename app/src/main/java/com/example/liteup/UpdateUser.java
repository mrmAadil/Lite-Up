package com.example.liteup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUser extends AppCompatActivity {

    private TextView etUserID, etFullname, etUserName, etEmail;
    private Button btnUpdate;
    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = new Databasehelper(this);
        etUserID = findViewById(R.id.etUserId);
        etFullname = findViewById(R.id.etFullName);
        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        User user = db.getUserData(String.valueOf(MainActivity.id));

        etUserID.setText("User ID: " + user.getId());
        etEmail.setText("Email: " + user.getEmail());
        etFullname.setText("Full Name: " + user.getFullname());
        etUserName.setText("User Name: " + user.getUsername());

            btnUpdate.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isUpdate = db.updateData(etUserID.getText().toString(),
                                    etFullname.getText().toString(),
                                    etUserName.getText().toString(), etEmail.getText().toString());
                            if (isUpdate == true)
                                Toast.makeText(UpdateUser.this, "Data Updated", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UpdateUser.this, ProfileActivity.class);
                                startActivity(intent);

                        }
                    }
            );


    }
}