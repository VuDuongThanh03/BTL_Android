package com.example.btl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    // Database name and version
    private static final String DATABASE_NAME = "QuanLyHocTapCaNhan.db";
    private static final int DATABASE_VERSION = 1;

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
    private static final String TABLE_THOIKHOABIEU="THOIKHOABIEU";
    // Subject table column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MON = "mon";
    private static final String COLUMN_THU = "thu";
    private static final String COLUMN_NGAY = "ngay";
    private static final String COLUMN_GIANGVIEN = "giangvien";
    private static final String COLUMN_PHONG = "phong";
    private static final String COLUMN_TIET = "tiet";
    private static final String COLUMN_DIADIEM = "diadiem";
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
            String query="CREATE TABLE " + TABLE_THOIKHOABIEU +
                    "("+  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_MON + " TEXT,"
                    + COLUMN_THU + " TEXT,"
                    + COLUMN_NGAY + " TEXT,"
                    + COLUMN_GIANGVIEN + " TEXT,"
                    + COLUMN_PHONG + " TEXT,"
                    + COLUMN_TIET + " TEXT,"
                    + COLUMN_DIADIEM + " TEXT" + ")";
            db.execSQL(query);
            String insertData = "INSERT INTO " + TABLE_THOIKHOABIEU + " ("
                    + COLUMN_MON + ", "
                    + COLUMN_THU + ", "
                    + COLUMN_NGAY + ", "
                    + COLUMN_GIANGVIEN + ", "
                    + COLUMN_PHONG + ", "
                    + COLUMN_TIET + ", "
                    + COLUMN_DIADIEM + ") VALUES " +
                    "('Thiết kế Web','Thứ 2','10/6/2024','Phạm Thế Anh(0902131386 - CNTT)','Phòng máy số 3','1,2,3','A1')," +
                    "('Tiếng Anh Công Nghệ Thông Tin 2','Thứ 3','11/6/2024','Bùi Phương Thảo(0389937161 - Trường NN-DL)','508','7,8','A9')," +
                    "('Phát triển ứng dụng trên thiết bị di động','Thứ 6','14/6/2024','Vũ Thị Dương(0904755919 - CNTT)','402','3,4,5','A8')," +
                    "('Thiết kế đồ hoạ 2D','Thứ 5','13/6/2024','Đỗ Mạnh Hùng(0916113319 - CNTT)','609','9,10,11','A9')," +
                    "('Đồ án chuyên ngành','Chủ nhật','16/6/2024','Nguyễn Bá Nghiễn (0358218310 - CNTT)','Phòng thực hành Khoa CNTT 06','1,2,3,4,5,7,8,9,10,11','A1');";
            db.execSQL(insertData);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THOIKHOABIEU);

        onCreate(db);
    }

    // CRUD operations for HocPhan
    public void addSubject(final HocPhan hocPhan) {
        final SQLiteDatabase db = getWritableDatabase();

        String sql = "INSERT INTO HocPhan (tenHp, maHp, soTinChiLyThuyet, hocKy) VALUES (?, ?, ?, ?)";

        db.execSQL(sql, new Object[]{
                hocPhan.getTenHp(),
                hocPhan.getMaHp(),
                hocPhan.getSoTinChiLt(),
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
                hocPhan.setSoTinChiLt(cursor.getInt(cursor.getColumnIndexOrThrow("soTinChiLyThuyet")));
                hocPhan.setHocKy(cursor.getString(cursor.getColumnIndexOrThrow("hocKy")));
                hocPhanList.add(hocPhan);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return hocPhanList;
    }

    public List<HocPhan> getSubjectsByYear(String tenTk, int year) {
        List<HocPhan> hocPhanList = new ArrayList<>();
        String selectQuery = "SELECT hp.maHp, hp.tenHp, hp.soTinChiLyThuyet, hp.soTinChiThucHanh, " +
                "hp.hinhThucThi, kq.lop, hp.heSo, kq.tx1, kq.tx2, kq.giuaKy, " +
                "kq.cuoiKy, kq.diemKiVong, " +
                "SUM(CASE WHEN dd.vang = 1 AND dd.loai = 0 THEN 1 ELSE 0 END) AS vangLt, " +
                "SUM(CASE WHEN dd.vang = 1 AND dd.loai = 1 THEN 1 ELSE 0 END) AS vangTh " +
                "FROM KetQuaHocPhan kq " +
                "JOIN HocPhan hp ON hp.maHp = kq.maHp " +
                "JOIN SinhVien sv ON sv.maSv = kq.maSv " +
                "LEFT JOIN DiemDanh dd ON dd.idHp = kq.lop AND dd.maSv = sv.maSv " +
                "WHERE sv.tenTk = ? AND kq.nam = ? " +
                "GROUP BY hp.maHp, hp.tenHp, hp.soTinChiLyThuyet, hp.soTinChiThucHanh, " +
                "hp.hinhThucThi, kq.lop, hp.heSo, kq.tx1, kq.tx2, kq.giuaKy, " +
                "kq.cuoiKy, kq.diemKiVong";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{tenTk, String.valueOf(year)});

        if (cursor.moveToFirst()) {
            do {
                HocPhan hocPhan = new HocPhan();
                hocPhan.setMaHp(cursor.getString(cursor.getColumnIndex("maHp")));
                hocPhan.setTenHp(cursor.getString(cursor.getColumnIndex("tenHp")));
                hocPhan.setSoTinChiLt(cursor.getInt(cursor.getColumnIndex("soTinChiLyThuyet")));
                hocPhan.setSoTinChiTh(cursor.getInt(cursor.getColumnIndex("soTinChiThucHanh")));
                hocPhan.setHinhThucThi(cursor.getString(cursor.getColumnIndex("hinhThucThi")));
                hocPhan.setLop(cursor.getString(cursor.getColumnIndex("lop")));
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

    public void AddTimeTable(String mon, String thu, String ngay, String giangvien, String phong, String tiet, String diadiem)
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
            Toast.makeText(context,"Add Thanh Cong",Toast.LENGTH_SHORT).show();
        }

    }
    public Cursor readAllData()
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
    public Cursor searchTKB(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Xây dựng câu truy vấn SQL
        String query = "SELECT * FROM " + TABLE_THOIKHOABIEU + " WHERE " + COLUMN_MON + " LIKE ? OR " + COLUMN_PHONG + " LIKE ?";
        String[] selectionArgs = {"%" + keyword + "%", "%" + keyword + "%"};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Trả về con trỏ
        return cursor;
    }

    public boolean updateDataTime(Context context, int row_id, String mon, String thu, String ngay, String giangvien, String phong, String tiet, String diadiem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MON, mon);
        values.put(COLUMN_THU, thu);
        values.put(COLUMN_NGAY, ngay);
        values.put(COLUMN_GIANGVIEN, giangvien);
        values.put(COLUMN_PHONG, phong);
        values.put(COLUMN_TIET, tiet);
        values.put(COLUMN_DIADIEM, diadiem);

        // Log các giá trị đầu vào
        Log.d("updateDataTime", "Updating row_id: " + row_id + " with values: "
                + COLUMN_MON + "=" + mon + ", "
                + COLUMN_THU + "=" + thu + ", "
                + COLUMN_NGAY + "=" + ngay + ", "
                + COLUMN_GIANGVIEN + "=" + giangvien + ", "
                + COLUMN_PHONG + "=" + phong + ", "
                + COLUMN_TIET + "=" + tiet + ", "
                + COLUMN_DIADIEM + "=" + diadiem);

        // Cập nhật bảng và lưu số hàng bị ảnh hưởng vào biến result
        int result = db.update(TABLE_THOIKHOABIEU, values, COLUMN_ID + " = ?", new String[]{String.valueOf(row_id)});
        db.close();

        // Ghi log kết quả cập nhật
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


    public boolean deleteData(int row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa hàng dựa trên row_id
        int result = db.delete(TABLE_THOIKHOABIEU, COLUMN_ID + "=?", new String[]{String.valueOf(row_id)});
        db.close();

        // Trả về true nếu có ít nhất một hàng đã bị xóa, ngược lại trả về false
        return result > 0;
    }


}