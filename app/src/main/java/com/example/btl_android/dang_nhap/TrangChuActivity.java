package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;
import com.example.btl_android.cong_viec.CongViecActivity;
import com.example.btl_android.hoc_phan_du_kien.HocPhanDuKien;


/** @noinspection ALL*/
public class TrangChuActivity extends AppCompatActivity {

    LinearLayout btnCongViec, btnHocPhan;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_trang_chu);

        btnCongViec = findViewById(R.id.btnCongViec);
        btnHocPhan = findViewById(R.id.btnHocPhan);

        btnCongViec.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, CongViecActivity.class);
            TrangChuActivity.this.startActivity(intent);
        });

        btnHocPhan.setOnClickListener(v -> {
            final Intent intent = new Intent(TrangChuActivity.this, HocPhanDuKien.class);
            TrangChuActivity.this.startActivity(intent);
        });
    }
}