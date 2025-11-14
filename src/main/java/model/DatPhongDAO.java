package model;

import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatPhongDAO {

    public List<DatPhong> getAll() {
        List<DatPhong> list = new ArrayList<>();
        String sql = "SELECT dp.MaDatPhong, dp.MaKH, dp.NgayDat, dp.NgayNhan, dp.NgayTra, dp.TrangThai, "
                + "STRING_AGG(p.TenPhong, ', ') AS TenPhong "
                + "FROM DatPhong dp "
                + "LEFT JOIN ChiTietDatPhong ctdp ON dp.MaDatPhong = ctdp.MaDatPhong "
                + "LEFT JOIN Phong p ON ctdp.MaPhong = p.MaPhong "
                + "GROUP BY dp.MaDatPhong, dp.MaKH, dp.NgayDat, dp.NgayNhan, dp.NgayTra, dp.TrangThai "
                + "ORDER BY dp.MaDatPhong ASC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DatPhong dp = new DatPhong();
                dp.setMaDatPhong(rs.getInt("MaDatPhong"));
                dp.setMaKH(rs.getInt("MaKH"));
                dp.setNgayDat(rs.getDate("NgayDat"));
                dp.setNgayNhan(rs.getDate("NgayNhan"));
                dp.setNgayTra(rs.getDate("NgayTra"));
                dp.setTrangThai(rs.getString("TrangThai"));
                dp.setTenPhong(rs.getString("TenPhong"));
                list.add(dp);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public DatPhong getById(int maDatPhong) {
        String sql = "SELECT dp.MaDatPhong, dp.MaKH, dp.NgayDat, dp.NgayNhan, dp.NgayTra, dp.TrangThai, "
                + "STRING_AGG(p.TenPhong, ', ') AS TenPhong "
                + "FROM DatPhong dp "
                + "LEFT JOIN ChiTietDatPhong ctdp ON dp.MaDatPhong = ctdp.MaDatPhong "
                + "LEFT JOIN Phong p ON ctdp.MaPhong = p.MaPhong "
                + "WHERE dp.MaDatPhong = ? "
                + "GROUP BY dp.MaDatPhong, dp.MaKH, dp.NgayDat, dp.NgayNhan, dp.NgayTra, dp.TrangThai";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDatPhong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DatPhong dp = new DatPhong();
                dp.setMaDatPhong(rs.getInt("MaDatPhong"));
                dp.setMaKH(rs.getInt("MaKH"));
                dp.setNgayDat(rs.getDate("NgayDat"));
                dp.setNgayNhan(rs.getDate("NgayNhan"));
                dp.setNgayTra(rs.getDate("NgayTra"));
                dp.setTrangThai(rs.getString("TrangThai"));
                dp.setTenPhong(rs.getString("TenPhong"));
                dp.setChiTiet(new ChiTietDatPhongDAO().getByDatPhong(dp.getMaDatPhong()));
                return dp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(DatPhong dp) {
        String sql = "INSERT INTO DatPhong (MaKH, NgayDat, NgayNhan, NgayTra, TongTien, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, dp.getMaKH());
            ps.setDate(2, new java.sql.Date(dp.getNgayDat().getTime()));
            ps.setDate(3, new java.sql.Date(dp.getNgayNhan().getTime()));
            ps.setDate(4, new java.sql.Date(dp.getNgayTra().getTime()));
            ps.setObject(5, dp.getTongTien());
            ps.setString(6, dp.getTrangThai());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        dp.setMaDatPhong(id);
                        new ChiTietDatPhongDAO().insertList(id, dp.getChiTiet());
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int maDatPhong, String trangThai) {
        String sqlDatPhong = "UPDATE DatPhong SET TrangThai = ? WHERE MaDatPhong = ?";
        String sqlChiTiet = "UPDATE ChiTietDatPhong SET TrangThai = ? WHERE MaDatPhong = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlDatPhong); PreparedStatement ps2 = conn.prepareStatement(sqlChiTiet)) {

                ps1.setString(1, trangThai);
                ps1.setInt(2, maDatPhong);
                ps1.executeUpdate();

                ps2.setString(1, trangThai);
                ps2.setInt(2, maDatPhong);
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM DatPhong";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<DatPhong> getPaged(int offset, int limit) {
        List<DatPhong> list = new ArrayList<>();
        String sql = "SELECT dp.MaDatPhong, dp.MaKH, dp.NgayNhan, dp.NgayTra, dp.TrangThai, "
                + "STRING_AGG(p.TenPhong, ', ') AS TenPhong "
                + "FROM DatPhong dp "
                + "LEFT JOIN ChiTietDatPhong ctdp ON dp.MaDatPhong = ctdp.MaDatPhong "
                + "LEFT JOIN Phong p ON ctdp.MaPhong = p.MaPhong "
                + "GROUP BY dp.MaDatPhong, dp.MaKH, dp.NgayNhan, dp.NgayTra, dp.TrangThai "
                + "ORDER BY dp.MaDatPhong DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DatPhong dp = new DatPhong();
                dp.setMaDatPhong(rs.getInt("MaDatPhong"));
                dp.setMaKH(rs.getInt("MaKH"));
                dp.setNgayNhan(rs.getDate("NgayNhan"));
                dp.setNgayTra(rs.getDate("NgayTra"));
                dp.setTrangThai(rs.getString("TrangThai"));
                dp.setTenPhong(rs.getString("TenPhong"));
                dp.setChiTiet(new ChiTietDatPhongDAO().getByDatPhong(dp.getMaDatPhong()));
                list.add(dp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DatPhong> getByKhachHang(int maKH) {
        List<DatPhong> list = new ArrayList<>();

        String sql
                = "SELECT dp.MaDatPhong, dp.MaKH, dp.NgayDat, dp.NgayNhan, dp.NgayTra, "
                + "STRING_AGG(p.TenPhong, ', ') AS TenPhong, "
                + "STRING_AGG(ctdp.TrangThai, ',') AS TrangThaiCT "
                + "FROM DatPhong dp "
                + "LEFT JOIN ChiTietDatPhong ctdp ON dp.MaDatPhong = ctdp.MaDatPhong "
                + "LEFT JOIN Phong p ON ctdp.MaPhong = p.MaPhong "
                + "WHERE dp.MaKH = ? "
                + "GROUP BY dp.MaDatPhong, dp.MaKH, dp.NgayDat, dp.NgayNhan, dp.NgayTra "
                + "ORDER BY dp.MaDatPhong DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKH);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DatPhong dp = new DatPhong();
                dp.setMaDatPhong(rs.getInt("MaDatPhong"));
                dp.setMaKH(rs.getInt("MaKH"));
                dp.setNgayDat(rs.getDate("NgayDat"));
                dp.setNgayNhan(rs.getDate("NgayNhan"));
                dp.setNgayTra(rs.getDate("NgayTra"));
                dp.setTenPhong(rs.getString("TenPhong"));

                dp.setTrangThai(rs.getString("TrangThaiCT"));

                list.add(dp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public DatPhong findExistingBooking(int maKH, Date ngayNhan, Date ngayTra) {
        DatPhong dp = null;
        String sql = "SELECT * FROM DatPhong WHERE MaKH = ? AND NgayNhan = ? AND NgayTra = ? AND TrangThai IN (N'Chờ duyệt', N'Đã duyệt', N'Đang ở')";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            ps.setDate(2, new java.sql.Date(ngayNhan.getTime()));
            ps.setDate(3, new java.sql.Date(ngayTra.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dp = new DatPhong();
                dp.setMaDatPhong(rs.getInt("MaDatPhong"));
                dp.setMaKH(rs.getInt("MaKH"));
                dp.setNgayNhan(rs.getDate("NgayNhan"));
                dp.setNgayTra(rs.getDate("NgayTra"));
                dp.setTrangThai(rs.getString("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dp;
    }

    public boolean insertChiTiet(ChiTietDatPhong ctdp) {
        String sql = "INSERT INTO ChiTietDatPhong (MaDatPhong, MaPhong, Gia, GhiChu, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ctdp.getMaDatPhong());
            ps.setInt(2, ctdp.getMaPhong());
            ps.setBigDecimal(3, java.math.BigDecimal.valueOf(ctdp.getGia()));
            ps.setString(4, ctdp.getGhiChu());
            ps.setString(5, ctdp.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
