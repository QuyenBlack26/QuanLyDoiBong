DROP DATABASE IF EXISTS QuanLyBongDa;
CREATE DATABASE QuanLyBongDa
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
 
USE QuanLyBongDa;
 
-- BẢNG 1: QUỐC GIA ------------------------------------------------------------------------------------------------------------
 
CREATE TABLE QuocGia (
    MaQG       CHAR(3)      PRIMARY KEY,
    TenQG      VARCHAR(100) NOT NULL
);
 
 
-- BẢNG 2: THÔNG TIN CLB (chỉ 1 dòng vì chỉ quản lý 1 đội)------------------------------------------------------------------------------------------------------------------------------
 
CREATE TABLE CauLacBo (
    MaCLB      VARCHAR(10)  PRIMARY KEY,
    TenCLB     VARCHAR(150) NOT NULL,
    MaQG       CHAR(3),
    NamThanhLap INT,
    SanNha     VARCHAR(150),
    HuanLuyenVien VARCHAR(100),
    FOREIGN KEY (MaQG) REFERENCES QuocGia(MaQG)
);
 
 
-- BẢNG 3: GIẢI ĐẤU (các giải mà CLB tham gia qua từng mùa)-----------------------------------------------------
 
CREATE TABLE GiaiDau (
    MaGiai     VARCHAR(10)  PRIMARY KEY,
    TenGiai    VARCHAR(150) NOT NULL,
    MaQG       CHAR(3),
    MuaGiai    CHAR(9)      NOT NULL,   -- vd: 2023-2024
    FOREIGN KEY (MaQG) REFERENCES QuocGia(MaQG)
);
 
 
-- BẢNG 4: CẦU THỦ-----------------------------------------------------------------------------------------------
 
CREATE TABLE CauThu (
    MaCT       VARCHAR(10)  PRIMARY KEY,
    HoTen      VARCHAR(100) NOT NULL,
    NgaySinh   DATE,
    MaQG       CHAR(3),                 -- quốc tịch
    ViTri      VARCHAR(30),             -- Thủ môn, Hậu vệ, Tiền vệ, Tiền đạo
    ChieuCao   INT,                     -- cm
    CanNang    INT,                     -- kg
    FOREIGN KEY (MaQG) REFERENCES QuocGia(MaQG)
);
 
 
-- BẢNG 5: HỢP ĐỒNG (Cầu thủ - CLB, không cần MaCLB vì chỉ có 1 CLB)-------------------------------------------------------------------------------------------------------
 
CREATE TABLE HopDong (
    MaHD       VARCHAR(10)  PRIMARY KEY,
    MaCT       VARCHAR(10)  NOT NULL,
    SoAo       INT,
    NgayBatDau DATE,
    NgayKetThuc DATE,
    FOREIGN KEY (MaCT)  REFERENCES CauThu(MaCT)
);
 
 
-- BẢNG 6: TRẬN ĐẤU (đối thủ chỉ lưu tên, không quản lý chi tiết CLB khác)--------------------------------------------------------------
 
CREATE TABLE TranDau (
    MaTran     VARCHAR(10)  PRIMARY KEY,
    MaGiai     VARCHAR(10)  NOT NULL,
    DoiNha     VARCHAR(150) NOT NULL,   -- tên đội đá sân nhà (CLB mình hoặc đối thủ)
    DoiKhach   VARCHAR(150) NOT NULL,   -- tên đội đá sân khách (CLB mình hoặc đối thủ)
    NgayThi    DATETIME,
    SanDau     VARCHAR(150),
    BanThangNha   INT DEFAULT 0,
    BanThangKhach INT DEFAULT 0,
    TrangThai  VARCHAR(20) DEFAULT 'Chưa đấu',  -- Chưa đấu / Đang đấu / Kết thúc
    FOREIGN KEY (MaGiai)     REFERENCES GiaiDau(MaGiai)
);
 
 
-- BẢNG 7: SỰ KIỆN TRẬN ĐẤU (bàn thắng, thẻ...)---------------------------------------------------
 
