package model;

public class KhachHang {
    private int maKH;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String diaChi;

    public KhachHang() {
        
    }

    public KhachHang(int maKH, String hoTen, String soDienThoai, String email, String diaChi) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    // Getters/Setters
    public int getMaKH() {
        return maKH; 
    }
    public void setMaKH(int maKH) {
        this.maKH = maKH; 
    }

    public String getHoTen() {
        return hoTen; 
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen; 
    }


    public String getSoDienThoai() {
        return soDienThoai; 
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai; 
    }

    public String getEmail() {
        return email; 
    }
    public void setEmail(String email) {
        this.email = email; 
    }

    public String getDiaChi() {
        return diaChi; 
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi; 
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH=" + maKH +
                ", hoTen='" + hoTen + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", email='" + email + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
