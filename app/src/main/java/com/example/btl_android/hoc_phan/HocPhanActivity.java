package com.example.btl_android.hoc_phan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.util.List;

/** @noinspection ALL*/
public class HocPhanActivity extends AppCompatActivity {
    private static final int THEMHOCPHAN_REQUEST_CODE = 1;
    private RecyclerView rvHocPhan;
    private TextView tvTongTinChi;
    private List<HocPhan> hocPhanList;
    private HocPhanAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_phan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvHocPhan = findViewById(R.id.rvDiemHp);
        tvTongTinChi = findViewById(R.id.tvTongTinChi);

        dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();

        hocPhanList = dbHelper.getAllSubjects();

        adapter = new HocPhanAdapter(hocPhanList);
        rvHocPhan.setLayoutManager(new LinearLayoutManager(this));
        rvHocPhan.setAdapter(adapter);

        updateTotalCredits();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnThemHp) {
            Intent intent = new Intent(this, CapNhatHocPhanActivity.class);
            startActivityForResult(intent, THEMHOCPHAN_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == THEMHOCPHAN_REQUEST_CODE && RESULT_OK == resultCode) {
            if (null != data) {
                final String tenHp = data.getStringExtra("tenHp");
                final String maHp = data.getStringExtra("maHp");
                final int soTinChiLyThuyet = data.getIntExtra("soTinChiLyThuyet", 0);
                final int hocKy = data.getIntExtra("hocKy", 0);

                final HocPhan hocPhan = new HocPhan(tenHp, maHp, soTinChiLyThuyet, hocKy);
                this.dbHelper.addSubject(hocPhan);

                this.hocPhanList.clear();
                this.hocPhanList.addAll(this.dbHelper.getAllSubjects());
                this.adapter.notifyDataSetChanged();
                this.updateTotalCredits();
            }
        }
    }

    private void updateTotalCredits() {
        int totalCredits = 0;
        for (final HocPhan hocPhan : this.hocPhanList) {
            totalCredits += hocPhan.getSoTietLt();
        }
        this.tvTongTinChi.setText("Tổng tín chỉ dự kiến: " + totalCredits);
    }
}