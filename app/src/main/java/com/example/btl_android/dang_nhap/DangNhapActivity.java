package com.example.btl_android.dang_nhap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;

/** @noinspection ALL*/
public class DangNhapActivity extends AppCompatActivity {

    Button login;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        this.setContentView(R.layout.activity_dang_nhap);

        login = this.findViewById(R.id.btnLogin);
        Intent intent = new Intent(this, ThongTinActivity.class);
        login.setOnClickListener(v -> DangNhapActivity.this.startActivity(intent));
    }
}