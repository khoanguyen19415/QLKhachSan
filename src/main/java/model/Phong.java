package model;

public class Phong {

    private int maPhong;
    private String tenPhong;
    private String loaiPhong;
    private double gia;
    private String moTa;
    private String trangThai;

    public Phong() {
    }

    public Phong(int maPhong, String tenPhong, String loaiPhong, double gia, String moTa, String trangThai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.loaiPhong = loaiPhong;
        this.gia = gia;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
