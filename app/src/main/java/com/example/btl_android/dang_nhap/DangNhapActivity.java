package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;
import com.example.btl_android.diem.Diem;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class DangNhapActivity extends AppCompatActivity {

    private Button dangnhap,dangky;
    private EditText edtusername, edtpassword;
    List<Diem> diemList;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_dang_nhap);

        dangnhap = this.findViewById(R.id.btnDangNhap);
        dangky = this.findViewById(R.id.btnDangKy);
        edtusername = this.findViewById(R.id.editTenTk);
        edtpassword = this.findViewById(R.id.editPassword);
        diemList = new ArrayList<>();


        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Intent intentDangKy = new Intent(this, DangKyActivity.class);
        dangky.setOnClickListener(v-> DangNhapActivity.this.startActivity(intentDangKy));
        DANG_NHAP();


    }
    private void DANG_NHAP(){
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getCount();
//                Intent intentDangNhap = new Intent(DangNhapActivity.this, TrangChuActivity.class);
//                startActivity(intentDangNhap);
                if(edtusername.length() == 0 || edtpassword.length() == 0){
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    String username = edtusername.getText().toString().trim();
                    String password = edtpassword.getText().toString().trim();
                    SinhVien sinhVien = CheckLogin(username,password);
                    if(sinhVien != null){
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        Intent intentDangNhap = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                        intentDangNhap.putExtra("maSV",sinhVien.getMaSV()+"");
                        intentDangNhap.putExtra("maCN",sinhVien.getMaChuyenNganh());
                        intentDangNhap.putExtra("tenSV",sinhVien.getTenSV());
                        intentDangNhap.putExtra("tenTK",sinhVien.getTenTK());
                        intentDangNhap.putExtra("matKhau",sinhVien.getMatKhau());
                        startActivity(intentDangNhap);
                        finish();
                    }
                    else{
                        Toast.makeText(DangNhapActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private SinhVien CheckLogin(String username, String password){
        String query = "SELECT * FROM SinhVien WHERE tenTk = ? AND matKhau = ?";
        String[] selectionArgs = {username, password};

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query,selectionArgs);

        if (cursor.moveToFirst()) {
            // Truy cập dữ liệu từ Cursor
            String maSv = cursor.getString(cursor.getColumnIndex("maSv"));
            int maCn = cursor.getInt(cursor.getColumnIndex("maCn"));
            String tenSv = cursor.getString(cursor.getColumnIndex("tenSv"));
            String tenTk = cursor.getString(cursor.getColumnIndex("tenTk"));
            String matKhau = cursor.getString(cursor.getColumnIndex("matKhau"));

            SinhVien user = new SinhVien(maSv,maCn, tenSv, tenTk, matKhau);
            cursor.close();
            return user;
        }
        // Đóng Cursor
        cursor.close();
        return null;
    }
}