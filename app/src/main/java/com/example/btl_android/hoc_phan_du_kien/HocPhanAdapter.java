package com.example.btl_android.hoc_phan_du_kien;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.R;

import java.util.List;

/**
 * @noinspection ALL
 */
public class HocPhanAdapter extends RecyclerView.Adapter<HocPhanAdapter.HocPhanViewHolder> {

    private List<HocPhan> hocPhanList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public HocPhanAdapter(List<HocPhan> hocPhanList) {
        this.hocPhanList = hocPhanList;
    }

    @NonNull
    @Override
    public HocPhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoc_phan, parent, false);
        return new HocPhanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HocPhanAdapter.HocPhanViewHolder holder, int pos) {
        HocPhan hocPhan = hocPhanList.get(pos);
        holder.maHpTextView.setText(hocPhan.getMaHp());
        holder.tenHpTextView.setText(hocPhan.getTenHp());
        holder.soTinChiLyThuyetTextView.setText(String.valueOf(hocPhan.getSoTinChiLt()));
        holder.soTinChiThucHanhTextView.setText(String.valueOf(hocPhan.getSoTinChiLt()));
        holder.hocKyTextView.setText(String.valueOf(hocPhan.getHocKy()));
        holder.hinhThucThiTextView.setText(hocPhan.getHinhThucThi());
        holder.heSoTextView.setText(hocPhan.getHeSo());

        holder.cardView.setCardBackgroundColor(selectedPosition == pos ? Color.LTGRAY : Color.WHITE);

        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return hocPhanList.size();
    }

    public HocPhan getSelectedHocPhan() {
        return selectedPosition != RecyclerView.NO_POSITION ? hocPhanList.get(selectedPosition) : null;
    }

    public class HocPhanViewHolder extends RecyclerView.ViewHolder {

        TextView maHpTextView, tenHpTextView, soTinChiLyThuyetTextView,
                soTinChiThucHanhTextView, hocKyTextView, hinhThucThiTextView, heSoTextView;
        CardView cardView;

        public HocPhanViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(final HocPhan hocPhan) {
            maHpTextView = itemView.findViewById(R.id.maHpTextView);
            tenHpTextView = itemView.findViewById(R.id.tenHpTextView);
            soTinChiLyThuyetTextView = itemView.findViewById(R.id.soTinChiLyThuyetTextView);
            soTinChiThucHanhTextView = itemView.findViewById(R.id.soTinChiThucHanhTextView);
            hocKyTextView = itemView.findViewById(R.id.hocKyTextView);
            hinhThucThiTextView = itemView.findViewById(R.id.hinhThucThiTextView);
            heSoTextView = itemView.findViewById(R.id.heSoTextView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
