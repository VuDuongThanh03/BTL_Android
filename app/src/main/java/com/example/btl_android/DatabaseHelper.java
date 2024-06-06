package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.btl_android.them_hoc_phan.HocPhan;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "QuanLyHocTapCaNhan.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String TABLE_SINHVIEN = "SinhVien";
    private static final String TABLE_CONGVIEC = "CongViec";
    private static final String TABLE_TAIKHOAN = "TaiKhoan";
    private static final String TABLE_THOIKHOABIEU="THOIKHOABIEU";

    // Common column names
    private static final String COLUMN_ID = "id";

    // HocPhan table column names
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

    // ThoiKhoaBieu table column names
     private static final String COLUMN_MON = "mon";
    private static final String COLUMN_THU = "thu";
    private static final String COLUMN_NGAY = "ngay";
    private static final String COLUMN_GIANGVIEN = "giangvien";
    private static final String COLUMN_PHONG = "phong";
    private static final String COLUMN_TIET = "tiet";
    private static final String COLUMN_DIADIEM = "diadiem";

    public DatabaseHelper(@Nullable final Context context) {
        super(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        this.createTableSubjects(db);
        this.createTableSinhVien(db);
        this.createTableTaiKhoan(db);
        this.createTableCongViec(db);
        this.createTableThoiKhoaBieu(db);
    }
     private void createTableThoiKhoaBieu(final SQLiteDatabase db) {
        final String query="CREATE TABLE " + TABLE_THOIKHOABIEU+
                "("+  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MON + " TEXT,"
                + COLUMN_THU + " TEXT,"
                + COLUMN_NGAY + " TEXT,"
                + COLUMN_GIANGVIEN + " TEXT,"
                + COLUMN_PHONG + " TEXT,"
                + COLUMN_TIET + " TEXT,"
                + COLUMN_DIADIEM + " TEXT" + ")";
        db.execSQL(query);
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
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_THOIKHOABIEU);
        // Create tables again
        this.onCreate(db);
    }

    // CRUD operations for subjects
    public void addSubject(final HocPhan hocPhan) {
        final SQLiteDatabase db = getWritableDatabase();

        final ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, hocPhan.getName());
        values.put(DatabaseHelper.COLUMN_CODE, hocPhan.getCode());
        values.put(DatabaseHelper.COLUMN_CREDITS, hocPhan.getCredits());
        values.put(DatabaseHelper.COLUMN_SEMESTER, hocPhan.getSemester());

        db.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);

        db.close();
    }

    public List<HocPhan> getAllSubjects() {
        final List<HocPhan> hocPhanList = new ArrayList<>();
        final String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SUBJECTS;

        final SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                final HocPhan hocPhan = new HocPhan();
                hocPhan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                hocPhan.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
                hocPhan.setCode(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CODE)));
                hocPhan.setCredits(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CREDITS)));
                hocPhan.setSemester(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SEMESTER)));
                hocPhanList.add(hocPhan);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return hocPhanList;
    }
     void AddTimeTable(String mon,String thu,String ngay,String giangvien,String phong,String tiet,String diadiem)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues tb=new ContentValues();

        tb.put(COLUMN_MON,mon);
        tb.put(COLUMN_THU,thu);
        tb.put(COLUMN_NGAY,ngay);
        tb.put(COLUMN_GIANGVIEN,giangvien);
        tb.put(COLUMN_PHONG,phong);
        tb.put(COLUMN_TIET,tiet);
        tb.put(COLUMN_DIADIEM,diadiem);
        long result = db.insert(TABLE_THOIKHOABIEU,null,tb);
        if(result == -1)
        {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"Them Thanh Cong",Toast.LENGTH_SHORT).show();
        }

    }
    Cursor readAllData()
    {
        String query = "SELECT * FROM "+TABLE_THOIKHOABIEU;
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor=  null;
        if (db != null);
        {
           cursor= db.rawQuery(query,null);

        }
        return cursor;
    }
}
