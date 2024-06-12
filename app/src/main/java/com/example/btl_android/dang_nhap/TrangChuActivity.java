package com.example.btl_android.dang_nhap;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.R;
import com.example.btl_android.diem.DiemActivity;
import com.example.btl_android.thong_bao.ThongBaoActivity;

/** @noinspection ALL*/
public class TrangChuActivity extends AppCompatActivity {

    LinearLayout btnDiem;
    ImageView btnThongBao;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_trang_chu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.trangChuActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnDiem = findViewById(R.id.btnDiem);
        btnThongBao = findViewById(R.id.imgThongBao);

        btnDiem.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, DiemActivity.class);
            startActivityForResult(intent, 0);
        });

        btnThongBao.setOnClickListener(v -> {
            btnThongBao.setBackgroundResource(R.drawable.bell);
            Intent intent = new Intent(TrangChuActivity.this, ThongBaoActivity.class);
            startActivityForResult(intent, 1);
        });
        deleteDatabase("QuanLyHocTapCaNhan.db");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            btnThongBao.setImageResource(R.drawable.bell_new_update);
        }
        if (requestCode == 1 && resultCode == 1) {
            btnThongBao.setImageResource(R.drawable.bell);
        }
    }
}