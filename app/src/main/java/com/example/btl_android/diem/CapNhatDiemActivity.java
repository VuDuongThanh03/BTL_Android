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
    EditText etTx1, etTx2, etGiuaKy, etKiVong, etCuoiKy;
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

        getWidget();
        settupButtons();
    }

    private void getWidget() {
        btnQuayLai = findViewById(R.id.imgQuayLai);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        etTx1 = findViewById(R.id.etTx1);
        etTx2 = findViewById(R.id.etTx2);
        etGiuaKy = findViewById(R.id.etGiuaKy);
        etKiVong = findViewById(R.id.etKiVong);
        etCuoiKy = findViewById(R.id.etCuoiKy);
        db = new DatabaseHelper(this);
    }

    private void settupButtons() {
        btnQuayLai.setOnClickListener(v -> finish());
        btnCapNhat.setOnClickListener(v -> {
            Intent intent = getIntent();
            Diem diem = (Diem) intent.getSerializableExtra("Diem");

            String tx1 = etTx1.getText().toString();
            String tx2 = etTx2.getText().toString();
            String giuaKy = etGiuaKy.getText().toString();
            String kiVong = etKiVong.getText().toString();
            String cuoiKy = etCuoiKy.getText().toString();

            if (!isValid(tx1)) {
                Toast.makeText(this, "Điểm thường xuyên 1 không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValid(tx2)) {
                Toast.makeText(this, "Điểm thường xuyên 2 không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValid(giuaKy)) {
                Toast.makeText(this, "Điểm giữa kỳ không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValid(kiVong)) {
                Toast.makeText(this, "Điểm kỳ vọng không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValid(cuoiKy)) {
                Toast.makeText(this, "Điểm cuối kỳ không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            diem.setTx1(Float.parseFloat(tx1));
            diem.setTx2(Float.parseFloat(tx2));
            diem.setGiuaKy(Float.parseFloat(giuaKy));
            diem.setDiemKiVong(Float.parseFloat(kiVong));
            diem.setCuoiKy(Float.parseFloat(cuoiKy));

            boolean res = db.updateDiem(diem);
            if (res) {
                db.getAllScoreModules();
                Toast.makeText(this, "Cập nhật điểm thành công", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private boolean isValid(String diemStr) {
        if (diemStr.isEmpty()) {
            return false;
        }
        try {
            Float diem = Float.parseFloat(diemStr);
            if (0.0f <= diem && diem <= 10.0f) return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}