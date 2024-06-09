package com.example.btl_android.dang_nhap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.btl_android.R;

import java.util.ArrayList;

public class DangKyActivity extends AppCompatActivity {
    Button btn_dangky;
    Spinner spn_chuyennganh;
    ArrayList<String> arrchuyennganh = new ArrayList<String>();
    ArrayAdapter<String> Adapterspn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        this.spn_chuyennganh = this.findViewById(R.id.spn_chuyennganh);
        btn_dangky = this.findViewById(R.id.btnDangKy);
        this.SPINNER_CHUYENNGANH();
    }
    public void SPINNER_CHUYENNGANH() {
        this.arrchuyennganh.add("Công nghệ thông tin");
        this.arrchuyennganh.add("Khoa học máy tính");
        this.arrchuyennganh.add("Hệ thống thông tin");
        this.arrchuyennganh.add("Kỹ thuật phần mềm");

        this.Adapterspn = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                this.arrchuyennganh);
        this.spn_chuyennganh.setAdapter(this.Adapterspn);

        this.spn_chuyennganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {

            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
    }
}
