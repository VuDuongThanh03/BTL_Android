package com.example.btl_android.diem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.hoc_phan.HocPhan;
import com.example.btl_android.R;

/** @noinspection ALL*/
public class DiemChiTietActivity extends AppCompatActivity {
    private ImageButton btnQuayLai;
    private TextView tvTenHp, tvMaHp, tvSoTc, tvSoTietLt, tvSoTietTh, tvHinhThucThi, tvLop, tvHeSo, tvHocKy,
                     tvTx1, tvTx2, tvGiuaKy, tvCuoiKy, tvKiVong, tvTongKet, tvNghiLt, tvNghiTh, tvDieuKien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diem_chi_tiet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnQuayLai = findViewById(R.id.imageQuayLai);
        tvTenHp = findViewById(R.id.tvTenHp);
        tvMaHp = findViewById(R.id.tvMaHp);
        tvSoTc = findViewById(R.id.tvSoTc);
        tvSoTietLt = findViewById(R.id.tvSoTietLt);
        tvSoTietTh = findViewById(R.id.tvSoTietTh);
        tvHinhThucThi = findViewById(R.id.tvHinhThucThi);
        tvLop = findViewById(R.id.tvLop);
        tvHeSo = findViewById(R.id.tvHeSo);
        tvHocKy = findViewById(R.id.tvHocKy);
        tvTx1 = findViewById(R.id.tvTx1);
        tvTx2 = findViewById(R.id.tvTx2);
        tvGiuaKy = findViewById(R.id.tvGiuaKy);
        tvCuoiKy = findViewById(R.id.tvCuoiKy);
        tvKiVong = findViewById(R.id.tvKiVong);
        tvTongKet = findViewById(R.id.tvTongKet);
        tvNghiLt = findViewById(R.id.tvNghiLt);
        tvNghiTh = findViewById(R.id.tvNghiTh);
        tvDieuKien = findViewById(R.id.tvDieuKien);

        btnQuayLai.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        HocPhan hocPhan = (HocPhan) intent.getSerializableExtra("DiemChiTiet");
        tvTenHp.setText(hocPhan.getTenHp());
        tvMaHp.setText(hocPhan.getMaHp());
        tvSoTc.setText(String.valueOf(hocPhan.getSoTinChiLt() + hocPhan.getSoTinChiTh()));
        tvSoTietLt.setText(String.valueOf(hocPhan.getSoTietLt()));
        tvSoTietTh.setText(String.valueOf(hocPhan.getSoTietTh()));
        tvHinhThucThi.setText(hocPhan.getHinhThucThi());
        tvLop.setText(hocPhan.getLop());
        tvHeSo.setText(hocPhan.getHeSo());
        tvHocKy.setText(String.valueOf(hocPhan.getHocKy()));
        tvTx1.setText(hocPhan.getTx1() != null ? String.valueOf(hocPhan.getTx1()) : "N/A");
        tvTx2.setText(hocPhan.getTx2() != null ? String.valueOf(hocPhan.getTx2()) : "N/A");
        tvGiuaKy.setText(hocPhan.getGiuaKy() != null ? String.valueOf(hocPhan.getGiuaKy()) : "N/A");
        tvCuoiKy.setText(hocPhan.getCuoiKy() != null ? String.valueOf(hocPhan.getCuoiKy()) : "N/A");
        tvKiVong.setText(hocPhan.getDiemKyVong() != null ? String.valueOf(hocPhan.getDiemKyVong()) : "N/A");
        tvTongKet.setText(hocPhan.getDiemTongKet());
        tvNghiLt.setText(String.valueOf(hocPhan.getVangLt()));
        tvNghiTh.setText(String.valueOf(hocPhan.getVangTh()));
        tvDieuKien.setText(hocPhan.getDieuKien());
    }
}