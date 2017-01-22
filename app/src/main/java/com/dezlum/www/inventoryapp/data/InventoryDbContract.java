package com.dezlum.www.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by saurabh on 1/23/2017.
 */

public final class InventoryDbContract {

    private InventoryDbContract(){

    }
    public static abstract class InventoryEntry implements BaseColumns {


        public static final String TABLE_NAME = "inventory";
        public static final String _ID = BaseColumns._ID;
        public static final String KEY_TITLE = "title";
        public static final String KEY_QUANTITY = "quantity";
        public static final String KEY_PRICE = "price";
        public static final String KEY_IMAGE = "image";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_QUANTITY + " INTEGER,"+
                KEY_PRICE + " REAL, "+
                KEY_IMAGE + " TEXT"+
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
