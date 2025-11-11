package model;

import java.util.Date;
import java.util.List;

public class DatPhong {

    private int maDatPhong;
    private int maKH;
    private Date ngayDat;
    private Date ngayNhan;
    private Date ngayTra;
    private Double tongTien; // có thể null
    private String trangThai;
    private List<ChiTietDatPhong> chiTiet; // danh sách phòng trong đơn

    private String tenPhong;

    public DatPhong() {
    }

    // getters / setters
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

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public List<ChiTietDatPhong> getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(List<ChiTietDatPhong> chiTiet) {
        this.chiTiet = chiTiet;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
}
