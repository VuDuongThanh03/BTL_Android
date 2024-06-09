package com.example.btl_android.diem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

/** @noinspection ALL*/
public class CapNhatDiemActivity extends AppCompatActivity {

    ImageButton btnQuayLai;
    Button btnCapNhat;
    EditText etTx1, etTx2, etGiuaKy, etCuoiKy;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cap_nhat_diem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        btnQuayLai = findViewById(R.id.imgQuayLai);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        etTx1 = findViewById(R.id.etTx1);
        etTx2 = findViewById(R.id.etTx2);
        etGiuaKy = findViewById(R.id.etGiuaKy);
        etCuoiKy = findViewById(R.id.etCuoiKy);

        btnQuayLai.setOnClickListener(v -> finish());
        btnCapNhat.setOnClickListener(v -> {
            Intent intent = getIntent();
            Diem diem = (Diem) intent.getSerializableExtra("Diem");
            boolean res = db.updateDiem(diem);
            if (res) {
                Toast.makeText(this, "Cập nhật điểm thành công", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}