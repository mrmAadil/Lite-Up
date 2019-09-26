package com.example.liteup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModifyRestaurantActivity extends Activity implements OnClickListener{

    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText emailText;
    private EditText addressText;
    private EditText phoneText;



    private long _id;

    private DBManagers dbManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_restaurant);

        dbManagers = new DBManagers(this);
        dbManagers.open();

        titleText = (EditText) findViewById(R.id.rastaurant_edittext);
        emailText = (EditText) findViewById(R.id.email_edittext);
        addressText = (EditText) findViewById(R.id.address_edittext);
        phoneText = (EditText) findViewById(R.id.phone_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String email = intent.getStringExtra("email");
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");


        _id = Long.parseLong(id);

        titleText.setText(name);
        emailText.setText(email);
        addressText.setText(address);
        phoneText.setText(phone);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String title = titleText.getText().toString();
                String email = emailText.getText().toString();
                String address = addressText.getText().toString();
                String phone = phoneText.getText().toString();


                dbManagers.update(_id, title, email,  address,phone);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbManagers.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), RestaurentListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}