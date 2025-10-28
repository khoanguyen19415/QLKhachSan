package model;

public class PhongAnh {
    private int maAnh;
    private int maPhong;
    private String duongDanAnh;

    public PhongAnh() {}

    public PhongAnh(int maAnh, int maPhong, String duongDanAnh) {
        this.maAnh = maAnh;
        this.maPhong = maPhong;
        this.duongDanAnh = duongDanAnh;
    }

    public int getMaAnh() {
        return maAnh;
    }

    public void setMaAnh(int maAnh) {
        this.maAnh = maAnh;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getDuongDanAnh() {
        return duongDanAnh;
    }

    public void setDuongDanAnh(String duongDanAnh) {
        this.duongDanAnh = duongDanAnh;
    }
}
