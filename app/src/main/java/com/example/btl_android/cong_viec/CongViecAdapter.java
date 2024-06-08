package com.example.btl_android.cong_viec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.btl_android.R;

import java.util.List;

/** @noinspection ALL*/
public class CongViecAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private final List<CongViec> congvieclist;
    public CongViecAdapter(final Context context, final int layout, final List<CongViec> congvieclist) {
        this.context = context;
        this.layout = layout;
        this.congvieclist = congvieclist;
    }

    @Override
    public int getCount() {
        return this.congvieclist.size();
    }

    @Override
    public Object getItem(final int i) {
        return null;
    }

    @Override
    public long getItemId(final int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, final ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(this.layout, null);
        final TextView tt = convertView.findViewById(R.id.tv_tenviec);
        final TextView chitiet = convertView.findViewById(R.id.tv_chitietcv);
        final TextView uutien = convertView.findViewById(R.id.tv_mucuutien);
        final TextView gio = convertView.findViewById(R.id.tv_hancvgio);
        final TextView ngay = convertView.findViewById(R.id.tv_hancvngay);

        final CongViec congViecx = this.congvieclist.get(i);
        tt.setText(congViecx.getTenCongViec());
        chitiet.setText(congViecx.getChiTietCongViec());
        uutien.setText(congViecx.getMucUuTien());
        gio.setText(congViecx.getThoiHanGio());
        ngay.setText(congViecx.getThoiHanNgay());
        return convertView;
    }
}