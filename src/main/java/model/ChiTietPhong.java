package model;

public class ChiTietPhong {
    private int maCTP;
    private int maPhong;
    private String tienNghi;
    private String moTa;

    public ChiTietPhong() {}

    public ChiTietPhong(int maCTP, int maPhong, String tienNghi, String moTa) {
        this.maCTP = maCTP;
        this.maPhong = maPhong;
        this.tienNghi = tienNghi;
        this.moTa = moTa;
    }

    public int getMaCTP() {
        return maCTP;
    }

    public void setMaCTP(int maCTP) {
        this.maCTP = maCTP;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getTienNghi() {
        return tienNghi;
    }

    public void setTienNghi(String tienNghi) {
        this.tienNghi = tienNghi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
