package com.example.btl_android.thoi_khoa_bieu;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;
import com.example.btl_android.hoc_phan_du_kien.HocPhan;

import java.util.ArrayList;
import java.util.List;

/**
 * @noinspection ALL
 */
// Activity để thêm lịch học
public class Add_Timetable extends AppCompatActivity {

    private Spinner spinnerMonHoc;
    private EditText thu, ngay, giangvien, phong, tiet, diadiem;
    private Button btnAdd, btnCancel;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);

        spinnerMonHoc = findViewById(R.id.spinnerMonHoc);
        thu = findViewById(R.id.thu);
        ngay = findViewById(R.id.ngay);
        giangvien = findViewById(R.id.giangvien);
        phong = findViewById(R.id.phong);
        tiet = findViewById(R.id.tiet);
        diadiem = findViewById(R.id.diadiem);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        myDB = new DatabaseHelper(this);

        loadMonHocData();

        btnAdd.setOnClickListener(v -> {
            if (areFieldsEmpty()) {
                Toast.makeText(Add_Timetable.this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            } else {
                String selectedMonHoc = spinnerMonHoc.getSelectedItem().toString();
                String thuText = thu.getText().toString().trim();
                String ngayText = ngay.getText().toString().trim();
                String giangvienText = giangvien.getText().toString().trim();
                String phongText = phong.getText().toString().trim();
                String tietText = tiet.getText().toString().trim();
                String diadiemText = diadiem.getText().toString().trim();

                boolean isInserted = myDB.insertLichHoc(
                        selectedMonHoc,
                        thuText,
                        ngayText,
                        giangvienText,
                        phongText,
                        tietText,
                        diadiemText
                );

                if (isInserted) {
                    Toast.makeText(Add_Timetable.this, "Thêm lịch học thành công.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Add_Timetable.this, "Thêm lịch học thất bại.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private void loadMonHocData() {
        List<String> monHocList = new ArrayList<>();
        List<HocPhan> hocPhanList = myDB.getAllHocPhan();

        for (HocPhan hocPhan : hocPhanList) {
            monHocList.add(hocPhan.getTenHp());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monHocList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonHoc.setAdapter(adapter);
    }

    private boolean areFieldsEmpty() {
        return thu.getText().toString().trim().isEmpty() ||
                ngay.getText().toString().trim().isEmpty() ||
                giangvien.getText().toString().trim().isEmpty() ||
                phong.getText().toString().trim().isEmpty() ||
                tiet.getText().toString().trim().isEmpty() ||
                diadiem.getText().toString().trim().isEmpty();
    }

}

