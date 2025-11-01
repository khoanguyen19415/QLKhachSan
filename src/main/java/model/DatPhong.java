package model;

import java.sql.Date;

public class DatPhong {

    private int maDatPhong;
    private int maKH;
    private int maPhong;
    private Date ngayDat;
    private Date ngayNhan;
    private Date ngayTra;
    private String trangThai;

    public DatPhong() {
    }

    public DatPhong(int maDatPhong, int maKH, int maPhong, Date ngayDat, Date ngayNhan, Date ngayTra, String trangThai) {
        this.maDatPhong = maDatPhong;
        this.maKH = maKH;
        this.maPhong = maPhong;
        this.ngayDat = ngayDat;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
    }

    public int getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(int maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public Date getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(Date ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
