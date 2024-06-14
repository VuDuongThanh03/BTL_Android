
package com.example.btl_android.thoi_khoa_bieu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.util.ArrayList;

/** @noinspection ALL*/
public class TimeTable extends AppCompatActivity {

    DatabaseHelper myDB;
    TimeTableAdapter timeTableAdapter;
    ArrayList<String> tb_mon, tb_thu, tb_ngay, tb_giangvien, tb_phong, tb_tiet, tb_diadiem;
    ArrayList<Integer> tb_id;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view before accessing any views
        setContentView(R.layout.activity_lich_hoc);

        // Initialize the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            String tab = "";
            for (int i = 0; i < 15; i++) {
                tab += "\t";
            }
            actionBar.setTitle(tab + "Lịch Học");
        }

        // Enable Edge to Edge
        EdgeToEdge.enable(this);

        // Apply window insets to the main view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the DatabaseHelper
        myDB = new DatabaseHelper(TimeTable.this);

        // Initialize the ArrayLists
        tb_id = new ArrayList<>();
        tb_mon = new ArrayList<>();
        tb_thu = new ArrayList<>();
        tb_ngay = new ArrayList<>();
        tb_giangvien = new ArrayList<>();
        tb_phong = new ArrayList<>();
        tb_tiet = new ArrayList<>();
        tb_diadiem = new ArrayList<>();

        // Load data from the database
        LuuTruData();

        // Initialize the TimeTableAdapter
        timeTableAdapter = new TimeTableAdapter(TimeTable.this, tb_id, tb_mon, tb_thu, tb_ngay, tb_giangvien, tb_phong, tb_tiet, tb_diadiem);
        Log.d("TimeTableAdapter", "tb_id: " + tb_id);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(timeTableAdapter);

        // Initialize the SearchView
        searchView = findViewById(R.id.searchPerson);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search when the user presses the search button or Enter key
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search as the user types
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
            // After adding new data, update the RecyclerView
            timeTableAdapter.notifyDataSetChanged();
        }
        cursor.close();
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
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
        return true;
    }

    @Override
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
