package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.util.ArrayList;

public class DangKyActivity extends AppCompatActivity {
    Button btn_dangky;
    Spinner spn_chuyennganh;
    ArrayList<String> arrchuyennganh = new ArrayList<String>();
    ArrayAdapter<String> Adapterspn;
    EditText edttentaikhoan, edtmatkhau, edthoten, edtmasinhvien;
    SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        this.spn_chuyennganh = this.findViewById(R.id.spn_chuyennganh);
        btn_dangky = this.findViewById(R.id.btnDangKy);
        edttentaikhoan = this.findViewById(R.id.editTenTkDk);
        edtmatkhau = this.findViewById(R.id.editPasswordDk);
        edthoten = this.findViewById(R.id.edt_hoten);
        edtmasinhvien = this.findViewById(R.id.edt_masv);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentaikhoan = edttentaikhoan.getText().toString().trim();
                String matkhau = edtmatkhau.getText().toString().trim();
                String hoten = edthoten.getText().toString().trim();
                String masinhvien = edtmasinhvien.getText().toString().trim();
                int chuyennganh = spn_chuyennganh.getSelectedItemPosition();
                if (tentaikhoan == "" || matkhau == "" || hoten == "" || masinhvien == "") {
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {

                } catch (Exception e) {
                }
                String insertquery = "INSERT INTO SinhVien (maSv, maCn, tenSv, tenTk, matKhau) VALUES " + "('" + masinhvien + "', " + chuyennganh + ", '" + hoten + "', '" + tentaikhoan + "', '" + matkhau + "')";
                db.execSQL(insertquery);
                db.close();
                Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });
        this.SPINNER_CHUYENNGANH();
    }

    public void SPINNER_CHUYENNGANH() {
        this.arrchuyennganh.add("Công nghệ thông tin");
        this.arrchuyennganh.add("Khoa học máy tính");
        this.arrchuyennganh.add("Hệ thống thông tin");
        this.arrchuyennganh.add("Kỹ thuật phần mềm");

        this.Adapterspn = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                this.arrchuyennganh);
        this.spn_chuyennganh.setAdapter(this.Adapterspn);

        this.spn_chuyennganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {

            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
    }
}
