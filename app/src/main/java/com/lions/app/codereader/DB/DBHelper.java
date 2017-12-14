package com.lions.app.codereader.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Panwar on 14/12/17.
 */

public class DBHelper  extends SQLiteOpenHelper {

    public static final String name = "UncleDB";
    public static final int version = 1;

    public DBHelper(Context context) {
        super(context, name, null, version);
    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE UserDetails (UID INTEGER PRIMARY KEY AUTOINCREMENT, NAME text, EMAIL text, PHONE text, CODE text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean InitFilter()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM UserDetails");
        Log.d("filter ","deleted");
        return true;
    }



    public HashMap<String, String> GetDetailsbyCode(String code)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from UserDetails where CODE = '"+code+"'",null);
        res.moveToFirst();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("email",res.getString(res.getColumnIndex("EMAIL")));
        map.put("phone",res.getString(res.getColumnIndex("PHONE")));
        map.put("name",res.getString(res.getColumnIndex("NAME")));
        map.put("code",res.getString(res.getColumnIndex("CODE")));

        return map;
    }

    public void InsertDetails(String name, String email, String phone, String code)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("EMAIL",email);
        contentValues.put("CODE",code);
        contentValues.put("PHONE",phone);
        long row = db.insertWithOnConflict("UserDetails",null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        Log.d(" filter inserted",""+row);
    }


}
