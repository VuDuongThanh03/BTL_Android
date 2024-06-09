package com.example.btl_android.diem;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_android.DatabaseHelper;
import com.example.btl_android.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;

/** @noinspection ALL*/
public class TongKetActivity extends AppCompatActivity {
    private ImageButton btnQuayLai;
    private LineChart lineChart;
    private BarChart barChart;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_tong_ket);
        ViewCompat.setOnApplyWindowInsetsListener(this.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnQuayLai = findViewById(R.id.imgQuayLai);
        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);
        db = new DatabaseHelper(this);

        btnQuayLai.setOnClickListener(v -> finish());

        setupLineChart();
        setupBarChart();
    }

    private void setupLineChart() {
        int[] diemChuList = new int[8];
        for (Diem diem : db.allDiemHpList) {
            switch (diem.getDiemChu()) {
                case "F":
                    diemChuList[0]++;
                    break;
                case "D":
                    diemChuList[1]++;
                    break;
                case "D+":
                    diemChuList[2]++;
                    break;
                case "C":
                    diemChuList[3]++;
                    break;
                case "C+":
                    diemChuList[4]++;
                    break;
                case "B":
                    diemChuList[5]++;
                    break;
                case "B+":
                    diemChuList[6]++;
                    break;
                case "A":
                    diemChuList[7]++;
                    break;
            }
        }
        for (int i = 0; i < 8; i++)
            Log.d("TongKetActivity", diemChuList[i] + "");

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(Arrays.asList("F", "D", "D+", "C", "C+", "B", "B+", "A"));

        for (int i = 0; i < 8; i++) {
            entries.add(new Entry(i, diemChuList[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Điểm chữ");
        dataSet.setColor(Color.CYAN);
        dataSet.setLineWidth(3f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLinesBehindData(true);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        lineChart.getXAxis().setTextSize(12f);
        lineChart.getXAxis().setTypeface(Typeface.DEFAULT_BOLD);
        lineChart.getXAxis().setYOffset(-8f);

        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisLeft().setTextSize(12f);
        lineChart.getAxisLeft().setXOffset(12f);

        lineChart.getAxisRight().setEnabled(false);

        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.animateY(1000, Easing.EaseInOutQuad);

        lineChart.invalidate();
    }

    private void setupBarChart() {
        int[] diemSoList = new int[8];
        for (Diem diem : db.allDiemHpList) {
            Float diemSo = diem.getDiem10();
            if (diemSo < 4.0f) {
                diemSoList[0]++;
            } else if (diemSo < 5.0f) {
                diemSoList[1]++;
            } else if (diemSo < 6.0f) {
                diemSoList[2]++;
            } else if (diemSo < 7.0f) {
                diemSoList[3]++;
            } else if (diemSo < 8.0f) {
                diemSoList[4]++;
            } else if (diemSo < 9.0f) {
                diemSoList[5]++;
            } else if (diemSo < 10.0f) {
                diemSoList[6]++;
            } else if (diemSo == 10.0f) {
                diemSoList[7]++;
            }
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(Arrays.asList("<4", ">=4", ">=5", ">=6", ">=7", ">=8", ">=9", "10"));

        for (int i = 0; i < 8; i++) {
            entries.add(new BarEntry(i, diemSoList[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Điểm số");
        dataSet.setColor(Color.rgb(104, 241, 175));
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLinesBehindData(true);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setTextSize(12f);
        barChart.getXAxis().setTypeface(Typeface.DEFAULT_BOLD);
        barChart.getXAxis().setYOffset(-8f);

        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisLeft().setTextSize(12f);
        barChart.getAxisLeft().setXOffset(12f);

        barChart.getAxisRight().setEnabled(false);

        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000, Easing.EaseInOutQuad);

        barChart.invalidate();
    }
}