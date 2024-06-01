package com.example.btl_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class congViecAdapter extends BaseAdapter {

    public congViecAdapter(Context context, int layout, List<CongViec> congvieclist) {
        this.context = context;
        this.layout = layout;
        this.congvieclist = congvieclist;
    }

    private Context context;
    private int layout;
    private List<CongViec> congvieclist;
    @Override
    public int getCount() {
        return congvieclist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView tt = convertView.findViewById(R.id.tv_tenviec);
        TextView chitiet = convertView.findViewById(R.id.tv_chitietcv);
        TextView uutien = convertView.findViewById(R.id.tv_mucuutien);
        TextView gio = convertView.findViewById(R.id.tv_hancvgio);
        TextView ngay = convertView.findViewById(R.id.tv_hancvngay);

        CongViec congViecx = congvieclist.get(i);
        tt.setText(congViecx.getTenCongViec());
        chitiet.setText(congViecx.getChiTietCongViec());
        uutien.setText(congViecx.getMucUuTien());
        gio.setText(congViecx.getThoiHanGio());
        ngay.setText(congViecx.getThoiHanNgay());
        return convertView;
    }
}
