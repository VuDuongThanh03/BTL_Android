package com.example.btl_android.add_subject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private final List<Subject> subjects;

    public SubjectAdapter(final List<Subject> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectViewHolder holder, final int position) {
        final Subject subject = this.subjects.get(position);
        holder.bind(subject);
    }

    @Override
    public int getItemCount() {
        return this.subjects.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCode, tvCredits;

        public SubjectViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvCode = itemView.findViewById(R.id.tvCode);
            this.tvCredits = itemView.findViewById(R.id.tvCredits);
        }

        public void bind(final Subject subject) {
            this.tvName.setText("Môn: " + subject.getName());
            this.tvCode.setText("Mã học phần: " + subject.getCode());
            this.tvCredits.setText("Số tín chỉ: " + subject.getCredits());
        }
    }
}