CREATE TABLE SuKienTran (
    MaSuKien   INT AUTO_INCREMENT PRIMARY KEY,
    MaTran     VARCHAR(10)  NOT NULL,
    MaCT       VARCHAR(10)  NOT NULL,
    LoaiSuKien VARCHAR(30)  NOT NULL,   -- Bàn thắng / Thẻ vàng / Thẻ đỏ / Kiến tạo
    Phut       INT,
    GhiChu     VARCHAR(200),
    FOREIGN KEY (MaTran) REFERENCES TranDau(MaTran),
    FOREIGN KEY (MaCT)   REFERENCES CauThu(MaCT)
);
 
 
-- BẢNG 8: BẢNG XẾP HẠNG (thành tích CLB mình theo từng giải, mỗi giải 1 dòng)-------------------------------------------------------------
 
CREATE TABLE BangXepHang (
    MaGiai     VARCHAR(10)  PRIMARY KEY,
    SoTran     INT DEFAULT 0,
    Thang      INT DEFAULT 0,
    Hoa        INT DEFAULT 0,
    Thua       INT DEFAULT 0,
    BanThang   INT DEFAULT 0,
    BanThua    INT DEFAULT 0,
    Diem       INT DEFAULT 0,
    FOREIGN KEY (MaGiai) REFERENCES GiaiDau(MaGiai)
);
 
-- BẢNG TÀI KHOẢN NHÂN VIÊN VÀ KHÁCH HÀNG
 
CREATE TABLE TAIKHOAN (
	MaTK       INT AUTO_INCREMENT PRIMARY KEY,
    TenDangNhap VARCHAR(50)  NOT NULL UNIQUE,
    MatKhau    VARCHAR(255) NOT NULL,        -- nên lưu mật khẩu đã mã hóa (hash)
    Email      VARCHAR(100) UNIQUE,
    HoTen      VARCHAR(100),
    VaiTro     ENUM('Admin', 'User') NOT NULL DEFAULT 'User'
);
-- DỮ LIỆU MẪU-------------------------------------------------------
 
 
-- Quốc gia
INSERT INTO QuocGia VALUES
('VIE', 'Việt Nam'),
('BRA', 'Brazil'),
('NGA', 'Nigeria'),
('KOR', 'Hàn Quốc');
 
-- Thông tin CLB (chỉ 1 dòng)
INSERT INTO CauLacBo VALUES
('HAN', 'Hà Nội FC', 'VIE', 2010, 'Sân Hàng Đẫy', 'Nguyễn Văn A');
 
-- Giải đấu
INSERT INTO GiaiDau VALUES
('VL1',    'V.League 1',      'VIE', '2023-2024'),
('CUPQG',  'Cúp Quốc Gia',    'VIE', '2023-2024');
 
-- Cầu thủ
INSERT INTO CauThu VALUES
('CT001', 'Đặng Văn Lâm',     '1993-06-13', 'VIE', 'Thủ môn',   187, 82),
('CT002', 'Quế Ngọc Hải',     '1993-12-20', 'VIE', 'Hậu vệ',    178, 74),
('CT003', 'Nguyễn Quang Hải', '1997-04-12', 'VIE', 'Tiền vệ',   168, 60),
('CT004', 'Tiến Linh',        '1997-03-25', 'VIE', 'Tiền đạo',  180, 74),
('CT005', 'Geovane Magno',    '1996-01-10', 'BRA', 'Tiền đạo',  175, 72);
 
-- Hợp đồng
INSERT INTO HopDong VALUES
('HD001', 'CT001', 1,  '2021-01-01', '2024-12-31'),
('HD002', 'CT002', 5,  '2020-01-01', '2024-12-31'),
('HD003', 'CT003', 19, '2023-01-01', '2026-12-31'),
('HD004', 'CT004', 9,  '2022-01-01', '2025-12-31'),
('HD005', 'CT005', 10, '2023-06-01', '2025-05-31');
 
