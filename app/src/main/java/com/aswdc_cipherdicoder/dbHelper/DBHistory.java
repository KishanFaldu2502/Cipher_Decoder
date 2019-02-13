package com.aswdc_cipherdicoder.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aswdc_cipherdicoder.util.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHistory extends SQLiteAssetHelper{
    public DBHistory(Context context) {
        super(context, Constant.DB_Name, null, Constant.DB_Version);
    }
    public void insert(){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
    }
}