package com.example.btl_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/** @noinspection ALL*/
public class LoginActivity extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.login = this.findViewById(R.id.btnLogin);
        final Intent intent = new Intent(this, ThongTinActivity.class);
        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LoginActivity.this.startActivity(intent);
            }
        });

    }
}