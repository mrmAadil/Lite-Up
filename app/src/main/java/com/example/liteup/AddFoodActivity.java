package com.example.liteup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFoodActivity extends AppCompatActivity {

    EditText editName, editPrice;
    Button btnChoose, btnAddFood, btnFoodList;
    ImageView imageView;

    final int REQUEST_CODE_GALLERY = 999;

    public static FoodDBHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        init();

        sqLiteHelper = new FoodDBHelper(this,"LiteUP.db",null,1);
        //sqLiteHelper.queryData("DROP TABLE FOODS");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOODS (id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,price VARCHAR,image BLOG) ");

        btnChoose.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddFoodActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FoodName = editName.getText().toString();
                String FoodPrice =  editPrice.getText().toString();

                if(FoodName.isEmpty()){
                    editName.setError("This field cannot be empty");
                }
                else if(FoodPrice.isEmpty()){
                    editPrice.setError("This field cannot be empty");
                }
                else {
                    try {
                        sqLiteHelper.insertFood(
                                editName.getText().toString().trim(),
                                editPrice.getText().toString().trim(),
                                imageViewToByte(imageView)
                        );

                        Toast.makeText(getApplicationContext(), "Food Added !", Toast.LENGTH_SHORT).show();
                        editName.setText("");
                        editPrice.setText("");
                        imageView.setImageResource(R.mipmap.ic_launcher);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        btnFoodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFoodActivity.this,FoodListActivity.class);
                startActivity(intent);
            }
        });

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(),"You don't have permissions to access file location", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        editName = (EditText) findViewById(R.id.editTextAddFood);
        editPrice =(EditText) findViewById(R.id.editTextAddPrice);
        btnChoose = (Button) findViewById(R.id.btnChooseImage);
        btnAddFood = (Button) findViewById(R.id.btnAddFood);
        btnFoodList = (Button) findViewById(R.id.btnFoodList);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
}
