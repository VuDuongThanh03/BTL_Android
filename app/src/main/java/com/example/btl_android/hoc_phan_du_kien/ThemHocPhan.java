package com.example.btl_android.hoc_phan_du_kien;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

public class ThemHocPhan extends AppCompatActivity {

    private EditText maHpEditText, tenHpEditText, soTinChiLyThuyetEditText, soTinChiThucHanhEditText, hocKyEditText, hinhThucThiEditText, heSoEditText;
    private Button buttonCancel, buttonSubmit;

    private EditText soTietLyThuyetEditText, soTietThucHanhEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoc_phan);

        // Khởi tạo database helper
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ các view
        maHpEditText = findViewById(R.id.maHpEditText);
        tenHpEditText = findViewById(R.id.tenHpEditText);
        soTinChiLyThuyetEditText = findViewById(R.id.soTinChiLyThuyetEditText);
        soTinChiThucHanhEditText = findViewById(R.id.soTinChiThucHanhEditText);
        hocKyEditText = findViewById(R.id.hocKyEditText);
        hinhThucThiEditText = findViewById(R.id.hinhThucThiEditText);
        soTietLyThuyetEditText = findViewById(R.id.soTietLyThuyetEditText);
        soTietThucHanhEditText = findViewById(R.id.soTietThucHanhEditText);
        heSoEditText = findViewById(R.id.heSoEditText);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Thiết lập sự kiện click cho nút Hủy
        buttonCancel.setOnClickListener(v -> finish());

        // Thiết lập sự kiện click cho nút Thêm
        buttonSubmit.setOnClickListener(v -> submitForm());
    }

    private void submitForm() {
        // Lấy dữ liệu từ các EditText
        String maHp = maHpEditText.getText().toString();
        String tenHp = tenHpEditText.getText().toString();
        String soTinChiLt = soTinChiLyThuyetEditText.getText().toString();
        String soTinChiTh = soTinChiThucHanhEditText.getText().toString();
        String soTietLt = soTietLyThuyetEditText.getText().toString();
        String soTietTh = soTietThucHanhEditText.getText().toString();
        String hocKy = hocKyEditText.getText().toString();
        String hinhThucThi = hinhThucThiEditText.getText().toString();
        String heSo = heSoEditText.getText().toString();

        // Kiểm tra trường bỏ trống
        if (maHp.isEmpty() || tenHp.isEmpty() || soTinChiLt.isEmpty() || soTinChiTh.isEmpty() ||
                soTietLt.isEmpty() || soTietTh.isEmpty() || hocKy.isEmpty() || hinhThucThi.isEmpty() || heSo.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra mã học phần chưa tồn tại
        if (!databaseHelper.isMaHpUnique(maHp)) {
            Toast.makeText(this, "Mã học phần đã tồn tại", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra học kỳ hợp lệ
        int hocKyInt = Integer.parseInt(hocKy);
        if (hocKyInt < 1 || hocKyInt > 8) {
            Toast.makeText(this, "Học kỳ phải nằm trong khoảng từ 1 đến 8", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra định dạng hệ số
        if (!heSo.matches("\\d+-\\d+-\\d+")) {
            Toast.makeText(this, "Hệ số phải theo dạng x-y-z (x, y, z là các số nguyên dương)", Toast.LENGTH_LONG).show();
            return;
        }

        // Thêm vào cơ sở dữ liệu
        boolean result = databaseHelper.addHocPhan(new HocPhan(maHp, tenHp, Float.parseFloat(soTinChiLt), Float.parseFloat(soTinChiTh),
                Integer.parseInt(soTietLt), Integer.parseInt(soTietTh), hocKyInt,
                hinhThucThi, heSo));

        if (result) {
            Toast.makeText(this, "Thêm học phần thành công", Toast.LENGTH_LONG).show();
            finish(); // Đóng activity nếu thêm thành công
        } else {
            Toast.makeText(this, "Không thể thêm học phần", Toast.LENGTH_LONG).show();
        }
    }


}
