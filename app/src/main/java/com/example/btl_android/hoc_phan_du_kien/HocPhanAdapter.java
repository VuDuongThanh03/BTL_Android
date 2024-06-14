package com.example.btl_android.hoc_phan_du_kien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.R;

import java.util.List;

public class HocPhanAdapter extends RecyclerView.Adapter<HocPhanAdapter.HocPhanViewHolder> {
    private List<HocPhan> hocPhanList;

    public HocPhanAdapter(List<HocPhan> hocPhanList) {
        this.hocPhanList = hocPhanList;
    }

    @NonNull
    @Override
    public HocPhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrv_hoc_phan, parent, false);
        return new HocPhanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HocPhanViewHolder holder, int position) {
        HocPhan hocPhan = hocPhanList.get(position);
        holder.maHpTextView.setText(hocPhan.getMaHp());
        holder.tenHpTextView.setText(hocPhan.getTenHp());
        holder.soTinChiLyThuyetTextView.setText(String.valueOf(hocPhan.getSoTinChiLt()));
        holder.soTinChiThucHanhTextView.setText(String.valueOf(hocPhan.getSoTinChiTh()));
        holder.soTietLyThuyetTextView.setText(String.valueOf(hocPhan.getSoTietLt()));
        holder.soTietThucHanhTextView.setText(String.valueOf(hocPhan.getSoTietTh()));
        holder.hocKyTextView.setText(String.valueOf(hocPhan.getHocKy()));
        holder.hinhThucThiTextView.setText(hocPhan.getHinhThucThi());
        holder.heSoTextView.setText(hocPhan.getHeSo());
    }

    @Override
    public int getItemCount() {
        return hocPhanList.size();
    }

    public static class HocPhanViewHolder extends RecyclerView.ViewHolder {
        TextView maHpTextView, tenHpTextView, soTinChiLyThuyetTextView, soTinChiThucHanhTextView, soTietLyThuyetTextView, soTietThucHanhTextView, hocKyTextView, hinhThucThiTextView, heSoTextView;

        public HocPhanViewHolder(@NonNull View itemView) {
            super(itemView);
            maHpTextView = itemView.findViewById(R.id.maHpTextView);
            tenHpTextView = itemView.findViewById(R.id.tenHpTextView);
            soTinChiLyThuyetTextView = itemView.findViewById(R.id.soTinChiLyThuyetTextView);
            soTinChiThucHanhTextView = itemView.findViewById(R.id.soTinChiThucHanhTextView);
            soTietLyThuyetTextView = itemView.findViewById(R.id.soTietLtTextView);
            soTietThucHanhTextView = itemView.findViewById(R.id.soTietThTextView);
            hocKyTextView = itemView.findViewById(R.id.hocKyTextView);
            hinhThucThiTextView = itemView.findViewById(R.id.hinhThucThiTextView);
            heSoTextView = itemView.findViewById(R.id.heSoTextView);
        }
    }
}
