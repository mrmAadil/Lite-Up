package com.example.liteup;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class RestaurentListActivity extends AppCompatActivity {

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.RESTAURANT, DatabaseHelper.EMAIL, DatabaseHelper.ADDRESS,DatabaseHelper.PHONE };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.email,R.id.address,R.id.phone };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurent_list);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.title);
                TextView emailTextView = (TextView) view.findViewById(R.id.email);
                TextView addressTextView = (TextView) view.findViewById(R.id.address);
                TextView phoneTextView = (TextView) view.findViewById(R.id.phone);


                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String address = addressTextView.getText().toString();
                String phone = phoneTextView.getText().toString();


                Intent modify_intent = new Intent(getApplicationContext(), ModifyRestaurantActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("email", email);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("address", address);
                modify_intent.putExtra("phone", phone);


                startActivity(modify_intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, AddRestaurantActivity.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }

}
