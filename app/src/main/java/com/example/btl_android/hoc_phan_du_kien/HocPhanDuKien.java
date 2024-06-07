package com.example.btl_android.hoc_phan_du_kien;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.util.List;

public class HocPhanDuKien extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private LinearLayout infoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoc_phan_du_kien);

        dbHelper = new DatabaseHelper(this);
        infoContainer = findViewById(R.id.infoContainer);

        //Tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadHocPhan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, ThemHocPhan.class);
                startActivity(intent);
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

    private void loadHocPhan() {
        List<HocPhan> hocPhanList = dbHelper.getAllHocPhan();

        for (HocPhan hocPhan : hocPhanList) {
            LinearLayout hocPhanLayout = new LinearLayout(this);
            hocPhanLayout.setOrientation(LinearLayout.VERTICAL);
            hocPhanLayout.setPadding(16, 16, 16, 16);

            TextView maHpTextView = new TextView(this);
            maHpTextView.setText("Mã học phần: " + hocPhan.getMaHp());
            hocPhanLayout.addView(maHpTextView);

            TextView tenHpTextView = new TextView(this);
            tenHpTextView.setText("Tên học phần: " + hocPhan.getTenHp());
            hocPhanLayout.addView(tenHpTextView);

            TextView soTinChiLyThuyetTextView = new TextView(this);
            soTinChiLyThuyetTextView.setText("Số tín chỉ lý thuyết: " + hocPhan.getSoTinChiLyThuyet());
            hocPhanLayout.addView(soTinChiLyThuyetTextView);

            TextView soTinChiThucHanhTextView = new TextView(this);
            soTinChiThucHanhTextView.setText("Số tín chỉ thực hành: " + hocPhan.getSoTinChiThucHanh());
            hocPhanLayout.addView(soTinChiThucHanhTextView);

            TextView hocKyTextView = new TextView(this);
            hocKyTextView.setText("Học kỳ: " + hocPhan.getHocKy());
            hocPhanLayout.addView(hocKyTextView);

            TextView hinhThucThiTextView = new TextView(this);
            hinhThucThiTextView.setText("Hình thức thi: " + hocPhan.getHinhThucThi());
            hocPhanLayout.addView(hinhThucThiTextView);

            TextView heSoTextView = new TextView(this);
            heSoTextView.setText("Hệ số: " + hocPhan.getHeSo());
            hocPhanLayout.addView(heSoTextView);

            infoContainer.addView(hocPhanLayout);
        }
    }
}
