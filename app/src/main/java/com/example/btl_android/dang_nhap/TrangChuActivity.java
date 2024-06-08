package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;
import com.example.btl_android.cong_viec.CongViecActivity;
import com.example.btl_android.diem.DiemActivity;
import com.example.btl_android.hoc_phan.HocPhanDuKienActivity;

/** @noinspection ALL*/
public class TrangChuActivity extends AppCompatActivity {

    LinearLayout btnCongViec, btnDiem, btnHocPhan;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_trang_chu);

        btnCongViec = findViewById(R.id.btnCongViec);
        btnDiem = findViewById(R.id.btnDiem);
        btnHocPhan = findViewById(R.id.btnHocPhan);

        btnCongViec.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, CongViecActivity.class);
            TrangChuActivity.this.startActivity(intent);
        });

        btnDiem.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, DiemActivity.class);
            TrangChuActivity.this.startActivity(intent);
        });

        btnHocPhan.setOnClickListener(v -> {
            final Intent intent = new Intent(TrangChuActivity.this, HocPhanDuKienActivity.class);
            TrangChuActivity.this.startActivity(intent);
        });
    }
}