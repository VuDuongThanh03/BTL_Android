package com.example.btl_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "QuanLyHocTapCaNhan.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    // SinhVien table
    private static final String CREATE_TABLE_SINHVIEN =
            "CREATE TABLE IF NOT EXISTS SinhVien (" +
                    "maSv TEXT NOT NULL," +
                    "maCn INTEGER NOT NULL," +
                    "tenTk INTEGER NOT NULL UNIQUE," +
                    "matKhau TEXT NOT NULL," +
                    "khoa TEXT NOT NULL," +
                    "PRIMARY KEY(maSv)," +
                    "FOREIGN KEY (maCn) REFERENCES ChuyenNganh(id)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    // CongViec table
    private static final String CREATE_TABLE_CONGVIEC =
            "CREATE TABLE IF NOT EXISTS CongViec (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idSv TEXT NOT NULL," +
                    "tenViec TEXT NOT NULL," +
                    "mucUuTien INTEGER," +
                    "thoiHan TEXT NOT NULL," +
                    "trangThai INTEGER NOT NULL," +
                    "chiTiet TEXT," +
                    "FOREIGN KEY (idSv) REFERENCES SinhVien(maSv)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    // ChuyenNganh table
    private static final String CREATE_TABLE_CHUYENNGANH =
            "CREATE TABLE IF NOT EXISTS ChuyenNganh (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tenCn TEXT NOT NULL" +
                    ");";

    // HocPhan table
    private static final String CREATE_TABLE_HOCPHAN =
            "CREATE TABLE IF NOT EXISTS HocPhan (" +
                    "maHp TEXT PRIMARY KEY," +
                    "tenHp TEXT NOT NULL," +
                    "soTinChi INTEGER NOT NULL," +
                    "soTietLyThuyet INTEGER NOT NULL," +
                    "soTietThucHanh INTEGER NOT NULL," +
                    "hocKy INTEGER NOT NULL," +
                    "hinhThucThi TEXT NOT NULL," +
                    "heSo TEXT NOT NULL" +
                    ");";

    // LoaiHocPhan table
    private static final String CREATE_TABLE_LOAIHOCPHAN =
            "CREATE TABLE IF NOT EXISTS LoaiHocPhan (" +
                    "maHp TEXT NOT NULL," +
                    "maCn INTEGER NOT NULL," +
                    "loai INTEGER NOT NULL," +
                    "PRIMARY KEY(maHp, maCn)," +
                    "FOREIGN KEY (maHp) REFERENCES HocPhan(maHp)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION," +
                    "FOREIGN KEY (maCn) REFERENCES ChuyenNganh(id)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    // KetQuaHocPhan table
    private static final String CREATE_TABLE_KETQUAHOCPHAN =
            "CREATE TABLE IF NOT EXISTS KetQuaHocPhan (" +
                    "maHp TEXT NOT NULL," +
                    "maSv TEXT NOT NULL," +
                    "lop TEXT NOT NULL," +
                    "tx1 REAL," +
                    "tx2 REAL," +
                    "giuaKy REAL," +
                    "cuoiKy REAL," +
                    "diemKiVong REAL," +
                    "hocKy INTEGER NOT NULL," +
                    "nam INTEGER NOT NULL," +
                    "PRIMARY KEY(maHp, maSv)," +
                    "FOREIGN KEY (maHp) REFERENCES HocPhan(maHp)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION," +
                    "FOREIGN KEY (maSv) REFERENCES SinhVien(maSv)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    // DiemDanh table
    private static final String CREATE_TABLE_DIEMDANH =
            "CREATE TABLE IF NOT EXISTS DiemDanh (" +
                    "maSv TEXT NOT NULL," +
                    "idHp TEXT NOT NULL," +
                    "ngay TEXT NOT NULL," +
                    "vang INTEGER," +
                    "loai INTEGER," +
                    "FOREIGN KEY (maSv) REFERENCES SinhVien(maSv)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION," +
                    "FOREIGN KEY (idHp) REFERENCES KetQuaHocPhan(lop)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    public DatabaseHelper(@Nullable final Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }
    @Override
    public void onCreate(final SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_SINHVIEN);
            db.execSQL(CREATE_TABLE_CONGVIEC);
            db.execSQL(CREATE_TABLE_CHUYENNGANH);
            db.execSQL(CREATE_TABLE_HOCPHAN);
            db.execSQL(CREATE_TABLE_LOAIHOCPHAN);
            db.execSQL(CREATE_TABLE_KETQUAHOCPHAN);
            db.execSQL(CREATE_TABLE_DIEMDANH);
        } catch (final Exception e) {
            Log.e("Error", "There are some problems in creating database");
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SinhVien");
        db.execSQL("DROP TABLE IF EXISTS CongViec");
        db.execSQL("DROP TABLE IF EXISTS ChuyenNganh");
        db.execSQL("DROP TABLE IF EXISTS HocPhan");
        db.execSQL("DROP TABLE IF EXISTS LoaiHocPhan");
        db.execSQL("DROP TABLE IF EXISTS KetQuaHocPhan");
        db.execSQL("DROP TABLE IF EXISTS DiemDanh");

        onCreate(db);
    }

    // CRUD operations for HocPhan
    public void addSubject(final HocPhan hocPhan) {
        final SQLiteDatabase db = getWritableDatabase();

        String sql = "INSERT INTO HocPhan (tenHp, maHp, soTinChiLyThuyet, hocKy) VALUES (?, ?, ?, ?)";

        db.execSQL(sql, new Object[]{
                hocPhan.getTenHp(),
                hocPhan.getMaHp(),
                hocPhan.getSoTietLt(),
                hocPhan.getHocKy()
        });

        db.close();
    }

    public List<HocPhan> getAllSubjects() {
        List<HocPhan> hocPhanList = new ArrayList<>();
        String selectQuery = "SELECT * FROM HocPhan";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HocPhan hocPhan = new HocPhan();
                hocPhan.setTenHp(cursor.getString(cursor.getColumnIndexOrThrow("tenHp")));
                hocPhan.setMaHp(cursor.getString(cursor.getColumnIndexOrThrow("maHp")));
                hocPhan.setSoTietLt(cursor.getInt(cursor.getColumnIndexOrThrow("soTinChiLyThuyet")));
                hocPhan.setHocKy(cursor.getInt(cursor.getColumnIndexOrThrow("hocKy")));
                hocPhanList.add(hocPhan);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return hocPhanList;
    }

    public List<HocPhan> getSubjectsByYear(String tenTk, String hocKy) {
        List<HocPhan> hocPhanList = new ArrayList<>();
        String selectQuery = "SELECT hp.maHp, hp.tenHp, hp.soTinChi, hp.soTietLyThuyet, hp.soTietThucHanh, " +
                "hp.hinhThucThi, kq.lop, kq.hocKy, hp.heSo, kq.tx1, kq.tx2, kq.giuaKy, " +
                "kq.cuoiKy, kq.diemKiVong, " +
                "SUM(CASE WHEN dd.vang = 1 AND dd.loai = 0 THEN 1 ELSE 0 END) AS vangLt, " +
                "SUM(CASE WHEN dd.vang = 1 AND dd.loai = 1 THEN 1 ELSE 0 END) AS vangTh " +
                "FROM KetQuaHocPhan kq " +
                "JOIN HocPhan hp ON hp.maHp = kq.maHp " +
                "JOIN SinhVien sv ON sv.maSv = kq.maSv " +
                "LEFT JOIN DiemDanh dd ON dd.idHp = kq.lop AND dd.maSv = sv.maSv " +
                "WHERE sv.tenTk = ? AND kq.hocKy = ? " +
                "GROUP BY hp.maHp, hp.tenHp, hp.soTinChi, hp.soTietLyThuyet, hp.soTietThucHanh, " +
                "hp.hinhThucThi, kq.lop, kq.hocKy, hp.heSo, kq.tx1, kq.tx2, kq.giuaKy, " +
                "kq.cuoiKy, kq.diemKiVong";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tenTk, hocKy});

        if (cursor.moveToFirst()) {
            do {
                HocPhan hocPhan = new HocPhan();
                hocPhan.setMaHp(cursor.getString(cursor.getColumnIndex("maHp")));
                hocPhan.setTenHp(cursor.getString(cursor.getColumnIndex("tenHp")));
                hocPhan.setSoTc(cursor.getInt(cursor.getColumnIndexOrThrow("soTc")));
                hocPhan.setSoTietLt(cursor.getInt(cursor.getColumnIndex("soTietLyThuyet")));
                hocPhan.setSoTietTh(cursor.getInt(cursor.getColumnIndex("soTietThucHanh")));
                hocPhan.setHinhThucThi(cursor.getString(cursor.getColumnIndex("hinhThucThi")));
                hocPhan.setLop(cursor.getString(cursor.getColumnIndex("lop")));
                hocPhan.setHocKy(cursor.getInt(cursor.getColumnIndexOrThrow("hocKy")));
                hocPhan.setHeSo(cursor.getString(cursor.getColumnIndex("heSo")));
                hocPhan.setTx1(cursor.getFloat(cursor.getColumnIndex("tx1")));
                hocPhan.setTx2(cursor.getFloat(cursor.getColumnIndex("tx2")));
                hocPhan.setGiuaKy(cursor.getFloat(cursor.getColumnIndex("giuaKy")));
                hocPhan.setCuoiKy(cursor.getFloat(cursor.getColumnIndex("cuoiKy")));
                hocPhan.setDiemKyVong(cursor.getFloat(cursor.getColumnIndex("diemKiVong")));
                hocPhan.setVangLt(cursor.getInt(cursor.getColumnIndex("vangLt")));
                hocPhan.setVangTh(cursor.getInt(cursor.getColumnIndex("vangTh")));
            } while (cursor.moveToNext());
        }
        return hocPhanList;
    }
}