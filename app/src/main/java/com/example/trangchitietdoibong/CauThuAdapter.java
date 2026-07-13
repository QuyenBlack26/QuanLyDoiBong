package com.example.trangchitietdoibong;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CauThuAdapter extends RecyclerView.Adapter<CauThuAdapter.ViewHolder> {

    private List<CauThu> danhSach;

    public CauThuAdapter(List<CauThu> danhSach) {
        this.danhSach = danhSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cauthu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CauThu ct = danhSach.get(position);
        
        holder.tvHoTen.setText(ct.HoTen);
        holder.tvViTri.setText("Vị trí: " + ct.ViTri);

        // Sử dụng Glide để tải ảnh từ URL vào ImageView
        // Nếu ct.HinhAnh là đường dẫn (ví dụ: http://.../anh.jpg)
        Glide.with(holder.itemView.getContext())
             .load(ct.HinhAnh)
             .placeholder(android.R.drawable.ic_menu_gallery) // Ảnh hiển thị khi đang tải
             .error(android.R.drawable.ic_menu_report_image) // Ảnh hiển thị nếu lỗi
             .into(holder.ivHinhAnh);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChiTietCauThuActivity.class);
            intent.putExtra("cauthu", ct);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return danhSach != null ? danhSach.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoTen, tvViTri;
        ImageView ivHinhAnh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvViTri = itemView.findViewById(R.id.tvViTri);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
        }
    }
}
