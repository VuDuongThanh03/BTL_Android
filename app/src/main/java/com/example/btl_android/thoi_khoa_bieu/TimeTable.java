package com.example.btl_android.thoi_khoa_bieu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.btl_android.DatabaseHelper;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.btl_android.R;

import java.util.ArrayList;



public class TimeTable extends AppCompatActivity {


    DatabaseHelper myDB;
    TimeTableAdapter timeTableAdapter;
    ArrayList<String> tb_mon,tb_thu,tb_ngay,tb_giangvien,tb_phong,tb_tiet,tb_diadiem;
    ArrayList<Integer> tb_id;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar;
        //searchView=findViewById(R.id.searchPerson);
        // Set the content view before accessing any views
        setContentView(R.layout.activity_lich_hoc);

        // Initialize the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        String tab="";
        for (int i = 0; i < 15; i++) {
            tab += "\t";
        }
        actionBar.setTitle(tab+"Thời Khóa Biểu");

        EdgeToEdge.enable(this);

        // Apply window insets to the main view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myDB = new DatabaseHelper(TimeTable.this);
        tb_id=new ArrayList<>();
        tb_mon=new ArrayList<>();
        tb_thu=new ArrayList<>();
        tb_ngay=new ArrayList<>();
        tb_giangvien=new ArrayList<>();
        tb_phong=new ArrayList<>();
        tb_tiet=new ArrayList<>();
        tb_diadiem=new ArrayList<>();

        LuuTruData();


        timeTableAdapter = new TimeTableAdapter(TimeTable.this, tb_id,tb_mon,tb_thu,tb_ngay,tb_giangvien,tb_phong
                ,tb_tiet,tb_diadiem);
        Log.d("TimeTableAdapter", "tb_id: " + tb_id);


        recyclerView.setAdapter(timeTableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimeTable.this));
        searchView = findViewById(R.id.searchPerson);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý tìm kiếm khi người dùng nhấn nút tìm kiếm hoặc phím Enter
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý tìm kiếm ngay khi người dùng thay đổi văn bản trong SearchView
                search(newText);
                return true;
            }
        });
    }

    private void search(String keyword) {
        Cursor cursor = myDB.searchTKB(keyword);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            tb_id.clear();
            tb_mon.clear();
            tb_thu.clear();
            tb_ngay.clear();
            tb_giangvien.clear();
            tb_phong.clear();
            tb_tiet.clear();
            tb_diadiem.clear();

            while (cursor.moveToNext()) {
                tb_id.add(cursor.getInt(0));
                tb_mon.add(cursor.getString(1));
                tb_thu.add(cursor.getString(2));
                tb_ngay.add(cursor.getString(3));
                tb_giangvien.add(cursor.getString(4));
                tb_phong.add(cursor.getString(5));
                tb_tiet.add(cursor.getString(6));
                tb_diadiem.add(cursor.getString(7));
            }
            // Sau khi thêm dữ liệu mới, cập nhật RecyclerView hoặc ListView
            timeTableAdapter.notifyDataSetChanged(); // Giả sử adapter là adapter của bạn
        }
    }


    void LuuTruData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                tb_id.add(cursor.getInt(0));
                tb_mon.add(cursor.getString(1));
                tb_thu.add(cursor.getString(2));
                tb_ngay.add(cursor.getString(3));
                tb_giangvien.add(cursor.getString(4));
                tb_phong.add(cursor.getString(5));
                tb_tiet.add(cursor.getString(6));
                tb_diadiem.add(cursor.getString(7));
            }
        }
        // Đóng Cursor sau khi sử dụng
        cursor.close();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_timetable,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_timetable) {
            Intent intent = new Intent(this, Add_Timetable.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
