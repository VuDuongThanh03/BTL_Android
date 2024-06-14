package com.example.btl_android.hoc_phan_du_kien;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

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

        recyclerView = findViewById(R.id.recyclerViewHocPhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        hocPhanList = databaseHelper.getAllHocPhan(); // Load all data initially
        hocPhanAdapter = new HocPhanAdapter(hocPhanList);
        recyclerView.setAdapter(hocPhanAdapter);

        setupSemesterButtons();
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
