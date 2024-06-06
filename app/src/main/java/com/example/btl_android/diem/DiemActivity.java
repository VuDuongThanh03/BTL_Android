package com.example.btl_android.diem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.R;

/** @noinspection ALL*/
public class DiemActivity extends AppCompatActivity {
    ImageButton btnQuayLai, btnTongKet;
    RecyclerView rvHocPhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnQuayLai = findViewById(R.id.imageQuayLai);
        btnTongKet = findViewById(R.id.imageTongKet);

        btnQuayLai.setOnClickListener(v -> finish());
        btnTongKet.setOnClickListener(v -> {
            final Intent intent = new Intent(DiemActivity.this, TongKetActivity.class);
            DiemActivity.this.startActivity(intent);
        });
    }
}