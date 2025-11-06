package model;

public class KhachHang {

    private int maKH;
    private Integer maTK; // có thể null nếu chưa liên kết tài khoản
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String diaChi;

    public KhachHang() {
    }

    public KhachHang(int maKH, Integer maTK, String hoTen, String soDienThoai, String email, String diaChi) {
        this.maKH = maKH;
        this.maTK = maTK;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    // constructor khi tạo mới (chưa có maKH)
    public KhachHang(Integer maTK, String hoTen, String soDienThoai, String email, String diaChi) {
        this.maTK = maTK;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public Integer getMaTK() {
        return maTK;
    }

    public void setMaTK(Integer maTK) {
        this.maTK = maTK;
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
        return "KhachHang{"
                + "maKH=" + maKH
                + ", maTK=" + maTK
                + ", hoTen='" + hoTen + '\''
                + ", soDienThoai='" + soDienThoai + '\''
                + ", email='" + email + '\''
                + ", diaChi='" + diaChi + '\''
                + '}';
    }
}
