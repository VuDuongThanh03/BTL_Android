package com.example.btl_android.diem;

import android.content.Context;
import android.widget.TextView;

import com.example.btl_android.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/** @noinspection ALL*/
public class MyMarkerView extends MarkerView {

    private final TextView tvTitle;
    private final TextView tvKetQua;
    private int chartId;

    public MyMarkerView(Context context, int layoutResource, int chartId) {
        super(context, layoutResource);
        tvTitle = findViewById(R.id.tvTitle);
        tvKetQua = findViewById(R.id.tvKetQua);
        this.chartId = chartId;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int xIndex = (int) e.getX();
        if (chartId <= 1) {
            int [][] data = new int[8][8];
            if (chartId == 0) data = ((TongKetActivity) getContext()).getDiemChuByHocKy();
            else if (chartId == 1) data = ((TongKetActivity) getContext()).getDiemSoByHocKy();

            boolean isEmpty = true;
            for (int count : data[xIndex]) {
                if (count > 0) {
                    isEmpty = false;
                    break;
                }
            }

            if (!isEmpty) {
                StringBuilder info = new StringBuilder();
                if (data[xIndex][0] > 0) info.append("HK1: ").append(data[xIndex][0]);
                if (data[xIndex][1] > 0) {
                    if (info.length() > 0) info.append("\nHK2: ").append(data[xIndex][1]);
                    else info.append("HK2: ").append(data[xIndex][1]);
                }
                if (data[xIndex][2] > 0) {
                    if (info.length() > 0) info.append("\nHK3: ").append(data[xIndex][2]);
                    else info.append("HK3: ").append(data[xIndex][2]);
                }
                if (data[xIndex][3] > 0) {
                    if (info.length() > 0) info.append("\nHK4: ").append(data[xIndex][3]);
                    else info.append("HK4: ").append(data[xIndex][3]);
                }
                if (data[xIndex][4] > 0) {
                    if (info.length() > 0) info.append("\nHK5: ").append(data[xIndex][4]);
                    else info.append("HK5: ").append(data[xIndex][4]);
                }
                if (data[xIndex][5] > 0) {
                    if (info.length() > 0) info.append("\nHK6: ").append(data[xIndex][5]);
                    else info.append("HK6: ").append(data[xIndex][5]);
                }
                if (data[xIndex][6] > 0) {
                    if (info.length() > 0) info.append("\nHK7: ").append(data[xIndex][6]);
                    else info.append("HK7: ").append(data[xIndex][6]);
                }
                if (data[xIndex][7] > 0) {
                    if (info.length() > 0) info.append("\nHK8: ").append(data[xIndex][7]);
                    else info.append("HK8: ").append(data[xIndex][7]);
                }
                tvKetQua.setText(info.toString());
            } else {
                tvKetQua.setText("Không có\ndữ liệu");
            }
        } else {
            if (e instanceof BarEntry) {
                tvTitle.setText("Điểm tổng kết");
                if (e.getY() > 0) tvKetQua.setText("Học kỳ " + (xIndex + 1) + ": " + String.format("%.2f", e.getY()));
                else tvKetQua.setText("Không có\ndữ liệu");
            } else {
                tvTitle.setText("Số điểm HK" + (xIndex + 1));
                if (e.getY() > 0) tvKetQua.setText(String.format("%.0f", e.getY()));
                else tvKetQua.setText("Không có\ndữ liệu");
            }
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}

