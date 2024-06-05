package com.example.btl_android.add_subject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;

import java.util.List;

public class AddSubject extends AppCompatActivity {
    private static final int ADD_SUBJECT_REQUEST_CODE = 1;
    private RecyclerView rvSubjects;
    private TextView tvTotalCredits;
    private List<Subject> subjectList;
    private SubjectAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvSubjects = findViewById(R.id.rvSubjects);
        tvTotalCredits = findViewById(R.id.tvTotalCredits);

        db = new DatabaseHelper(this);
        subjectList = db.getAllSubjects();

        adapter = new SubjectAdapter(subjectList);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        rvSubjects.setAdapter(adapter);

        updateTotalCredits();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_subject) {
            Intent intent = new Intent(this, AddSubjectHandle.class);
            startActivityForResult(intent, ADD_SUBJECT_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_SUBJECT_REQUEST_CODE && RESULT_OK == resultCode) {
            if (null != data) {
                final String name = data.getStringExtra("name");
                final String code = data.getStringExtra("code");
                final int credits = data.getIntExtra("credits", 0);
                final String semester = data.getStringExtra("semester");

                final Subject subject = new Subject(name, code, credits, semester);
                this.db.addSubject(subject);

                this.subjectList.clear();
                this.subjectList.addAll(this.db.getAllSubjects());
                this.adapter.notifyDataSetChanged();
                this.updateTotalCredits();
            }
        }
    }

    private void updateTotalCredits() {
        int totalCredits = 0;
        for (final Subject subject : this.subjectList) {
            totalCredits += subject.getCredits();
        }
        this.tvTotalCredits.setText("Tổng tín chỉ dự kiến: " + totalCredits);
    }
}