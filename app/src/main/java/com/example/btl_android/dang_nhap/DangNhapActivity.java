package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;

/** @noinspection ALL*/
public class DangNhapActivity extends AppCompatActivity {
    public static EditText etTenTk;
    Button login;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_dang_nhap);
        this.login = this.findViewById(R.id.btnLogin);
        final Intent intent = new Intent(this, ThongTinActivity.class);
        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DangNhapActivity.this.startActivity(intent);
            }
        });
        etTenTk = findViewById(R.id.etTenTk);
    }
}