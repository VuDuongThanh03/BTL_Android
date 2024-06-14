package com.example.btl_android.hoc_phan_du_kien;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;
import com.example.btl_android.dang_nhap.TrangChuActivity;

import java.util.List;

public class HocPhanDuKienActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_HOCPHAN = 1;
    private static final int REQUEST_CODE_EDIT_HOCPHAN = 2;

    private RecyclerView recyclerView;
    private HocPhanAdapter hocPhanAdapter;
    private List<HocPhan> hocPhanList;
    private DatabaseHelper databaseHelper;
    private Button selectedButton = null; // Biến lưu nút hiện tại được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_phan_du_kien);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Thiết lập nút navigation quay về TrangChuActivity
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại TrangChuActivity
                Intent intent = new Intent(HocPhanDuKienActivity.this, TrangChuActivity.class);
                startActivity(intent);
                finish();  // Kết thúc activity hiện tại để người dùng không quay lại bằng nút back
            }
        });

        // Các thiết lập khác cho RecyclerView và các thành phần khác
        recyclerView = findViewById(R.id.recyclerViewHocPhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        hocPhanList = databaseHelper.getAllHocPhan(); // Load all data initially
        hocPhanAdapter = new HocPhanAdapter(hocPhanList);
        recyclerView.setAdapter(hocPhanAdapter);

        setupSemesterButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);  // Đảm bảo tên file menu đúng
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, ThemHocPhan.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_HOCPHAN);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setupSemesterButtons() {
        int[] buttonIds = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8};
        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            int finalI = i + 1;
            button.setOnClickListener(v -> filterBySemester(finalI, button));
        }
    }

    private void filterBySemester(int semester, Button button) {
        if (selectedButton != null) {
            // Đặt lại background cho nút trước đó
            selectedButton.setBackground(getResources().getDrawable(R.drawable.button_default1));
        }
        // Cập nhật nút mới được chọn
        selectedButton = button;
        selectedButton.setBackground(getResources().getDrawable(R.drawable.button_selected));

        // Lọc danh sách học phần theo học kỳ được chọn
        hocPhanList = databaseHelper.getHocPhanByHocKy(semester);
        hocPhanAdapter = new HocPhanAdapter(hocPhanList);
        recyclerView.setAdapter(hocPhanAdapter);
    }
}
