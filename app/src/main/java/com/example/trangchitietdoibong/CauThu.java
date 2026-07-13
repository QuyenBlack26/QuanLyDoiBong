package com.example.trangchitietdoibong;

import java.io.Serializable;

// Lớp này dùng để định nghĩa các thuộc tính của một Cầu thủ
// Implements Serializable để có thể gửi dữ liệu giữa các màn hình (Activity)
public class CauThu implements Serializable {
    public String MaCT;      // Mã cầu thủ
    public String HoTen;     // Họ và tên
    public String NgaySinh;  // Ngày sinh
    public String MaQG;      // Quốc tịch (Mã quốc gia)
    public String ViTri;     // Vị trí thi đấu
    public String ChieuCao;  // Chiều cao
    public String CanNang;   // Cân nặng
    public String HinhAnh;   // Đường dẫn ảnh (sẽ dùng sau này)
}
