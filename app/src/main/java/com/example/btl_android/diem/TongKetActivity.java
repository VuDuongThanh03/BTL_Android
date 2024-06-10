package com.example.btl_android.diem;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @noinspection ALL
 */
public class TongKetActivity extends AppCompatActivity {
    private ImageButton btnQuayLai;
    private LineChart lineChart, stackedAreaChart;
    private BarChart barChart;
    private DatabaseHelper db;
    private int[][] diemChuByHocKy, diemSoByHocKy;

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

        getWidget();
        setupLineChart();
        setupBarChart();
        setupStackedAreaChart();

        btnQuayLai.setOnClickListener(v -> finish());
    }

    private void getWidget() {
        btnQuayLai = findViewById(R.id.imgQuayLai);
        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);
        stackedAreaChart = findViewById(R.id.stackedAreaChart);
        db = new DatabaseHelper(this);
    }

    private void setupLineChart() {
        diemChuByHocKy = new int[8][8];
        for (Diem diem : db.allDiemHpList) {
            int hocKy = diem.getHocKy() - 1;
            if (diem.getDiemChu().equals("-")) continue;
            switch (diem.getDiemChu()) {
                case "F":
                    diemChuByHocKy[0][hocKy]++;
                    break;
                case "D":
                    diemChuByHocKy[1][hocKy]++;
                    break;
                case "D+":
                    diemChuByHocKy[2][hocKy]++;
                    break;
                case "C":
                    diemChuByHocKy[3][hocKy]++;
                    break;
                case "C+":
                    diemChuByHocKy[4][hocKy]++;
                    break;
                case "B":
                    diemChuByHocKy[5][hocKy]++;
                    break;
                case "B+":
                    diemChuByHocKy[6][hocKy]++;
                    break;
                case "A":
                    diemChuByHocKy[7][hocKy]++;
                    break;
            }
        }

        int[] diemChu = new int[8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                diemChu[i] += diemChuByHocKy[i][j];
            }
        }

        List<Entry> diemChuEntries = new ArrayList<>();
        List<String> diemChuLabels = new ArrayList<>(Arrays.asList("F", "D", "D+", "C", "C+", "B", "B+", "A"));

        for (int i = 0; i < 8; i++) {
            diemChuEntries.add(new Entry(i, diemChu[i]));
        }

        LineDataSet lineData = new LineDataSet(diemChuEntries, "Điểm chữ");
        lineData.setColor(Color.rgb(104, 241, 175));
        lineData.setLineWidth(3f);
        lineData.setValueTextColor(Color.WHITE);
        lineData.setValueTextSize(0f);
        lineData.setValueFormatter(new DefaultValueFormatter(0));
        lineData.setDrawFilled(true);
        lineData.setFillColor(Color.rgb(100, 240, 170));

        lineChart.setData(new LineData(lineData));

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(diemChuLabels));
        lineChart.getXAxis().setTextSize(12f);
        lineChart.getXAxis().setTypeface(Typeface.DEFAULT_BOLD);
        lineChart.getXAxis().setYOffset(3f);

        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        lineChart.getAxisLeft().setLabelCount((int) lineChart.getData().getYMax());
        lineChart.getAxisLeft().setTextSize(12f);
        lineChart.getAxisLeft().setXOffset(12f);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.animateY(1000, Easing.EaseInOutQuad);

        MyMarkerView markerView = new MyMarkerView(this, R.layout.custom_marker_view, 0);
        markerView.setChartView(lineChart);
        lineChart.setMarker(markerView);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                lineChart.highlightValue(highlight);
            }

            @Override
            public void onNothingSelected() {
            }
        });

        lineChart.invalidate();
    }

    private void setupBarChart() {
        diemSoByHocKy = new int[8][8];
        for (Diem diem : db.allDiemHpList) {
            int hocKy = diem.getHocKy() - 1;
            Float diemSo = diem.getDiem10();
            if (diemSo == null) continue;
            if (diemSo < 4.0f) {
                diemSoByHocKy[0][hocKy]++;
            } else if (diemSo < 5.0f) {
                diemSoByHocKy[1][hocKy]++;
            } else if (diemSo < 6.0f) {
                diemSoByHocKy[2][hocKy]++;
            } else if (diemSo < 7.0f) {
                diemSoByHocKy[3][hocKy]++;
            } else if (diemSo < 8.0f) {
                diemSoByHocKy[4][hocKy]++;
            } else if (diemSo < 9.0f) {
                diemSoByHocKy[5][hocKy]++;
            } else if (diemSo < 10.0f) {
                diemSoByHocKy[6][hocKy]++;
            } else if (diemSo == 10.0f) {
                diemSoByHocKy[7][hocKy]++;
            }
        }

        int[] diemSo = new int[8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                diemSo[i] += diemSoByHocKy[i][j];
            }
        }

        List<BarEntry> diemSoEntries = new ArrayList<>();
        List<String> diemSoLabels = new ArrayList<>(Arrays.asList("<4", ">=4", ">=5", ">=6", ">=7", ">=8", ">=9", "10"));

        for (int i = 0; i < 8; i++) {
            diemSoEntries.add(new BarEntry(i, diemSo[i]));
        }

        BarDataSet barData = new BarDataSet(diemSoEntries, "Điểm số");
        barData.setColor(Color.rgb(104, 241, 175));
        barData.setValueTextColor(Color.WHITE);
        barData.setValueTextSize(0f);
        barData.setValueFormatter(new DefaultValueFormatter(0));

        barChart.setData(new BarData(barData));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setYOffset(-8f);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(diemSoLabels));
        barChart.getXAxis().setTextSize(12f);
        barChart.getXAxis().setTypeface(Typeface.DEFAULT_BOLD);
        barChart.getXAxis().setYOffset(3f);

        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setXOffset(12f);
        barChart.getAxisLeft().setTextSize(12f);
        barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        barChart.getAxisLeft().setLabelCount((int) barChart.getData().getYMax());
        barChart.getAxisRight().setEnabled(false);

        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000, Easing.EaseInOutQuad);

        MyMarkerView markerView = new MyMarkerView(this, R.layout.custom_marker_view, 1);
        markerView.setChartView(barChart);
        barChart.setMarker(markerView);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                barChart.highlightValue(highlight);
            }

            @Override
            public void onNothingSelected() {
            }
        });

        barChart.invalidate();
    }

    private void setupStackedAreaChart() {
        List<List<Pair<Float, Float>>> diemHpByHocKy = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            diemHpByHocKy.add(new ArrayList<>(8));
        }
        for (Diem diem : db.allDiemHpList) {
            int hocKy = diem.getHocKy() - 1;
            if (diem.getDiem4() == null) continue;
            Float diem4 = diem.getDiem4(), soTc = diem.getSoTinChiLt() + diem.getSoTinChiTh();
            if (diem4 == 0.0f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            } else if (diem4 == 1.0f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            } else if (diem4 == 1.5f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            } else if (diem4 == 2.0f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            } else if (diem4 == 2.5f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            } else if (diem4 == 3.0f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            } else if (diem4 == 3.5f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            } else if (diem4 == 4.0f) {
                diemHpByHocKy.get(hocKy).add(new Pair<>(diem4, soTc));
            }
        }
        float[] diemTkByHocKy = new float[8];
        for (int i = 0; i < 8; i++) {
            Float sumOfHpxTc = 0.0f, sumOfTc = 0.0f;
            for (Pair<Float, Float> hp : diemHpByHocKy.get(i)) {
                sumOfHpxTc += (hp.first * hp.second);
                sumOfTc += hp.second;
            }
            diemTkByHocKy[i] = (sumOfHpxTc / sumOfTc);
        }

        List<List<Entry>> entriesList = new ArrayList<>();

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            entries.add(new Entry(i, diemTkByHocKy[i]));
        }
        entriesList.add(entries);

        for (int i = 1; i <= 8; i++) {
            entriesList.add(new ArrayList<>());
            for (int j = 0; j < 8; j++) {
                Entry entry = new Entry(j, diemChuByHocKy[i - 1][j]);
                entriesList.get(i).add(entry);
            }
        }

        int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.CYAN, Color.LTGRAY, Color.GRAY, Color.BLACK, Color.WHITE};

        List<LineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < entriesList.size(); i++) {
            LineDataSet dataSet = new LineDataSet(entriesList.get(i), null);
            dataSet.setLineWidth(2f);
            dataSet.setColor(colors[i]);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(colors[i]);
            dataSet.setDrawCircles(false);
            dataSet.setValueTextColor(Color.WHITE);
            dataSet.setValueTextSize(12f);
            dataSet.setValueFormatter(new DefaultValueFormatter(0));
            dataSets.add(dataSet);
        }

        LineData stackedAreaData = new LineData();
        for (int i = 0; i < dataSets.size(); i++) {
            stackedAreaData.addDataSet(dataSets.get(i));
        }

        stackedAreaChart.setData(stackedAreaData);
        stackedAreaChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        stackedAreaChart.getXAxis().setDrawAxisLine(false);
        List<String> hocKyLables = new ArrayList<>(Arrays.asList("HK1", "HK2", "HK3", "HK4", "HK5", "HK6", "HK7", "HK8"));
        stackedAreaChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(hocKyLables));
        stackedAreaChart.getXAxis().setTextSize(12f);
        stackedAreaChart.getXAxis().setTypeface(Typeface.DEFAULT_BOLD);
        stackedAreaChart.getXAxis().setYOffset(3f);

        stackedAreaChart.getAxisLeft().setDrawAxisLine(false);
        stackedAreaChart.getAxisLeft().setAxisMinimum(0f);
        stackedAreaChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });
        stackedAreaChart.getAxisLeft().setLabelCount((int) stackedAreaChart.getData().getYMax());
        stackedAreaChart.getAxisLeft().setTextSize(12f);
        stackedAreaChart.getAxisLeft().setXOffset(12f);
        stackedAreaChart.getAxisRight().setEnabled(false);

        Legend legend = stackedAreaChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);

        List<String> legendLabels = Arrays.asList("F", "D", "D+", "C", "C+", "B", "B+", "A");
        List<LegendEntry> legendEntries = new ArrayList<>();
        for (int i = 0; i < legendLabels.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.label = legendLabels.get(i);
            entry.formColor = colors[i];
            legendEntries.add(entry);
        }
        legend.setCustom(legendEntries);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(12f);
        stackedAreaChart.getDescription().setEnabled(false);

        MyMarkerView markerView = new MyMarkerView(this, R.layout.custom_marker_view, 2);
        markerView.setChartView(stackedAreaChart);
        stackedAreaChart.setMarker(markerView);
        stackedAreaChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                stackedAreaChart.invalidate();
            }

            @Override
            public void onNothingSelected() {
            }
        });

        stackedAreaChart.invalidate();
    }

    public int[][] getDiemChuByHocKy() {
        return diemChuByHocKy;
    }

    public int[][] getDiemSoByHocKy() {
        return diemSoByHocKy;
    }
}