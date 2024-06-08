package com.example.btl_android.hoc_phan_du_kien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;
import com.example.btl_android.dang_nhap.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class HocPhanDuKien extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_HOCPHAN = 1;
    private static final int REQUEST_CODE_EDIT_HOCPHAN = 2;

    private RecyclerView recyclerView;
    private HocPhanAdapter hocPhanAdapter;
    private List<HocPhan> hocPhanList;
    private DatabaseHelper databaseHelper;
    private int selectedHocKy = -1;
    private Button[] hocKyButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_phan_du_kien);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HocPhanDuKien.this, TrangChuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewHocPhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hocPhanList = new ArrayList<>();

        hocPhanAdapter = new HocPhanAdapter(hocPhanList);
        recyclerView.setAdapter(hocPhanAdapter);

        setupHocKyButtons();
    }

    private void setupHocKyButtons() {
        hocKyButtons = new Button[8];
        for (int i = 1; i <= 8; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            hocKyButtons[i - 1] = findViewById(buttonId);
            final int hocKy = i;
            hocKyButtons[i - 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedHocKy = hocKy;
                    updateHocKyButtonColors();
                    hocPhanList.clear();
                    loadHocPhanFromDatabase();
                    hocPhanAdapter.notifyDataSetChanged();  
                }
            });
        }
    }

    private void updateHocKyButtonColors() {
        for (int i = 0; i < hocKyButtons.length; i++) {
            if (i == selectedHocKy - 1) {
                hocKyButtons[i].setBackgroundResource(R.drawable.button_selected);
                hocKyButtons[i].setTextColor(0xFFFFFFFF);
            } else {
                hocKyButtons[i].setBackgroundResource(R.drawable.button_border);
                hocKyButtons[i].setTextColor(0xFF000000);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        HocPhan selectedHocPhan = hocPhanAdapter.getSelectedHocPhan();
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent addIntent = new Intent(this, ThemHocPhan.class);
                startActivityForResult(addIntent, REQUEST_CODE_ADD_HOCPHAN);
                return true;
            case R.id.action_edit:
                if (selectedHocPhan != null) {
                    Intent editIntent = new Intent(this, ThemHocPhan.class);
                    editIntent.putExtra("hocPhan", selectedHocPhan);
                    startActivityForResult(editIntent, REQUEST_CODE_EDIT_HOCPHAN);
                } else {
                    Toast.makeText(this, "Vui lòng chọn môn học cần sửa", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_delete:
                if (selectedHocPhan != null) {
                    databaseHelper.deleteHocPhan(selectedHocPhan.getMaHp());
                    hocPhanList.clear();
                    loadHocPhanFromDatabase();
                    hocPhanAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Vui lòng chọn môn học cần xóa", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_ADD_HOCPHAN || requestCode == REQUEST_CODE_EDIT_HOCPHAN) && resultCode == RESULT_OK) {
            hocPhanList.clear();
            loadHocPhanFromDatabase();
            hocPhanAdapter.notifyDataSetChanged();
        }
    }

    private void loadHocPhanFromDatabase() {
        String query = "SELECT * FROM HocPhan";
        if (selectedHocKy != -1) {
            query += " WHERE hocKy = " + selectedHocKy;
        }

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(query, null);
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
    }
}
