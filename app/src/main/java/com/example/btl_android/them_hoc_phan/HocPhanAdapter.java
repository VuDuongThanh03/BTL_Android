package com.example.btl_android.them_hoc_phan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        TextView tvName, tvCode, tvCredits;

        public SubjectViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvCode = itemView.findViewById(R.id.tvCode);
            this.tvCredits = itemView.findViewById(R.id.tvCredits);
        }

        public void bind(final HocPhan hocPhan) {
            this.tvName.setText("Môn: " + hocPhan.getName());
            this.tvCode.setText("Mã học phần: " + hocPhan.getCode());
            this.tvCredits.setText("Số tín chỉ: " + hocPhan.getCredits());
        }
    }
}