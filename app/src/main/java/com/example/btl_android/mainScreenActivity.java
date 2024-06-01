package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class mainScreenActivity extends AppCompatActivity {

    LinearLayout congviec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        congviec = findViewById(R.id.congvieczone);
        congviec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreenActivity.this,congViecActivity.class);
                startActivity(intent);
            }
        });
    }
}