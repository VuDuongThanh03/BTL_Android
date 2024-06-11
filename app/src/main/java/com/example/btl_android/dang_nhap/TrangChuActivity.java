package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.R;
import com.example.btl_android.diem.DiemActivity;

/** @noinspection ALL*/
public class TrangChuActivity extends AppCompatActivity {

    LinearLayout btnDiem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_trang_chu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnDiem = findViewById(R.id.btnDiem);

        btnDiem.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActivity.this, DiemActivity.class);
            TrangChuActivity.this.startActivity(intent);
        });
    }
}