-- Trận đấu (đội "Hà Nội FC" là CLB mình, còn lại là đối thủ)
INSERT INTO TranDau VALUES
('TD001', 'VL1',   'Hà Nội FC',   'TP.HCM FC',    '2024-02-10 18:00:00', 'Sân Hàng Đẫy', 1, 2, 'Kết thúc'),
('TD002', 'VL1',   'Viettel FC',  'Hà Nội FC',    '2024-02-17 18:00:00', 'Sân Hàng Đẫy', 0, 3, 'Kết thúc'),
('TD003', 'CUPQG', 'Hà Nội FC',   'Bình Định FC', '2024-03-05 19:00:00', 'Sân Hàng Đẫy', 2, 1, 'Kết thúc');
 
-- Sự kiện trận đấu
INSERT INTO SuKienTran (MaTran, MaCT, LoaiSuKien, Phut) VALUES
('TD001', 'CT003', 'Bàn thắng', 15),
('TD002', 'CT003', 'Bàn thắng', 20),
('TD002', 'CT004', 'Bàn thắng', 55),
('TD002', 'CT004', 'Bàn thắng', 78),
('TD002', 'CT002', 'Thẻ vàng',  60),
('TD003', 'CT005', 'Bàn thắng', 30),
('TD003', 'CT004', 'Bàn thắng', 66);
 
-- Bảng xếp hạng
INSERT INTO BangXepHang VALUES
('VL1',   2, 1, 0, 1, 4, 2, 3),
('CUPQG', 1, 1, 0, 0, 2, 1, 3);
 
INSERT INTO TAIKHOAN (TenDangNhap, MatKhau, Email, HoTen, VaiTro) VALUES
('admin',     '123456', 'admin@hanoifc.vn', 'Quản trị viên', 'Admin'),
('nguyenvana','123456', 'vana@gmail.com',   'Nguyễn Văn A',  'User');
 
 
-- ---------------------------------------------------------------------------------------------------------
USE QuanLyBongDa;
SELECT * FROM TAIKHOAN;
 
-- 1. Danh sách cầu thủ hiện tại + hợp đồng
 SELECT ct.HoTen, ct.ViTri, hd.SoAo, hd.NgayBatDau, hd.NgayKetThuc
 FROM CauThu ct
 JOIN HopDong hd ON ct.MaCT = hd.MaCT
 WHERE CURDATE() BETWEEN hd.NgayBatDau AND hd.NgayKetThuc;
 
-- 2. Bảng xếp hạng theo giải
 SELECT gd.TenGiai, bxh.SoTran, bxh.Thang, bxh.Hoa, bxh.Thua,
        bxh.BanThang, bxh.BanThua, (bxh.BanThang - bxh.BanThua) AS HieuSo, bxh.Diem
 FROM BangXepHang bxh
 JOIN GiaiDau gd ON bxh.MaGiai = gd.MaGiai
 ORDER BY bxh.Diem DESC, (bxh.BanThang - bxh.BanThua) DESC;
 
-- 3. Vua phá lưới
 SELECT ct.HoTen, COUNT(*) AS SoBanThang
 FROM SuKienTran sk
 JOIN CauThu ct ON sk.MaCT = ct.MaCT
 WHERE sk.LoaiSuKien = 'Bàn thắng'
 GROUP BY ct.MaCT, ct.HoTen -- gom theo từng cầu thủ
 ORDER BY SoBanThang DESC; -- sắp xếp ai ghi nhiều nhất lên đầu
 
-- ASC = tăng dần (mặc định)
-- DESC = giảm dần
 
-- 4. Lịch thi đấu
 SELECT td.NgayThi, td.DoiNha, td.BanThangNha,
        td.BanThangKhach, td.DoiKhach, gd.TenGiai
 FROM TranDau td
 JOIN GiaiDau gd ON td.MaGiai = gd.MaGiai
 ORDER BY td.NgayThi;
 