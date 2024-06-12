package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.btl_android.hoc_phan_du_kien.HocPhan;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "QuanLyHocTapCaNhan.db";
    private static final int DATABASE_VERSION = 2;

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
                    "soTinChiLyThuyet INTEGER NOT NULL," +
                    "soTinChiThucHanh INTEGER NOT NULL," +
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
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idHp TEXT NOT NULL," +
                    "ngay TEXT NOT NULL," +
                    "vang INTEGER," +
                    "loai INTEGER," +
                    "FOREIGN KEY (idHp) REFERENCES KetQuaHocPhan(lop)" +
                    " ON UPDATE NO ACTION ON DELETE NO ACTION" +
                    ");";

    public DatabaseHelper(@Nullable final Context context) {
        super(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
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
            Log.e("Error", "There are some problems in creating database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS SinhVien");
        db.execSQL("DROP TABLE IF EXISTS CongViec");
        db.execSQL("DROP TABLE IF EXISTS ChuyenNganh");
        db.execSQL("DROP TABLE IF EXISTS HocPhan");
        db.execSQL("DROP TABLE IF EXISTS LoaiHocPhan");
        db.execSQL("DROP TABLE IF EXISTS KetQuaHocPhan");
        db.execSQL("DROP TABLE IF EXISTS DiemDanh");

        // Tạo lại cấu trúc cơ sở dữ liệu
        onCreate(db);
    }
    public void insertHocPhan(HocPhan hocPhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHp", hocPhan.getMaHp());
        values.put("tenHp", hocPhan.getTenHp());
        values.put("soTinChiLyThuyet", hocPhan.getSoTinChiLyThuyet());
        values.put("soTinChiThucHanh", hocPhan.getSoTinChiThucHanh());
        values.put("hocKy", hocPhan.getHocKy());
        values.put("hinhThucThi", hocPhan.getHinhThucThi());
        values.put("heSo", hocPhan.getHeSo());

        long result = db.insert("HocPhan", null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert HocPhan");
        } else {
            Log.i("DatabaseHelper", "HocPhan inserted successfully");
        }
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

    public void updateHocPhan(HocPhan hocPhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenHp", hocPhan.getTenHp());
        values.put("soTinChiLyThuyet", hocPhan.getSoTinChiLyThuyet());
        values.put("soTinChiThucHanh", hocPhan.getSoTinChiThucHanh());
        values.put("hocKy", hocPhan.getHocKy());
        values.put("hinhThucThi", hocPhan.getHinhThucThi());
        values.put("heSo", hocPhan.getHeSo());

        db.update("HocPhan", values, "maHp = ?", new String[]{hocPhan.getMaHp()});
    }

    public void themDuLieuHocPhanMoiLan() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa dữ liệu cũ để tránh trùng lặp, nếu không muốn xóa thì bỏ qua bước này
        db.execSQL("DELETE FROM HocPhan");

        // Thêm dữ liệu mới
        themHocPhanMau(db);
    }

    private void themHocPhanMau(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        // Thêm môn học cho Học kỳ 1
        cv.put("maHp", "LP6010");
        cv.put("tenHp", "Triết học Mác-Lênin");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "BS6002");
        cv.put("tenHp", "Giải tích");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 0);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        // Thêm các môn học khác tương tự cho Học kỳ 1
        cv.put("maHp", "BS6001");
        cv.put("tenHp", "Đại số tuyến tính");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6016");
        cv.put("tenHp", "Vật lý đại cương");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6015");
        cv.put("tenHp", "Kỹ thuật lập trình");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6017");
        cv.put("tenHp", "Cấu trúc dữ liệu");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6018");
        cv.put("tenHp", "Lý thuyết thông tin");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6019");
        cv.put("tenHp", "Mạng máy tính");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6020");
        cv.put("tenHp", "Hệ điều hành");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6021");
        cv.put("tenHp", "Nhập môn AI");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 1);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        // Thêm môn học cho Học kỳ 2
        cv.put("maHp", "MH2_01");
        cv.put("tenHp", "Toán cao cấp");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_02");
        cv.put("tenHp", "Xác suất thống kê");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 0);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_03");
        cv.put("tenHp", "Phân tích thiết kế hệ thống");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_04");
        cv.put("tenHp", "An ninh mạng");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_05");
        cv.put("tenHp", "Kiến trúc máy tính");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_06");
        cv.put("tenHp", "Phát triển phần mềm");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_07");
        cv.put("tenHp", "Trí tuệ nhân tạo nâng cao");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_08");
        cv.put("tenHp", "Khoa học dữ liệu");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_09");
        cv.put("tenHp", "Phát triển game");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "MH2_10");
        cv.put("tenHp", "Blockchain");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 2);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);


        // Học kỳ 3
        cv.put("maHp", "LP6012");
        cv.put("tenHp", "Chủ nghĩa xã hội khoa học");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 3);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6035");
        cv.put("tenHp", "Toán rời rạc");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 0);
        cv.put("hocKy", 3);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6126");
        cv.put("tenHp", "Hệ thống cơ sở dữ liệu");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 3);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6067");
        cv.put("tenHp", "Kiến trúc máy tính và hệ điều hành");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 3);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6120");
        cv.put("tenHp", "Lập trình hướng đối tượng");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 3);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);


        // Học kỳ 4
        cv.put("maHp", "LP6013");
        cv.put("tenHp", "Lịch sử Đảng Cộng sản Việt Nam");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 4);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6001");
        cv.put("tenHp", "An toàn và bảo mật thông tin");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 0);
        cv.put("hocKy", 4);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6002");
        cv.put("tenHp", "Cấu trúc dữ liệu và giải thuật");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 4);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);


        // Học kỳ 5
        cv.put("maHp", "LP6004");
        cv.put("tenHp", "Tư tưởng Hồ Chí Minh");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 5);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6071");
        cv.put("tenHp", "Phát triển dự án công nghệ thông tin");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 0);
        cv.put("hocKy", 5);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6100");
        cv.put("tenHp", "Thiết kế đồ họa 2D");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 5);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);


        // Học kỳ 6
        cv.put("maHp", "IT6047");
        cv.put("tenHp", "Học máy");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 6);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6057");
        cv.put("tenHp", "Phát triển ứng dụng thương mại điện tử");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 0);
        cv.put("hocKy", 6);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6125");
        cv.put("tenHp", "Thiết kế web nâng cao");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 6);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);


        // Học kỳ 7
        cv.put("maHp", "IT6122");
        cv.put("tenHp", "Đồ án chuyên ngành");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 0);
        cv.put("hocKy", 7);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6013");
        cv.put("tenHp", "Kiểm thử phần mềm");
        cv.put("soTinChiLyThuyet", 2);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 7);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "25-25-50");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6029");
        cv.put("tenHp", "Phát triển ứng dụng trên thiết bị di động");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 1);
        cv.put("hocKy", 7);
        cv.put("hinhThucThi", "Thi trên máy tính");
        cv.put("heSo", "20-20-60");
        db.insert("HocPhan", null, cv);


        // Học kỳ 8
        cv.put("maHp", "IT6129");
        cv.put("tenHp", "Đồ án tốt nghiệp");
        cv.put("soTinChiLyThuyet", 4);
        cv.put("soTinChiThucHanh", 5);
        cv.put("hocKy", 8);
        cv.put("hinhThucThi", "Tự luận");
        cv.put("heSo", "30-30-40");
        db.insert("HocPhan", null, cv);

        cv.put("maHp", "IT6128");
        cv.put("tenHp", "Thực tập doanh nghiệp");
        cv.put("soTinChiLyThuyet", 3);
        cv.put("soTinChiThucHanh", 3);
        cv.put("hocKy", 8);
        cv.put("hinhThucThi", "Bài tập lớn");
        cv.put("heSo", "20-30-50");
        db.insert("HocPhan", null, cv);

    }








}