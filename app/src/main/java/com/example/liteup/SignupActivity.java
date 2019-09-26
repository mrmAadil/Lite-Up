package com.example.liteup;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private TextView textViewFullname, textViewUsername, textViewEmail, textViewPassword, textViewConfirm;
    private Button buttonSignup, buttonCancel, btn;
    Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new Databasehelper(this);
        textViewFullname = findViewById(R.id.editTextFullname);
        textViewUsername = findViewById(R.id.editTextUsername);
        textViewEmail = findViewById(R.id.editTextEmail);
        textViewPassword = findViewById(R.id.editTextPassword);
        textViewConfirm = findViewById(R.id.editTextConfirm);
        buttonSignup = findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup_onClick(view);
            }
        });

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAll(view);
            }
        });

    }

    public void signup_onClick(View view) {
        String fullname = textViewFullname.getText().toString();
        String username = textViewUsername.getText().toString();
        String email = textViewEmail.getText().toString();
        String password = textViewPassword.getText().toString();
        String confirm = textViewConfirm.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if(fullname.isEmpty()){
            textViewFullname.setError("This field can not be empty!");
        }
        else if (username.isEmpty()){
            textViewUsername.setError("This field can not be empty!");
        }
        else if(!email.matches(emailPattern)){
            textViewEmail.setError("Invalid Email!");
        }
        else if(password.length() < 8 ){
            textViewPassword.setError("Password must have at lest 8 characters!");
        }

        else {
            if (password.equals(confirm)) {
                long val = db.create(fullname, username, email, password);
                if (val > 0) {
                    Toast.makeText(SignupActivity.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(SignupActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(SignupActivity.this, "Passwords are not matching!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void viewAll(View view){
        Cursor res = db.getAllData();
        StringBuffer buffer = new StringBuffer();
        if( res.getCount() == 0){
            message("error", "nothing");
            return;
        }
        while (res.moveToNext()){
            buffer.append(" ID : " + res.getString(0) + "\n");
            buffer.append(" ID : " + res.getString(1) + "\n");
            buffer.append(" ID : " + res.getString(2) + "\n");
            buffer.append(" ID : " + res.getString(3) + "\n");
            buffer.append(" ID : " + res.getString(4) + "\n\n");
        }
        message("Data", buffer.toString());

    }

    public void message(String title, String Message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
