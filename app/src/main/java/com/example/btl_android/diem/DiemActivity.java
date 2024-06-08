package com.example.btl_android.diem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.OnItemClickListener;
import com.example.btl_android.R;
import com.example.btl_android.hoc_phan.HocPhan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** @noinspection ALL*/
public class DiemActivity extends AppCompatActivity implements OnItemClickListener {
    private DatabaseHelper db;
    private ImageButton btnQuayLai, btnTongKet;
    private RecyclerView rvHocKy, rvDiemHp;
    List<String> hocKyList;
    List<HocPhan> hocPhanList;
    private HocKyAdapter hocKyAdapter;
    private DiemAdapter diemHpAdapter;
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

        db = new DatabaseHelper(this);

        btnQuayLai = findViewById(R.id.imageQuayLai);
        btnTongKet = findViewById(R.id.imageTongKet);
        rvHocKy = findViewById(R.id.rvHocKy);
        rvDiemHp = findViewById(R.id.rvDiemHp);
        hocKyList = new ArrayList<>();
        hocPhanList = new ArrayList<>();

        btnQuayLai.setOnClickListener(v -> finish());

        btnTongKet.setOnClickListener(v -> {
            final Intent intent = new Intent(DiemActivity.this, TongKetActivity.class);
            DiemActivity.this.startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                                                LinearLayoutManager.HORIZONTAL, false);
        rvHocKy.setLayoutManager(layoutManager);

        rvDiemHp.setLayoutManager(new LinearLayoutManager(this));

        hocKyList = Arrays.asList("Học kỳ 8", "Học kỳ 7", "Học kỳ 6", "Học kỳ 5", "Học kỳ 4", "Học kỳ 3", "Học kỳ 2", "Học kỳ 1");
        hocKyAdapter = new HocKyAdapter(hocKyList, this, R.id.rvHocKy);
        rvHocKy.setAdapter(hocKyAdapter);
    }

    @Override
    public void onItemClick(View view, int pos, int id) {
        switch (id) {
            case R.id.rvHocKy:
                String hocKy = hocKyList.get(pos);
                hocKy = Character.toString(hocKy.charAt(hocKy.length() - 1));
                hocPhanList = db.getSubjectsBySemester(hocKy);
                if (hocPhanList.isEmpty()) {
                    Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
                diemHpAdapter = new DiemAdapter(hocPhanList, this, R.id.rvDiemHp);
                rvDiemHp.setAdapter(diemHpAdapter);
                break;
            case R.id.rvDiemHp:
                HocPhan hp = hocPhanList.get(pos);
                Intent intent = new Intent(DiemActivity.this, DiemChiTietActivity.class);
                intent.putExtra("DiemChiTiet", hp);
                DiemActivity.this.startActivity(intent);
                break;
        }
    }
}