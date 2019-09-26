package com.example.liteup;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class AddRestaurantActivity extends Activity implements OnClickListener {

    private Button addTodoBtn;
    private EditText restaurantEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText phoneEditText;


    private DBManagers dbManagers;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobilePattern = "[0-9]{10}";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_add_record);

        restaurantEditText = (EditText) findViewById(R.id.rastaurant_edittext);
        emailEditText = (EditText) findViewById(R.id.email_edittext);
        addressEditText = (EditText) findViewById(R.id.address_edittext);
        phoneEditText = (EditText) findViewById(R.id.phone_edittext);


        addTodoBtn = (Button) findViewById(R.id.add_record);

        dbManagers = new DBManagers(this);
        dbManagers.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:

                final String name = restaurantEditText.getText().toString();
                final String desc = emailEditText.getText().toString();
                final String address = addressEditText.getText().toString();
                final String phone = phoneEditText.getText().toString();


                if (restaurantEditText.getText().toString().isEmpty()) {
                    restaurantEditText.setError("Name can not be Empty");
                }

                else if (emailEditText.getText().toString().isEmpty()){
                    emailEditText.setError("Email can not be Empty");
                }

                else if (addressEditText.getText().toString().isEmpty()) {
                    addressEditText.setError("Email can not be Empty");
                }

                else if (phoneEditText.getText().toString().isEmpty()) {
                    phoneEditText.setError("Email can not be Empty");
                }
                else if (!emailEditText.getText().toString().trim().matches(emailPattern)){
                    phoneEditText.setError("Enter Valid Email");
                }
                 else if (!phoneEditText.getText().toString().trim().matches(MobilePattern)) {
                    phoneEditText.setError("Enter Valid Phone Number");
                }
                 else{
                    dbManagers.insert(name, desc, address, phone);
                    Toast.makeText(getApplicationContext(), "Details Inserted", Toast.LENGTH_SHORT).show();

                    Intent main = new Intent(AddRestaurantActivity.this, RestaurentListActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                    break;
                }


                //startActivity(main);
                //break;


        }


    }}
