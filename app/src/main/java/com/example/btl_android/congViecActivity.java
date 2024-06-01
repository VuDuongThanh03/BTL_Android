package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class congViecActivity extends AppCompatActivity {

    ListView lvcongviec;
    ArrayList<CongViec> listcongviec = new ArrayList<>();
    congViecAdapter cvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_viec);
        listcongviec.add(new CongViec("Ăn cơm","Lấy thìa múc cơm đổ vào mồm","Quan trọng","21:00","1/6/2024",0));
        listcongviec.add(new CongViec("Ăn cơm","Lấy thìa múc cơm đổ vào mồm","Quan trọng","21:00","1/6/2024",0));
        listcongviec.add(new CongViec("Ăn cơm","Lấy thìa múc cơm đổ vào mồm","Quan trọng","21:00","1/6/2024",0));
        listcongviec.add(new CongViec("Ăn cơm","Lấy thìa múc cơm đổ vào mồm","Quan trọng","21:00","1/6/2024",0));
        listcongviec.add(new CongViec("Ăn cơm","Lấy thìa múc cơm đổ vào mồm","Quan trọng","21:00","1/6/2024",0));
        listcongviec.add(new CongViec("Ăn cơm","Lấy thìa múc cơm đổ vào mồm","Quan trọng","21:00","1/6/2024",0));
        listcongviec.add(new CongViec("Ăn cơm","Lấy thìa múc cơm đổ vào mồm","Quan trọng","21:00","1/6/2024",0));
        showlvCongViec();
    }
    void showlvCongViec(){
        Context x = this;
        lvcongviec = findViewById(R.id.lvcongviec);

        cvAdapter = new congViecAdapter(x,R.layout.customlv_congviec,listcongviec);
        lvcongviec.setAdapter(cvAdapter);
    }
}