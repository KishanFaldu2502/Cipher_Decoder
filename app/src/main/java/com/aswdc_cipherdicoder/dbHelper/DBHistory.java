package com.aswdc_cipherdicoder.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aswdc_cipherdicoder.bean.Bean_History;
import com.aswdc_cipherdicoder.util.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DBHistory extends SQLiteAssetHelper{
    public DBHistory(Context context) {
        super(context, Constant.DB_Name, null, Constant.DB_Version);
    }
    public void insert(Bean_History bh){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Plain_Text",bh.getPlain_Text());
        cv.put("Cipher_Method",bh.getCipher_Method());
        cv.put("Cipher_Result",bh.getCipher_Result());

        db.insert("Cipher_history",null,cv);
        db.close();
    }
    public ArrayList<Bean_History> gethistory()
    {
        ArrayList<Bean_History> arrayhistory=new ArrayList<Bean_History>();
        SQLiteDatabase db=getReadableDatabase();
        String strQuery="select * from Cipher_history";
        Cursor cursor=db.rawQuery(strQuery,null);
        if (cursor.moveToFirst())
        {
            do {
                Bean_History bh=new Bean_History();
                bh.setPlain_Text(cursor.getString(cursor.getColumnIndex("Plain Text")));
                bh.setCipher_Method(cursor.getString(cursor.getColumnIndex("Cipher_Method")));
                bh.setCipher_Result(cursor.getString(cursor.getColumnIndex("Cipher_Result")));
                arrayhistory.add(bh);
            }while (cursor.moveToNext());
        }
        return arrayhistory;
    }
}