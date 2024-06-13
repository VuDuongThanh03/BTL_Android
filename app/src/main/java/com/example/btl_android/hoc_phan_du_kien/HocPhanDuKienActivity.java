package com.example.btl_android.hoc_phan_du_kien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;
import com.example.btl_android.dang_nhap.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @noinspection ALL
 */
public class HocPhanDuKienActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HocPhanAdapter hocPhanAdapter;
    private List<HocPhan> hocPhanList;
    private DatabaseHelper databaseHelper;
    private int selectedHocKy = -1;
    private Button[] hocKyButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoc_phan_du_kien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.themDuLieuHocPhanMoiLan();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(HocPhanDuKienActivity.this, TrangChuActivity.class);
            startActivity(intent);
            finish();
        });

        recyclerView = findViewById(R.id.recyclerViewHocPhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hocPhanList = new ArrayList<>();
        hocPhanAdapter = new HocPhanAdapter(hocPhanList);
        recyclerView.setAdapter(hocPhanAdapter);

        setupHocKyButtons();
        loadHocPhanFromDatabase(); // Load dữ liệu ngay khi khởi tạo
    }

    private void setupHocKyButtons() {
        hocKyButtons = new Button[8];
        for (int i = 1; i <= 8; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            hocKyButtons[i - 1] = findViewById(buttonId);
            int hocKy = i;
            hocKyButtons[i - 1].setOnClickListener(v -> {
                selectedHocKy = hocKy;
                updateHocKyButtonColors();
                hocPhanList.clear();
                loadHocPhanFromDatabase();
                hocPhanAdapter.notifyDataSetChanged();
            });
        }
    }

    private void updateHocKyButtonColors() {
        for (int i = 0; i < hocKyButtons.length; i++) {
            if (i == selectedHocKy - 1) {
                hocKyButtons[i].setBackgroundResource(R.drawable.button_selected);
                hocKyButtons[i].setTextColor(0xFFFFFFFF);
            } else {
                hocKyButtons[i].setBackgroundResource(R.drawable.button_default1);
                hocKyButtons[i].setTextColor(0xFF000000);
            }
        }
    }

    private void loadHocPhanFromDatabase() {
        hocPhanList.clear();
        String query = "SELECT * FROM HocPhan";
        if (selectedHocKy != -1) {
            query += " WHERE hocKy = " + selectedHocKy;
        }
        Cursor cursor = databaseHelper.getWritableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String maHp = cursor.getString(cursor.getColumnIndex("maHp"));
                @SuppressLint("Range") String tenHp = cursor.getString(cursor.getColumnIndex("tenHp"));
                @SuppressLint("Range") int soTinChiLyThuyet = cursor.getInt(cursor.getColumnIndex("soTinChiLyThuyet"));
                @SuppressLint("Range") int soTinChiThucHanh = cursor.getInt(cursor.getColumnIndex("soTinChiThucHanh"));
                @SuppressLint("Range") int hocKy = cursor.getInt(cursor.getColumnIndex("hocKy"));
                @SuppressLint("Range") String hinhThucThi = cursor.getString(cursor.getColumnIndex("hinhThucThi"));
                @SuppressLint("Range") String heSo = cursor.getString(cursor.getColumnIndex("heSo"));

                HocPhan hocPhan = new HocPhan(maHp, tenHp, soTinChiLyThuyet, soTinChiThucHanh, hocKy, hinhThucThi, heSo);
                hocPhanList.add(hocPhan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        hocPhanAdapter.notifyDataSetChanged();
    }
}
