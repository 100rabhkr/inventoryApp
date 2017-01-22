package com.dezlum.www.inventoryapp;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dezlum.www.inventoryapp.data.InventoryDbHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InsertItem extends AppCompatActivity {

    public String price;
    public String countitem;
    public String name;
    public long nextID;

    public inventory inventoryObject = new inventory();
    InventoryDbHandler mDbHandler = new InventoryDbHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);
        nextID = (mDbHandler.rowCount() + 1);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("HEADER");
        setTitle(msg);
        Button btn = (Button)findViewById(R.id.submitAddButton);
        /*final EditText nameText = (EditText) findViewById(R.id.NameOfProduct);
        final EditText priceText = (EditText) findViewById(R.id.PriceOfProduct);
        final EditText quantityText = (EditText) findViewById(R.id.QuantityOfProduct);
        final ImageView image = (ImageView) findViewById(R.id.imageSelected);
        final ImageView imageError = (ImageView) findViewById(R.id.imageError);
        name = nameText.getText().toString();
        price = priceText.getText().toString();
        countitem = quantityText.getText().toString();*/
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final EditText nameText = (EditText) findViewById(R.id.NameOfProduct);
                final EditText priceText = (EditText) findViewById(R.id.PriceOfProduct);
                final EditText quantityText = (EditText) findViewById(R.id.QuantityOfProduct);
                final ImageView image = (ImageView) findViewById(R.id.imageSelected);
                final ImageView imageError = (ImageView) findViewById(R.id.imageError);
                name = nameText.getText().toString();
                price = priceText.getText().toString();
                countitem = quantityText.getText().toString();
                if(name.isEmpty()){
                    nameText.setError("This Field Can not Be Empty");
                }
                else if (price.isEmpty()){
                    priceText.setError("This Field Can not Be Empty");
                }
                else if(countitem.isEmpty()){
                    quantityText.setError("This Field Can not Be Empty");
                }
                else if(image.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Upload an image", Toast.LENGTH_LONG).show();

                    imageError.setVisibility(View.VISIBLE);
                    imageError.setImageResource(R.drawable.error);
                }
                else{

                    mDbHandler.addItem(new inventory(name, Integer.parseInt(countitem), Double.parseDouble(price)));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
            }
        });


    }

    public void btnImageOnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 &&
                resultCode == RESULT_OK && null != data) {
            Toast.makeText(this, "Please Wait", Toast.LENGTH_LONG).show();
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ImageView imageView = (ImageView) findViewById(R.id.imageSelected);
                imageView.setImageBitmap(bitmap);
                String filename = Long.toString(nextID);
                Log.v("Image path: ", filename);
                saveToStorage(bitmap, filename);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void saveToStorage(Bitmap bmp, String filename) {

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File Directory = contextWrapper.getFilesDir();

        File currentPath = new File(Directory, filename);

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(currentPath);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
