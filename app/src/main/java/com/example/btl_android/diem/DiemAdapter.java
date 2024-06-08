package com.example.btl_android.diem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.hoc_phan.HocPhan;
import com.example.btl_android.OnItemClickListener;
import com.example.btl_android.R;

import java.util.List;

/** @noinspection ALL*/
public class DiemAdapter extends RecyclerView.Adapter<DiemAdapter.ViewHolder> {
    private List<HocPhan> data;
    private OnItemClickListener listener;
    private int id;

    public DiemAdapter(List<HocPhan> data, OnItemClickListener listener, int id) {
        this.data = data;
        this.listener = listener;
        this.id = id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        LinearLayout diemHp;
        public LinearLayout rvDiemHp;
        public TextView tvTenHp, btnSua, tvTx1, tvTx2, tvGiuaKy, tvCuoiKy, tvTongKet;

        public ViewHolder(View view) {
            super(view);
            tvTenHp = view.findViewById(R.id.tvTenHp);
            btnSua = view.findViewById(R.id.btnSua);
            tvTx1 = view.findViewById(R.id.tvTx1);
            tvTx2 = view.findViewById(R.id.tvTx2);
            tvGiuaKy = view.findViewById(R.id.tvGiuaKy);
            tvCuoiKy = view.findViewById(R.id.tvCuoiKy);
            tvTongKet = view.findViewById(R.id.tvTongKet);
            diemHp = view.findViewById(R.id.diemHp);

            diemHp.setOnClickListener(this);
            btnSua.setOnClickListener(v -> {
                Intent intent = new Intent((Context) listener, CapNhatDiemActivity.class);
                ((Context) listener).startActivity(intent);
            });
        }
        @Override
        public void onClick(View view) {
            if (listener != null) {
                int pos = (int) view.getTag();
                listener.onItemClick(view, pos, id);
            }
        }
    }

    @NonNull
    @Override
    public DiemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrv_diem_hoc_phan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        HocPhan item = data.get(pos);
        holder.tvTenHp.setText(item.getTenHp());
        holder.tvTx1.setText(String.format("%.1f", item.getTx1()));
        holder.tvTx2.setText(String.format("%.1f", item.getTx2()));
        holder.tvGiuaKy.setText(String.format("%.1f", item.getGiuaKy()));
        holder.tvCuoiKy.setText(String.format("%.1f", item.getCuoiKy()));
        holder.tvTongKet.setText(item.getDiemTongKet());
        holder.diemHp.setTag(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
