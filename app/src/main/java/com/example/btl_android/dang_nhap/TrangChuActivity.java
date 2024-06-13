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

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;
import com.example.btl_android.cong_viec.CongViecActivity;
import com.example.btl_android.diem.DiemActivity;
import com.example.btl_android.hoc_phan_du_kien.HocPhanDuKienActivity;
import com.example.btl_android.thong_bao.ThongBaoActivity;

/**
 * @noinspection ALL
 */
public class TrangChuActivity extends AppCompatActivity {

    LinearLayout btnCongViec, btnHocPhan, btnDiem;
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

        btnCongViec = findViewById(R.id.btnCongViec);
        btnHocPhan = findViewById(R.id.btnHocPhan);
        btnDiem = findViewById(R.id.btnDiem);
        btnThongBao = findViewById(R.id.imgThongBao);

        btnCongViec.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, CongViecActivity.class);
            startActivity(intent);
        });

        btnDiem.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, DiemActivity.class);
            startActivityForResult(intent, 0);
        });

        btnHocPhan.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, HocPhanDuKienActivity.class);
            startActivity(intent);
        });

        btnThongBao.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, ThongBaoActivity.class);
            startActivityForResult(intent, 1);
        });
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