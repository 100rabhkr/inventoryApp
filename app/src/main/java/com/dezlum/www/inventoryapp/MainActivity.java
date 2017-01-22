package com.dezlum.www.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dezlum.www.inventoryapp.data.InventoryDbHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InventoryDbHandler db = new InventoryDbHandler(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        TextView empty = (TextView) findViewById(R.id.empty);
        ArrayList<inventory> listArray = db.readInventory();
        if (listArray.size() == 0) {
            empty.setText("No Data Found Please Click Add Item");
        } else {
            empty.setText("");
        }
        CustomAdapter customAdapter = new CustomAdapter(listArray);
        listView.setAdapter(customAdapter);

    }
    public void addNewItem(View view) {
        Intent intent = new Intent(this, InsertItem.class);
        intent.putExtra("HEADER", "Add a New Item");
        startActivity(intent);
    }
}