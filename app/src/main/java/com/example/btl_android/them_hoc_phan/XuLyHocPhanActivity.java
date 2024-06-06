package com.example.btl_android.them_hoc_phan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;

public class XuLyHocPhanActivity extends AppCompatActivity {
    private EditText etTenHp;
    private EditText etMaHp;
    private EditText etSoTinChiLyThuyet;
    private EditText etSoTinChiThucHanh;
    private Spinner spinnerHocKy;
    private EditText etHinhThucThi;
    private EditText etHeSo;
    private Button btnAdd;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_ly_hoc_phan);

        etTenHp = findViewById(R.id.etTenHp);
        etMaHp = findViewById(R.id.etMaHp);
        etSoTinChiLyThuyet = findViewById(R.id.etSoTinChiLyThuyet);
        etSoTinChiThucHanh = findViewById(R.id.etSoTinChiThucHanh);
        spinnerHocKy = findViewById(R.id.spinnerHocKy);
        etHinhThucThi = findViewById(R.id.etHinhThucThi);
        etHeSo = findViewById(R.id.etHeSo);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHocKy.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddSubject();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleAddSubject() {
        String tenHp = etTenHp.getText().toString().trim();
        String maHp = etMaHp.getText().toString().trim();
        String soTinChiLyThuyetStr = etSoTinChiLyThuyet.getText().toString().trim();
        String soTinChiThucHanhStr = etSoTinChiThucHanh.getText().toString().trim();
        String hocKyStr = spinnerHocKy.getSelectedItem().toString(); // Lấy kỳ học được chọn từ Spinner
        String hinhThucThi = etHinhThucThi.getText().toString().trim();
        String heSo = etHeSo.getText().toString().trim();

        if (tenHp.isEmpty() || maHp.isEmpty() || soTinChiLyThuyetStr.isEmpty() || soTinChiThucHanhStr.isEmpty() || hocKyStr.isEmpty() || hinhThucThi.isEmpty() || heSo.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int soTinChiLyThuyet;
        int soTinChiThucHanh;
        int hocKy;

        try {
            soTinChiLyThuyet = Integer.parseInt(soTinChiLyThuyetStr);
            soTinChiThucHanh = Integer.parseInt(soTinChiThucHanhStr);
            hocKy = Integer.parseInt(hocKyStr); // Giả sử Spinner hocKy chứa các giá trị số nguyên
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số tín chỉ và học kỳ phải là số nguyên", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thông báo thêm thành công
        Toast.makeText(this, "Thêm môn học thành công", Toast.LENGTH_SHORT).show();

        // Tạo Intent để gửi dữ liệu về Activity HocPhanActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("tenHp", tenHp);
        resultIntent.putExtra("maHp", maHp);
        resultIntent.putExtra("soTinChiLyThuyet", soTinChiLyThuyet);
        resultIntent.putExtra("soTinChiThucHanh", soTinChiThucHanh);
        resultIntent.putExtra("hocKy", hocKy);
        resultIntent.putExtra("hinhThucThi", hinhThucThi);
        resultIntent.putExtra("heSo", heSo);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}