package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.btl_android.cong_viec.CongViec;
import com.example.btl_android.diem.Diem;
import com.example.btl_android.hoc_phan_du_kien.HocPhan;
import com.example.btl_android.thong_bao.ThongBao;

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
        db.insert("HocPhan", null, values);
        db.close();
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
        db.close();
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

    public void themDuLieuHocPhanMoiLan() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa dữ liệu cũ để tránh trùng lặp, nếu không muốn xóa thì bỏ qua bước này
        db.execSQL("DELETE FROM HocPhan");

        // Thêm dữ liệu mới
        themHocPhanMau(db);

        db.close();
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

    public void getTatCaDiemHp() {
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

    public boolean updateThongBao(String tieuDe, String noiDung, String thoiGian) {
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
        Cursor cursor = db.rawQuery("SELECT tieuDe, noiDung, thoiGian FROM ThongBao", null);

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
                String tencongviec = cursor.getString(cursor.getColumnIndex("tenViec"));
                String chitietcongviec = cursor.getString(cursor.getColumnIndex("chiTiet"));
                String mucuutien = cursor.getString(cursor.getColumnIndex("mucUuTien"));
                String thoihanngay = cursor.getString(cursor.getColumnIndex("thoiHanNgay"));
                String thoihangio = cursor.getString(cursor.getColumnIndex("thoiHanGio"));
                int trangthai = cursor.getInt(cursor.getColumnIndex("trangThai"));
                CongViec congViec = new CongViec(tencongviec,chitietcongviec,mucuutien,thoihanngay,thoihanngay,trangthai);

                congViecList.add(congViec);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return congViecList;
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

    // ThongBao table
    private static final String CREATE_TABLE_THONGBAO =
            "CREATE TABLE IF NOT EXISTS ThongBao (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tieuDe TEXT NOT NULL, " +
                    "noiDung TEXT NOT NULL, " +
                    "thoiGian TEXT NOT NULL" +
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
                    "('HP001', 3, 1), " +
                    "('HP002', 3, 1), " +
                    "('HP003', 3, 0), " +
                    "('HP003', 4, 1), " +
                    "('HP005', 3, 0), " +
                    "('HP006', 1, 1), " +
                    "('HP007', 2, 0), " +
                    "('HP008', 3, 1), " +
                    "('HP009', 3, 1), " +
                    "('HP09', 2, 0);";

    private static final String INSERT_TABLE_KETQUAHOCPHAN =
            "INSERT INTO KetQuaHocPhan (maLop, maHp, tx1, tx2, giuaKy, cuoiKy, diemKiVong, hocKy) VALUES " +
                    "('2021HP003.1', 'HP003', 8.5, 9.0, null, null, null, 1), " +
                    "('2021HP002.3', 'HP002', 7.5, 8.0, 6.5, null, 7.0, 1), " +
                    "('2021HP001.3', 'HP001', 3.5, 2.5, null, 5.0, null, 1), " +
                    "('2021HP008.1', 'HP008', 9.0, 9.5, 8.5, 9.0, 9.0, 1), " +
                    "('2021HP005.9', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 2), " +
                    "('2021HP002.2', 'HP002', 9.0, 9.5, 8.5, 9.0, 9.0, 2), " +
                    "('2021HP009.4', 'HP009', 8.0, 8.5, 7.0, null, null, 2), " +
                    "('2021HP001.2', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 2), " +
                    "('2022HP005.1', 'HP005', 7.5, 6.0, 7.5, 7.5, null, 3), " +
                    "('2022HP002.2', 'HP002', 9.0, 3.5, 8.5, 9.0, 9.0, 3), " +
                    "('2022HP009.4', 'HP009', 8.0, 6.5, 7.0, null, null, 3), " +
                    "('2022HP001.2', 'HP001', 7.5, 7.0, null, 7.5, 7.5, 3), " +
                    "('2022HP005.9', 'HP005', 7.5, 8.0, 8.5, 4.5, null, 4), " +
                    "('2022HP001.1', 'HP001', 9.0, 9.5, 8.5, 9.0, 9.0, 4), " +
                    "('2022HP009.7', 'HP009', 8.0, 8.5, 7.0, null, null, 4), " +
                    "('2022HP001.3', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 4), " +
                    "('2022HP003.3', 'HP003', 8.0, 8.5, null, null, null, 4), " +
                    "('2023HP005.9', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 5), " +
                    "('2023HP008.3', 'HP008', 7.5, 8.0, null, null, 7.0, 5), " +
                    "('2023HP002.2', 'HP002', 9.0, 9.5, 8.5, 9.0, 9.0, 5), " +
                    "('2023HP009.4', 'HP009', 8.0, 8.5, 7.0, null, null, 5), " +
                    "('2023HP001.2', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 5), " +
                    "('2023HP005.1', 'HP005', 7.5, 8.0, 7.5, 7.5, null, 6), " +
                    "('2023HP008.2', 'HP008', 7.5, 6.0, null, null, 7.0, 6), " +
                    "('2023HP002.7', 'HP002', 4.0, 9.5, 6.5, 9.0, 9.0, 6), " +
                    "('2023HP003.3', 'HP003', 8.0, 8.5, 7.0, null, null, 6), " +
                    "('2023HP001.3', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 6), " +
                    "('2024HP008.1', 'HP008', 8.5, 7.0, null, null, 8.0, 7), " +
                    "('2024HP001.2', 'HP001', 8.0, 6.5, 8.5, 9.0, 9.0, 7), " +
                    "('2024HP009.4', 'HP009', 8.0, 9.5, 9.0, null, null, 7), " +
                    "('2024HP001.6', 'HP001', 7.5, 8.0, null, 7.5, 7.5, 7), " +
                    "('2024HP005.5', 'HP005', 8.0, 8.5, null, null, null, 7), " +
                    "('2025HP003.9', 'HP003', 7.5, 8.0, 7.5, 7.5, null, 8), " +
                    "('2025HP008.1', 'HP008', 8.5, 9.0, null, null, 8.0, 8), " +
                    "('2025HP002.2', 'HP002', 7.0, 9.5, 8.5, 9.0, 9.0, 8), " +
                    "('2025HP001.4', 'HP001', 9.0, 8.5, 7.0, null, null, 8), " +
                    "('2025HP001.2', 'HP001', 7.5, 7.0, null, 7.5, 7.5, 8), " +
                    "('2025HP005.5', 'HP005', 8.0, 6.5, null, null, null, 8)";

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
}
