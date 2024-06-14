package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.btl_android.cong_viec.CongViec;
import com.example.btl_android.diem.Diem;
import com.example.btl_android.hoc_phan_du_kien.HocPhan;
import com.example.btl_android.thong_bao.ThongBao;

import java.util.ArrayList;
import java.util.List;

/**
 * @noinspection ALL
 */
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
        db.execSQL(CREATE_TABLE_THONGBAO);

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
        db.execSQL("DROP TABLE IF EXISTS ThongBao");
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
        db.insert("HocPhan", null, values);
        db.close();
    }

    public boolean addHocPhan(HocPhan hocPhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHp", hocPhan.getMaHp());
        values.put("tenHp", hocPhan.getTenHp());
        values.put("soTinChiLyThuyet", hocPhan.getSoTinChiLt());
        values.put("soTinChiThucHanh", hocPhan.getSoTinChiTh());
        values.put("soTietLyThuyet", hocPhan.getSoTietLt());
        values.put("soTietThucHanh", hocPhan.getSoTietTh());
        values.put("hocKy", hocPhan.getHocKy());
        values.put("hinhThucThi", hocPhan.getHinhThucThi());
        values.put("heSo", hocPhan.getHeSo());

        // Thêm học phần vào bảng HocPhan
        long result = db.insert("HocPhan", null, values);
        db.close(); // Đóng cơ sở dữ liệu sau khi hoàn thành

        // Kiểm tra kết quả thêm mới
        return result != -1; // Nếu result khác -1 thì thêm mới thành công
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
        db.close();
    }

    public void deleteHocPhan(String maHp) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("HocPhan", "maHp=?", new String[]{maHp});
        if (result == -1) {
            Log.e("DatabaseHelper", "Xóa học phần thất bại");
        } else {
            Log.i("DatabaseHelper", "Xóa học phần thành công");
        }
        db.close();
    }

    public boolean isMaHpUnique(String maHp) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HocPhan WHERE maHp = ?", new String[]{maHp});
        boolean isUnique = !cursor.moveToFirst();
        cursor.close();
        db.close();
        return isUnique;
    }

    public List<HocPhan> getAllHocPhan() {
        List<HocPhan> hocPhanList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HocPhan", null);

        if (cursor.moveToFirst()) {
            do {
                HocPhan hocPhan = new HocPhan(
                        cursor.getString(cursor.getColumnIndexOrThrow("maHp")),
                        cursor.getString(cursor.getColumnIndexOrThrow("tenHp")),
                        cursor.getFloat(cursor.getColumnIndexOrThrow("soTinChiLyThuyet")),
                        cursor.getFloat(cursor.getColumnIndexOrThrow("soTinChiThucHanh")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soTietLyThuyet")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soTietThucHanh")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("hocKy")),
                        cursor.getString(cursor.getColumnIndexOrThrow("hinhThucThi")),
                        cursor.getString(cursor.getColumnIndexOrThrow("heSo"))
                );
                hocPhanList.add(hocPhan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hocPhanList;
    }

    public void insertLichHoc(String maLop, String thu, String ngay, String giangVien, String phong, String tiet, String diaDiem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues tb = new ContentValues();

        tb.put("maLop", maLop);
        tb.put("thu", thu);
        tb.put("ngay", ngay);
        tb.put("giangVien", giangVien);
        tb.put("phong", phong);
        tb.put("tiet", tiet);
        tb.put("diaDiem", diaDiem);
        tb.put("loaiTietHoc", 0);
        tb.put("vang", 0);
        long result = db.insert("LichHoc", null, tb);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Thanh Cong", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getLichHoc() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM LichHoc";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor searchLichHoc(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM LichHoc WHERE maLop LIKE ? OR phong LIKE ?";
        String[] selectionArgs = {"%" + keyword + "%", "%" + keyword + "%"};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        return cursor;
    }

    public boolean updateDataTime(Context context, int row_id, String maLop, String thu, String ngay, String giangVien, String phong, String tiet, String diaDiem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maLop", maLop);
        values.put("thu", thu);
        values.put("ngay", ngay);
        values.put("giangVien", giangVien);
        values.put("phong", phong);
        values.put("tiet", tiet);
        values.put("diaDiem", diaDiem);
        values.put("loaiTietHoc", 0);
        values.put("vang", 0);

        int result = db.update("LichHoc", values, "id = ?", new String[]{String.valueOf(row_id)});
        db.close();

        if (result > 0) {
            Log.d("updateDataTime", "Update Successful for row_id: " + row_id);
            Toast.makeText(context, "Cập Nhật Thành Công !!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Log.d("updateDataTime", "Update Failed for row_id: " + row_id);
            Toast.makeText(context, "Cập Nhật Không Thành Công !!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteLichHoc(int row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("LichHoc", "id=?", new String[]{String.valueOf(row_id)});
        db.close();
        return result > 0;
    }


    public boolean updateDiem(Diem diem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tx1", diem.getTx1());
        values.put("tx2", diem.getTx2());
        values.put("giuaKy", diem.getGiuaKy());
        values.put("diemKiVong", diem.getDiemKiVong());
        values.put("cuoiKy", diem.getCuoiKy());
        long res = db.update("KetQuaHocPhan", values, "maLop = ?", new String[]{diem.getMaLop()});
        db.close();
        return res > 0;
    }

    public void getDiemHp() {
        allDiemHpList.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "";
        Cursor cursor;

        query = "SELECT maCn FROM SinhVien";
        cursor = db.rawQuery(query, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("maCn"));
        }

        query = "SELECT kq.maLop, hp.maHp, hp.tenHp, lhp.loai, hp.soTinChiLyThuyet, hp.soTinChiThucHanh, " +
                "hp.soTietLyThuyet, hp.soTietThucHanh, hp.hinhThucThi, hp.heSo, kq.hocKy, " +
                "kq.tx1, kq.tx2, kq.giuaKy, kq.cuoiKy, kq.diemKiVong, " +
                "SUM(CASE WHEN lh.vang = 1 AND lh.loaiTietHoc = 0 THEN 1 ELSE 0 END) AS vangLt, " +
                "SUM(CASE WHEN lh.vang = 1 AND lh.loaiTietHoc = 1 THEN 1 ELSE 0 END) AS vangTh " +
                "FROM KetQuaHocPhan kq " +
                "LEFT JOIN HocPhan hp ON hp.maHp = kq.maHp " +
                "LEFT JOIN LoaiHocPhan lhp ON lhp.maHp = hp.maHp " +
                "LEFT JOIN LichHoc lh ON lh.maLop = kq.maLop " +
                "WHERE lhp.maCn = ? " +
                "GROUP BY kq.maLop, kq.maHp, hp.tenHp, lhp.loai, hp.soTietLyThuyet, hp.soTietThucHanh, " +
                "hp.hinhThucThi, hp.heSo, kq.hocKy, kq.tx1, kq.tx2, kq.giuaKy, kq.cuoiKy, kq.diemKiVong " +
                "ORDER BY hp.tenHp";

        cursor = db.rawQuery(query, new String[]{id + ""});
        if (cursor.moveToFirst()) {
            do {
                Diem diem = new Diem();

                diem.setMaLop(cursor.getString(cursor.getColumnIndex("maLop")));
                diem.setMaHp(cursor.getString(cursor.getColumnIndex("maHp")));
                diem.setTenHp(cursor.getString(cursor.getColumnIndex("tenHp")));
                diem.setLoai(cursor.getInt(cursor.getColumnIndex("loai")));
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

    public List<Diem> getDiemHpTheoKy(String hocKyStr) {
        int hocKy = Integer.parseInt(hocKyStr);
        List<Diem> diemList = new ArrayList<>();
        for (Diem diem : allDiemHpList) {
            if (diem.getHocKy() == hocKy) diemList.add(diem);
        }
        return diemList;
    }

    public boolean insertThongBao(String tieuDe, String noiDung, String thoiGian) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tieuDe", tieuDe);
        values.put("noiDung", noiDung);
        values.put("thoiGian", thoiGian);
        long res = db.insert("ThongBao", null, values);
        db.close();
        return res > 0;
    }

    public List<ThongBao> getThongBao() {
        List<ThongBao> thongBaoList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT tieuDe, noiDung, thoiGian FROM ThongBao ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String tieuDe = cursor.getString(cursor.getColumnIndex("tieuDe"));
                String noiDung = cursor.getString(cursor.getColumnIndex("noiDung"));
                String thoiGian = cursor.getString(cursor.getColumnIndex("thoiGian"));
                ThongBao thongBao = new ThongBao(tieuDe, noiDung, thoiGian);
                thongBaoList.add(thongBao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return thongBaoList;
    }

    public ArrayList<CongViec> getAllCongViec() {
        ArrayList<CongViec> congViecList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CongViec", null);
        if (cursor.moveToFirst()) {
            do {
                int macongviec = cursor.getInt(cursor.getColumnIndex("id"));
                String tencongviec = cursor.getString(cursor.getColumnIndex("tenViec"));
                String chitietcongviec = cursor.getString(cursor.getColumnIndex("chiTiet"));
                String mucuutien = cursor.getString(cursor.getColumnIndex("mucUuTien"));
                String thoihanngay = cursor.getString(cursor.getColumnIndex("thoiHanNgay"));
                String thoihangio = cursor.getString(cursor.getColumnIndex("thoiHanGio"));
                int trangthai = cursor.getInt(cursor.getColumnIndex("trangThai"));
                CongViec congViec = new CongViec(macongviec, tencongviec, chitietcongviec, mucuutien, thoihangio, thoihanngay, trangthai);

                congViecList.add(congViec);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return congViecList;
    }

    public void addCongViec(CongViec congViec) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", congViec.getMaCongViec());
        values.put("tenViec", congViec.getTenCongViec());
        values.put("chiTiet", congViec.getChiTietCongViec());
        values.put("mucUuTien", congViec.getMucUuTien());
        values.put("thoiHanNgay", congViec.getThoiHanNgay());
        values.put("thoiHanGio", congViec.getThoiHanGio());
        values.put("trangThai", congViec.getTrangThai());

        db.insert("CongViec", null, values);
        db.close();
    }

    public void updateCongViec(CongViec congViec) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", congViec.getMaCongViec());
        values.put("tenViec", congViec.getTenCongViec());
        values.put("chiTiet", congViec.getChiTietCongViec());
        values.put("mucUuTien", congViec.getMucUuTien());
        values.put("thoiHanNgay", congViec.getThoiHanNgay());
        values.put("thoiHanGio", congViec.getThoiHanGio());
        values.put("trangThai", congViec.getTrangThai());

        // Cập nhật công việc dựa trên ID
        db.update("CongViec", values, "id" + " = ?", new String[]{String.valueOf(congViec.getMaCongViec())});
        db.close();
    }

    public void deleteCongViec(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CongViec", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ThongBao table
    public List<HocPhan> getHocPhanByHocKy(int hocKy) {
        List<HocPhan> hocPhanList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HocPhan WHERE hocKy = ?", new String[]{String.valueOf(hocKy)});

        if (cursor.moveToFirst()) {
            do {
                HocPhan hocPhan = new HocPhan(
                        cursor.getString(cursor.getColumnIndex("maHp")),
                        cursor.getString(cursor.getColumnIndex("tenHp")),
                        cursor.getFloat(cursor.getColumnIndex("soTinChiLyThuyet")),
                        cursor.getFloat(cursor.getColumnIndex("soTinChiThucHanh")),
                        cursor.getInt(cursor.getColumnIndex("soTietLyThuyet")),
                        cursor.getInt(cursor.getColumnIndex("soTietThucHanh")),
                        cursor.getInt(cursor.getColumnIndex("hocKy")),
                        cursor.getString(cursor.getColumnIndex("hinhThucThi")),
                        cursor.getString(cursor.getColumnIndex("heSo"))
                );
                hocPhanList.add(hocPhan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return hocPhanList;
    }

    // Database name and version
    private static final String DATABASE_NAME = "QuanLyHocTapCaNhan.db";
    private static final int DATABASE_VERSION = 1;
    public static List<Diem> allDiemHpList = new ArrayList<>();
    private Context context;
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
                    "thoiHanGio TEXT NOT NULL," +
                    "thoiHanNgay TEXT NOT NULL," +
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
    private static final String CREATE_TABLE_THONGBAO =
            "CREATE TABLE IF NOT EXISTS ThongBao (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tieuDe TEXT NOT NULL, " +
                    "noiDung TEXT NOT NULL, " +
                    "thoiGian TEXT NOT NULL" +
                    ");";
    private static final String INSERT_TABLE_SINHVIEN =
            "INSERT INTO SinhVien (maSv, maCn, tenSv, tenTk, matKhau) VALUES " +
                    "('2021606516', 1, 'Phùng Đức Cần', 'Tao là nhất', 'abc123!@#')";
    private static final String INSERT_TABLE_CONGVIEC =
            "INSERT INTO CongViec (id, tenViec, mucUuTien, thoiHanGio, thoiHanNgay, trangThai, chiTiet) VALUES " +
                    "(1, 'Complete Math Homework', 1, '9:30', '2023-06-10', 0, 'Chapter 1-3 exercises'), " +
                    "(2, 'Prepare Physics Presentation', 2, '15:10', '2023-06-12', 0, 'Presentation on Quantum Mechanics'), " +
                    "(3, 'Chemistry Lab Report', 1, '8:00', '2023-06-14', 0, 'Lab report on chemical reactions'), " +
                    "(4, 'Biology Field Trip', 3, '11:15', '2023-06-16', 1, 'Field trip to the botanical garden'), " +
                    "(5, 'Computer Science Project', 1, '16:30', '2023-06-18', 0, 'Project on data structures'), " +
                    "(6, 'Math Quiz Preparation', 2, '10:30', '2023-06-20', 0, 'Prepare for upcoming quiz'), " +
                    "(7, 'Physics Assignment', 1, '6:50', '2023-06-22', 0, 'Complete assignments from chapter 4'), " +
                    "(8, 'Chemistry Homework', 2, '20:40', '2023-06-24', 0, 'Solve problems from the textbook'), " +
                    "(9, 'Biology Research', 3, '22:00', '2023-06-26', 1, 'Research on genetic mutations'), " +
                    "(10, 'Computer Science Exam', 1, '23:30', '2023-06-28', 0, 'Study for final exam');";
    private static final String INSERT_TABLE_CHUYENNGANH =
            "INSERT INTO ChuyenNganh (id, tenCn) VALUES " +
                    "(1, 'Công nghệ thông tin'), " +
                    "(2, 'Khoa học máy tính'), " +
                    "(3, 'Hệ thống thông tin'), " +
                    "(4, 'Kỹ thuật phần mềm');";
    private static final String INSERT_TABLE_HOCPHAN =
            "INSERT INTO HocPhan (maHp, tenHp, soTinChiLyThuyet, soTinChiThucHanh, soTietLyThuyet, soTietThucHanh, hocKy, hinhThucThi, heSo) VALUES " +
                    "('LP6010', 'Triết học Mác-Lênin', 2, 0, 20, 0, 1, 'Tự luận', '20-20-60'), " +
                    "('BS6002', 'Giải tích', 3, 0, 30, 0, 1, 'Bài tập lớn', '25-25-50'), " +
                    "('BS6001', 'Đại số tuyến tính', 3, 0, 30, 0, 1, 'Tự luận', '20-20-60'), " +
                    "('IT6016', 'Vật lý đại cương', 2, 1, 40, 30, 1, 'Tự luận', '25-25-50'), " +
                    "('IT6015', 'Kỹ thuật lập trình', 3, 1, 30, 30, 1, 'Thi trên máy tính', '20-20-60'), " +
                    "('IT6017', 'Cấu trúc dữ liệu', 3, 1, 30, 30, 1, 'Tự luận', '25-25-50'), " +
                    "('IT6018', 'Lý thuyết thông tin', 2, 1, 20, 30, 1, 'Bài tập lớn', '20-20-60'), " +
                    "('IT6019', 'Mạng máy tính', 2, 1, 20, 30, 1, 'Thi trên máy tính', '25-25-50'), " +
                    "('IT6020', 'Hệ điều hành', 3, 1, 30, 30, 1, 'Tự luận', '20-20-60'), " +
                    "('IT6021', 'Nhập môn AI', 2, 1, 20, 30, 1, 'Bài tập lớn', '25-25-50'), " +
                    "('MH2_01', 'Toán cao cấp', 3, 0, 40, 0, 2, 'Tự luận', '20-20-60'), " +
                    "('MH2_02', 'Xác suất thống kê', 3, 0, 30, 0, 2, 'Bài tập lớn', '25-25-50'), " +
                    "('MH2_03', 'Phân tích thiết kế hệ thống', 2, 1, 20, 30, 2, 'Thi trên máy tính', '20-20-60'), " +
                    "('MH2_04', 'An ninh mạng', 2, 1, 30, 30, 2, 'Tự luận', '25-25-50'), " +
                    "('MH2_05', 'Kiến trúc máy tính', 3, 1, 30, 30, 2, 'Thi trên máy tính', '20-20-60'), " +
                    "('MH2_06', 'Phát triển phần mềm', 3, 1, 30, 30, 2, 'Tự luận', '25-25-50'), " +
                    "('MH2_07', 'Trí tuệ nhân tạo nâng cao', 2, 1, 30, 30, 2, 'Bài tập lớn', '20-20-60'), " +
                    "('MH2_08', 'Khoa học dữ liệu', 2, 1, 20, 30, 2, 'Thi trên máy tính', '25-25-50'), " +
                    "('MH2_09', 'Phát triển game', 3, 1, 30, 30, 2, 'Tự luận', '20-20-60'), " +
                    "('MH2_10', 'Blockchain', 2, 1, 20, 30, 2, 'Bài tập lớn', '25-25-50'), " +
                    "('LP6012', 'Chủ nghĩa xã hội khoa học', 2, 0, 20, 0, 3, 'Tự luận', '20-20-60'), " +
                    "('IT6035', 'Toán rời rạc', 3, 0, 30, 0, 3, 'Bài tập lớn', '25-25-50'), " +
                    "('IT6126', 'Hệ thống cơ sở dữ liệu', 2, 1, 20, 30, 3, 'Thi trên máy tính', '20-20-60'), " +
                    "('IT6067', 'Kiến trúc máy tính và hệ điều hành', 2, 1, 20, 30, 3, 'Tự luận', '25-25-50'), " +
                    "('IT6120', 'Lập trình hướng đối tượng', 3, 1, 30, 30, 3, 'Thi trên máy tính', '20-20-60'), " +
                    "('LP6013', 'Lịch sử Đảng Cộng sản Việt Nam', 2, 1, 20, 30, 4, 'Tự luận', '20-20-60'), " +
                    "('IT6001', 'An toàn và bảo mật thông tin', 3, 0, 30, 0, 4, 'Bài tập lớn', '25-25-50'), " +
                    "('IT6002', 'Cấu trúc dữ liệu và giải thuật', 2, 1, 20, 30, 4, 'Thi trên máy tính', '20-20-60'), " +
                    "('LP6004', 'Tư tưởng Hồ Chí Minh', 2, 0, 20, 0, 5, 'Tự luận', '20-20-60'), " +
                    "('IT6071', 'Phát triển dự án công nghệ thông tin', 3, 0, 30, 0, 5, 'Bài tập lớn', '25-25-50'), " +
                    "('IT6100', 'Thiết kế đồ họa 2D', 2, 1, 20, 30, 5, 'Thi trên máy tính', '20-20-60'), " +
                    "('IT6047', 'Học máy', 2, 1, 20, 30, 6, 'Tự luận', '20-20-60'), " +
                    "('IT6057', 'Phát triển ứng dụng thương mại điện tử', 2, 1, 30, 30, 6, 'Bài tập lớn', '25-25-50'), " +
                    "('IT6125', 'Thiết kế web nâng cao', 2, 1, 20, 30, 6, 'Thi trên máy tính', '20-20-60'), " +
                    "('IT6122', 'Đồ án chuyên ngành', 3, 0, 30, 0, 7, 'Tự luận', '20-20-60'), " +
                    "('IT6013', 'Kiểm thử phần mềm', 2, 1, 20, 30, 7, 'Bài tập lớn', '25-25-50'), " +
                    "('IT6029', 'Phát triển ứng dụng trên thiết bị di động', 3, 1, 30, 30, 7, 'Thi trên máy tính', '20-20-60'), " +
                    "('IT6129', 'Đồ án tốt nghiệp', 4, 5, 40, 45, 8, 'Tự luận', '30-30-40'), " +
                    "('IT6128', 'Thực tập doanh nghiệp', 3, 3, 30, 45, 8, 'Bài tập lớn', '20-30-50');";
    private static final String INSERT_TABLE_LOAIHOCPHAN =
            "INSERT INTO LoaiHocPhan (maHp, maCn, loai) VALUES " +
                    "('LP6010', 1, 0), " +
                    "('BS6002', 1, 1), " +
                    "('BS6001', 1, 1), " +
                    "('IT6016', 1, 1), " +
                    "('IT6015', 1, 1), " +
                    "('IT6017', 1, 1), " +
                    "('IT6019', 1, 1), " +
                    "('IT6020', 1, 1), " +
                    "('IT6021', 1, 1), " +
                    "('MH2_02', 1, 0), " +
                    "('MH2_03', 1, 1), " +
                    "('MH2_04', 1, 1), " +
                    "('MH2_05', 1, 1), " +
                    "('MH2_06', 1, 1), " +
                    "('MH2_07', 1, 1), " +
                    "('MH2_08', 1, 1), " +
                    "('MH2_09', 1, 1), " +
                    "('MH2_10', 1, 1), " +
                    "('IT6035', 1, 1), " +
                    "('IT6126', 1, 1), " +
                    "('IT6067', 1, 1), " +
                    "('IT6120', 1, 1), " +
                    "('LP6013', 1, 1), " +
                    "('IT6002', 1, 1), " +
                    "('LP6004', 1, 0), " +
                    "('IT6100', 1, 1), " +
                    "('IT6047', 1, 1), " +
                    "('IT6057', 1, 1), " +
                    "('IT6125', 1, 1), " +
                    "('IT6122', 1, 0), " +
                    "('IT6013', 1, 1), " +
                    "('IT6129', 1, 0), " +
                    "('IT6128', 1, 1), " +
                    "('LP6010', 2, 1), " +
                    "('BS6002', 2, 0), " +
                    "('BS6001', 2, 1), " +
                    "('IT6016', 2, 0), " +
                    "('IT6015', 2, 0), " +
                    "('IT6017', 2, 0), " +
                    "('IT6019', 2, 0), " +
                    "('IT6020', 2, 0), " +
                    "('IT6021', 2, 0), " +
                    "('MH2_01', 2, 1), " +
                    "('MH2_03', 2, 0), " +
                    "('MH2_04', 2, 0), " +
                    "('MH2_05', 2, 0), " +
                    "('MH2_06', 2, 0), " +
                    "('MH2_08', 2, 0), " +
                    "('MH2_09', 2, 0), " +
                    "('MH2_10', 2, 0), " +
                    "('LP6012', 2, 1), " +
                    "('IT6035', 2, 0), " +
                    "('IT6126', 2, 0), " +
                    "('IT6067', 2, 0), " +
                    "('LP6013', 2, 0), " +
                    "('IT6001', 2, 1), " +
                    "('IT6002', 2, 0), " +
                    "('LP6004', 2, 1), " +
                    "('IT6071', 2, 1), " +
                    "('IT6100', 2, 0), " +
                    "('IT6047', 2, 0), " +
                    "('IT6057', 2, 0), " +
                    "('IT6125', 2, 0), " +
                    "('IT6122', 2, 1), " +
                    "('IT6029', 2, 0), " +
                    "('IT6129', 2, 1), " +
                    "('IT6128', 2, 0), " +
                    "('LP6010', 3, 1), " +
                    "('BS6002', 3, 1), " +
                    "('BS6001', 3, 1), " +
                    "('IT6016', 3, 0), " +
                    "('IT6015', 3, 1), " +
                    "('IT6018', 3, 0), " +
                    "('IT6019', 3, 1), " +
                    "('IT6020', 3, 1), " +
                    "('IT6021', 3, 1), " +
                    "('MH2_01', 3, 0), " +
                    "('MH2_02', 3, 0), " +
                    "('MH2_03', 3, 1), " +
                    "('MH2_05', 3, 1), " +
                    "('MH2_06', 3, 1), " +
                    "('MH2_07', 3, 1), " +
                    "('MH2_08', 3, 1), " +
                    "('MH2_09', 3, 1), " +
                    "('LP6012', 3, 0), " +
                    "('IT6035', 3, 1), " +
                    "('IT6126', 3, 1), " +
                    "('IT6067', 3, 1), " +
                    "('IT6120', 3, 1), " +
                    "('IT6001', 3, 0), " +
                    "('IT6002', 3, 1), " +
                    "('LP6004', 3, 0), " +
                    "('IT6071', 3, 0), " +
                    "('IT6100', 3, 1), " +
                    "('IT6057', 3, 1), " +
                    "('IT6125', 3, 1), " +
                    "('IT6122', 3, 0), " +
                    "('IT6013', 3, 1), " +
                    "('IT6029', 3, 1), " +
                    "('IT6129', 3, 0), " +
                    "('IT6128', 3, 1), " +
                    "('LP6010', 4, 0), " +
                    "('BS6002', 4, 1), " +
                    "('IT6016', 4, 1), " +
                    "('IT6015', 4, 1), " +
                    "('IT6017', 4, 1), " +
                    "('IT6018', 4, 0), " +
                    "('IT6019', 4, 1), " +
                    "('IT6020', 4, 1), " +
                    "('IT6021', 4, 1), " +
                    "('MH2_01', 4, 0), " +
                    "('MH2_02', 4, 0), " +
                    "('MH2_03', 4, 1), " +
                    "('MH2_04', 4, 1), " +
                    "('MH2_05', 4, 1), " +
                    "('MH2_07', 4, 1), " +
                    "('MH2_08', 4, 1), " +
                    "('MH2_09', 4, 1), " +
                    "('MH2_10', 4, 1), " +
                    "('LP6012', 4, 0), " +
                    "('IT6035', 4, 1), " +
                    "('IT6126', 4, 1), " +
                    "('IT6067', 4, 1), " +
                    "('IT6120', 4, 1), " +
                    "('IT6001', 4, 0), " +
                    "('IT6002', 4, 1), " +
                    "('LP6004', 4, 0), " +
                    "('IT6071', 4, 0), " +
                    "('IT6100', 4, 1), " +
                    "('IT6047', 4, 1), " +
                    "('IT6057', 4, 1), " +
                    "('IT6122', 4, 0), " +
                    "('IT6013', 4, 1), " +
                    "('IT6029', 4, 1), " +
                    "('IT6129', 4, 0), " +
                    "('IT6128', 4, 1)";
    private static final String INSERT_TABLE_KETQUAHOCPHAN =
            "INSERT INTO KetQuaHocPhan (maLop, maHp, tx1, tx2, giuaKy, cuoiKy, diemKiVong, hocKy) VALUES " +
                    "('2021HP003.1', 'IT6016', 8.5, 9.0, null, null, null, 1), " +
                    "('2021HP002.3', 'IT6015', 7.5, 8.0, 6.5, null, 7.0, 1), " +
                    "('2021HP001.3', 'BS6002', 3.5, 2.5, null, 5.0, null, 1), " +
                    "('2021HP008.1', 'IT6017', 9.0, 9.5, 8.5, 9.0, 9.0, 1), " +
                    "('2021HP005.9', 'BS6001', 7.5, 8.0, 7.5, 7.5, null, 2), " +
                    "('2021HP002.2', 'IT6015', 9.0, 9.5, 8.5, 9.0, 9.0, 2), " +
                    "('2021HP009.4', 'IT6018', 8.0, 8.5, 7.0, null, null, 2), " +
                    "('2021HP001.2', 'BS6002', 7.5, 8.0, null, 7.5, 7.5, 2), " +
                    "('2022HP005.1', 'BS6001', 7.5, 6.0, 7.5, 7.5, null, 3), " +
                    "('2022HP002.2', 'IT6015', 9.0, 3.5, 8.5, 9.0, 9.0, 3), " +
                    "('2022HP009.4', 'IT6018', 8.0, 6.5, 7.0, null, null, 3), " +
                    "('2022HP001.2', 'BS6002', 7.5, 7.0, null, 7.5, 7.5, 3), " +
                    "('2022HP005.9', 'BS6001', 7.5, 8.0, 8.5, 4.5, null, 4), " +
                    "('2022HP001.1', 'BS6002', 9.0, 9.5, 8.5, 9.0, 9.0, 4), " +
                    "('2022HP009.7', 'IT6018', 8.0, 8.5, 7.0, null, null, 4), " +
                    "('2022HP001.3', 'BS6002', 7.5, 8.0, null, 7.5, 7.5, 4), " +
                    "('2022HP003.3', 'IT6016', 8.0, 8.5, null, null, null, 4), " +
                    "('2023HP005.9', 'BS6001', 7.5, 8.0, 7.5, 7.5, null, 5), " +
                    "('2023HP008.3', 'IT6017', 7.5, 8.0, null, null, 7.0, 5), " +
                    "('2023HP002.2', 'IT6015', 9.0, 9.5, 8.5, 9.0, 9.0, 5), " +
                    "('2023HP009.4', 'IT6018', 8.0, 8.5, 7.0, null, null, 5), " +
                    "('2023HP001.2', 'BS6002', 7.5, 8.0, null, 7.5, 7.5, 5), " +
                    "('2023HP005.1', 'BS6001', 7.5, 8.0, 7.5, 7.5, null, 6), " +
                    "('2023HP008.2', 'IT6017', 7.5, 6.0, null, null, 7.0, 6), " +
                    "('2023HP002.7', 'IT6015', 4.0, 9.5, 6.5, 9.0, 9.0, 6), " +
                    "('2023HP003.3', 'IT6016', 8.0, 8.5, 7.0, null, null, 6), " +
                    "('2023HP001.3', 'BS6002', 7.5, 8.0, null, 7.5, 7.5, 6), " +
                    "('2024HP008.1', 'IT6017', 8.5, 7.0, null, null, 8.0, 7), " +
                    "('2024HP001.2', 'BS6002', 8.0, 6.5, 8.5, 9.0, 9.0, 7), " +
                    "('2024HP009.4', 'IT6018', 8.0, 9.5, 9.0, null, null, 7), " +
                    "('2024HP001.6', 'BS6002', 7.5, 8.0, null, 7.5, 7.5, 7), " +
                    "('2024HP005.5', 'BS6001', 8.0, 8.5, null, null, null, 7), " +
                    "('2025HP003.9', 'IT6016', 7.5, 8.0, 7.5, 7.5, null, 8), " +
                    "('2025HP008.1', 'IT6017', 8.5, 9.0, null, null, 8.0, 8), " +
                    "('2025HP002.2', 'IT6015', 7.0, 9.5, 8.5, 9.0, 9.0, 8), " +
                    "('2025HP001.4', 'BS6002', 9.0, 8.5, 7.0, null, null, 8), " +
                    "('2025HP001.2', 'BS6002', 7.5, 7.0, null, 7.5, 7.5, 8), " +
                    "('2025HP005.5', 'BS6001', 8.0, 6.5, null, null, null, 8);";
    private static final String INSERT_TABLE_LICHHOC =
            "INSERT INTO LichHoc " +
                    "(id, maLop, thu, ngay, phong, giangVien, tiet, diaDiem, loaiTietHoc, vang) " +
                    "VALUES " +
                    "(1, '2021HP002.3', 'Friday', '2023-06-21', 110, 'James Anderson', '19-20', 'Room J', 0, 1), " +
                    "(2, '2021HP003.1', 'Tuesday', '2023-06-25', 112, 'William Hernandez', '23-24', 'Room L', 0, 0), " +
                    "(3, '2021HP002.3', 'Wednesday', '2023-06-26', 113, 'Isabella Martinez', '25-26', 'Room M', 1, 1), " +
                    "(4, '2021HP003.3', 'Thursday', '2023-06-27', 114, 'Ethan Phillips', '27-28', 'Room N', 0, 0), " +
                    "(5, '2021HP003.1', 'Friday', '2023-06-28', 115, 'Amelia Brown', '29-30', 'Room O', 1, 0), " +
                    "(6, '2021HP002.3', 'Monday', '2023-07-01', 116, 'Benjamin Davis', '31-32', 'Room P', 0, 1), " +
                    "(7, '2021HP003.3', 'Tuesday', '2023-07-02', 117, 'Mia Miller', '33-34', 'Room Q', 1, 0), " +
                    "(8, '2021HP003.1', 'Wednesday', '2023-07-03', 118, 'Logan Wilson', '35-36', 'Room R', 0, 0), " +
                    "(9, '2021HP002.3', 'Thursday', '2023-07-04', 119, 'Harper Garcia', '37-38', 'Room S', 1, 1), " +
                    "(10, '2021HP003.3', 'Friday', '2023-07-05', 120, 'Evelyn Rodriguez', '39-40', 'Room T', 0, 0), " +
                    "(11, '2021HP003.3', 'Monday', '2023-06-24', 111, 'Emma Garcia', '21-22', 'Room K', 1, 0), " +
                    "(12, '2021HP003.1', 'Thursday', '2023-06-20', 109, 'Olivia Taylor', '17-18', 'Room I', 1, 0), " +
                    "(13, '2021HP001.3', 'Friday', '2023-07-12', 121, 'John Doe', '41-42', 'Room U', 0, 1), " +
                    "(14, '2021HP004.6', 'Tuesday', '2023-07-16', 122, 'Jane Smith', '43-44', 'Room V', 0, 0), " +
                    "(15, '2021HP005.9', 'Wednesday', '2023-07-17', 123, 'Tom Johnson', '45-46', 'Room W', 1, 1), " +
                    "(16, '2021HP006.1', 'Thursday', '2023-07-18', 124, 'Lucy Adams', '47-48', 'Room X', 0, 0), " +
                    "(17, '2021HP007.3', 'Friday', '2023-07-19', 125, 'Steve Brown', '49-50', 'Room Y', 1, 0), " +
                    "(18, '2021HP002.2', 'Monday', '2023-07-22', 126, 'Nancy Davis', '51-52', 'Room Z', 0, 1), " +
                    "(19, '2021HP009.4', 'Tuesday', '2023-07-23', 127, 'Mark Lee', '53-54', 'Room AA', 1, 0), " +
                    "(20, '2021HP001.2', 'Wednesday', '2023-07-24', 128, 'Emily Clark', '55-56', 'Room BB', 0, 0), " +
                    "(21, '2021HP004.5', 'Thursday', '2023-07-25', 129, 'Jason Lewis', '57-58', 'Room CC', 1, 1), " +
                    "(22, '2021HP002.3', 'Friday', '2023-07-26', 130, 'Sophia Hill', '59-60', 'Room DD', 0, 0), " +
                    "(23, '2021HP005.9', 'Monday', '2023-07-29', 131, 'Liam Harris', '61-62', 'Room EE', 1, 0), " +
                    "(24, '2022HP005.1', 'Tuesday', '2023-07-30', 132, 'Noah Martin', '63-64', 'Room FF', 0, 1), " +
                    "(25, '2022HP006.1', 'Wednesday', '2023-07-31', 133, 'Olivia Thompson', '65-66', 'Room GG', 1, 0), " +
                    "(26, '2022HP007.3', 'Thursday', '2023-08-01', 134, 'Mason White', '67-68', 'Room HH', 0, 0), " +
                    "(27, '2022HP002.2', 'Friday', '2023-08-02', 135, 'Elijah Harris', '69-70', 'Room II', 1, 1), " +
                    "(28, '2022HP009.4', 'Monday', '2023-08-05', 136, 'Lucas Walker', '71-72', 'Room JJ', 0, 0), " +
                    "(29, '2022HP001.2', 'Tuesday', '2023-08-06', 137, 'Mia Young', '73-74', 'Room KK', 1, 0), " +
                    "(30, '2022HP004.5', 'Wednesday', '2023-08-07', 138, 'Harper Martinez', '75-76', 'Room LL', 0, 1), " +
                    "(31, '2022HP005.9', 'Thursday', '2023-08-08', 139, 'Evelyn Hernandez', '77-78', 'Room MM', 1, 0), " +
                    "(32, '2022HP006.3', 'Friday', '2023-08-09', 140, 'James Allen', '79-80', 'Room NN', 0, 0), " +
                    "(33, '2022HP007.4', 'Monday', '2023-08-12', 141, 'Liam King', '81-82', 'Room OO', 1, 1), " +
                    "(34, '2022HP001.1', 'Tuesday', '2023-08-13', 142, 'Charlotte Wright', '83-84', 'Room PP', 0, 0), " +
                    "(35, '2022HP009.7', 'Wednesday', '2023-08-14', 143, 'Alexander Scott', '85-86', 'Room QQ', 1, 0), " +
                    "(36, '2022HP001.3', 'Thursday', '2023-08-15', 144, 'Sofia Green', '87-88', 'Room RR', 0, 1), " +
                    "(37, '2023HP005.9', 'Monday', '2023-08-19', 146, 'Ava Carter', '91-92', 'Room TT', 0, 0), " +
                    "(38, '2023HP006.1', 'Tuesday', '2023-08-20', 147, 'Logan Hill', '93-94', 'Room UU', 1, 1), " +
                    "(39, '2023HP008.3', 'Wednesday', '2023-08-21', 148, 'Sophie Foster', '95-96', 'Room VV', 0, 0), " +
                    "(40, '2023HP002.2', 'Thursday', '2023-08-22', 149, 'Henry Reed', '97-98', 'Room WW', 1, 0), " +
                    "(41, '2023HP009.4', 'Friday', '2023-08-23', 150, 'Aiden Gray', '99-100', 'Room XX', 0, 1), " +
                    "(42, '2023HP001.2', 'Monday', '2023-08-26', 151, 'Ella Brooks', '101-102', 'Room YY', 1, 0), " +
                    "(43, '2023HP006.5', 'Tuesday', '2023-08-27', 152, 'Leo Wood', '103-104', 'Room ZZ', 0, 0), " +
                    "(44, '2023HP005.1', 'Wednesday', '2023-08-28', 153, 'Stella Cook', '105-106', 'Room AAA', 1, 1), " +
                    "(45, '2023HP004.4', 'Thursday', '2023-08-29', 154, 'Zoe Murphy', '107-108', 'Room BBB', 0, 0), " +
                    "(46, '2023HP008.2', 'Friday', '2023-08-30', 155, 'David Price', '109-110', 'Room CCC', 1, 0), " +
                    "(47, '2023HP002.7', 'Monday', '2023-09-02', 156, 'Luna Rivera', '111-112', 'Room DDD', 0, 1), " +
                    "(48, '2023HP003.3', 'Tuesday', '2023-09-03', 157, 'Hudson Ward', '113-114', 'Room EEE', 1, 0), " +
                    "(49, '2023HP001.3', 'Wednesday', '2023-09-04', 158, 'Mila Brooks', '115-116', 'Room FFF', 0, 0), " +
                    "(50, '2023HP004.3', 'Thursday', '2023-09-05', 159, 'Owen Watson', '117-118', 'Room GGG', 1, 1), " +
                    "(51, '2024HP006.9', 'Friday', '2023-09-06', 160, 'Ruby Long', '119-120', 'Room HHH', 0, 0), " +
                    "(52, '2024HP008.1', 'Monday', '2023-09-09', 161, 'Carter Hughes', '121-122', 'Room III', 1, 0), " +
                    "(53, '2024HP007.3', 'Tuesday', '2023-09-10', 162, 'Samantha Price', '123-124', 'Room JJJ', 0, 1), " +
                    "(54, '2024HP001.2', 'Wednesday', '2023-09-11', 163, 'Xavier Evans', '125-126', 'Room KKK', 1, 0), " +
                    "(55, '2024HP009.4', 'Thursday', '2023-09-12', 164, 'Layla Ward', '127-128', 'Room LLL', 0, 0), " +
                    "(56, '2024HP001.6', 'Friday', '2023-09-13', 165, 'Gabriel Bryant', '129-130', 'Room MMM', 1, 1), " +
                    "(57, '2024HP005.5', 'Monday', '2023-09-16', 166, 'Molly Perry', '131-132', 'Room NNN', 0, 0), " +
                    "(58, '2025HP003.9', 'Tuesday', '2023-09-17', 167, 'Anthony Lopez', '133-134', 'Room OOO', 1, 0), " +
                    "(59, '2025HP008.1', 'Wednesday', '2023-09-18', 168, 'Nathan Ward', '135-136', 'Room PPP', 0, 1), " +
                    "(60, '2025HP006.3', 'Thursday', '2023-09-19', 169, 'Aria Griffin', '137-138', 'Room QQQ', 1, 0), " +
                    "(61, '2025HP002.2', 'Friday', '2023-09-20', 170, 'Eliana Rivera', '139-140', 'Room RRR', 0, 0), " +
                    "(62, '2025HP001.4', 'Monday', '2023-09-23', 171, 'Riley Torres', '141-142', 'Room SSS', 1, 1), " +
                    "(63, '2025HP001.2', 'Tuesday', '2023-09-24', 172, 'Peyton Ward', '143-144', 'Room TTT', 0, 0), " +
                    "(64, '2025HP005.5', 'Wednesday', '2023-09-25', 173, 'Angelina Allen', '145-146', 'Room UUU', 1, 0)";

//                "('Thiết kế Web','Thứ 2','10/6/2024','Phạm Thế Anh(0902131386 - CNTT)','Phòng máy số 3','1,2,3','A1')," +
//                "('Tiếng Anh Công Nghệ Thông Tin 2','Thứ 3','11/6/2024','Bùi Phương Thảo(0389937161 - Trường NN-DL)','508','7,8','A9')," +
//                "('Phát triển ứng dụng trên thiết bị di động','Thứ 6','14/6/2024','Vũ Thị Dương(0904755919 - CNTT)','402','3,4,5','A8')," +
//                "('Thiết kế đồ hoạ 2D','Thứ 5','13/6/2024','Đỗ Mạnh Hùng(0916113319 - CNTT)','609','9,10,11','A9')," +
//                "('Đồ án chuyên ngành','Chủ nhật','16/6/2024','Nguyễn Bá Nghiễn (0358218310 - CNTT)','Phòng thực hành Khoa CNTT 06','1,2,3,4,5,7,8,9,10,11','A1');";
}
