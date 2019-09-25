package com.example.liteup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Food> list;
    FoodListAdapter adapter = null;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new FoodListAdapter(this, R.layout.food_items,list);
        gridView.setAdapter(adapter);

        //Get all data from sqlite
        Cursor cursor = AddFoodActivity.sqLiteHelper.getFood("SELECT * FROM FOODS");
        list.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name =  cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Food(id,name,price,image));
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                CharSequence[] items ={"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(FoodListActivity.this);

                dialog.setTitle("Choose an Action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                         if (item == 0){
                             //Update
                             Cursor cursor = AddFoodActivity.sqLiteHelper.getFood("SELECT id FROM FOODS");
                             ArrayList<Integer> arrID = new ArrayList<Integer>();

                             while(cursor.moveToNext()){
                                 arrID.add(cursor.getInt(0));
                             }

                             //show update dialog
                             showDialogUpdate(FoodListActivity.this, arrID.get(position));
                         }else{
                             //delete
                             Cursor cursor = AddFoodActivity.sqLiteHelper.getFood("SELECT id FROM FOODS");
                             ArrayList<Integer> arrID = new ArrayList<Integer>();

                             while(cursor.moveToNext()){
                                 arrID.add(cursor.getInt(0));
                             }
                             showDialogDelete(arrID.get(position));

                         }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    ImageView imageViewFood;
    //Dialog to confirm Update
    private void showDialogUpdate(Activity activity,final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_food_activity);
        dialog.setTitle("Update");

        imageViewFood = (ImageView) dialog.findViewById(R.id.updateImage);
        final EditText editName = (EditText) dialog.findViewById(R.id.editUpdateName);
        final EditText editPrice = (EditText) dialog.findViewById(R.id.editUpdatePrice);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        //set width for dialog box
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        //set height of dialog box
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);

        dialog.getWindow().setLayout(width,height);
        dialog.show();

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //req photo library
                ActivityCompat.requestPermissions(
                        FoodListActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AddFoodActivity.sqLiteHelper.updateFood(
                            editName.getText().toString().trim(),
                            editPrice.getText().toString().trim(),
                            AddFoodActivity.imageViewToByte(imageViewFood),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_SHORT).show();

                } catch (Exception error) {
                    Log.e("Update Error : ",error.getMessage());
                }
                updateFoodList();
            }

        });
    }

    //Delete Food
    public void showDialogDelete(final int foodID){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(FoodListActivity.this);

        dialogDelete.setTitle("Are you sure to delete this ?");
        dialogDelete.setPositiveButton("I'm Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                try {
                    AddFoodActivity.sqLiteHelper.deleteFood(foodID);
                    Toast.makeText(getApplicationContext(),"Delete Successfully!!!",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Log.e("error", e.getMessage());
                }
                updateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }

    //Update food in the list
    private void updateFoodList(){
        //Get all data from sqlite
        Cursor cursor = AddFoodActivity.sqLiteHelper.getFood("SELECT * FROM FOODS");
        list.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Food(id,name,price,image));
        }
        adapter.notifyDataSetChanged();
    }

    //Permision to Storage Access
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else{
                Toast.makeText(getApplicationContext(),"You don't have permisions to access file location", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
