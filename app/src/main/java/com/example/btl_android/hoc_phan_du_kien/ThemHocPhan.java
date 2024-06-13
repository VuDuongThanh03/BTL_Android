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

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

/**
 * @noinspection ALL
 */
public class ThemHocPhan extends AppCompatActivity {

    private EditText maHpEditText, tenHpEditText, soTinChiLyThuyetEditText, soTinChiThucHanhEditText, hocKyEditText, hinhThucThiEditText, heSoEditText;
    private Button buttonSubmit, buttonCancel;
    private DatabaseHelper dbHelper;
    private HocPhan hocPhanToEdit;

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

        // Check if editing an existing HocPhan
        hocPhanToEdit = (HocPhan) getIntent().getSerializableExtra("hocPhan");
        if (hocPhanToEdit != null) {
            maHpEditText.setText(hocPhanToEdit.getMaHp());
            maHpEditText.setEnabled(false); // Không cho phép chỉnh sửa mã học phần
            tenHpEditText.setText(hocPhanToEdit.getTenHp());
            soTinChiLyThuyetEditText.setText(String.valueOf(hocPhanToEdit.getSoTinChiLt()));
            soTinChiThucHanhEditText.setText(String.valueOf(hocPhanToEdit.getSoTinChiTh()));
            hocKyEditText.setText(String.valueOf(hocPhanToEdit.getHocKy()));
            hinhThucThiEditText.setText(hocPhanToEdit.getHinhThucThi());
            heSoEditText.setText(hocPhanToEdit.getHeSo());
        }

        // Set button listeners
        buttonSubmit.setOnClickListener(view -> {
            if (validateInputs()) {
                if (hocPhanToEdit == null) {
                    // Create HocPhan object
                    HocPhan hocPhan = new HocPhan(
                            maHpEditText.getText().toString(),
                            tenHpEditText.getText().toString(),
                            Float.parseFloat(soTinChiLyThuyetEditText.getText().toString()),
                            Float.parseFloat(soTinChiThucHanhEditText.getText().toString()),
                            Integer.parseInt(hocKyEditText.getText().toString()),
                            hinhThucThiEditText.getText().toString(),
                            heSoEditText.getText().toString()
                    );

                    // Insert HocPhan into database
                    if (dbHelper.isMaHpUnique(hocPhan.getMaHp())) {
                        dbHelper.insertHocPhan(hocPhan);
                        Toast.makeText(this, "Học phần đã được thêm!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(this, "Mã học phần đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Update HocPhan
                    hocPhanToEdit.setTenHp(tenHpEditText.getText().toString());
                    hocPhanToEdit.setSoTinChiLt(Float.parseFloat(soTinChiLyThuyetEditText.getText().toString()));
                    hocPhanToEdit.setSoTinChiTh(Float.parseFloat(soTinChiThucHanhEditText.getText().toString()));
                    hocPhanToEdit.setHocKy(Integer.parseInt(hocKyEditText.getText().toString()));
                    hocPhanToEdit.setHinhThucThi(hinhThucThiEditText.getText().toString());
                    hocPhanToEdit.setHeSo(heSoEditText.getText().toString());

                    dbHelper.updateHocPhan(hocPhanToEdit);
                    Toast.makeText(this, "Học phần đã được cập nhật!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        buttonCancel.setOnClickListener(view -> finish());
    }

    private boolean validateInputs() {
        String maHp = maHpEditText.getText().toString();
        String tenHp = tenHpEditText.getText().toString();
        String soTinChiLyThuyet = soTinChiLyThuyetEditText.getText().toString();
        String soTinChiThucHanh = soTinChiThucHanhEditText.getText().toString();
        String hocKy = hocKyEditText.getText().toString();
        String hinhThucThi = hinhThucThiEditText.getText().toString();
        String heSo = heSoEditText.getText().toString();

        if (maHp.isEmpty() || tenHp.isEmpty() || soTinChiLyThuyet.isEmpty() || soTinChiThucHanh.isEmpty() || hocKy.isEmpty() || hinhThucThi.isEmpty() || heSo.isEmpty()) {
            Toast.makeText(this, "Yêu cầu nhập tất cả các trường", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!heSo.matches("^\\d+-\\d+-\\d+$")) {
            Toast.makeText(this, "Hệ số phải có định dạng x-y-z với x, y, z là các số", Toast.LENGTH_SHORT).show();
            return false;
        }

        float soTinChiLyThuyetFloat = Float.parseFloat(soTinChiLyThuyet);
        float soTinChiThucHanhFloat = Float.parseFloat(soTinChiThucHanh);
        int hocKyInt = Integer.parseInt(hocKy);

        if (soTinChiLyThuyetFloat < 0 || soTinChiThucHanhFloat < 0) {
            Toast.makeText(this, "Số tín chỉ không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hocKyInt < 1 || hocKyInt > 8) {
            Toast.makeText(this, "Học kỳ phải từ 1 đến 8", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
