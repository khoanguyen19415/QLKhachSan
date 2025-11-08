package model;

import java.sql.*;
import java.util.*;
import util.DBConnection;

public class KhachHangDAO {

    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang ORDER BY MaKH ASC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getInt("MaKH"),
                        (rs.getObject("MaTK") != null) ? rs.getInt("MaTK") : null,
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int insertKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (MaTK, HoTen, SoDienThoai, Email, DiaChi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (kh.getMaTK() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, kh.getMaTK());
            }
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getEmail());
            ps.setString(5, kh.getDiaChi());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET MaTK=?, HoTen=?, SoDienThoai=?, Email=?, DiaChi=? WHERE MaKH=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            if (kh.getMaTK() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, kh.getMaTK());
            }
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getEmail());
            ps.setString(5, kh.getDiaChi());
            ps.setInt(6, kh.getMaKH());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public KhachHang getById(int maKH) {
        String sql = "SELECT * FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                            rs.getInt("MaKH"),
                            (rs.getObject("MaTK") != null) ? rs.getInt("MaTK") : null,
                            rs.getString("HoTen"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("DiaChi")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHang getByMaTK(int maTK) {
        String sql = "SELECT * FROM KhachHang WHERE MaTK = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTK);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new KhachHang(
                            rs.getInt("MaKH"),
                            rs.getInt("MaTK"),
                            rs.getString("HoTen"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("DiaChi")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertKhachHangWithMaTK(KhachHang kh, int maTK) {
        String sql = "INSERT INTO KhachHang (MaTK, HoTen, SoDienThoai, Email, DiaChi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTK);
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getEmail());
            ps.setString(5, kh.getDiaChi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteKhachHang(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHang> searchKhachHang(String keyword) {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE HoTen LIKE ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new KhachHang(
                            rs.getInt("MaKH"),
                            (rs.getObject("MaTK") != null) ? rs.getInt("MaTK") : null,
                            rs.getString("HoTen"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("DiaChi")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int countAll() {
    String sql = "SELECT COUNT(*) FROM KhachHang";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) return rs.getInt(1);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

public List<KhachHang> getPaged(int offset, int limit) {
    List<KhachHang> list = new ArrayList<>();
    String sql = "SELECT * FROM KhachHang ORDER BY MaKH ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, offset);
        ps.setInt(2, limit);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getInt("MaKH"),
                        (rs.getObject("MaTK") != null) ? rs.getInt("MaTK") : null,
                        rs.getString("HoTen"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi")
                ));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

}
