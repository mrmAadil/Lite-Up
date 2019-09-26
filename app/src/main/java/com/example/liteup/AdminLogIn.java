package com.example.liteup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogIn extends AppCompatActivity {

    static int id;
    private TextView textViewUsername, textViewPassword;
    private Button buttonLogin;
    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        db = new Databasehelper(this);
        textViewUsername = findViewById(R.id.editTextUsername);
        textViewPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(textViewUsername.getText().toString(), textViewPassword.getText().toString());


                }


        });


    }
    private void validate(String userName, String userPassword) {
        if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(AdminLogIn.this, AdminHome.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(AdminLogIn.this, "Enter valid Admin UserName & Password!", Toast.LENGTH_LONG).show();

        }
    }
}