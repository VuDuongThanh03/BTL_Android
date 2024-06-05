package com.example.btl_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreenActivity extends AppCompatActivity {

    LinearLayout congviec;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_screen);
        this.congviec = this.findViewById(R.id.congvieczone);
        this.congviec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(MainScreenActivity.this, CongViecActivity.class);
                MainScreenActivity.this.startActivity(intent);
            }
        });
    }
}