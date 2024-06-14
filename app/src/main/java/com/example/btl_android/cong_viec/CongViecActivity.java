package com.example.btl_android.cong_viec;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @noinspection ALL
 */
public class CongViecActivity extends AppCompatActivity {

    ListView lvcongviec;
    ArrayList<CongViec> congViecArrayList = new ArrayList<>();
    CongViecAdapter cvAdapter;
    SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    int selectedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cong_viec);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        congViecArrayList = dbHelper.getAllCongViec();
        sortCongViecList(congViecArrayList);
        showlvCongViec();
//        registerForContextMenu(lvcongviec);
        lvcongviec.setClickable(true);
        lvcongviec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItemPosition = i;
                Toast.makeText(CongViecActivity.this, "a", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    void showlvCongViec() {
        Context x = this;
        lvcongviec = findViewById(R.id.lvcongviec);

        cvAdapter = new CongViecAdapter(x, R.layout.customlv_cong_viec, congViecArrayList);
        lvcongviec.setAdapter(cvAdapter);

//        registerForContextMenu(lvcongviec);
//        lvcongviec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedItemPosition = i;
//                Toast.makeText(x, ""+i, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
        lvcongviec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(x, ""+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_cong_viec, menu);
    }

    public void sortCongViecList(ArrayList<CongViec> congViecList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        ArrayList<CongViec> trangThai1List = new ArrayList<>();
        ArrayList<CongViec> trangThai0List = new ArrayList<>();

        for (CongViec cv : congViecList) {
            if (cv.trangThai == 1) {
                trangThai1List.add(cv);
            } else {
                trangThai0List.add(cv);
            }
        }
        for (int i = 0; i < trangThai0List.size() - 1; i++) {
            for (int j = i + 1; j < trangThai0List.size(); j++) {
                CongViec cv1 = trangThai0List.get(i);
                CongViec cv2 = trangThai0List.get(j);
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(cv1.thoiHanNgay + " " + cv1.thoiHanGio);
                    date2 = dateFormat.parse(cv2.thoiHanNgay + " " + cv2.thoiHanGio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date1 != null && date2 != null) {
                    if (date1.after(date2) || (date1.equals(date2) && Integer.parseInt(cv1.mucUuTien) < Integer.parseInt(cv2.mucUuTien))) {
                        trangThai0List.set(i, cv2);
                        trangThai0List.set(j, cv1);
                    }
                }
            }
        }

        congViecList.clear();
        congViecList.addAll(trangThai0List);
        congViecList.addAll(trangThai1List);
    }
}