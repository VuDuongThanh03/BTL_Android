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
                    "FOREIGN KEY (idHp) REFERENCES HocPhan(maHp)" +
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

            populateInitialData(db);
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

    private void populateInitialData(final SQLiteDatabase db) {
        db.execSQL("INSERT INTO ChuyenNganh (id, tenCn) VALUES (1, 'Computer Science'), (2, 'Physics'), (3, 'Chemistry'), " +
                "(4, 'Mathematics'), (5, 'Biology')");
        db.execSQL("INSERT INTO SinhVien (maSv, maCn, tenTk, matKhau, khoa) VALUES " +
                "('SV001', 1, 'student1', 'password1', 'Engineering'), " +
                "('SV002', 2, 'student2', 'password2', 'Science'), " +
                "('SV003', 3, 'student3', 'password3', 'Arts'), " +
                "('SV004', 4, 'student4', 'password4', 'Mathematics'), " +
                "('SV005', 5, 'student5', 'password5', 'Biology'), " +
                "('SV006', 1, 'student6', 'password6', 'Engineering'), " +
                "('SV007', 2, 'student7', 'password7', 'Science'), " +
                "('SV008', 3, 'student8', 'password8', 'Arts'), " +
                "('SV009', 4, 'student9', 'password9', 'Mathematics'), " +
                "('SV010', 5, 'student10', 'password10', 'Biology')");
        db.execSQL("INSERT INTO HocPhan (maHp, tenHp, soTinChi, soTietLyThuyet, soTietThucHanh, hocKy, hinhThucThi, heSo) VALUES " +
                "('HP001', 'Math 101', 3, 30, 15, 1, 'Written', 'A'), " +
                "('HP002', 'Physics 101', 4, 40, 20, 1, 'Written', 'B'), " +
                "('HP003', 'Chemistry 101', 3, 30, 15, 1, 'Written', 'C'), " +
                "('HP004', 'Biology 101', 4, 40, 20, 1, 'Written', 'A'), " +
                "('HP005', 'Computer Science 101', 3, 30, 15, 1, 'Written', 'B'), " +
                "('HP006', 'Math 102', 3, 30, 15, 2, 'Written', 'A'), " +
                "('HP007', 'Physics 102', 4, 40, 20, 2, 'Written', 'B'), " +
                "('HP008', 'Chemistry 102', 3, 30, 15, 2, 'Written', 'C'), " +
                "('HP009', 'Biology 102', 4, 40, 20, 2, 'Written', 'A'), " +
                "('HP010', 'Computer Science 102', 3, 30, 15, 2, 'Written', 'B')");
        db.execSQL("INSERT INTO KetQuaHocPhan (maHp, maSv, lop, tx1, tx2, giuaKy, cuoiKy, diemKiVong, hocKy, nam) VALUES " +
                "('HP001', 'SV001', 'Class1', 8.5, 9.0, 7.5, 8.0, 8.0, 1, 2023), " +
                "('HP002', 'SV002', 'Class2', 7.5, 8.0, 6.5, 7.0, 7.0, 1, 2023), " +
                "('HP003', 'SV003', 'Class3', 9.0, 9.5, 8.5, 9.0, 9.0, 1, 2023), " +
                "('HP008', 'SV003', 'Class8', 9.0, 9.5, 8.5, 9.0, 9.0, 1, 2023), " +
                "('HP004', 'SV004', 'Class4', 8.0, 8.5, 7.0, 8.5, 8.0, 1, 2023), " +
                ", 'HP005', 'SV005', 'Class5', 7.5, 8.0, 7.5, 7.5, 7.5, 1, 2023), " +
                "('HP006', 'SV006', 'Class6', 8.5, 9.0, 7.5, 8.0, 8.0, 2, 2023), " +
                "('HP007', 'SV007', 'Class7', 7.5, 8.0, 6.5, 7.0, 7.0, 2, 2023), " +
                "('HP008', 'SV008', 'Class8', 9.0, 9.5, 8.5, 9.0, 9.0, 2, 2023), " +
                "('HP009', 'SV009', 'Class9', 8.0, 8.5, 7.0, 8.5, 8.0, 2, 2023), " +
                "('HP010', 'SV010', 'Class10', 7.5, 8.0, 7.5, 7.5, 7.5, 2, 2023)");
        db.execSQL("INSERT INTO DiemDanh (maSv, idHp, ngay, vang, loai) VALUES " +
                "('SV001', 'HP001', '2023-05-01', 1, 0), " +
                "('SV002', 'HP002', '2023-05-02', 1, 1), " +
                "('SV003', 'HP003', '2023-05-03', 0, 0), " +
                "('SV004', 'HP004', '2023-05-04', 1, 0), " +
                "('SV005', 'HP005', '2023-05-05', 0, 1), " +
                "('SV006', 'HP006', '2023-05-06', 1, 0), " +
                "('SV007', 'HP007', '2023-05-07', 0, 1), " +
                "('SV008', 'HP008', '2023-05-08', 1, 0), " +
                "('SV009', 'HP009', '2023-05-09', 0, 1), " +
                "('SV010', 'HP010', '2023-05-10', 1, 0), " +
                "('SV001', 'HP006', '2023-05-11', 0, 0), " +
                "('SV002', 'HP007', '2023-05-12', 1, 1), " +
                "('SV003', 'HP008', '2023-05-13', 0, 0), " +
                "('SV004', 'HP009', '2023-05-14', 1, 0), " +
                "('SV005', 'HP010', '2023-05-15', 0, 1)");
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

    public List<HocPhan> getSubjectsBySemester(String tenTk, String hocKy) {
        List<HocPhan> hocPhanList = new ArrayList<>();
        String selectQuery = "SELECT * FROM HocPhan WHERE tenHp = ? AND hocKy = ?";
//                             "SELECT hp.maHp, hp.tenHp, hp.soTinChi, hp.soTietLyThuyet, hp.soTietThucHanh, " +
//                             "hp.hinhThucThi, kq.lop, kq.hocKy, hp.heSo, kq.tx1, kq.tx2, kq.giuaKy, " +
//                             "kq.cuoiKy, kq.diemKiVong, " +
//                             "SUM(CASE WHEN dd.vang = 1 AND dd.loai = 0 THEN 1 ELSE 0 END) AS vangLt, " +
//                             "SUM(CASE WHEN dd.vang = 1 AND dd.loai = 1 THEN 1 ELSE 0 END) AS vangTh " +
//                             "FROM KetQuaHocPhan kq " +
//                             "JOIN HocPhan hp ON hp.maHp = kq.maHp " +
//                             "JOIN SinhVien sv ON sv.maSv = kq.maSv " +
//                             "LEFT JOIN DiemDanh dd ON dd.idHp = kq.maHp AND dd.maSv = sv.maSv " +
//                             "WHERE sv.tenTk = ? AND kq.hocKy = ? " +
//                             "GROUP BY hp.maHp, hp.tenHp, hp.soTinChi, hp.soTietLyThuyet, hp.soTietThucHanh, " +
//                             "hp.hinhThucThi, kq.lop, kq.hocKy, hp.heSo, kq.tx1, kq.tx2, kq.giuaKy, kq.cuoiKy, kq.diemKiVong";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tenTk, hocKy});

        if (cursor.moveToFirst()) {
            do {
                HocPhan hocPhan = new HocPhan();
                hocPhan.setMaHp(cursor.getString(cursor.getColumnIndex("maHp")));
                hocPhan.setTenHp(cursor.getString(cursor.getColumnIndex("tenHp")));
                hocPhan.setSoTc(cursor.getInt(cursor.getColumnIndex("soTinChi")));
                hocPhan.setSoTietLt(cursor.getInt(cursor.getColumnIndex("soTietLyThuyet")));
                hocPhan.setSoTietTh(cursor.getInt(cursor.getColumnIndex("soTietThucHanh")));
                hocPhan.setHinhThucThi(cursor.getString(cursor.getColumnIndex("hinhThucThi")));
                hocPhan.setLop(cursor.getString(cursor.getColumnIndex("lop")));
                hocPhan.setHocKy(cursor.getInt(cursor.getColumnIndexOrThrow("hocKy")));
                hocPhan.setHeSo(cursor.getString(cursor.getColumnIndex("heSo")));
//                hocPhan.setTx1(cursor.getFloat(cursor.getColumnIndex("tx1")));
//                hocPhan.setTx2(cursor.getFloat(cursor.getColumnIndex("tx2")));
//                hocPhan.setGiuaKy(cursor.getFloat(cursor.getColumnIndex("giuaKy")));
//                hocPhan.setCuoiKy(cursor.getFloat(cursor.getColumnIndex("cuoiKy")));
//                hocPhan.setDiemKyVong(cursor.getFloat(cursor.getColumnIndex("diemKiVong")));
//                hocPhan.setVangLt(cursor.getInt(cursor.getColumnIndex("vangLt")));
//                hocPhan.setVangTh(cursor.getInt(cursor.getColumnIndex("vangTh")));
            } while (cursor.moveToNext());
        }
        return hocPhanList;
    }
}