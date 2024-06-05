package com.example.btl_android.add_subject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;

/** @noinspection ALL*/
public class AddSubjectHandle extends AppCompatActivity {
    private EditText etName;
    private EditText etCode;
    private EditText etCredits;
    private EditText etSemester;
    private Button btnAdd;
    private Button btnCancel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_subject_handle);

        this.etName = this.findViewById(R.id.etName);
        this.etCode = this.findViewById(R.id.etCode);
        this.etCredits = this.findViewById(R.id.etCredits);
        this.etSemester = this.findViewById(R.id.etSemester);
        this.btnAdd = this.findViewById(R.id.btnAdd);
        this.btnCancel = this.findViewById(R.id.btnCancel);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AddSubjectHandle.this.handleAddSubject();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AddSubjectHandle.this.finish();
            }
        });
    }

    private void handleAddSubject() {
        final String name = this.etName.getText().toString().trim();
        final String code = this.etCode.getText().toString().trim();
        final String creditsStr = this.etCredits.getText().toString().trim();
        final String semester = this.etSemester.getText().toString().trim();

        if (name.isEmpty() || code.isEmpty() || creditsStr.isEmpty() || semester.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        final int credits;
        try {
            credits = Integer.parseInt(creditsStr);
        } catch (final NumberFormatException e) {
            Toast.makeText(this, "Số tín chỉ phải là số nguyên", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thông báo thêm thành công
        Toast.makeText(this, "Thêm môn học thành công", Toast.LENGTH_SHORT).show();

        // Tạo Intent để gửi dữ liệu về Activity AddSubject
        final Intent resultIntent = new Intent();
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("code", code);
        resultIntent.putExtra("credits", credits);
        resultIntent.putExtra("semester", semester);
        this.setResult(Activity.RESULT_OK, resultIntent);
        this.finish();
    }
}