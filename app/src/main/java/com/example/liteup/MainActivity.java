package com.example.liteup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static int id;
    private TextView textViewUsername, textViewPassword, tvInfo,tvAdminLogin;
    private Button buttonLogin, buttonSignup;
    private int counter =5;
    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Databasehelper(this);
        textViewUsername = findViewById(R.id.editTextUsername);
        textViewPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);
        tvInfo= (TextView)findViewById(R.id.tvInfo);

        tvInfo.setText("No of attempts remaining: 5");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = textViewUsername.getText().toString().trim();
                String password = textViewPassword.getText().toString().trim();
                int res = db.checkUser(user, password);
                if (res != -1) {
                    id = res;
                    Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent1);
                } else {
                    counter--;

                    tvInfo.setText("No of attempts remaining: " + String.valueOf(counter));

                    if(counter == 0){
                        buttonLogin.setEnabled(false);
                    }

                }
            }

        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
        });
        tvAdminLogin = findViewById(R.id.tvAdminLogIn);
        tvAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, AdminLogIn.class);
                    startActivity(intent);
                }

        });


    }
}
