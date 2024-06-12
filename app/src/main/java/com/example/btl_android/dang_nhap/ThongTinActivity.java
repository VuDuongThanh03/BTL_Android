package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;

import java.util.ArrayList;

/**
 * @noinspection ALL
 */
public class ThongTinActivity extends AppCompatActivity {

    Button btn_continue;
    Spinner spn_chuyennganh, spn_khoa;
    ArrayList<String> arrchuyennganh = new ArrayList<String>();
    ArrayAdapter<String> Adapterspn;
    ArrayList<String> arrkhoa = new ArrayList<String>();
    ArrayAdapter<String> Adapterspnkhoa;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_thong_tin);

        this.spn_chuyennganh = this.findViewById(R.id.spn_chuyennganh);
        this.spn_khoa = this.findViewById(R.id.spn_khoa);
        this.btn_continue = this.findViewById(R.id.buttoncontinue);
        this.SPINNER_CHUYENNGANH();
        this.SPINNER_KHOA();
        this.btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(ThongTinActivity.this, TrangChuActivity.class);
                ThongTinActivity.this.startActivity(intent);
            }
        });
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

    public void SPINNER_KHOA() {
        this.arrkhoa.add("K14");
        this.arrkhoa.add("K15");
        this.arrkhoa.add("K16");
        this.arrkhoa.add("K17");
        this.arrkhoa.add("K18");

        this.Adapterspnkhoa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                this.arrkhoa);
        this.spn_khoa.setAdapter(this.Adapterspnkhoa);

        this.spn_khoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {

            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
    }
}