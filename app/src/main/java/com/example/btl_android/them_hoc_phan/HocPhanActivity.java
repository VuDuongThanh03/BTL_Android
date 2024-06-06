package com.example.btl_android.them_hoc_phan;

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
    private static final int ADD_SUBJECT_REQUEST_CODE = 1;
    private RecyclerView rvSubjects;
    private TextView tvTotalCredits;
    private TextView tvTotalPracticalCredits;
    private TextView tvTotalHeSo;
    private List<HocPhan> hocPhanList;
    private HocPhanAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_phan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvSubjects = findViewById(R.id.rvSubjects);
        tvTotalCredits = findViewById(R.id.tvTotalCredits);
        tvTotalPracticalCredits = findViewById(R.id.tvTotalPracticalCredits);
        tvTotalHeSo = findViewById(R.id.tvTotalHeSo);

        db = new DatabaseHelper(this);
        hocPhanList = db.getAllSubjects();

        adapter = new HocPhanAdapter(hocPhanList);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        rvSubjects.setAdapter(adapter);

        updateTotals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_subject) {
            Intent intent = new Intent(this, XuLyHocPhanActivity.class);
            startActivityForResult(intent, ADD_SUBJECT_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_SUBJECT_REQUEST_CODE && RESULT_OK == resultCode) {
            if (null != data) {
                final String tenHp = data.getStringExtra("tenHp");
                final String maHp = data.getStringExtra("maHp");
                final int soTinChiLyThuyet = data.getIntExtra("soTinChiLyThuyet", 0);
                final int soTinChiThucHanh = data.getIntExtra("soTinChiThucHanh", 0);
                final int hocKy = data.getIntExtra("hocKy", 0);
                final String hinhThucThi = data.getStringExtra("hinhThucThi");
                final String heSo = data.getStringExtra("heSo");

                final HocPhan hocPhan = new HocPhan(tenHp, maHp, soTinChiLyThuyet, soTinChiThucHanh, hocKy, hinhThucThi, heSo);
                this.db.addSubject(hocPhan);

                this.hocPhanList.clear();
                this.hocPhanList.addAll(this.db.getAllSubjects());
                this.adapter.notifyDataSetChanged();
                this.updateTotals();
            }
        }
    }

    private void updateTotals() {
        int totalCredits = 0;
        int totalPracticalCredits = 0;
        float totalHeSo = 0;

        for (final HocPhan hocPhan : this.hocPhanList) {
            totalCredits += hocPhan.getSoTinChiLyThuyet();
            totalPracticalCredits += hocPhan.getSoTinChiThucHanh();
            totalHeSo += Float.parseFloat(hocPhan.getHeSo());
        }

        this.tvTotalCredits.setText("Tổng tín chỉ lý thuyết: " + totalCredits);
        this.tvTotalPracticalCredits.setText("Tổng tín chỉ thực hành: " + totalPracticalCredits);
        this.tvTotalHeSo.setText("Tổng hệ số: " + totalHeSo);
    }
}