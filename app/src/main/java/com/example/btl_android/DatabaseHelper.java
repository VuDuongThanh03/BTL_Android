package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.btl_android.add_subject.Subject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "QuanLyHocTapCaNhan.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String TABLE_SINHVIEN = "SinhVien";
    private static final String TABLE_CONGVIEC = "CongViec";
    private static final String TABLE_TAIKHOAN = "TaiKhoan";

    // Common column names
    private static final String COLUMN_ID = "id";

    // Subject table column names
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_CREDITS = "credits";
    private static final String COLUMN_SEMESTER = "semester";

    // SinhVien table column names
    private static final String COLUMN_MA_SINHVIEN = "maSV";
    private static final String COLUMN_KHOA = "khoa";
    private static final String COLUMN_ID_TK = "idTk";
    private static final String COLUMN_MA_CHUYENNGANH = "maChuyenNganh";

    // CongViec table column names
    private static final String COLUMN_TEN_VIEC = "tenViec";
    private static final String COLUMN_CHITIET = "chiTiet";
    private static final String COLUMN_MUC_UUTIEN = "mucUuTien";
    private static final String COLUMN_THOIHAN = "thoiHan";
    private static final String COLUMN_TRANGTHAI = "trangThai";
    private static final String COLUMN_ID_SV = "idSv";

    // TaiKhoan table column names
    private static final String COLUMN_TAIKHOAN = "taiKhoan";
    private static final String COLUMN_MATKHAU = "matKhau";
    private static final String COLUMN_HOTEN = "hoTen";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableSubjects(db);
        createTableSinhVien(db);
        createTableTaiKhoan(db);
        createTableCongViec(db);
    }

    private void createTableSubjects(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SUBJECTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CODE + " TEXT,"
                + COLUMN_CREDITS + " INTEGER,"
                + COLUMN_SEMESTER + " TEXT" + ")";
        db.execSQL(createTable);
    }

    private void createTableSinhVien(SQLiteDatabase db) {
        try {
            String createTable = "CREATE TABLE " + TABLE_SINHVIEN + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_MA_SINHVIEN + " TEXT NOT NULL,"
                    + COLUMN_KHOA + " TEXT,"
                    + COLUMN_ID_TK + " INTEGER NOT NULL,"
                    + COLUMN_MA_CHUYENNGANH + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + COLUMN_ID_TK + ") REFERENCES " + TABLE_TAIKHOAN + "(" + COLUMN_ID + ")" + ")";
            db.execSQL(createTable);
        } catch (Exception e) {
            Log.e("Error", "There are some problems in createTableSinhVien!");
        }
    }

    private void createTableTaiKhoan(SQLiteDatabase db) {
        try {
            String createTable = "CREATE TABLE " + TABLE_TAIKHOAN + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TAIKHOAN + " TEXT NOT NULL,"
                    + COLUMN_MATKHAU + " TEXT NOT NULL,"
                    + COLUMN_HOTEN + " TEXT NOT NULL" + ")";
            db.execSQL(createTable);
        } catch (Exception e) {
            Log.e("Error", "There are some problems in createTableTaiKhoan!");
        }
    }

    private void createTableCongViec(SQLiteDatabase db) {
        try {
            String createTable = "CREATE TABLE " + TABLE_CONGVIEC + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TEN_VIEC + " TEXT NOT NULL,"
                    + COLUMN_CHITIET + " TEXT,"
                    + COLUMN_MUC_UUTIEN + " INTEGER,"
                    + COLUMN_THOIHAN + " TEXT NOT NULL,"
                    + COLUMN_TRANGTHAI + " INTEGER NOT NULL,"
                    + COLUMN_ID_SV + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + COLUMN_ID_SV + ") REFERENCES " + TABLE_SINHVIEN + "(" + COLUMN_ID + ")"
                    + ")";
            db.execSQL(createTable);
        } catch (Exception e) {
            Log.e("Error", "There are some problems in createTableCongViec!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONGVIEC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAIKHOAN);

        // Create tables again
        onCreate(db);
    }

    // CRUD operations for subjects
    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, subject.getName());
        values.put(COLUMN_CODE, subject.getCode());
        values.put(COLUMN_CREDITS, subject.getCredits());
        values.put(COLUMN_SEMESTER, subject.getSemester());

        db.insert(TABLE_SUBJECTS, null, values);

        db.close();
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjectList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SUBJECTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                subject.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                subject.setCode(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CODE)));
                subject.setCredits(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CREDITS)));
                subject.setSemester(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEMESTER)));
                subjectList.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return subjectList;
    }
}