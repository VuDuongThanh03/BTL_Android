package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class thongTinActivity extends AppCompatActivity {

    Button btn_continue;
    Spinner spn_chuyennganh,spn_khoa;
    ArrayList<String> arrchuyennganh = new ArrayList<String>();
    ArrayAdapter<String> Adapterspn = null;
    ArrayList<String> arrkhoa = new ArrayList<String>();
    ArrayAdapter<String> Adapterspnkhoa = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        spn_chuyennganh = findViewById(R.id.spn_chuyennganh);
        spn_khoa = findViewById(R.id.spn_khoa);
        btn_continue = findViewById(R.id.buttoncontinue);
        SPINNER_CHUYENNGANH();
        SPINNER_KHOA();
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thongTinActivity.this,mainScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SPINNER_CHUYENNGANH(){
        arrchuyennganh.add("Công nghệ thông tin");
        arrchuyennganh.add("Khoa học máy tính");
        arrchuyennganh.add("Hệ thống thông tin");
        arrchuyennganh.add("Kỹ thuật phần mềm");

        Adapterspn = new ArrayAdapter<String>(thongTinActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                arrchuyennganh);
        spn_chuyennganh.setAdapter(Adapterspn);

        spn_chuyennganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void SPINNER_KHOA(){
        arrkhoa.add("K14");
        arrkhoa.add("K15");
        arrkhoa.add("K16");
        arrkhoa.add("K17");
        arrkhoa.add("K18");

        Adapterspnkhoa = new ArrayAdapter<String>(thongTinActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                arrkhoa);
        spn_khoa.setAdapter(Adapterspnkhoa);

        spn_khoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}