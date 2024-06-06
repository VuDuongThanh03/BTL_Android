package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.btl_android.cong_viec.CongViecActivity;

/** @noinspection ALL*/
public class mainScreenActivity extends AppCompatActivity {

    LinearLayout congviec;
    LinearLayout monHocDuKien; // Thêm biến cho "Môn học dự kiến"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        congviec = findViewById(R.id.btnCongViec);

        congviec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreenActivity.this, CongViecActivity.class);
                startActivity(intent);
            }
        });
    }
}