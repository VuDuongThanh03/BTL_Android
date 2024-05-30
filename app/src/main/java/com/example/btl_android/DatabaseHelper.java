package com.example.btl_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "QuanLyHocTapCaNhan.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createtablecongvieccanlam(db);
    }

    private void createtablecongvieccanlam(SQLiteDatabase db) {
        try{
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
