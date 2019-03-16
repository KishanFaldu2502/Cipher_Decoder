package com.aswdc_cipherdicoder.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aswdc_cipherdicoder.bean.Bean_History;
import com.aswdc_cipherdicoder.util.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper_History extends SQLiteAssetHelper {
    public DBHelper_History(Context context) {
        super(context, Constant.DB_Name, null, Constant.DB_Version);
    }

    public void insert(Bean_History bh) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Plain_Text", bh.getPlain_Text());
        db.insert("Cipher_history", null, cv);
        db.close();
    }

    public int getCount() {
        SQLiteDatabase db=getReadableDatabase();
        String strQuery="select * from Cipher_history";
        Cursor cursor=db.rawQuery(strQuery,null);
        int a=cursor.getCount();
        db.close();
        return a;
    }
}
