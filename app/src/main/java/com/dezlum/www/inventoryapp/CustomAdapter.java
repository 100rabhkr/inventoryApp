package com.dezlum.www.inventoryapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dezlum.www.inventoryapp.data.InventoryDbHandler;

import java.util.ArrayList;

/**
 * Created by saurabh on 1/23/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private static final String LOG_TAG = CustomAdapter.class.getSimpleName();
    ArrayList<inventory> inventories;

    public CustomAdapter(ArrayList<inventory> listArray) {
        this.inventories = new ArrayList<>(listArray);
    }

    @Override
    public int getCount() {
        return inventories.size();
    }

    @Override
    public Object getItem(int i) {
        return inventories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, final ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.list, parent, false);
        }
        final inventory data = inventories.get(i);
        final TextView productName = (TextView) view.findViewById(R.id.productName);
        productName.setText(data.getProductName());
        final TextView productAvailable;
        productAvailable = (TextView) view.findViewById(R.id.productAvailable);
        productAvailable.setText(""+data.getQuantity());
        final TextView productPrice = (TextView) view.findViewById(R.id.productPrice);
        productPrice.setText(""+data.getPrice());
        Button button = (Button) view.findViewById(R.id.listItemButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryDbHandler db = new InventoryDbHandler(view.getContext());
                data.quantitySell();
                if (data.getQuantity() == 0) {
                    Toast.makeText(parent.getContext(), "No more item(s) of " + data.getProductName(), Toast.LENGTH_SHORT).show();
                }
                db.updateoneRow(data.getProductId(), data);
                productAvailable.setText("" + data.getQuantity());
                Toast.makeText(parent.getContext(), "Quantity for  " + data.getProductName() + " is reduced.\n" +
                        "Remaining Quantity: " + data.getQuantity(), Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent details = new Intent(parent.getContext(), PreviewActivity.class);
                details.putExtra("productName", data.getProductName());
                details.putExtra("productQuantity", data.getQuantity());
                details.putExtra("id", data.getProductId());
                parent.getContext().startActivity(details);

            }
        });
        return view;
    }
}
