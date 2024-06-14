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
 */public class HocPhanDuKienActivity extends AppCompatActivity {

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

        recyclerView = findViewById(R.id.recyclerViewHocPhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        hocPhanList = databaseHelper.getAllHocPhan();  // Load data
        hocPhanAdapter = new HocPhanAdapter(hocPhanList);
        recyclerView.setAdapter(hocPhanAdapter);
    }


}
