package com.example.btl_android.cong_viec;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * @noinspection ALL
 */
public class CongViecActivity extends AppCompatActivity {

    ListView lvcongviec;
    ArrayList<CongViec> listcongviec = new ArrayList<>();
    CongViecAdapter cvAdapter;
    SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_viec);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        listcongviec = dbHelper.getAllCongViec();
        sortCongViecList(listcongviec);
        showlvCongViec();
    }

    void showlvCongViec() {
        Context x = this;
        lvcongviec = findViewById(R.id.lvcongviec);

        cvAdapter = new CongViecAdapter(x, R.layout.customlv_cong_viec, listcongviec);
        lvcongviec.setAdapter(cvAdapter);
    }
    public void sortCongViecList(ArrayList<CongViec> congViecList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Collections.sort(congViecList, new Comparator<CongViec>() {
            @Override
            public int compare(CongViec cv1, CongViec cv2) {
                // Step 1: Compare by trangThai (1 should go to the end)
                if (cv1.trangThai != cv2.trangThai) {
                    return cv1.trangThai - cv2.trangThai;
                }

                // Combine thoiHanNgay and thoiHanGio into a single Date object for comparison
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(cv1.thoiHanNgay + " " + cv1.thoiHanGio);
                    date2 = dateFormat.parse(cv2.thoiHanNgay + " " + cv2.thoiHanGio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Step 2: Compare by date (closest to current date)
                if (date1 != null && date2 != null) {
                    int dateComparison = date1.compareTo(date2);
                    if (dateComparison != 0) {
                        return dateComparison;
                    }
                }

                // Step 3: Compare by thoiHanGio (largest to smallest) - already included in date comparison

                // Step 4: Compare by mucUuTien (largest to smallest)
                return Integer.parseInt(cv2.mucUuTien) - Integer.parseInt(cv1.mucUuTien);
            }
        });
    }
}