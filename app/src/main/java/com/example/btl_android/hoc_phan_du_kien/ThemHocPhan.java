package com.example.btl_android.hoc_phan_du_kien;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.R;
import com.example.btl_android.DatabaseHelper;

public class ThemHocPhan extends AppCompatActivity {

    private EditText maHpEditText, tenHpEditText, soTinChiLyThuyetEditText, soTinChiThucHanhEditText, hocKyEditText, hinhThucThiEditText, heSoEditText;
    private Button buttonSubmit, buttonCancel;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_hoc_phan);

        // Initialize views
        maHpEditText = findViewById(R.id.maHpEditText);
        tenHpEditText = findViewById(R.id.tenHpEditText);
        soTinChiLyThuyetEditText = findViewById(R.id.soTinChiLyThuyetEditText);
        soTinChiThucHanhEditText = findViewById(R.id.soTinChiThucHanhEditText);
        hocKyEditText = findViewById(R.id.hocKyEditText);
        hinhThucThiEditText = findViewById(R.id.hinhThucThiEditText);
        heSoEditText = findViewById(R.id.heSoEditText);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonCancel = findViewById(R.id.buttonCancel);

        dbHelper = new DatabaseHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set button listeners
        buttonSubmit.setOnClickListener(view -> {
            if (validateInputs()) {
                // Create HocPhan object
                HocPhan hocPhan = new HocPhan(
                        maHpEditText.getText().toString(),
                        tenHpEditText.getText().toString(),
                        Integer.parseInt(soTinChiLyThuyetEditText.getText().toString()),
                        Integer.parseInt(soTinChiThucHanhEditText.getText().toString()),
                        Integer.parseInt(hocKyEditText.getText().toString()),
                        hinhThucThiEditText.getText().toString(),
                        heSoEditText.getText().toString()
                );

                // Insert HocPhan into database
                dbHelper.insertHocPhan(hocPhan);

                Toast.makeText(this, "Học phần đã được thêm!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonCancel.setOnClickListener(view -> finish());
    }

    private boolean validateInputs() {
        if (maHpEditText.getText().toString().isEmpty() ||
                tenHpEditText.getText().toString().isEmpty() ||
                soTinChiLyThuyetEditText.getText().toString().isEmpty() ||
                soTinChiThucHanhEditText.getText().toString().isEmpty() ||
                hocKyEditText.getText().toString().isEmpty() ||
                hinhThucThiEditText.getText().toString().isEmpty() ||
                heSoEditText.getText().toString().isEmpty()) {

            Toast.makeText(this, "Yêu cầu nhập tất cả các trường", Toast.LENGTH_SHORT).show();
            return false;
        }

        int soTinChiLyThuyet = Integer.parseInt(soTinChiLyThuyetEditText.getText().toString());
        int soTinChiThucHanh = Integer.parseInt(soTinChiThucHanhEditText.getText().toString());
        int hocKy = Integer.parseInt(hocKyEditText.getText().toString());

        if (soTinChiLyThuyet < 0 || soTinChiThucHanh < 0) {
            Toast.makeText(this, "Số tín chỉ không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hocKy < 1 || hocKy > 8) {
            Toast.makeText(this, "Học kỳ phải từ 1 đến 8", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
