-- ==========================
-- TẠO DATABASE
-- ==========================
CREATE DATABASE QLKhachSan;
GO
USE QLKhachSan;
GO

-- ==========================
-- BANG TAI KHOAN
-- ==========================
CREATE TABLE TaiKhoan (
    MaTK INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap NVARCHAR(50) UNIQUE NOT NULL,
    MatKhau NVARCHAR(100) NOT NULL,
    ChucVu NVARCHAR(20) CHECK (ChucVu IN (N'Admin', N'KhachHang')) NOT NULL DEFAULT N'KhachHang'
);
GO

-- ==========================
-- BANG KHACH HANG
-- ==========================
CREATE TABLE KhachHang (
    MaKH INT IDENTITY(1,1) PRIMARY KEY,
    MaTK INT NULL,
    HoTen NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255) NULL,
    SoDienThoai NVARCHAR(15) NULL,
    Email NVARCHAR(100) NULL,
    FOREIGN KEY (MaTK) REFERENCES TaiKhoan(MaTK)
);
GO

-- ==========================
-- BANG PHONG
-- ==========================
CREATE TABLE Phong (
    MaPhong INT IDENTITY(1,1) PRIMARY KEY,
    TenPhong NVARCHAR(100) NOT NULL,
    LoaiPhong NVARCHAR(50) NOT NULL,
    Gia DECIMAL(18,2) NOT NULL,
    MoTa NVARCHAR(255),
    TrangThai NVARCHAR(20) DEFAULT N'Trống'
);
GO

--  BẢNG LƯU ẢNH PHÒNG
CREATE TABLE PhongAnh (
    MaAnh INT IDENTITY(1,1) PRIMARY KEY,
    MaPhong INT NOT NULL,
    DuongDanAnh NVARCHAR(255) NOT NULL,
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong) ON DELETE CASCADE
);
GO

-- ==========================
-- BANG CHI TIET PHONG
-- ==========================
CREATE TABLE ChiTietPhong (
    MaCTP INT IDENTITY(1,1) PRIMARY KEY,
    MaPhong INT NOT NULL,
    TienNghi NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong) ON DELETE CASCADE
);
GO

-- ==========================
-- BANG DAT PHONG
-- ==========================
CREATE TABLE DatPhong (
    MaDatPhong INT IDENTITY(1,1) PRIMARY KEY,
    MaKH INT NOT NULL,
    MaPhong INT NOT NULL,
    TenPhong NVARCHAR(100) NOT NULL,
    NgayDat DATE NOT NULL,
    NgayNhan DATE NOT NULL,
    NgayTra DATE NOT NULL,
    TrangThai NVARCHAR(20) DEFAULT N'Chờ xác nhận',
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH) ON DELETE CASCADE,
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong) ON DELETE CASCADE
);
GO

-- ====================================
-- INSERT TAI KHOAN
-- ====================================
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, ChucVu)
VALUES 
(N'admin', N'123456', N'Admin');
GO

-- ====================================
-- INSERT KHACH HANG
-- ====================================
INSERT INTO KhachHang (HoTen, DiaChi, SoDienThoai, Email)
VALUES
(N'Nguyễn Văn A', N'Hà Nội', '0912345678', 'vana@gmail.com'),
(N'Trần Thị B',  N'Đà Nẵng', '0905123456', 'thib@gmail.com'),
(N'Lê Văn C',  N'Hồ Chí Minh', '0988777666', 'vanc@gmail.com'),
(N'Phạm Thị D',  N'Cần Thơ', '0977555444', 'thid@gmail.com'),
(N'Hoàng Văn E',  N'Hải Phòng', '0966333222', 'vane@gmail.com');
GO

-- ====================================
-- INSERT PHONG
-- ====================================
INSERT INTO Phong (TenPhong, LoaiPhong, Gia, MoTa, TrangThai)
VALUES
(N'Phòng Deluxe 101', N'Deluxe', 850000, N'Phòng rộng rãi, có ban công view biển', N'Trống'),
(N'Phòng Standard 102', N'Standard', 500000, N'Phòng tiêu chuẩn, đầy đủ tiện nghi cơ bản', N'Trống'),
(N'Phòng Suite 201', N'Suite', 1200000, N'Phòng cao cấp có phòng khách riêng', N'Đã đặt'),
(N'Phòng VIP 202', N'VIP', 950000, N'Phòng cao cấp có đầy đủ mọi thứ trên đời', N'Trống'),
(N'Phòng VIP 301', N'VIP', 1800000, N'Phòng cao cấp có bồn tắm và minibar', N'Đã đặt'),
(N'Phòng VIP 302', N'VIP', 350000, N'Phòng cực cao cấp dành cho đại gia', N'Trống');
GO

-- ====================================
-- INSERT CHI TIET PHONG
-- ====================================
INSERT INTO ChiTietPhong (MaPhong, TienNghi, MoTa)
VALUES
(1, N'Tivi màn hình phẳng', N'Kết nối Netflix, YouTube'),
(2, N'Bình nước nóng', N'Dung tích 20L'),
(3, N'Minibar', N'Có sẵn nước ngọt và bia'),
(4, N'Giường đôi', N'Dành cho 4 người'),
(5, N'Loa Bluetooth', N'Hệ thống âm thanh cao cấp'),
(6, N'Wifi miễn phí', N'Tốc độ cao');
GO

-- ====================================
-- INSERT DAT PHONG
-- ====================================
INSERT INTO DatPhong (MaKH, MaPhong, TenPhong, NgayDat, NgayNhan, NgayTra, TrangThai)
VALUES
(1, 3, N'Phòng Deluxe', '2025-10-10', '2025-10-15', '2025-10-18', N'Chờ xác nhận'),
(2, 6, N'VIP', '2025-10-12', '2025-10-20', '2025-10-22', N'Chờ xác nhận'),
(3, 1, N'Phòng VIP', '2025-10-08', '2025-10-10', '2025-10-12', N'Đã trả phòng'),
(4, 5, N'Phòng Tiêu Chuẩn', '2025-09-28', '2025-09-30', '2025-10-02', N'Đang ở'),
(5, 4, N'Phòng Deluxe', '2025-10-01', '2025-10-05', '2025-10-08', N'Hủy');
GO
