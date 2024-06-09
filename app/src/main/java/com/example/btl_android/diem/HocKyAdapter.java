package com.example.btl_android.diem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.OnItemClickListener;
import com.example.btl_android.R;

import java.util.List;

/** @noinspection ALL*/
public class HocKyAdapter extends RecyclerView.Adapter<HocKyAdapter.ViewHolder> {

    private List<String> data;
    private OnItemClickListener listener;
    private int id;
    private View lastHocKy;

    public HocKyAdapter(List<String> data, OnItemClickListener listener, int id) {
        this.data = data;
        this.listener = listener;
        this.id = id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public Button btnHocKy;

        public ViewHolder(View view) {
            super(view);
            btnHocKy = view.findViewById(R.id.btnHocKy);
            btnHocKy.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (listener != null) {
                int pos = (int) view.getTag();
                if (lastHocKy != null) {
                    lastHocKy.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.button_border));
                }
                view.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.button_selected));
                lastHocKy = view;
                listener.onItemClick(view, pos, id);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrv_hoc_ky, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        String item = data.get(pos);
        holder.btnHocKy.setText(item);
        holder.btnHocKy.setTag(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
