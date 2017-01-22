package com.dezlum.www.inventoryapp.data;

import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dezlum.www.inventoryapp.inventory;

import java.util.ArrayList;

/**
 * Created by saurabh on 1/23/2017.
 */

public class InventoryDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Inventory.db";

    public InventoryDbHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(InventoryDbContract.InventoryEntry.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(InventoryDbContract.InventoryEntry.DELETE_TABLE);
        onCreate(sqLiteDatabase);

    }

    public void addItem(inventory newinventory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryDbContract.InventoryEntry.KEY_TITLE, newinventory.getProductName());
        values.put(InventoryDbContract.InventoryEntry.KEY_QUANTITY, newinventory.getQuantity());
        values.put(InventoryDbContract.InventoryEntry.KEY_PRICE, newinventory.getPrice());
        db.insert(InventoryDbContract.InventoryEntry.TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<inventory> readInventory(){

        ArrayList<inventory> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM "+ InventoryDbContract.InventoryEntry.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                inventory inv = new inventory();
                inv.setProductID(Integer.parseInt(cursor.getString(0)));

                inv.setProductName(cursor.getString(1));
                inv.setQuantity(cursor.getInt(2));
                inv.setPrice(cursor.getDouble(3));
                inventoryList.add(inv);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return inventoryList;
    }


    public void updateoneRow(double rowId, inventory inv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryDbContract.InventoryEntry.KEY_TITLE,inv.getProductName());
        values.put(InventoryDbContract.InventoryEntry.KEY_PRICE,inv.getPrice());
        values.put(InventoryDbContract.InventoryEntry.KEY_QUANTITY,inv.getQuantity());
        db.update(InventoryDbContract.InventoryEntry.TABLE_NAME, values,InventoryDbContract.InventoryEntry._ID + "=" + rowId,null);



    }


    public void deleteoneRow(double rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(InventoryDbContract.InventoryEntry.TABLE_NAME, InventoryDbContract.InventoryEntry._ID + "=" + rowId, null);

    }
    public long rowCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        long count  = DatabaseUtils.queryNumEntries(db,InventoryDbContract.InventoryEntry.TABLE_NAME );
        db.close();
        return count;

    }


}
