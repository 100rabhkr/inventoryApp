package com.dezlum.www.inventoryapp;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dezlum.www.inventoryapp.data.InventoryDbHandler;

import java.io.File;

public class PreviewActivity extends AppCompatActivity {
    public static double priceofProduct;
    public int rowID;
    public inventory inv = new inventory();
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        TextView name = (TextView) findViewById(R.id.productName);
        TextView price = (TextView) findViewById(R.id.productPrice);
        TextView quantity = (TextView) findViewById(R.id.productQuantity);
        quantity.setText("0");

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("productName"));
        name.setText(intent.getStringExtra("productName"));
        int id  = intent.getIntExtra("id",0);
        ContextWrapper cw = new ContextWrapper(this);
        File dir = cw.getFilesDir();
        InventoryDbHandler mDbHandler = new InventoryDbHandler(this);
        String imageLocationDir = dir.toString();
        rowID = intent.getIntExtra("id", 0) - 1;
        String imagePath = imageLocationDir + "/" + rowID;

        ImageView imageView = (ImageView) findViewById(R.id.imgIcon);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        imageView.setImageBitmap(bitmap);

        quantity.setText(":" + intent.getIntExtra("productQuantity", 0));
        priceofProduct = intent.getDoubleExtra("productPrice", 0.0);
        price.setText(":" + priceofProduct);


    }

    public void quantityIncrement(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void quantityDecrement(View view) {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    private void displayQuantity(int count) {
        TextView quantity = (TextView) findViewById(R.id.quantity);
        quantity.setText("" + count);

    }
    public void OrderMore(View view) {

        String subject = "More Item Required:"+inv.getProductName();
        String message = "Product Name: " + inv.getProductName() +
                "\nQuantity To be ordered: " + quantity ;
        String[] emails = {"emailidhere@example.com"};

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void onClickDelete(final View view) {

        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InventoryDbHandler inventoryDbHandler = new InventoryDbHandler(getApplicationContext());
                        inventoryDbHandler.deleteoneRow(rowID);
                        Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
