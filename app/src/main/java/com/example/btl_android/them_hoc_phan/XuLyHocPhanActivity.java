package com.example.btl_android.them_hoc_phan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;

/** @noinspection ALL*/
public class XuLyHocPhanActivity extends AppCompatActivity {
    private EditText etTenHp;
    private EditText etMaHp;
    private EditText etSoTinChiLyThuyet;
    private EditText etHocKy;
    private Button btnThem;
    private Button btnHuy;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_xu_ly_hoc_phan);

        this.etTenHp = this.findViewById(R.id.etTenHp);
        this.etMaHp = this.findViewById(R.id.etMaHp);
        this.etSoTinChiLyThuyet = this.findViewById(R.id.etSoTinChiLyThuyet);
        this.etHocKy = this.findViewById(R.id.etHocKy);
        this.btnThem = this.findViewById(R.id.btnThem);
        this.btnHuy = this.findViewById(R.id.btnHuy);

        this.btnThem.setOnClickListener(v -> XuLyHocPhanActivity.this.handleAddSubject());

        this.btnHuy.setOnClickListener(v -> XuLyHocPhanActivity.this.finish());
    }

    private void handleAddSubject() {
        final String tenHp = this.etTenHp.getText().toString().trim();
        final String maHp = this.etMaHp.getText().toString().trim();
        final String creditsStr = this.etSoTinChiLyThuyet.getText().toString().trim();
        final String hocKy = this.etHocKy.getText().toString().trim();

        if (tenHp.isEmpty() || maHp.isEmpty() || creditsStr.isEmpty() || hocKy.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        final int soTinChiLyThuyet;
        try {
            soTinChiLyThuyet = Integer.parseInt(creditsStr);
        } catch (final NumberFormatException e) {
            Toast.makeText(this, "Số tín chỉ phải là số nguyên", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thông báo thêm thành công
        Toast.makeText(this, "Thêm môn học thành công", Toast.LENGTH_SHORT).show();

        // Tạo Intent để gửi dữ liệu về Activity HocPhanActivity
        final Intent resultIntent = new Intent();
        resultIntent.putExtra("tenHp", tenHp);
        resultIntent.putExtra("maHp", maHp);
        resultIntent.putExtra("soTinChiLyThuyet", soTinChiLyThuyet);
        resultIntent.putExtra("hocKy", hocKy);
        this.setResult(Activity.RESULT_OK, resultIntent);
        this.finish();
    }
}