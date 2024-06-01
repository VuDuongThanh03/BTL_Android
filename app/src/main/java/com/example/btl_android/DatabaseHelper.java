package com.example.btl_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "QuanLyHocTapCaNhan.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createtablesinhvien(db);
        createtabletaikhoan(db);
        createtablecongviec(db);
    }

    private void createtablesinhvien(SQLiteDatabase db) {
        try{
            String tblsinhvien = "CREATE TABLE SinhVien" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "maSV TEXT NOT NULL," +
                    "khoa TEXT," +
                    "idTk INTEGER NOT NULL," +
                    "maChuyenNganh INTEGER NOT NULL," +
                    "FOREIGN KEY (idTk) REFERENCES TaiKhoan(id));";
            db.execSQL(tblsinhvien);
        }catch (Exception e){
            Log.e("Error","There are some problem!");
        }
    }

    private void createtablecongviec(SQLiteDatabase db) {
        try{
            String tblcongviec = "CREATE TABLE CongViec" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tenViec TEXT NOT NULL," +
                    "chiTiet TEXT," +
                    "mucUuTien INTEGER," +
                    "thoiHan TEXT NOT NULL," +
                    "trangThai INTEGER NOT NULL," +
                    "idSv INTEGER NOT NULL," +
                    "FOREIGN KEY (idSv) REFERENCES SinhVien(id)" +
                    ");";
            db.execSQL(tblcongviec);
        }catch (Exception e){
            Log.e("Error","There are some problem!");
        }
    }

    private void createtabletaikhoan(SQLiteDatabase db) {
        try{
            String tbltaikhoan = "CREATE TABLE TaiKhoan" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "taiKhoan TEXT NOT NULL," +
                    "matKhau TEXT NOT NULL," +
                    "hoTen TEXT NOT NULL" +
                    ");";
            db.execSQL(tbltaikhoan);
        }catch (Exception e){
            Log.e("Error","There are some problem!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
