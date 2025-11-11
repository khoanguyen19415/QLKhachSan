-- TẠO DATABASE
CREATE DATABASE QLKhachSan;
GO
USE QLKhachSan;
GO

-- ======================
-- BẢNG TÀI KHOẢN
-- ======================
CREATE TABLE TaiKhoan (
    MaTK INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap NVARCHAR(50) UNIQUE NOT NULL,
    MatKhau NVARCHAR(100) NOT NULL,
    ChucVu NVARCHAR(20) CHECK (ChucVu IN (N'Admin', N'KhachHang')) NOT NULL DEFAULT N'KhachHang'
);
GO

-- ======================
-- BẢNG KHÁCH HÀNG
-- ======================
CREATE TABLE KhachHang (
    MaKH INT IDENTITY(1,1) PRIMARY KEY,
    MaTK INT NULL,
    HoTen NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    SoDienThoai NVARCHAR(15),
    Email NVARCHAR(100),
    FOREIGN KEY (MaTK) REFERENCES TaiKhoan(MaTK) ON DELETE SET NULL
);
GO

-- ======================
-- BẢNG PHÒNG
-- ======================
CREATE TABLE Phong (
    MaPhong INT IDENTITY(1,1) PRIMARY KEY,
    TenPhong NVARCHAR(100) NOT NULL,
    LoaiPhong NVARCHAR(50) NOT NULL,
    Gia DECIMAL(18,2) NOT NULL,
    MoTa NVARCHAR(255),
    TrangThai NVARCHAR(20)
        
);
GO

-- ======================
-- BẢNG ẢNH PHÒNG
-- ======================
CREATE TABLE PhongAnh (
    MaAnh INT IDENTITY(1,1) PRIMARY KEY,
    MaPhong INT NOT NULL,
    DuongDanAnh NVARCHAR(255) NOT NULL,
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong) ON DELETE CASCADE
);
GO

-- ======================
-- BẢNG CHI TIẾT PHÒNG
-- ======================
CREATE TABLE ChiTietPhong (
    MaCTP INT IDENTITY	(1,1) PRIMARY KEY,
    MaPhong INT NOT NULL,
    TienNghi NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong) ON DELETE CASCADE
);
GO

-- ======================
-- BẢNG ĐẶT PHÒNG (ĐƠN CHÍNH)
-- ======================
CREATE TABLE DatPhong (
    MaDatPhong INT IDENTITY(1,1) PRIMARY KEY,
    MaKH INT NOT NULL,
    NgayDat DATETIME DEFAULT GETDATE(),
    NgayNhan DATE NOT NULL,
    NgayTra DATE NOT NULL,
    TongTien DECIMAL(18,2) NULL,
    TrangThai NVARCHAR(20)
        
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);
GO

-- ======================
-- BẢNG CHI TIẾT ĐẶT PHÒNG
-- ======================
CREATE TABLE ChiTietDatPhong (
    MaCTDP INT IDENTITY(1,1) PRIMARY KEY,
    MaDatPhong INT NOT NULL,
    MaPhong INT NOT NULL,
    Gia DECIMAL(18,2) NOT NULL,
    GhiChu NVARCHAR(255),
    TrangThai NVARCHAR(20)
        
    FOREIGN KEY (MaDatPhong) REFERENCES DatPhong(MaDatPhong) ON DELETE CASCADE,
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);
GO

-- ======================
-- DỮ LIỆU MẪU
-- ======================

-- TÀI KHOẢN
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, ChucVu)
VALUES 
(N'admin', N'123456', N'Admin'),
(N'kha1', N'123', N'KhachHang'),
(N'kha2', N'123', N'KhachHang'),
(N'kha3', N'123', N'KhachHang');
GO

-- KHÁCH HÀNG
INSERT INTO KhachHang (MaTK, HoTen, DiaChi, SoDienThoai, Email)
VALUES
(2, N'Nguyễn Văn A', N'Hà Nội', '0912345678', 'vana@gmail.com'),
(3, N'Trần Thị B', N'Đà Nẵng', '0905123456', 'thib@gmail.com'),
(4, N'Lê Văn C', N'Hồ Chí Minh', '0988777666', 'vanc@gmail.com');
GO

-- PHÒNG
INSERT INTO Phong (TenPhong, LoaiPhong, Gia, MoTa, TrangThai)
VALUES
(N'Phòng Standard 101', N'Standard', 500000, N'Tiện nghi cơ bản', N'Trống'),
(N'Phòng Deluxe 102', N'Deluxe', 850000, N'Ban công view biển', N'Trống'),
(N'Phòng Suite 201', N'Suite', 1200000, N'Phòng cao cấp có phòng khách riêng', N'Trống'),
(N'Phòng VIP 301', N'VIP', 1800000, N'Phòng có bồn tắm và minibar', N'Trống'),
(N'Phòng Presidential 401', N'Presidential Suite', 3500000, N'Phòng tổng thống sang trọng', N'Trống');
GO

-- CHI TIẾT PHÒNG
INSERT INTO ChiTietPhong (MaPhong, TienNghi, MoTa)
VALUES
(1, N'Tivi', N'Màn hình phẳng 50 inch'),
(2, N'Máy lạnh', N'Điều hòa inverter'),
(3, N'Giường đôi', N'Nệm cao su non'),
(4, N'Minibar', N'Có bia, nước ngọt miễn phí'),
(5, N'Bồn tắm', N'Thiết kế cao cấp');
GO

-- ĐƠN ĐẶT PHÒNG
INSERT INTO DatPhong (MaKH, NgayNhan, NgayTra, TongTien, TrangThai)
VALUES
(1, '2025-11-15', '2025-11-17', NULL, N'Đã duyệt'),
(2, '2025-11-20', '2025-11-22', NULL, N'Chờ duyệt');
GO

-- CHI TIẾT ĐẶT PHÒNG (MỖI PHÒNG RIÊNG TRẠNG THÁI)
INSERT INTO ChiTietDatPhong (MaDatPhong, MaPhong, Gia, GhiChu, TrangThai)
VALUES
(1, 1, 500000, N'Phòng đơn', N'Chờ duyệt'),
(1, 2, 850000, N'Phòng đôi', N'Chờ duyệt'),
(2, 3, 1200000, N'Yêu cầu thêm giường phụ', N'Chờ duyệt');
GO
