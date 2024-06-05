package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;
import com.example.btl_android.cong_viec.CongViecActivity;

/** @noinspection ALL*/
public class TrangChuActivity extends AppCompatActivity {

    LinearLayout congviec;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_trang_chu);
        this.congviec = this.findViewById(R.id.congvieczone);
        this.congviec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(TrangChuActivity.this, CongViecActivity.class);
                TrangChuActivity.this.startActivity(intent);
            }
        });
    }
}