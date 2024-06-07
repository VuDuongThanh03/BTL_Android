package com.example.btl_android.hoc_phan_du_kien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.util.ArrayList;
import java.util.List;

public class HocPhanDuKien extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_HOCPHAN = 1;

    private RecyclerView recyclerView;
    private HocPhanAdapter hocPhanAdapter;
    private List<HocPhan> hocPhanList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_phan_du_kien);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewHocPhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hocPhanList = new ArrayList<>();
        loadHocPhanFromDatabase();

        hocPhanAdapter = new HocPhanAdapter(hocPhanList);
        recyclerView.setAdapter(hocPhanAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, ThemHocPhan.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_HOCPHAN);
                return true;
            case R.id.action_edit:
                // Handle edit action
                return true;
            case R.id.action_delete:
                // Handle delete action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_HOCPHAN && resultCode == RESULT_OK) {
            hocPhanList.clear();
            loadHocPhanFromDatabase();
            hocPhanAdapter.notifyDataSetChanged();
        }
    }

    private void loadHocPhanFromDatabase() {
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("SELECT * FROM HocPhan", null);
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
