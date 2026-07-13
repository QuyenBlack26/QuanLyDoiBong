package com.example.trangchitietdoibong;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ChiTietCauThuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cau_thu);

        // Nút quay lại (thoát)
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish()); // Đóng activity này để về màn hình trước

        // Lấy dữ liệu cầu thủ được gửi từ Adapter
        CauThu ct = (CauThu) getIntent().getSerializableExtra("cauthu");

        if (ct != null) {
            // Ánh xạ các view
            ImageView ivHinhAnh = findViewById(R.id.ivHinhAnhChiTiet);
            TextView tvHoTen = findViewById(R.id.tvHoTenChiTiet);
            TextView tvNgaySinh = findViewById(R.id.tvNgaySinhChiTiet);
            TextView tvQuocGia = findViewById(R.id.tvQuocGiaChiTiet);
            TextView tvViTri = findViewById(R.id.tvViTriChiTiet);
            TextView tvChieuCao = findViewById(R.id.tvChieuCaoChiTiet);
            TextView tvCanNang = findViewById(R.id.tvCanNangChiTiet);

            // Hiển thị ảnh bằng Glide
            Glide.with(this)
                 .load(ct.HinhAnh)
                 .placeholder(android.R.drawable.ic_menu_gallery)
                 .error(android.R.drawable.ic_menu_report_image)
                 .into(ivHinhAnh);

            // Đổ dữ liệu vào text
            tvHoTen.setText(ct.HoTen);
            tvNgaySinh.setText(ct.NgaySinh);
            tvQuocGia.setText(ct.MaQG);
            tvViTri.setText(ct.ViTri);
            tvChieuCao.setText(ct.ChieuCao + " cm");
            tvCanNang.setText(ct.CanNang + " kg");
        }
    }
}
