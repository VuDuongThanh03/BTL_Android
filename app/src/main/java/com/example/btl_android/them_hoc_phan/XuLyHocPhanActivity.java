package com.example.btl_android.them_hoc_phan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.R;

public class XuLyHocPhanActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etCode;
    private EditText etCredits;
    private Spinner spinnerSemester;
    private Button btnAdd;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_ly_hoc_phan);

        etName = findViewById(R.id.etName);
        etCode = findViewById(R.id.etCode);
        etCredits = findViewById(R.id.etCredits);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddSubject();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleAddSubject() {
        String name = etName.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String creditsStr = etCredits.getText().toString().trim();
        String semester = spinnerSemester.getSelectedItem().toString(); // Lấy kỳ học được chọn từ Spinner

        if (name.isEmpty() || code.isEmpty() || creditsStr.isEmpty() || semester.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số tín chỉ phải là số nguyên", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thông báo thêm thành công
        Toast.makeText(this, "Thêm môn học thành công", Toast.LENGTH_SHORT).show();

        // Tạo Intent để gửi dữ liệu về Activity HocPhanActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("code", code);
        resultIntent.putExtra("credits", credits);
        resultIntent.putExtra("semester", semester);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
