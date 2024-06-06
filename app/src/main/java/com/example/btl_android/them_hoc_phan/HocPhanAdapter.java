package com.example.btl_android.them_hoc_phan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.HocPhan;
import com.example.btl_android.R;

import java.util.List;

/** @noinspection ALL*/
public class HocPhanAdapter extends RecyclerView.Adapter<HocPhanAdapter.SubjectViewHolder> {
    private final List<HocPhan> hocPhans;

    public HocPhanAdapter(final List<HocPhan> hocPhans) {
        this.hocPhans = hocPhans;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrv_hoc_phan, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectViewHolder holder, final int position) {
        final HocPhan hocPhan = this.hocPhans.get(position);
        holder.bind(hocPhan);
    }

    @Override
    public int getItemCount() {
        return this.hocPhans.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenHp, tvMaHp, tvTongTinChi;

        public SubjectViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tvTenHp = itemView.findViewById(R.id.tvTenHp);
            this.tvMaHp = itemView.findViewById(R.id.tvMaHp);
            this.tvTongTinChi = itemView.findViewById(R.id.tvTongTinChi);
        }

        public void bind(final HocPhan hocPhan) {
            this.tvTenHp.setText("Môn: " + hocPhan.getTenHp());
            this.tvMaHp.setText("Mã học phần: " + hocPhan.getMaHp());
            this.tvTongTinChi.setText("Số tín chỉ: " + hocPhan.getSoTinChiLt());
        }
    }
}