package model;

public class ChiTietDatPhong {

    private int maCTDP;
    private int maDatPhong;
    private int maPhong;
    private double gia;
    private String ghiChu;
    private String trangThai;
    private String tenPhong;

    public ChiTietDatPhong() {
    }

    public ChiTietDatPhong(int maCTDP, int maDatPhong, int maPhong, double gia, String ghiChu, String trangThai, String tenPhong) {
        this.maCTDP = maCTDP;
        this.maDatPhong = maDatPhong;
        this.maPhong = maPhong;
        this.gia = gia;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
        this.tenPhong = tenPhong;
    }

    // getters / setters
    public int getMaCTDP() {
        return maCTDP;
    }

    public void setMaCTDP(int maCTDP) {
        this.maCTDP = maCTDP;
    }

    public int getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(int maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
}
