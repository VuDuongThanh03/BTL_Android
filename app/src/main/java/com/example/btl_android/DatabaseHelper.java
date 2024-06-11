package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.btl_android.diem.Diem;
import com.example.btl_android.hoc_phan_du_kien.HocPhan;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SINHVIEN);
        db.execSQL(CREATE_TABLE_CONGVIEC);
        db.execSQL(CREATE_TABLE_CHUYENNGANH);
        db.execSQL(CREATE_TABLE_HOCPHAN);
        db.execSQL(CREATE_TABLE_LOAIHOCPHAN);
        db.execSQL(CREATE_TABLE_KETQUAHOCPHAN);
        db.execSQL(CREATE_TABLE_LICHHOC);

        populateInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SinhVien");
        db.execSQL("DROP TABLE IF EXISTS CongViec");
        db.execSQL("DROP TABLE IF EXISTS ChuyenNganh");
        db.execSQL("DROP TABLE IF EXISTS HocPhan");
        db.execSQL("DROP TABLE IF EXISTS LoaiHocPhan");
        db.execSQL("DROP TABLE IF EXISTS KetQuaHocPhan");
        db.execSQL("DROP TABLE IF EXISTS LichHoc");

        onCreate(db);
    }


    private void populateInitialData(final SQLiteDatabase db) {
        db.execSQL(INSERT_TABLE_SINHVIEN);
        db.execSQL(INSERT_TABLE_CONGVIEC);
        db.execSQL(INSERT_TABLE_CHUYENNGANH);
        db.execSQL(INSERT_TABLE_HOCPHAN);
        db.execSQL(INSERT_TABLE_LOAIHOCPHAN);
        db.execSQL(INSERT_TABLE_KETQUAHOCPHAN);
        db.execSQL(INSERT_TABLE_LICHHOC);
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

    public void insertHocPhan(HocPhan hocPhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHp", hocPhan.getMaHp());
        values.put("tenHp", hocPhan.getTenHp());
        values.put("soTietLyThuyet", hocPhan.getSoTietLt());
        values.put("soTietThucHanh", hocPhan.getSoTietTh());
        values.put("hocKy", hocPhan.getHocKy());
        values.put("hinhThucThi", hocPhan.getHinhThucThi());
        values.put("heSo", hocPhan.getHeSo());
    }

    public void updateHocPhan(HocPhan hocPhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenHp", hocPhan.getTenHp());
        values.put("soTietLyThuyet", hocPhan.getSoTietLt());
        values.put("soTietThucHanh", hocPhan.getSoTietTh());
        values.put("hocKy", hocPhan.getHocKy());
        values.put("hinhThucThi", hocPhan.getHinhThucThi());
        values.put("heSo", hocPhan.getHeSo());

        db.update("HocPhan", values, "maHp = ?", new String[]{hocPhan.getMaHp()});
    }

    public void deleteHocPhan(String maHp) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("HocPhan", "maHp=?", new String[]{maHp});
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to delete HocPhan");
        } else {
            Log.i("DatabaseHelper", "HocPhan deleted successfully");
        }
        db.close();
    }

    public boolean isMaHpUnique(String maHp) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HocPhan WHERE maHp = ?", new String[]{maHp});
        boolean isUnique = !cursor.moveToFirst();
        cursor.close();
        return isUnique;
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

    public boolean updateDiem(Diem diem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tx1", diem.getTx1());
        values.put("tx2", diem.getTx2());
        values.put("giuaKy", diem.getGiuaKy());
        values.put("diemKiVong", diem.getDiemKiVong());
        values.put("cuoiKy", diem.getCuoiKy());
        int res = db.update("KetQuaHocPhan", values, "maLop = ?", new String[]{diem.getMaLop()});
        return res > 0;
    }

    public void getAllScoreModules() {
        allDiemHpList.clear();
        String selectQuery = "SELECT kq.maLop, hp.maHp, hp.tenHp, hp.soTinChiLyThuyet, hp.soTinChiThucHanh, " +
                "hp.soTietLyThuyet, hp.soTietThucHanh, hp.hinhThucThi, hp.heSo, kq.hocKy, " +
                "kq.tx1, kq.tx2, kq.giuaKy, kq.cuoiKy, kq.diemKiVong, " +
                "SUM(CASE WHEN lh.vang = 1 AND lh.loaiTietHoc = 0 THEN 1 ELSE 0 END) AS vangLt, " +
                "SUM(CASE WHEN lh.vang = 1 AND lh.loaiTietHoc = 1 THEN 1 ELSE 0 END) AS vangTh " +
                "FROM KetQuaHocPhan kq " +
                "JOIN HocPhan hp ON hp.maHp = kq.maHp " +
                "LEFT JOIN LichHoc lh ON lh.maLop = kq.maLop " +
                "GROUP BY kq.maLop, kq.maHp, hp.tenHp, hp.soTietLyThuyet, hp.soTietThucHanh, " +
                "hp.hinhThucThi, hp.heSo, kq.hocKy, kq.tx1, kq.tx2, kq.giuaKy, kq.cuoiKy, kq.diemKiVong " +
                "ORDER BY hp.tenHp";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Diem diem = new Diem();

                diem.setMaLop(cursor.getString(cursor.getColumnIndex("maLop")));
                diem.setMaHp(cursor.getString(cursor.getColumnIndex("maHp")));
                diem.setTenHp(cursor.getString(cursor.getColumnIndex("tenHp")));
                diem.setSoTinChiLt(cursor.getFloat(cursor.getColumnIndex("soTinChiLyThuyet")));
                diem.setSoTinChiTh(cursor.getFloat(cursor.getColumnIndex("soTinChiThucHanh")));
                diem.setSoTietLt(cursor.getInt(cursor.getColumnIndex("soTietLyThuyet")));
                diem.setSoTietTh(cursor.getInt(cursor.getColumnIndex("soTietThucHanh")));
                diem.setHinhThucThi(cursor.getString(cursor.getColumnIndex("hinhThucThi")));
                diem.setHocKy(cursor.getInt(cursor.getColumnIndex("hocKy")));
                diem.setHeSo(cursor.getString(cursor.getColumnIndex("heSo")));
                diem.setTx1(cursor.isNull(cursor.getColumnIndex("tx1")) ? null : cursor.getFloat(cursor.getColumnIndex("tx1")));
                diem.setTx2(cursor.isNull(cursor.getColumnIndex("tx2")) ? null : cursor.getFloat(cursor.getColumnIndex("tx2")));
                diem.setGiuaKy(cursor.isNull(cursor.getColumnIndex("giuaKy")) ? null : cursor.getFloat(cursor.getColumnIndex("giuaKy")));
                diem.setCuoiKy(cursor.isNull(cursor.getColumnIndex("cuoiKy")) ? null : cursor.getFloat(cursor.getColumnIndex("cuoiKy")));
                diem.setDiemKiVong(cursor.isNull(cursor.getColumnIndex("diemKiVong")) ? null : cursor.getFloat(cursor.getColumnIndex("diemKiVong")));
                diem.setVangLt(cursor.getInt(cursor.getColumnIndex("vangLt")));
                diem.setVangTh(cursor.getInt(cursor.getColumnIndex("vangTh")));

                allDiemHpList.add(diem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    public List<Diem> getScoreModulesBySemester(String hocKyStr) {
        int hocKy = Integer.parseInt(hocKyStr);
        List<Diem> diemList = new ArrayList<>();
        for (Diem diem : allDiemHpList) {
            if (diem.getHocKy() == hocKy) diemList.add(diem);
        }
        return diemList;
    }

    // Database name and version
    private static final String DATABASE_NAME = "QuanLyHocTapCaNhan.db";
    private static final int DATABASE_VERSION = 1;

    public static List<Diem> allDiemHpList = new ArrayList<>();

    // SinhVien table
    private static final String CREATE_TABLE_SINHVIEN =
            "CREATE TABLE IF NOT EXISTS SinhVien (" +
                    "maSv TEXT NOT NULL," +
                    "maCn INTEGER NOT NULL," +
                    "tenSv TEXT NOT NULL," +
                    "tenTk INTEGER NOT NULL," +
                    "matKhau TEXT NOT NULL," +
                    "PRIMARY KEY(maSv)," +
                    "FOREIGN KEY (maCn) REFERENCES ChuyenNganh(id)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    // CongViec table
    private static final String CREATE_TABLE_CONGVIEC =
            "CREATE TABLE IF NOT EXISTS CongViec (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tenViec TEXT NOT NULL," +
                    "mucUuTien INTEGER," +
                    "thoiHan TEXT NOT NULL," +
                    "trangThai INTEGER NOT NULL," +
                    "chiTiet TEXT" +
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
                    "soTinChiLyThuyet REAL NOT NULL," +
                    "soTinChiThucHanh REAL NOT NULL," +
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
                    "maLop TEXT NOT NULL," +
                    "maHp TEXT NOT NULL," +
                    "tx1 REAL," +
                    "tx2 REAL," +
                    "giuaKy REAL," +
                    "cuoiKy REAL," +
                    "diemKiVong REAL," +
                    "hocKy INTEGER NOT NULL," +
                    "PRIMARY KEY(maLop)," +
                    "FOREIGN KEY (maHp) REFERENCES HocPhan(maHp)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    // LichHoc table
    private static final String CREATE_TABLE_LICHHOC =
            "CREATE TABLE IF NOT EXISTS LichHoc (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "maLop TEXT NOT NULL, " +
                    "tenHP TEXT NOT NULL, " +
                    "thu TEXT NOT NULL, " +
                    "ngay TEXT NOT NULL, " +
                    "phong INTEGER NOT NULL, " +
                    "giangVien TEXT NOT NULL, " +
                    "tiet TEXT NOT NULL, " +
                    "diaDiem TEXT NOT NULL, " +
                    "loaiTietHoc INTEGER NOT NULL, " +
                    "vang INTEGER, " +
                    "FOREIGN KEY(maLop) REFERENCES KetQuaHocPhan(maLop) " +
                    "ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    private static final String INSERT_TABLE_SINHVIEN =
            "INSERT INTO SinhVien (maSv, maCn, tenSv, tenTk, matKhau) VALUES " +
                    "('2021606516', 3, 'Phùng Đức Cần', 'Tao là nhất', 'abc123!@#')";

    private static final String INSERT_TABLE_CONGVIEC =
            "INSERT INTO CongViec (id, tenViec, mucUuTien, thoiHan, trangThai, chiTiet) VALUES " +
                    "(1, 'Complete Math Homework', 1, '2023-06-10', 0, 'Chapter 1-3 exercises'), " +
                    "(2, 'Prepare Physics Presentation', 2, '2023-06-12', 0, 'Presentation on Quantum Mechanics'), " +
                    "(3, 'Chemistry Lab Report', 1, '2023-06-14', 0, 'Lab report on chemical reactions'), " +
                    "(4, 'Biology Field Trip', 3, '2023-06-16', 1, 'Field trip to the botanical garden'), " +
                    "(5, 'Computer Science Project', 1, '2023-06-18', 0, 'Project on data structures'), " +
                    "(6, 'Math Quiz Preparation', 2, '2023-06-20', 0, 'Prepare for upcoming quiz'), " +
                    "(7, 'Physics Assignment', 1, '2023-06-22', 0, 'Complete assignments from chapter 4'), " +
                    "(8, 'Chemistry Homework', 2, '2023-06-24', 0, 'Solve problems from the textbook'), " +
                    "(9, 'Biology Research', 3, '2023-06-26', 1, 'Research on genetic mutations'), " +
                    "(10, 'Computer Science Exam', 1, '2023-06-28', 0, 'Study for final exam');";

    private static final String INSERT_TABLE_CHUYENNGANH =
            "INSERT INTO ChuyenNganh (id, tenCn) VALUES " +
                    "(1, 'Computer Science'), " +
                    "(2, 'Physics'), " +
                    "(3, 'CNTT'), " +
                    "(4, 'Mathematics'), " +
                    "(5, 'Biology');";

    private static final String INSERT_TABLE_HOCPHAN =
            "INSERT INTO HocPhan (maHp, tenHp, soTinChiLyThuyet, soTinChiThucHanh, soTietLyThuyet, soTietThucHanh, hocKy, hinhThucThi, heSo) VALUES " +
                    "('HP001', 'Math 101', 2, 1, 30, 15, 1, 'Written', '15-15-70'), " +
                    "('HP002', 'Physics 101', 2.5, 0.5, 40, 20, 1, 'Written', '20-30-50'), " +
                    "('HP003', 'Chemistry 101', 3, 0, 30, 15, 1, 'Written', '15-15-70'), " +
                    "('HP004', 'Biology 101', 4, 1, 40, 20, 1, 'Written', '20-20-60'), " +
                    "('HP005', 'Computer Science 101', 0, 3, 30, 15, 1, 'Written', '10-10-20-60'), " +
                    "('HP006', 'Math 102', 2, 1, 30, 15, 2, 'Written', '15-15-70'), " +
                    "('HP007', 'Physics 102', 1, 2, 40, 20, 2, 'Written', '20-20-60'), " +
                    "('HP008', 'Chemistry 102', 3, 0, 30, 15, 2, 'Written', '10-10-20-60'), " +
                    "('HP009', 'Biology 102', 2, 1, 40, 20, 2, 'Written', '20-30-50'), " +
                    "('HP010', 'Computer Science 102', 1.5, 1.5, 30, 15, 2, 'Written', '20-20-60');";

    private static final String INSERT_TABLE_LOAIHOCPHAN =
            "INSERT INTO LoaiHocPhan (maHp, maCn, loai) VALUES " +
                    "('HP001', 1, 1), " +
                    "('HP002', 2, 2), " +
                    "('HP003', 3, 1), " +
                    "('HP004', 4, 2), " +
                    "('HP005', 1, 1), " +
                    "('HP006', 1, 2), " +
                    "('HP007', 2, 1), " +
                    "('HP008', 3, 2), " +
                    "('HP009', 4, 1), " +
                    "('HP010', 1, 2);";

    private static final String INSERT_TABLE_KETQUAHOCPHAN =
            "INSERT INTO KetQuaHocPhan (maLop, maHp, tx1, tx2, giuaKy, cuoiKy, diemKiVong, hocKy) VALUES " +
                    "('2021HP003.1', 'HP003', 8.5, 9.0, null, null, null, 1), " +
                    "('2021HP002.3', 'HP002', 7.5, 8.0, 6.5, null, 7.0, 1), " +
                    "('2021HP001.3', 'HP001', 3.5, 2.5, null, 5.0, null, 1), " +
                    "('2021HP008.1', 'HP008', 9.0, 9.5, 8.5, 9.0, 9.0, 1), " +
                    "('2021HP004.6', 'HP004', 8.0, 8.5, null, null, null, 1), " +
                    "('2021HP005.9', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 2), " +
                    "('2021HP006.1', 'HP006', 8.5, 9.0, null, null, 8.0, 2), " +
                    "('2021HP007.3', 'HP007', 7.5, 8.0, null, null, 7.0, 2), " +
                    "('2021HP002.2', 'HP002', 9.0, 9.5, 8.5, 9.0, 9.0, 2), " +
                    "('2021HP009.4', 'HP009', 8.0, 8.5, 7.0, null, null, 2), " +
                    "('2021HP001.2', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 2), " +
                    "('2021HP004.5', 'HP004', 8.0, 8.5, null, null, null, 2), " +
                    "('2022HP005.1', 'HP005', 7.5, 6.0, 7.5, 7.5, null, 3), " +
                    "('2022HP006.1', 'HP006', 4.5, 9.0, null, null, 8.0, 3), " +
                    "('2022HP007.3', 'HP007', 8.5, 8.0, null, null, 7.0, 3), " +
                    "('2022HP002.2', 'HP002', 9.0, 3.5, 8.5, 9.0, 9.0, 3), " +
                    "('2022HP009.4', 'HP009', 8.0, 6.5, 7.0, null, null, 3), " +
                    "('2022HP001.2', 'HP001', 7.5, 7.0, null, 7.5, 7.5, 3), " +
                    "('2022HP004.5', 'HP004', 5.0, 8.5, null, null, null, 3), " +
                    "('2022HP005.9', 'HP005', 7.5, 8.0, 8.5, 4.5, null, 4), " +
                    "('2022HP006.3', 'HP006', 8.5, 9.0, null, null, 8.0, 4), " +
                    "('2022HP007.4', 'HP007', 7.5, 8.0, null, null, 7.0, 4), " +
                    "('2022HP001.1', 'HP001', 9.0, 9.5, 8.5, 9.0, 9.0, 4), " +
                    "('2022HP009.7', 'HP009', 8.0, 8.5, 7.0, null, null, 4), " +
                    "('2022HP001.3', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 4), " +
                    "('2022HP003.3', 'HP003', 8.0, 8.5, null, null, null, 4), " +
                    "('2023HP005.9', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 5), " +
                    "('2023HP006.1', 'HP006', 8.5, 9.0, null, null, 8.0, 5), " +
                    "('2023HP008.3', 'HP008', 7.5, 8.0, null, null, 7.0, 5), " +
                    "('2023HP002.2', 'HP002', 9.0, 9.5, 8.5, 9.0, 9.0, 5), " +
                    "('2023HP009.4', 'HP009', 8.0, 8.5, 7.0, null, null, 5), " +
                    "('2023HP001.2', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 5), " +
                    "('2023HP006.5', 'HP006', 8.0, 8.5, null, null, null, 5), " +
                    "('2023HP005.1', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 6), " +
                    "('2023HP006.4', 'HP006', 8.5, 9.0, null, null, 8.0, 6), " +
                    "('2023HP007.2', 'HP007', 7.5, 8.0, null, null, 7.0, 6), " +
                    "('2023HP002.7', 'HP002', 9.0, 9.5, 8.5, 9.0, 9.0, 6), " +
                    "('2023HP009.9', 'HP009', 8.0, 8.5, 7.0, null, null, 6), " +
                    "('2023HP001.3', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 6), " +
                    "('2023HP004.4', 'HP004', 8.0, 8.5, null, null, null, 6), " +
                    "('2024HP005.9', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 7), " +
                    "('2024HP006.1', 'HP006', 8.5, 9.0, null, null, 8.0, 7), " +
                    "('2024HP007.3', 'HP007', 7.5, 8.0, null, null, 7.0, 7), " +
                    "('2024HP002.2', 'HP002', 9.0, 9.5, 8.5, 9.0, 9.0, 7), " +
                    "('2024HP009.4', 'HP009', 8.0, 8.5, 7.0, null, null, 7), " +
                    "('2024HP001.2', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 7), " +
                    "('2024HP007.5', 'HP007', 8.0, 8.5, null, null, null, 7), " +
                    "('2025HP005.9', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 8), " +
                    "('2025HP006.1', 'HP006', 8.5, 9.0, null, null, 8.0, 8), " +
                    "('2025HP007.3', 'HP007', 7.5, 8.0, null, null, 7.0, 8), " +
                    "('2025HP002.2', 'HP002', 9.0, 9.5, 8.5, 9.0, 9.0, 8), " +
                    "('2025HP009.4', 'HP009', 8.0, 8.5, 7.0, null, null, 8), " +
                    "('2025HP001.2', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 8), " +
                    "('2025HP004.5', 'HP004', 8.0, 8.5, null, null, null, 8)";

    private static final String INSERT_TABLE_LICHHOC =
            "INSERT INTO LichHoc " +
                    "(id, maLop, tenHP, thu, ngay, phong, giangVien, tiet, diaDiem, loaiTietHoc, vang) " +
                    "VALUES " +
                    "(9, '2021HP003.1', 'Chemistry 101', 'Thursday', '2023-06-20', 109, 'Olivia Taylor', '17-18', 'Room I', 1, 0), " +
                    "(10, '2021HP002.3', 'Physics 101', 'Friday', '2023-06-21', 110, 'James Anderson', '19-20', 'Room J', 2, 1), " +
                    "(11, '2021HP003.3', 'Math 101', 'Monday', '2023-06-24', 111, 'Emma Garcia', '21-22', 'Room K', 1, 0), " +
                    "(12, '2021HP003.1', 'Chemistry 101', 'Tuesday', '2023-06-25', 112, 'William Hernandez', '23-24', 'Room L', 2, 0), " +
                    "(13, '2021HP002.3', 'Physics 101', 'Wednesday', '2023-06-26', 113, 'Isabella Martinez', '25-26', 'Room M', 1, 1), " +
                    "(14, '2021HP003.3', 'Math 101', 'Thursday', '2023-06-27', 114, 'Ethan Phillips', '27-28', 'Room N', 2, 0), " +
                    "(15, '2021HP003.1', 'Chemistry 101', 'Friday', '2023-06-28', 115, 'Amelia Brown', '29-30', 'Room O', 1, 0), " +
                    "(16, '2021HP002.3', 'Physics 101', 'Monday', '2023-07-01', 116, 'Benjamin Davis', '31-32', 'Room P', 2, 1), " +
                    "(17, '2021HP003.3', 'Math 101', 'Tuesday', '2023-07-02', 117, 'Mia Miller', '33-34', 'Room Q', 1, 0), " +
                    "(18, '2021HP003.1', 'Chemistry 101', 'Wednesday', '2023-07-03', 118, 'Logan Wilson', '35-36', 'Room R', 2, 0), " +
                    "(19, '2021HP002.3', 'Physics 101', 'Thursday', '2023-07-04', 119, 'Harper Garcia', '37-38', 'Room S', 1, 1), " +
                    "(20, '2021HP003.3', 'Math 101', 'Friday', '2023-07-05', 120, 'Evelyn Rodriguez', '39-40', 'Room T', 2, 0);";
}
