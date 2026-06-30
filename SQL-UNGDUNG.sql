
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
 

-- BẢNG 2: GIẢI ĐẤU------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE GiaiDau (
    MaGiai     VARCHAR(10)  PRIMARY KEY,
    TenGiai    VARCHAR(150) NOT NULL,
    MaQG       CHAR(3),
    MuaGiai    CHAR(9)      NOT NULL,   -- vd: 2023-2024
    FOREIGN KEY (MaQG) REFERENCES QuocGia(MaQG)
);
 

-- BẢNG 3: CÂU LẠC BỘ-----------------------------------------------------

CREATE TABLE CauLacBo (
    MaCLB      VARCHAR(10)  PRIMARY KEY,
    TenCLB     VARCHAR(150) NOT NULL,
    MaQG       CHAR(3),
    NamThanhLap INT,
    SanNha     VARCHAR(150),
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
 

-- BẢNG 5: HỢP ĐỒNG (Cầu thủ - CLB)-------------------------------------------------------------------------------------------------------

CREATE TABLE HopDong (
    MaHD       VARCHAR(10)  PRIMARY KEY,
    MaCT       VARCHAR(10)  NOT NULL,
    MaCLB      VARCHAR(10)  NOT NULL,
    SoAo       INT,
    NgayBatDau DATE,
    NgayKetThuc DATE,
    FOREIGN KEY (MaCT)  REFERENCES CauThu(MaCT),
    FOREIGN KEY (MaCLB) REFERENCES CauLacBo(MaCLB)
);
 

-- BẢNG 6: THAM GIA GIẢI ĐẤU (CLB - Giải)-----------------------------------------------------------------------------

CREATE TABLE ThamGiaGiai (
    MaCLB      VARCHAR(10),
    MaGiai     VARCHAR(10),
    PRIMARY KEY (MaCLB, MaGiai),
    FOREIGN KEY (MaCLB)  REFERENCES CauLacBo(MaCLB),
    FOREIGN KEY (MaGiai) REFERENCES GiaiDau(MaGiai)
);
 

-- BẢNG 7: TRẬN ĐẤU--------------------------------------------------------------------

CREATE TABLE TranDau (
    MaTran     VARCHAR(10)  PRIMARY KEY,
    MaGiai     VARCHAR(10)  NOT NULL,
    MaCLBNha   VARCHAR(10)  NOT NULL,
    MaCLBKhach VARCHAR(10)  NOT NULL,
    NgayThi    DATETIME,
    SanDau     VARCHAR(150),
    BanThangNha   INT DEFAULT 0,
    BanThangKhach INT DEFAULT 0,
    TrangThai  VARCHAR(20) DEFAULT 'Chưa đấu',  -- Chưa đấu / Đang đấu / Kết thúc
    FOREIGN KEY (MaGiai)     REFERENCES GiaiDau(MaGiai),
    FOREIGN KEY (MaCLBNha)   REFERENCES CauLacBo(MaCLB),
    FOREIGN KEY (MaCLBKhach) REFERENCES CauLacBo(MaCLB)
);
 

-- BẢNG 8: SỰ KIỆN TRẬN ĐẤU (bàn thắng, thẻ...)---------------------------------------------------

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
 

-- BẢNG 9: BẢNG XẾP HẠNG-------------------------------------------------------------

CREATE TABLE BangXepHang (
    MaGiai     VARCHAR(10),
    MaCLB      VARCHAR(10),
    SoTran     INT DEFAULT 0,
    Thang      INT DEFAULT 0,
    Hoa        INT DEFAULT 0,
    Thua       INT DEFAULT 0,
    BanThang   INT DEFAULT 0,
    BanThua    INT DEFAULT 0,
    Diem       INT DEFAULT 0,
    PRIMARY KEY (MaGiai, MaCLB),
    FOREIGN KEY (MaGiai) REFERENCES GiaiDau(MaGiai),
    FOREIGN KEY (MaCLB)  REFERENCES CauLacBo(MaCLB)
);
 
-- BẢNG TÀI KHOẢN NHÂN VIÊN VÀ KHÁCH HÀNH

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
('ENG', 'Anh'),
('ESP', 'Tây Ban Nha'),
('GER', 'Đức'),
('FRA', 'Pháp'),
('BRA', 'Brazil'),
('NOR', 'Na Uy'), 
('BEL', 'Bỉ'),
('ARG', 'Argentina'),
('POR', 'Bồ Đào Nha');
 
-- Giải đấu
INSERT INTO GiaiDau VALUES
('VL1',    'V.League 1',           'VIE', '2023-2024'),
('EPL',    'Ngoại Hạng Anh',       'ENG', '2023-2024'),
('LALIGA', 'La Liga',              'ESP', '2023-2024'),
('UCL',    'UEFA Champions League', NULL,  '2023-2024');
 
-- Câu lạc bộ
INSERT INTO CauLacBo VALUES
('HCMC',  'TP.HCM FC',            'VIE', 2016, 'Sân Thống Nhất'),
('HAN',   'Hà Nội FC',            'VIE', 2010, 'Sân Hàng Đẫy'),
('MAN',   'Viettel FC',           'VIE', 2019, 'Sân Hàng Đẫy'),
('MCI',   'Manchester City',      'ENG', 1880, 'Etihad Stadium'),
('MNU',   'Manchester United',    'ENG', 1878, 'Old Trafford'),
('ARS',   'Arsenal',              'ENG', 1886, 'Emirates Stadium'),
('FCB',   'FC Barcelona',         'ESP', 1899, 'Camp Nou'),
('RMA',   'Real Madrid',          'ESP', 1902, 'Santiago Bernabéu');
 
-- Cầu thủ
INSERT INTO CauThu VALUES
('CT001', 'Nguyễn Văn Toàn',  '1996-04-12', 'VIE', 'Tiền đạo',  172, 65),
('CT002', 'Đặng Văn Lâm',     '1993-06-13', 'VIE', 'Thủ môn',   187, 82),
('CT003', 'Quế Ngọc Hải',     '1993-12-20', 'VIE', 'Hậu vệ',    178, 74),
('CT004', 'Nguyễn Quang Hải', '1997-04-12', 'VIE', 'Tiền vệ',   168, 60),
('CT005', 'Tiến Linh',        '1997-03-25', 'VIE', 'Tiền đạo',  180, 74),
('CT006', 'Erling Haaland',   '2000-07-21', 'NOR', 'Tiền đạo',  194, 88),
('CT007', 'Kevin De Bruyne',  '1991-06-28', 'BEL', 'Tiền vệ',   181, 70),
('CT008', 'Pedri',            '2002-11-25', 'ESP', 'Tiền vệ',   174, 60),
('CT009', 'Vinicius Jr',      '2000-07-12', 'BRA', 'Tiền đạo',  176, 73),
('CT010', 'Lamine Yamal',     '2007-07-13', 'ESP', 'Tiền đạo',  180, 65);
 
-- Hợp đồng
INSERT INTO HopDong VALUES
('HD001', 'CT001', 'HCMC', 10, '2022-01-01', '2025-12-31'),
('HD002', 'CT002', 'HAN',   1, '2021-01-01', '2024-12-31'),
('HD003', 'CT003', 'HAN',   5, '2020-01-01', '2024-12-31'),
('HD004', 'CT004', 'HAN',   19,'2023-01-01', '2026-12-31'),
('HD005', 'CT005', 'MAN',   9, '2022-01-01', '2025-12-31'),
('HD006', 'CT006', 'MCI',   9, '2022-07-01', '2027-06-30'),
('HD007', 'CT007', 'MCI',   17,'2015-08-30', '2025-06-30'),
('HD008', 'CT008', 'FCB',   8, '2021-07-01', '2026-06-30'),
('HD009', 'CT009', 'RMA',   7, '2022-07-01', '2027-06-30'),
('HD010', 'CT010', 'FCB',   19,'2023-07-01', '2028-06-30');
 
-- Tham gia giải
INSERT INTO ThamGiaGiai VALUES
('HCMC', 'VL1'), ('HAN', 'VL1'), ('MAN', 'VL1'),
('MCI',  'EPL'), ('MNU', 'EPL'), ('ARS', 'EPL'),
('FCB',  'LALIGA'), ('RMA', 'LALIGA'),
('MCI',  'UCL'), ('FCB', 'UCL'), ('RMA', 'UCL'), ('ARS', 'UCL');
 
-- Trận đấu
INSERT INTO TranDau VALUES
('TD001', 'VL1',    'HCMC', 'HAN',  '2024-02-10 18:00:00', 'Sân Thống Nhất', 2, 1, 'Kết thúc'),
('TD002', 'VL1',    'HAN',  'MAN',  '2024-02-17 18:00:00', 'Sân Hàng Đẫy',  3, 0, 'Kết thúc'),
('TD003', 'EPL',    'MCI',  'MNU',  '2024-03-03 21:00:00', 'Etihad Stadium', 3, 1, 'Kết thúc'),
('TD004', 'EPL',    'ARS',  'MCI',  '2024-03-10 22:30:00', 'Emirates Stadium',0,1, 'Kết thúc'),
('TD005', 'LALIGA', 'FCB',  'RMA',  '2024-04-21 21:00:00', 'Camp Nou',       1, 3, 'Kết thúc'),
('TD006', 'UCL',    'MCI',  'RMA',  '2024-04-17 02:00:00', 'Etihad Stadium', 1, 1, 'Kết thúc'),
('TD007', 'VL1',    'MAN',  'HCMC', '2024-05-05 17:00:00', 'Sân Hàng Đẫy',  1, 2, 'Kết thúc');
 
-- Sự kiện trận đấu
INSERT INTO SuKienTran (MaTran, MaCT, LoaiSuKien, Phut) VALUES
('TD001', 'CT001', 'Bàn thắng', 23),
('TD001', 'CT005', 'Bàn thắng', 67),
('TD001', 'CT004', 'Bàn thắng', 71),
('TD001', 'CT003', 'Thẻ vàng',  55),
('TD002', 'CT004', 'Bàn thắng', 15),
('TD002', 'CT001', 'Bàn thắng', 40),
('TD002', 'CT004', 'Bàn thắng', 88),
('TD003', 'CT006', 'Bàn thắng', 18),
('TD003', 'CT006', 'Bàn thắng', 52),
('TD003', 'CT007', 'Bàn thắng', 76),
('TD004', 'CT006', 'Bàn thắng', 61),
('TD005', 'CT008', 'Bàn thắng', 34),
('TD005', 'CT009', 'Bàn thắng', 45),
('TD005', 'CT009', 'Bàn thắng', 70),
('TD005', 'CT009', 'Bàn thắng', 90),
('TD006', 'CT006', 'Bàn thắng', 35),
('TD006', 'CT009', 'Bàn thắng', 80);
 
-- Bảng xếp hạng V.League
INSERT INTO BangXepHang VALUES
('VL1', 'HAN',  3, 2, 0, 1, 6, 3, 6),
('VL1', 'HCMC', 3, 2, 0, 1, 5, 2, 6),
('VL1', 'MAN',  3, 0, 0, 3, 1, 7, 0);
 
-- Bảng xếp hạng EPL
INSERT INTO BangXepHang VALUES
('EPL', 'MCI', 2, 1, 1, 0, 4, 1, 4),
('EPL', 'ARS', 2, 0, 0, 2, 0, 2, 0),
('EPL', 'MNU', 2, 0, 0, 2, 1, 4, 0);

INSERT INTO TAIKHOAN (TenDangNhap, MatKhau, Email, HoTen, VaiTro) VALUES
('admin',     '123456', 'admin@bongda.vn',   'Quản trị viên',   'Admin'),
('nguyenvana','123456', 'vana@gmail.com',    'Nguyễn Văn A',    'User'),
('tranthib',  '123456', 'thib@gmail.com',    'Trần Thị B',      'User'),
('levanc',    '123456', 'vanc@gmail.com',    'Lê Văn C',        'User');


-- ---------------------------------------------------------------------------------------------------------
USE QuanLyBongDa;
SELECT * FROM TAIKHOAN;

-- 1. Danh sách cầu thủ + CLB hiện tại
 SELECT ct.HoTen, ct.ViTri, clb.TenCLB, hd.SoAo
 FROM CauThu ct
 JOIN HopDong hd ON ct.MaCT = hd.MaCT
 JOIN CauLacBo clb ON hd.MaCLB = clb.MaCLB
 WHERE CURDATE() BETWEEN hd.NgayBatDau AND hd.NgayKetThuc;
 
-- 2. Bảng xếp hạng theo giải
 SELECT clb.TenCLB, bxh.SoTran, bxh.Thang, bxh.Hoa, bxh.Thua,
        bxh.BanThang, bxh.BanThua, (bxh.BanThang - bxh.BanThua) AS HieuSo, bxh.Diem
 FROM BangXepHang bxh
 JOIN CauLacBo clb ON bxh.MaCLB = clb.MaCLB
 WHERE bxh.MaGiai = 'VL1'
 ORDER BY bxh.Diem DESC, (bxh.BanThang - bxh.BanThua) DESC;
 
-- 3. Vua phá lưới
 SELECT ct.HoTen, clb.TenCLB, COUNT(*) AS SoBanThang
 FROM SuKienTran sk
 JOIN CauThu ct ON sk.MaCT = ct.MaCT
 JOIN HopDong hd ON ct.MaCT = hd.MaCT
 JOIN CauLacBo clb ON hd.MaCLB = clb.MaCLB
 WHERE sk.LoaiSuKien = 'Bàn thắng'
 GROUP BY ct.MaCT, ct.HoTen, clb.TenCLB -- gom theo từng cầu thủ
 ORDER BY SoBanThang DESC; -- sắp xếp ai ghi nhiều nhất lên đầu
 
-- ASC = tăng dần (mặc định)
-- DESC = giảm dần
 
-- 4. Lịch thi đấu
 SELECT td.NgayThi, clbn.TenCLB AS DoiNha, td.BanThangNha,
        td.BanThangKhach, clbk.TenCLB AS DoiKhach, gd.TenGiai
 FROM TranDau td
 JOIN CauLacBo clbn ON td.MaCLBNha = clbn.MaCLB
 JOIN CauLacBo clbk ON td.MaCLBKhach = clbk.MaCLB
 JOIN GiaiDau gd ON td.MaGiai = gd.MaGiai
 ORDER BY td.NgayThi;