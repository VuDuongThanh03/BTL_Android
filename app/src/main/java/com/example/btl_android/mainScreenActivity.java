package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.btl_android.add_subject.AddSubject;
import com.example.btl_android.add_subject.AddSubjectHandle;

public class mainScreenActivity extends AppCompatActivity {

    LinearLayout congviec;
    LinearLayout monHocDuKien; // Thêm biến cho "Môn học dự kiến"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        congviec = findViewById(R.id.congvieczone);
        monHocDuKien = findViewById(R.id.linearLayoutMonHocDuKien); // Liên kết biến với LinearLayout "Môn học dự kiến"

        congviec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreenActivity.this, congViecActivity.class);
                startActivity(intent);
            }
        });

        monHocDuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreenActivity.this, AddSubject.class);
                startActivity(intent); // Chuyển sang Activity AddSubject khi nhấn vào "Môn học dự kiến"
            }
        });
    }
}