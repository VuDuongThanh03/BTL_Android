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

    public DatabaseHelper(@Nullable final Context context) {
        super(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        this.createTableSubjects(db);
        this.createTableSinhVien(db);
        this.createTableTaiKhoan(db);
        this.createTableCongViec(db);
    }

    private void createTableSubjects(final SQLiteDatabase db) {
        final String createTable = "CREATE TABLE " + DatabaseHelper.TABLE_SUBJECTS + "("
                + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseHelper.COLUMN_NAME + " TEXT,"
                + DatabaseHelper.COLUMN_CODE + " TEXT,"
                + DatabaseHelper.COLUMN_CREDITS + " INTEGER,"
                + DatabaseHelper.COLUMN_SEMESTER + " TEXT" + ")";
        db.execSQL(createTable);
    }

    private void createTableSinhVien(final SQLiteDatabase db) {
        try {
            final String createTable = "CREATE TABLE " + DatabaseHelper.TABLE_SINHVIEN + "("
                    + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DatabaseHelper.COLUMN_MA_SINHVIEN + " TEXT NOT NULL,"
                    + DatabaseHelper.COLUMN_KHOA + " TEXT,"
                    + DatabaseHelper.COLUMN_ID_TK + " INTEGER NOT NULL,"
                    + DatabaseHelper.COLUMN_MA_CHUYENNGANH + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + DatabaseHelper.COLUMN_ID_TK + ") REFERENCES " + DatabaseHelper.TABLE_TAIKHOAN + "(" + DatabaseHelper.COLUMN_ID + ")" + ")";
            db.execSQL(createTable);
        } catch (final Exception e) {
            Log.e("Error", "There are some problems in createTableSinhVien!");
        }
    }

    private void createTableTaiKhoan(final SQLiteDatabase db) {
        try {
            final String createTable = "CREATE TABLE " + DatabaseHelper.TABLE_TAIKHOAN + "("
                    + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DatabaseHelper.COLUMN_TAIKHOAN + " TEXT NOT NULL,"
                    + DatabaseHelper.COLUMN_MATKHAU + " TEXT NOT NULL,"
                    + DatabaseHelper.COLUMN_HOTEN + " TEXT NOT NULL" + ")";
            db.execSQL(createTable);
        } catch (final Exception e) {
            Log.e("Error", "There are some problems in createTableTaiKhoan!");
        }
    }

    private void createTableCongViec(final SQLiteDatabase db) {
        try {
            final String createTable = "CREATE TABLE " + DatabaseHelper.TABLE_CONGVIEC + "("
                    + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DatabaseHelper.COLUMN_TEN_VIEC + " TEXT NOT NULL,"
                    + DatabaseHelper.COLUMN_CHITIET + " TEXT,"
                    + DatabaseHelper.COLUMN_MUC_UUTIEN + " INTEGER,"
                    + DatabaseHelper.COLUMN_THOIHAN + " TEXT NOT NULL,"
                    + DatabaseHelper.COLUMN_TRANGTHAI + " INTEGER NOT NULL,"
                    + DatabaseHelper.COLUMN_ID_SV + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + DatabaseHelper.COLUMN_ID_SV + ") REFERENCES " + DatabaseHelper.TABLE_SINHVIEN + "(" + DatabaseHelper.COLUMN_ID + ")"
                    + ")";
            db.execSQL(createTable);
        } catch (final Exception e) {
            Log.e("Error", "There are some problems in createTableCongViec!");
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_SINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_CONGVIEC);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_TAIKHOAN);

        // Create tables again
        this.onCreate(db);
    }

    // CRUD operations for subjects
    public void addSubject(final Subject subject) {
        final SQLiteDatabase db = getWritableDatabase();

        final ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, subject.getName());
        values.put(DatabaseHelper.COLUMN_CODE, subject.getCode());
        values.put(DatabaseHelper.COLUMN_CREDITS, subject.getCredits());
        values.put(DatabaseHelper.COLUMN_SEMESTER, subject.getSemester());

        db.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);

        db.close();
    }

    public List<Subject> getAllSubjects() {
        final List<Subject> subjectList = new ArrayList<>();
        final String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SUBJECTS;

        final SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                final Subject subject = new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                subject.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
                subject.setCode(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CODE)));
                subject.setCredits(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CREDITS)));
                subject.setSemester(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEMESTER)));
                subjectList.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return subjectList;
    }
}